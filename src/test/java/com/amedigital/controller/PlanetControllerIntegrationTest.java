package com.amedigital.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.amedigital.model.Planet;
import com.amedigital.repository.PlanetRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PlanetControllerIntegrationTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private PlanetRepository planetRepository;

	@Before
	public void setup() {

		Planet planet = new Planet();
		planet.setId("forced_id");
		planet.setName("planet_test");
		planetRepository.save(planet);
	}

	@After
	public void clean() {

		for (Planet planet : planetRepository.findByName("planet_test")) {
			planetRepository.delete(planet);
		}
	}

	@Test
	public void shouldListPlanets() {
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/v1/planets", String.class))
				.contains("\"name\":\"planet_test\"");
	}

	@Test
	public void shouldSearchPlanetsByName() {
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/v1/planets?name=planet_test",
				String.class)).contains("\"name\":\"planet_test\"");
	}

	@Test
	public void shouldSearchPlanetsByNameWithoutResults() {
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/v1/planets?name=earth", String.class))
				.contains("[]");
	}

	@Test
	public void shouldFindById() {
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/v1/planets/forced_id", String.class))
				.contains("id\":\"forced_id");
	}

	@Test
	public void shouldCreate() {

		Planet planet = new Planet();
		planet.setName("planet name");
		planet.setClimate("planet climate");
		planet.setTerrain("planet terrain");
		planet.setExternalId(5);

		ResponseEntity<String> result = restTemplate.postForEntity("http://localhost:" + port + "/v1/planets", planet,
				String.class);

		assertThat(result.getBody()).contains(
				"name\":\"planet name\",\"climate\":\"planet climate\",\"terrain\":\"planet terrain\",\"externalId\":5,\"numberOfAppearances\":3");
	}

	@Test
	public void shouldDelete() {

		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/v1/planets/forced_id", String.class))
				.contains("id\":\"forced_id");

		restTemplate.delete("http://localhost:" + port + "/v1/planets/forced_id", String.class);

		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/v1/planets/forced_id", String.class))
				.isNull();
	}
}