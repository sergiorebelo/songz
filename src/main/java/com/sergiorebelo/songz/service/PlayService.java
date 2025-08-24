package com.sergiorebelo.songz.service;

import com.sergiorebelo.songz.domain.Play;
import com.sergiorebelo.songz.domain.Track;
import com.sergiorebelo.songz.repo.PlayRepo;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlayService {

    private final PlayRepo playRepository;

    public PlayService(PlayRepo playRepository) {
        this.playRepository = playRepository;
    }

    public Instant lastPlayedAt(String source) {
        return playRepository.findFirstBySourceOrderByPlayedAtDesc(source).map(Play::getPlayedAt).orElse(null);
    }

    public void savePlay(Play play) {
        playRepository.save(play);
    }

    public Play recordPlay(Track track, Instant playedAt, String source) {
        if (playRepository.existsByTrackAndPlayedAt(track, playedAt)) {
            throw new IllegalArgumentException("Play already exists for track " + track.getTitle() + " at " + playedAt);
        }
        Play play = new Play();
        play.setTrack(track);
        play.setPlayedAt(playedAt);
        play.setSource(source);
        return playRepository.save(play);
    }

    public Map<String, Integer> getTopArtistsWithPlayCount(LocalDate from, LocalDate to) {
        Instant f = from.atStartOfDay(ZoneOffset.UTC).toInstant();
        Instant t = to.plusDays(1).atStartOfDay(ZoneOffset.UTC).toInstant();

        List<Map<String, Object>> results = playRepository.findTopArtistsBetween(f, t);

        return results.stream()
                //sort by plays in descending order
                .sorted((m1,m2) -> Integer.compare(
                        ((Number) m2.get("plays")).intValue(),
                        ((Number) m1.get("plays")).intValue()
                ))
                //collect as LinkedHashMap to preserve the order
                .collect(Collectors.toMap(
                        m -> (String) m.get("artist"),
                        m -> ((Number) m.get("plays")).intValue(),
                        (Integer existing, Integer replacement) ->  existing, // keep the first if duplicate key
                        LinkedHashMap::new
                ));
    }

    public boolean existsByTrackAndPlayedAt(Track track, Instant played) {
        return playRepository.existsByTrackAndPlayedAt(track, played);
    }
}