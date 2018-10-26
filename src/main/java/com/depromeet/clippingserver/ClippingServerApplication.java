package com.depromeet.clippingserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ClippingServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClippingServerApplication.class, args);
	}
}
