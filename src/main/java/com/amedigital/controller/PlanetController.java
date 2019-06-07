package com.amedigital.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.amedigital.model.Planet;
import com.amedigital.repository.PlanetRepository;

@RestController
public class PlanetController {

	Logger logger = LoggerFactory.getLogger(PlanetController.class);

	@Autowired
	private PlanetRepository planetRepository;

	@RequestMapping(value = "/planets", method = RequestMethod.GET)
	public List<Planet> search(@RequestParam(value = "name", defaultValue = "") String name) {

		if (name.equals("")) {
			logger.info("Listing planets");
			return planetRepository.findAll();
		}

		logger.info("Searching planet with name = {}", name);
		return planetRepository.findByName(name);
	}

	@RequestMapping(value = "/planets/{id}", method = RequestMethod.GET)
	public Planet findById(@PathVariable("id") String id) {

		logger.info("Finding planet by id = {}", id);
		return planetRepository.findById(id).get();
	}

	@RequestMapping(value = "/planets", method = RequestMethod.POST)
	public Planet create(@RequestBody Planet planet) {

		logger.info("Creating planet {}", planet);
		planetRepository.save(planet);
		return findById(planet.getId());
	}

	@RequestMapping(value = "/planets/{id}", method = RequestMethod.DELETE)
	public List<Planet> delete(@PathVariable("id") String id) {

		logger.info("Deleting planet with id = {}", id);
		planetRepository.deleteById(id);
		return search("");
	}

}