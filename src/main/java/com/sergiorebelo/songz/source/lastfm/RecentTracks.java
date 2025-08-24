package com.sergiorebelo.songz.source.lastfm;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RecentTracks(
        @JsonProperty("track") List<TrackItem> track
) {}