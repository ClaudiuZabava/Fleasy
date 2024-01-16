package com.backend.fleasy.model;

import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "seat")
public class Seat {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private int number;

	@JsonIgnore
	@ManyToMany(mappedBy = "reservedSeats")//, cascade = CascadeType.ALL)
	private List<Booking> reserved;

	@ManyToOne()
	@JoinColumn(name = "airplane_id", nullable = false)
	@JsonIgnore
	private Airplane airplane;

	public Seat() {
	}

	public Seat(int number) {
		this.number = number;
	}

	@Override
	public String toString() {
		return "Seat [id=" + id + ", number=" + number + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public List<Booking> getReserved() {
		return reserved;
	}

	public void setReserved(List<Booking> reserved) {
		this.reserved = reserved;
	}

	public Airplane getairplane() {
		return airplane;
	}

	public void setairplane(Airplane airplane) {
		this.airplane = airplane;
	}

}
