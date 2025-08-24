package com.sergiorebelo.songz.source.lastfm;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

@ConfigurationProperties(prefix = "lastfm")
public record LastfmConfig(String baseurl, String apikey, String user) { }