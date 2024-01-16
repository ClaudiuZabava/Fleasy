package com.backend.fleasy.model;

import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "user")
public class Passenger {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String email;
	private String lastName;
	private String firstName;

	@OneToMany(mappedBy = "passenger")
	@JsonIgnore
	private List<Booking> bookings;

	public Passenger() {
	}

	public Passenger(int id, String email, String lastName, String firstName) {
		this.id = id;
		this.email = email;
		this.lastName = lastName;
		this.firstName = firstName;
	}

	public Passenger(String email, String lastName, String firstName) {
		super();
		this.email = email;
		this.lastName = lastName;
		this.firstName = firstName;
	}

	@Override
	public String toString() {
		return "Passenger [id=" + id + ", email=" + email + ", lastName=" + lastName + ", firstName=" + firstName + "]";
	}

	public List<Booking> getbookings() {
		return bookings;
	}

	public void setbookings(List<Booking> bookings) {
		this.bookings = bookings;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

}
