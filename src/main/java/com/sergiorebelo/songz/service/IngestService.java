package com.sergiorebelo.songz.service;

import com.sergiorebelo.songz.source.lastfm.LastfmClient;
import com.sergiorebelo.songz.source.lastfm.RecentTracksResponse;
import com.sergiorebelo.songz.source.lastfm.TrackItem;
import org.springframework.stereotype.Service;
import com.sergiorebelo.songz.domain.Track;

import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.logging.Logger;

@Service
public class IngestService {
    //SOURCES
    public static final String LASTFM = "lastfm";
    private final LastfmClient lastfmClient;
    //SERVICES
    private final PlayService playService;
    private final TrackService trackService;
    private final Logger log = Logger.getLogger(IngestService.class.getName());


    public IngestService(LastfmClient lastfmClient, PlayService playService, TrackService trackService) {
        this.lastfmClient = lastfmClient; this.playService = playService; this.trackService = trackService;
    }

    public int lastfmIngest(Instant since) {
        Long fromEpoch = since != null ? since.getEpochSecond() : null;
        RecentTracksResponse resp = lastfmClient.getRecentTracks(fromEpoch);
        return processTracksResponse(resp);
    }

    @Transactional
    protected int processTracksResponse(RecentTracksResponse resp) {
        int inserted = 0;
        for (TrackItem trackItem : resp.recenttracks().track()) {
            if (insertPlayedTrack(trackItem)) inserted++;
        }
        return inserted;
    }

    private boolean insertPlayedTrack(TrackItem trackItem) {
        Instant played = trackItem.playedAt();
        if (played == null) return false;
        Track track = trackService.findByTitleAndArtist(trackItem.title(), trackItem.artistName())
                .orElseGet(() -> trackService.createNewTrack(trackItem.artistName(), trackItem.title()));
        if (playService.existsByTrackAndPlayedAt(track, played))  return false;
        playService.recordPlay(track, played, LASTFM);
        return true;
    }

    public Instant lastIngestAt(String source) {
        return playService.lastPlayedAt(source);
    }
}