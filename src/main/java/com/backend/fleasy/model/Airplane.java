package com.backend.fleasy.model;

import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "airplane")
public class Airplane {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name;
	private int capacity;

	@OneToMany(mappedBy = "airplane", cascade = CascadeType.ALL) 
	//@JsonIgnore
	private List<Seat> seats;

	@OneToMany(mappedBy = "airplane")
	@JsonIgnore
	private List<Flight> flights;

	public Airplane() {
	}

	public Airplane(String name, int capacity) {
		super();
		this.name = name;
		this.capacity = capacity;
	}

	@Override
	public String toString() {
		return "Airplane [id=" + id + ", name=" + name + ", capacity=" + capacity + "]";
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

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public List<Seat> getSeats() {
		return seats;
	}

	public void setSeats(List<Seat> seats) {
		this.seats = seats;
	}

	public List<Flight> getflights() {
		return flights;
	}

	public void setflights(List<Flight> flights) {
		this.flights = flights;
	}

}
