package com.example.b2capi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class B2cApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(B2cApiApplication.class, args);
	}

}
