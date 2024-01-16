package com.backend.fleasy.model;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "booking")
public class Booking {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private Integer noPersons;
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private Date dateRegistered;

	@ManyToOne
	@JoinColumn(name = "passenger_id", nullable = false)
	private Passenger passenger;

	@ManyToMany(targetEntity = Seat.class)
	@JoinTable(name = "reserved_seat", joinColumns = { @JoinColumn(name = "booking_id") }, inverseJoinColumns = {
			@JoinColumn(name = "seat_id") })
	private List<Seat> reservedSeats;

	@OneToOne//(cascade = CascadeType.ALL) 
	@JoinColumn(name = "flight_id", referencedColumnName = "id")
	private Flight flight;

	public Booking() {
	}

	public Booking(Integer noPersons, Date dateRegistered) {
		this.noPersons = noPersons;
		this.dateRegistered = dateRegistered;
	}

	@Override
	public String toString() {
		return "Booking [id=" + id + ", noPersons=" + noPersons + ", dateRegistered=" + dateRegistered + "]";
	}

	public List<Seat> getReservedSeats() {
		return reservedSeats;
	}

	public void setReservedSeats(List<Seat> reservedSeats) {
		this.reservedSeats = reservedSeats;
	}

	public Flight getflight() {
		return flight;
	}

	public void setflight(Flight flight) {
		this.flight = flight;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getNoPersons() {
		return noPersons;
	}

	public void setNoPersons(Integer noPersons) {
		this.noPersons = noPersons;
	}

	public Passenger getpassenger() {
		return passenger;
	}

	public void setpassenger(Passenger passenger) {
		this.passenger = passenger;
	}

	public Date getDateRegistered() {
		return dateRegistered;
	}

	public void setDateRegistered(Date dateRegistered) {
		this.dateRegistered = dateRegistered;
	}

}
