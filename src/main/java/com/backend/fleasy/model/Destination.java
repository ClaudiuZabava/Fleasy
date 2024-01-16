package com.backend.fleasy.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "destination")
public class Destination {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name;
	@Enumerated(EnumType.STRING)
	private DestinationType type;

	@OneToOne(mappedBy = "destination")
	@JsonIgnore 
	private Flight flight;

	public Destination() {
	}

	public Destination(String name, DestinationType type) {
		this.name = name;
		this.type = type;
	}

	@Override
	public String toString() {
		return "Destination [iId=" + id + ", name=" + name + ", type=" + type + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DestinationType getType() {
		return type;
	}

	public void setType(DestinationType type) {
		this.type = type;
	}

	public Flight getflight() {
		return flight;
	}

	public void setflight(Flight flight) {
		this.flight = flight;
	}

}
