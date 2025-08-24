package com.sergiorebelo.songz.source.lastfm;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import java.util.Optional;

@Component
public class LastfmClient {
  private final RestClient restClient;
  private final LastfmConfig config;

  public LastfmClient(LastfmConfig cfg, RestClient.Builder builder) {
    this.config = cfg;
    this.restClient = builder.baseUrl(cfg.baseUrl()).build();
  }

  @Transactional(propagation = Propagation.NOT_SUPPORTED)
  public RecentTracksResponse getRecentTracks(Long sinceEpoch) {
    System.out.println(">> Fetching recent tracks from Last.fm since: " + (sinceEpoch != null ? sinceEpoch : "latest"));
    return restClient.get()
            .uri(uri -> uri
                    .queryParam("method", "user.getrecenttracks")
                    .queryParam("user", config.user())
                    .queryParam("api_key", config.apiKey())
                    .queryParam("format", "json")
                    .queryParamIfPresent("from", Optional.ofNullable(sinceEpoch))
                    .build())
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .body(RecentTracksResponse.class);
  }
}
