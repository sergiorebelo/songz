package com.sergiorebelo.songz.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name = "track",
        uniqueConstraints = @UniqueConstraint(name = "uq_track", columnNames = {"title","artist_id"}))
@Getter @Setter
public class Track {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @ManyToOne(optional = false) @JoinColumn(name = "artist_id")
    private Artist artist;
}