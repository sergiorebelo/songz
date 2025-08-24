package com.sergiorebelo.songz.service;

import com.sergiorebelo.songz.domain.Artist;
import com.sergiorebelo.songz.domain.Track;
import com.sergiorebelo.songz.repo.TrackRepo;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class TrackService {

    // Assuming there's a TrackRepo interface similar to ArtistRepo
    private final TrackRepo trackRepo;
    private final ArtistService artistService;
    public TrackService(TrackRepo trackRepo, ArtistService artistService) {
        this.trackRepo = trackRepo;
        this.artistService = artistService;
    }

    public Optional<Track> findByTitleAndArtist(String title, String artistName) {
        Optional<Artist> artist = artistService.findByName(artistName);
        if (artist.isEmpty()) { return Optional.empty(); }

        return trackRepo.findByTitleAndArtist(title, artist.get());
    }

    public Track createNewTrack(String artistName, String trackTitle) {

        Artist artist = artistService.findByName(artistName).orElseGet(() -> {
            // If artist does not exist, create a new one
            return artistService.newArtist(artistName);
        });

        if (trackRepo.findByTitleAndArtist(trackTitle, artist).isPresent()) {
            throw new IllegalArgumentException("Track with title " + trackTitle + " by artist " + artistName + " already exists");
        }

        Track track = new Track();
        track.setArtist(artist);
        track.setTitle(trackTitle);
        return trackRepo.save(track);
    }
}