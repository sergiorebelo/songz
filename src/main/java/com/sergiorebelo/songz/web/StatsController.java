package com.sergiorebelo.songz.web;

import com.sergiorebelo.songz.service.PlayService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/stats")
public class StatsController {

    private final PlayService playService;

    public StatsController(PlayService playService) {
        this.playService = playService;
    }
    @GetMapping("/top-artists")
    public Map<String, Integer> topArtistsWithPlayCount(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return playService.getTopArtistsWithPlayCount(from, to);
    }
}