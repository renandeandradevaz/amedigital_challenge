package com.amedigital.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.amedigital.client.SwapiClient;
import com.amedigital.model.Planet;
import com.amedigital.repository.PlanetRepository;

@RestController
public class PlanetController {

	Logger logger = LoggerFactory.getLogger(PlanetController.class);

	@Autowired
	private PlanetRepository planetRepository;

	@Autowired
	private SwapiClient swapiClient;

	@RequestMapping(value = "/v1/planets", method = RequestMethod.GET)
	List<Planet> search(@RequestParam(value = "name", defaultValue = "") String name) {

		if (name.equals("")) {
			logger.info("Listing planets");
			return planetRepository.findAll();
		}

		logger.info("Searching planet with name = {}", name);
		return planetRepository.findByName(name);
	}

	@RequestMapping(value = "/v1/planets/{id}", method = RequestMethod.GET)
	Planet findById(@PathVariable("id") String id) {

		logger.info("Finding planet by id = {}", id);
		Optional<Planet> optionalPlanet = planetRepository.findById(id);

		if (optionalPlanet.isPresent()) {
			return optionalPlanet.get();
		}
		return null;
	}

	@RequestMapping(value = "/v1/planets", method = RequestMethod.POST)
	Planet create(@RequestBody Planet planet) {

		planet.setNumberOfAppearances(swapiClient.getPlanetInfo(planet.getExternalId()).getFilms().size());
		logger.info("Creating planet {}", planet);
		planetRepository.save(planet);
		return findById(planet.getId());
	}

	@RequestMapping(value = "/v1/planets/{id}", method = RequestMethod.DELETE)
	List<Planet> delete(@PathVariable("id") String id) {

		logger.info("Deleting planet with id = {}", id);
		planetRepository.deleteById(id);
		return search("");
	}

}
