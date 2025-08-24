package com.sergiorebelo.songz.service;

import com.sergiorebelo.songz.repo.ArtistRepo;
import com.sergiorebelo.songz.domain.Artist;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class ArtistService {

// Assuming there's an ArtistRepo interface similar to TrackRepo

    private final ArtistRepo artistRepo;
    public ArtistService(ArtistRepo artistRepo) {
        this.artistRepo = artistRepo;
    }
    public Optional<Artist> findByName(String name) {
        return artistRepo.findByName(name);
    }

    public Artist newArtist(String artistName) {
        if (artistRepo.findByName(artistName).isPresent()) {
            throw new IllegalArgumentException("Artist with name " + artistName + " already exists");
        }
        Artist artist = new Artist();
        artist.setName(artistName);
        return artistRepo.save(artist);
    }
}
