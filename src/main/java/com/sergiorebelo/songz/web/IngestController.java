// IngestController.java
package com.sergiorebelo.songz.web;

import com.sergiorebelo.songz.service.IngestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Map;

@RestController
@RequestMapping("/ingest")
public class IngestController {
    private final IngestService service;
    public IngestController(IngestService service) { this.service = service; }

    @PostMapping("/lastfm")
    public ResponseEntity<Map<String,Object>> ingestLastfm() {
        Instant since = service.lastIngestAt(IngestService.LASTFM);
        int n = service.lastfmIngest(since);
        return ResponseEntity.ok(Map.of("inserted", n));
    }
}
