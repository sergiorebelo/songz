
package com.sergiorebelo.songz.source.lastfm;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TrackItem(
        @JsonProperty("name") String title,
        @JsonProperty("artist") Map<String,String> artistObj,
        @JsonProperty("date") Map<String,String> dateObj
) {
    public String artistName() { return artistObj != null ? artistObj.get("#text") : "Unknown"; }
    public Instant playedAt() {
        if (dateObj == null) return null; // now playing
        String uts = dateObj.get("uts");
        return Instant.ofEpochSecond(Long.parseLong(uts));
    }
}
