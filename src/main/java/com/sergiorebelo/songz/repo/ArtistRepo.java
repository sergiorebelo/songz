package com.sergiorebelo.songz.repo;

import com.sergiorebelo.songz.domain.Artist;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ArtistRepo extends JpaRepository<Artist, Long> {
    Optional<Artist> findByName(String name);
}