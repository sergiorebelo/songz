package com.sergiorebelo.songz;

import com.sergiorebelo.songz.source.lastfm.LastfmConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableConfigurationProperties({LastfmConfig.class})
@EnableScheduling
// The @EnableConfigurationProperties annotation is used to enable support for
// configuration properties in Spring Boot. It allows the application to bind
// properties defined in the application.properties or application.yml file
// to the LastfmConfig class, which is annotated with @ConfigurationProperties.

public class SongzApplication {

	public static void main(String[] args) {
		SpringApplication.run(SongzApplication.class, args);
	}

}
