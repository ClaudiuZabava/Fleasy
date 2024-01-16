package com.backend.fleasy.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.backend.fleasy.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.backend.fleasy.exception.BookingNotFoundException;
import com.backend.fleasy.model.Booking;
import com.backend.fleasy.repository.BookingRepository;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {

	@InjectMocks
	private BookingService bookingService;

	@Mock
	private BookingRepository bookingRepository;

	@Test
	void whenbookingDoesntExist_create() throws ParseException {
		Passenger passenger = new Passenger(1, "testbot26@gmail.com", "bot", "test-Bot");
		Flight flight = new Flight();
		flight.setId(1);
		Airplane airplane = new Airplane();
		airplane.setName("1A");
		airplane.setCapacity(2);
		Seat seat1 = new Seat();
		seat1.setNumber(1);
		seat1.setairplane(airplane);

		Seat seat2 = new Seat();
		seat2.setNumber(2);
		seat2.setairplane(airplane);

		List<Seat> seats = new ArrayList<Seat>();
		seats.add(seat1);
		seats.add(seat2);

		
		Booking booking = new Booking();
		booking.setDateRegistered(new SimpleDateFormat("dd-MM-yyyy").parse("05-07-2023"));
		booking.setNoPersons(2);
		booking.setflight(flight);
		booking.setReservedSeats(seats);
		booking.setpassenger(passenger);

		Booking savedBooking = new Booking();
		savedBooking.setDateRegistered(new SimpleDateFormat("dd-MM-yyyy").parse("05-07-2023"));
		savedBooking.setNoPersons(2);
		savedBooking.setpassenger(passenger);
		savedBooking.setflight(flight);
		savedBooking.setReservedSeats(seats);
		savedBooking.setId(1);

		when(bookingRepository.save(booking)).thenReturn(savedBooking);

		// Act
		Booking result = bookingService.createbooking(booking, passenger, flight, seats);

		// Assert
		assertNotNull(result);
		assertEquals(savedBooking.getId(), result.getId());
		assertEquals(savedBooking.getNoPersons(), result.getNoPersons());
		assertEquals(booking.getNoPersons(), result.getNoPersons());


		verify(bookingRepository).save(booking);
	}

	@Test
	void whenbookingDoesntExists_getbooking_throwsbookingNotFoundException() {

		// Act
		BookingNotFoundException exception = assertThrows(BookingNotFoundException.class,
				() -> bookingService.getbooking(1));

		// Assert
		assertEquals("Booking with id 1 doesn't exist ", exception.getMessage());

	}

	@Test
	void whenbookingExists_getbooking_returnsThebooking() throws ParseException {
		// Arrange
		Passenger passenger = new Passenger(1, "testbot26@gmail.com", "bot", "test-Bot");
		Flight flight = new Flight();
		flight.setId(1);
		Airplane airplane = new Airplane();
		airplane.setName("1A");
		airplane.setCapacity(2);
		Seat seat1 = new Seat();
		seat1.setNumber(1);
		seat1.setairplane(airplane);

		Seat seat2 = new Seat();
		seat2.setNumber(2);
		seat2.setairplane(airplane);

		List<Seat> seats = new ArrayList<Seat>();
		seats.add(seat1);
		seats.add(seat2);

		
		Booking booking = new Booking();
		booking.setDateRegistered(new SimpleDateFormat("dd-MM-yyyy").parse("05-07-2023"));
		booking.setNoPersons(2);
		booking.setflight(flight);
		booking.setReservedSeats(seats);
		booking.setpassenger(passenger);
		booking.setId(1);
		when(bookingRepository.findById(1)).thenReturn(Optional.of(booking));

		// Act
		Booking result = bookingService.getbooking(1);

		// Assert
		assertNotNull(result);
		assertEquals(booking.getId(), result.getId());
	}

	@Test
	public void delete_booking() throws ParseException {
		Passenger passenger = new Passenger(1, "testbot26@gmail.com", "bot", "test-Bot");
		Flight flight = new Flight();
		flight.setId(1);
		Airplane airplane = new Airplane();
		airplane.setName("1A");
		airplane.setCapacity(2);
		Seat seat1 = new Seat();
		seat1.setNumber(1);
		seat1.setairplane(airplane);

		Seat seat2 = new Seat();
		seat2.setNumber(2);
		seat2.setairplane(airplane);

		List<Seat> seats = new ArrayList<Seat>();
		seats.add(seat1);
		seats.add(seat2);

		
		Booking booking = new Booking();
		booking.setDateRegistered(new SimpleDateFormat("dd-MM-yyyy").parse("05-07-2023"));
		booking.setNoPersons(2);
		booking.setflight(flight);
		booking.setReservedSeats(seats);
		booking.setpassenger(passenger);
		booking.setId(1);
		when(bookingRepository.findById(1)).thenReturn(Optional.of(booking));

		bookingService.deletebooking(1);

		when(bookingRepository.findById(1)).thenReturn(Optional.empty());
		// Act
		BookingNotFoundException exception = assertThrows(BookingNotFoundException.class,
				() -> bookingService.getbooking(1));

		// Assert
		assertEquals("Booking with id 1 doesn't exist ", exception.getMessage());

	}

}
