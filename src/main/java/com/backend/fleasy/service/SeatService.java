package com.backend.fleasy.service;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.Optional;

import com.backend.fleasy.model.Airplane;
import com.backend.fleasy.model.Flight;
import org.springframework.stereotype.Service;

import com.backend.fleasy.model.Booking;
import com.backend.fleasy.model.Seat;
import com.backend.fleasy.repository.SeatRepository;

@Service
public class SeatService {
	
	private SeatRepository seatRepository;

	public SeatService(SeatRepository seatRepository) {
		this.seatRepository = seatRepository;
	}

	public List<Seat> getSeatsFromList(List<Integer> seatIds) {
		List<Seat> list = new ArrayList<Seat>();
		for (Integer id : seatIds) {
			Optional<Seat> seat = seatRepository.findById(id);
			if (seat.isPresent()) {
				list.add(seat.get());
			}

		}
		return list;
	}

	public Seat createSeat(Seat seat) {
		return seatRepository.save(seat);
	}

	public Dictionary<Airplane, List<Seat>> getFreeSeats(List<Booking> bookings, Flight flight) {
		Dictionary<Airplane, List<Seat>> dictionary = new Hashtable<Airplane, List<Seat>>();
		List<Seat> allSeats = seatRepository.findAll();
		List<Seat> reservedSeats = new ArrayList<Seat>();
		for (Booking booking : bookings) {
			reservedSeats.addAll(booking.getReservedSeats());
		}
		for (Seat seat : allSeats) {
			if (seat.getairplane().getId() == flight.getairplane().getId() && !reservedSeats.contains(seat)) {
				List<Seat> l = dictionary.get(seat.getairplane());
				if (l == null) {
					l = new ArrayList<Seat>();
				}
				l.add(seat);
				dictionary.put(seat.getairplane(), l);
			}
		}
		return dictionary;
	}

	public Optional<Seat> getSeat(Integer id) {
		Optional<Seat> seatOptional = seatRepository.findById(id);
		if (seatOptional.isPresent()) {
			return seatOptional;
		} else {
			return null;
		}
	}

	public List<Seat> createSeats(Airplane airplane) {
		List<Seat> seats = new ArrayList<Seat>();
		for (int i = 1; i <= airplane.getCapacity(); i++) {
			Seat seat = new Seat(i);
			seat.setairplane(airplane);
			seats.add(createSeat(seat));
		}
		return seats;
	}
}
