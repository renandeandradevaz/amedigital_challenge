package com.amedigital.model;

import org.springframework.data.annotation.Id;

public class Planet {

	@Id
	private String id;

	private String name;
	private String climate;
	private String terrain;
	private Integer externalId;
	private Integer numberOfAppearances;

	@Override
	public String toString() {
		return "Planet [id=" + id + ", name=" + name + ", climate=" + climate + ", terrain=" + terrain + ", externalId="
				+ externalId + ", numberOfAppearances=" + numberOfAppearances + "]";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClimate() {
		return climate;
	}

	public void setClimate(String climate) {
		this.climate = climate;
	}

	public String getTerrain() {
		return terrain;
	}

	public void setTerrain(String terrain) {
		this.terrain = terrain;
	}

	public Integer getExternalId() {
		return externalId;
	}

	public void setExternalId(Integer externalId) {
		this.externalId = externalId;
	}

	public Integer getNumberOfAppearances() {
		return numberOfAppearances;
	}

	public void setNumberOfAppearances(Integer numberOfAppearances) {
		this.numberOfAppearances = numberOfAppearances;
	}
}
