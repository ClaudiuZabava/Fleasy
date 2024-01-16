package com.backend.fleasy.model;

import javax.persistence.*;

@Entity
@Table(name = "flight")
public class Flight {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne // (cascade = CascadeType.ALL)
	@JoinColumn(name = "airplane_id", nullable = false)
	private Airplane airplane;

	@OneToOne // (cascade = CascadeType.ALL)
	@JoinColumn(name = "destination_id", referencedColumnName = "id")
	private Destination destination;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "schedule_id", nullable = false)
	private Schedule schedule;

	public Flight() {
	}

	public Flight(Schedule schedule) {
		this.schedule = schedule;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Airplane getairplane() {
		return airplane;
	}

	public void setairplane(Airplane airplane) {
		this.airplane = airplane;
	}

	public Destination getdestination() {
		return destination;
	}

	public void setdestination(Destination destination) {
		this.destination = destination;
	}

	public Schedule getSchedule() {
		return schedule;
	}

	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}

}
