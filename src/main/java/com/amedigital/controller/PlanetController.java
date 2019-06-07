package com.amedigital.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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

	@RequestMapping(value = "/planets", method = RequestMethod.GET)
	List<Planet> search(@RequestParam(value = "name", defaultValue = "") String name) {

		if (name.equals("")) {
			logger.info("Listing planets");
			return planetRepository.findAll();
		}

		logger.info("Searching planet with name = {}", name);
		return planetRepository.findByName(name);
	}

	@RequestMapping(value = "/planets/{id}", method = RequestMethod.GET)
	Planet findById(@PathVariable("id") String id) {

		logger.info("Finding planet by id = {}", id);
		return planetRepository.findById(id).get();
	}

	@RequestMapping(value = "/planets", method = RequestMethod.POST)
	Planet create(@RequestBody Planet planet, @RequestHeader(value = "User-Agent") String userAgent) {

		System.out.println(userAgent);

		planet.setNumberOfAppearances(swapiClient.getPlanetInfo(planet.getExternalId()).getFilms().size());
		logger.info("Creating planet {}", planet);
		planetRepository.save(planet);
		return findById(planet.getId());
	}

	@RequestMapping(value = "/planets/{id}", method = RequestMethod.DELETE)
	List<Planet> delete(@PathVariable("id") String id) {

		logger.info("Deleting planet with id = {}", id);
		planetRepository.deleteById(id);
		return search("");
	}

}