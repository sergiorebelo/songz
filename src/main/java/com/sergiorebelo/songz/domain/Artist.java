package com.sergiorebelo.songz.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "artist")
@Getter
@Setter
public class Artist {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;
}