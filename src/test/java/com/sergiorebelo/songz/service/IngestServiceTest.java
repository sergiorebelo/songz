package com.sergiorebelo.songz.service;

import com.sergiorebelo.songz.source.lastfm.LastfmConfig;
import org.springframework.boot.test.context.SpringBootTest;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
@SpringBootTest
@ActiveProfiles("test")
public class IngestServiceTest {

    @Autowired
    private IngestService ingestService;

    private WireMockServer wireMockServer;

    @Autowired
    private LastfmConfig config;


    @BeforeEach
    public void setup() {
        wireMockServer = new WireMockServer(8089);
        wireMockServer.start();
        WireMock.configureFor("localhost", 8089);
        setupStub();
    }

    @AfterEach
    public void teardown() {
        wireMockServer.stop();
    }

    private void setupStub() {

        stubFor(get(urlPathEqualTo("/"))
                .withQueryParam("method", equalTo("user.getrecenttracks"))
                .withQueryParam("user", equalTo(config.user()))
                .withQueryParam("api_key", equalTo(config.apikey()))
                .withQueryParam("format", equalTo("json"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBodyFile("recenttracks-response.json")
                        .withStatus(200)
                )
        );
    }

    @Test
    public void testLastfmIngest() {
        Instant since = Instant.now().minusSeconds(60 * 60 * 24 * 365); // Adjust as needed for your test

        int insertedCount = ingestService.lastfmIngest(since);

        Assertions.assertTrue(insertedCount > 0, "Should ingest some tracks: "  + insertedCount);
    }
}
