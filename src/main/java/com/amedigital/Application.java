package com.amedigital;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.amedigital.client.SwapiClient;

import feign.Feign;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;

@SpringBootApplication
public class Application {

	@Value("${application.swapi.url}")
	String swapiUrl;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public SwapiClient createSwapiClient() throws Exception {
		return Feign.builder().decoder(new GsonDecoder())//
				.encoder(new GsonEncoder())//
				.target(SwapiClient.class, swapiUrl);
	}
}
