package com.sergiorebelo.songz.repo;



import com.sergiorebelo.songz.domain.Artist;
import com.sergiorebelo.songz.domain.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.Instant;
import java.util.Optional;

public interface TrackRepo extends JpaRepository<Track, Long> {
    Optional<Track> findByTitleAndArtist(String title, Artist artist);
}