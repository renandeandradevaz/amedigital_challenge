package com.amedigital.client;

import com.amedigital.dto.SwapiPlanetDto;

import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface SwapiClient {

	@RequestLine("GET /planets/{planetId}/")
	@Headers({ "Content-Type: application/json", "User-Agent: curl/7.47.0" }) // user-agent = curl was a workaround to connect to swapi API. The API was blocking my http requests
	SwapiPlanetDto getPlanetInfo(@Param("planetId") Integer planetId);
}