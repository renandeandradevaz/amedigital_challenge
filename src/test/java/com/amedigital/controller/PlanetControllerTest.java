package com.amedigital.controller;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.amedigital.client.SwapiClient;
import com.amedigital.dto.PlanetDto;
import com.amedigital.model.Planet;
import com.amedigital.repository.PlanetRepository;

@RunWith(SpringRunner.class)
@WebMvcTest(PlanetController.class)
public class PlanetControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private SwapiClient swapiClient;

	@MockBean
	private PlanetRepository planetRepository;

	@Test
	public void greetingShouldReturnMessageFromService() throws Exception {

		List<String> films = new ArrayList<String>();
		films.add("mock 1");
		films.add("mock 2");
		PlanetDto planetDto = new PlanetDto();
		planetDto.setFilms(films);

		Planet planet = new Planet();
		planet.setExternalId(1);

		when(planetRepository.save(planet)).thenReturn(planet);
		when(swapiClient.getPlanetInfo(1)).thenReturn(planetDto);

		this.mockMvc.perform(post("/v1/planets", planet)).andExpect(content().string(containsString("name")));
	}
}