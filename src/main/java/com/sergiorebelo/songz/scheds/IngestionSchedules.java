package com.sergiorebelo.songz.scheds;

import com.sergiorebelo.songz.service.IngestService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class IngestionSchedules {

    private final IngestService ingestService;

    public IngestionSchedules(IngestService ingestService) {
        this.ingestService = ingestService;
    }
    @Scheduled(cron = "0 24 3 * * ?") // Everyday 3:24 am
    public void scheduleLastfmIngestion() {
        int inserted = ingestService.lastfmIngest(null);
    }
}
