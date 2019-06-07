package com.amedigital.controller;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.amedigital.client.SwapiClient;
import com.amedigital.dto.SwapiPlanetDto;
import com.amedigital.model.Planet;
import com.amedigital.repository.PlanetRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

@RunWith(SpringRunner.class)
@WebMvcTest(PlanetController.class)
public class PlanetControllerMockTest {

	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private SwapiClient swapiClient;

	@MockBean
	private PlanetRepository planetRepository;

	private List<Planet> getPlanetsMock() {

		Planet planet1 = new Planet();
		planet1.setName("planet 1");
		Planet planet2 = new Planet();
		planet2.setName("planet 2");

		List<Planet> planets = new ArrayList<Planet>();
		planets.add(planet1);
		planets.add(planet2);

		return planets;
	}

	@Test
	public void shouldListPlanets() throws Exception {

		when(planetRepository.findAll()).thenReturn(getPlanetsMock());
		this.mockMvc.perform(get("/v1/planets")).andExpect(content().string(containsString("planet 2")));
	}

	@Test
	public void shouldCreatePlanet() throws Exception {

		Planet planet = new Planet();
		planet.setExternalId(1);
		planet.setName("Big planet");
		planet.setNumberOfAppearances(2);
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(planet);

		List<String> films = new ArrayList<String>();
		films.add("mock 1");
		films.add("mock 2");
		SwapiPlanetDto swapiPlanetDto = new SwapiPlanetDto();
		swapiPlanetDto.setFilms(films);

		when(swapiClient.getPlanetInfo(Matchers.<Integer>any())).thenReturn(swapiPlanetDto);
		when(planetRepository.findById(Matchers.<String>any())).thenReturn(Optional.of(planet));
		when(planetRepository.save(planet)).thenReturn(planet);

		mockMvc.perform(post("/v1/planets").contentType(APPLICATION_JSON_UTF8).content(requestJson))//
				.andExpect(content().string(containsString("name\":\"Big planet\"")))//
				.andExpect(content().string(containsString("numberOfAppearances\":2")));
	}
}