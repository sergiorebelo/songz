package com.sergiorebelo.songz.service;

import com.sergiorebelo.songz.source.lastfm.LastfmClient;
import com.sergiorebelo.songz.source.lastfm.RecentTracksResponse;
import com.sergiorebelo.songz.source.lastfm.TrackItem;
import org.springframework.stereotype.Service;
import com.sergiorebelo.songz.domain.Track;

import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class IngestService {
    //SOURCES
    public static final String LASTFM = "lastfm";
    private final LastfmClient lastfmClient;
    //SERVICES
    private final PlayService playService;
    private final TrackService trackService;

    public IngestService(LastfmClient lastfmClient, PlayService playService, TrackService trackService) {
        this.lastfmClient = lastfmClient; this.playService = playService; this.trackService = trackService;
    }

    @Transactional
    public int lastfmIngest(Instant since) {
        Long fromEpoch = since != null ? since.getEpochSecond() : null;
        RecentTracksResponse resp = lastfmClient.getRecentTracks(fromEpoch);

        int inserted = 0;
        for (TrackItem trackItem : resp.recenttracks().track()) {
            Instant played = trackItem.playedAt();
            if (played == null) continue;
            Track track = trackService.findByTitleAndArtist(trackItem.title(), trackItem.artistName())
                    .orElseGet(() -> trackService.createNewTrack(trackItem.artistName(), trackItem.title()));

            if (playService.existsByTrackAndPlayedAt(track, played)) {
                continue; // Skip if already recorded
            }

            playService.recordPlay(track, played, LASTFM);
            inserted++;

        }

        return inserted;
    }


    public Instant lastIngestAt(String source) {
        return playService.lastPlayedAt(source);
    }
}