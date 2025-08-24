package com.sergiorebelo.songz.source.lastfm;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RecentTracksResponse(
        @JsonProperty("recenttracks") RecentTracks recenttracks
) {}
