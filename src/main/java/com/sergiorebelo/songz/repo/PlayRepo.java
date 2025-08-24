package com.sergiorebelo.songz.repo;



import com.sergiorebelo.songz.domain.Play;
import com.sergiorebelo.songz.domain.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.Map;
import java.util.List;
import java.util.Optional;


public interface PlayRepo extends JpaRepository<Play, Long> {
    boolean existsByTrackAndPlayedAt(Track track, Instant playedAt);
    Optional<Play> findFirstBySourceOrderByPlayedAtDesc(String source);

    @Query("""
      select new map(a.name as artist, count(p.id) as plays)
      from Play p join p.track t2 join t2.artist a
      where p.playedAt between :from and :to
      group by a.name order by count(p.id) desc
      """)
    List<Map<String, Object>> findTopArtistsBetween(@Param("from") Instant from, @Param("to") Instant to);
}
