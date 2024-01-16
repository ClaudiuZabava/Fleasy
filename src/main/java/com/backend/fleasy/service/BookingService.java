package com.backend.fleasy.service;

import java.util.List;
import java.util.Optional;

import com.backend.fleasy.model.Booking;
import com.backend.fleasy.model.Passenger;
import org.springframework.stereotype.Service;

import com.backend.fleasy.exception.BookingNotFoundException;
import com.backend.fleasy.model.Flight;
import com.backend.fleasy.model.Seat;
import com.backend.fleasy.repository.BookingRepository;

@Service
public class BookingService {
	private BookingRepository bookingRepository;

	public BookingService(BookingRepository bookingRepository) {
		this.bookingRepository = bookingRepository;
	}

	public Booking createbooking(Booking booking, Passenger passenger, Flight flight, List<Seat> seats) {
		booking.setpassenger(passenger);
		booking.setflight(flight);
		booking.setReservedSeats(seats);
		return bookingRepository.save(booking);
	}

	public List<Booking> getAllbookingsByflight(Integer flightId) {
		return bookingRepository.findAllByflightId(flightId);
	}

	public Booking getbooking(Integer id) {
		Optional<Booking> bookingOptional = bookingRepository.findById(id);
		if (bookingOptional.isPresent()) {
			return bookingOptional.get();
		} else {
			throw new BookingNotFoundException(id);
		}
	}

	public void deletebooking(Integer id) {
		Optional<Booking> bookingOptional = bookingRepository.findById(id);
		if (bookingOptional.isPresent()) {
			bookingRepository.delete(bookingOptional.get());
		} else {
			throw new BookingNotFoundException(id);
		}
	}

}
