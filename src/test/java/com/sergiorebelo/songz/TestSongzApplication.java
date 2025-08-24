package com.sergiorebelo.songz;

import org.springframework.boot.SpringApplication;

public class TestSongzApplication {

	public static void main(String[] args) {
		SpringApplication.from(SongzApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
