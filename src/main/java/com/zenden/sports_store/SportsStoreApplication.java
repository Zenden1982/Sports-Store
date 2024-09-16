package com.zenden.sports_store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SportsStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(SportsStoreApplication.class, args);

	}

}
