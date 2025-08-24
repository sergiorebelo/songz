package com.sergiorebelo.songz.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity
@Table(name = "play",
        uniqueConstraints = @UniqueConstraint(name = "uq_play", columnNames = {"track_id","played_at"}))
@Getter
@Setter
public class Play {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false) @JoinColumn(name = "track_id")
    private Track track;
    @Column(name = "played_at", nullable = false)
    private Instant playedAt;
    @Column(nullable = false)
    private String source;
}