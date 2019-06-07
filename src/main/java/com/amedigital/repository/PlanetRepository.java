package com.amedigital.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.amedigital.model.Planet;

public interface PlanetRepository extends MongoRepository<Planet, String> {

	public List<Planet> findByName(String name);
}
