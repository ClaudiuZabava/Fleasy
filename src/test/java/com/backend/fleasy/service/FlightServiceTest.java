package com.backend.fleasy.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Optional;

import com.backend.fleasy.model.*;
import com.backend.fleasy.repository.FlightRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.backend.fleasy.exception.FlightNotFoundException;
import com.backend.fleasy.model.Airplane;

@ExtendWith(MockitoExtension.class)
public class FlightServiceTest {

	@InjectMocks
	private FlightService flightService;

	@Mock
	private FlightRepository flightRepository;

	@Test
	void whenflightDoesntExist_create() throws ParseException {
		Destination destination = new Destination();
		destination.setName("1A");
		destination.setType(DestinationType.D2);

		Airplane airplane = new Airplane();
		airplane.setName("1A");
		airplane.setCapacity(2);

		Schedule schedule = new Schedule();
		schedule.setDate(new SimpleDateFormat("dd-MM-yyyy").parse("05-07-2023"));
		schedule.setEndingHour(LocalTime.of(12, 12));
		schedule.setStartingHour(LocalTime.of(10, 10));

		Flight flight = new Flight();
		flight.setdestination(destination);
		flight.setairplane(airplane);
		flight.setSchedule(schedule);

		/// ----------------------------------------------
		Destination savedDestination = new Destination();
		savedDestination.setName("1A");
		savedDestination.setType(DestinationType.D2);
		savedDestination.setId(1);

		Airplane savedAirplane = new Airplane();
		savedAirplane.setName("1A");
		savedAirplane.setCapacity(2);
		savedAirplane.setId(1);

		Schedule savedSchedule = new Schedule();
		savedSchedule.setDate(new SimpleDateFormat("dd-MM-yyyy").parse("05-07-2023"));
		savedSchedule.setEndingHour(LocalTime.of(12, 12));
		savedSchedule.setStartingHour(LocalTime.of(10, 10));
		savedSchedule.setId(1);

		Flight savedFlight = new Flight();
		savedFlight.setdestination(savedDestination);
		savedFlight.setairplane(savedAirplane);
		savedFlight.setSchedule(savedSchedule);
		savedFlight.setId(1);

		when(flightRepository.save(flight)).thenReturn(savedFlight);

		// Act
		Flight result = flightService.createflight(flight, airplane, destination);

		// Assert
		assertNotNull(result);
		assertEquals(savedFlight.getId(), result.getId());
		assertEquals(savedFlight.getdestination().getId(), result.getdestination().getId());

		assertEquals(savedFlight.getSchedule().getId(), result.getSchedule().getId());

		assertEquals(savedFlight.getairplane().getId(), result.getairplane().getId());

		verify(flightRepository).save(flight);
	}

	@Test
	void whenflightDoesntExists_getflight_throwsflightNotFoundException() {

		// Act
		FlightNotFoundException exception = assertThrows(FlightNotFoundException.class,
				() -> flightService.getflight(1));

		// Assert
		assertEquals("Flight with id 1 doesn't exist ", exception.getMessage());

	}

	@Test
	void whenflightExists_getflight_returnsTheflight() throws ParseException {
		// Arrange
		Destination destination = new Destination();
		destination.setName("1A");
		destination.setType(DestinationType.D2);

		Airplane airplane = new Airplane();
		airplane.setName("1A");
		airplane.setCapacity(2);

		Schedule schedule = new Schedule();
		schedule.setDate(new SimpleDateFormat("dd-MM-yyyy").parse("05-07-2023"));
		schedule.setEndingHour(LocalTime.of(12, 12));
		schedule.setStartingHour(LocalTime.of(10, 10));

		Flight flight = new Flight();
		flight.setdestination(destination);
		flight.setairplane(airplane);
		flight.setSchedule(schedule);

		when(flightRepository.findById(1)).thenReturn(Optional.of(flight));

		// Act
		Flight result = flightService.getflight(1);

		// Assert
		assertNotNull(result);
		assertEquals(flight.getId(), result.getId());
	}

	@Test
	public void delete_flight() throws ParseException {
		Destination destination = new Destination();
		destination.setName("1A");
		destination.setType(DestinationType.D2);

		Airplane airplane = new Airplane();
		airplane.setName("1A");
		airplane.setCapacity(2);

		Schedule schedule = new Schedule();
		schedule.setDate(new SimpleDateFormat("dd-MM-yyyy").parse("05-07-2023"));
		schedule.setEndingHour(LocalTime.of(12, 12));
		schedule.setStartingHour(LocalTime.of(10, 10));

		Flight flight = new Flight();
		flight.setdestination(destination);
		flight.setairplane(airplane);
		flight.setSchedule(schedule);

		when(flightRepository.findById(1)).thenReturn(Optional.of(flight));

		flightService.deleteflight(1);

		when(flightRepository.findById(1)).thenReturn(Optional.empty());
		// Act
		FlightNotFoundException exception = assertThrows(FlightNotFoundException.class,
				() -> flightService.getflight(1));

		// Assert
		assertEquals("Flight with id 1 doesn't exist ", exception.getMessage());

	}
	
	@Test
	void updateSchedule_returnsNewTheSchedule() throws ParseException {
		// Arrange
		Airplane oldAirplane = new Airplane();
		oldAirplane.setName("1A");
		oldAirplane.setCapacity(2);
		
		Flight oldFlight = new Flight();
		oldFlight.setairplane(oldAirplane);
		oldFlight.setId(1);
		
		//------------------------------------
		
		Airplane newAirplane = new Airplane();
		newAirplane.setName("2A");
		newAirplane.setCapacity(3);

		Flight newFlight = new Flight();
		newFlight.setairplane(newAirplane);
		newFlight.setId(1);

		when(flightRepository.save(oldFlight)).thenReturn(newFlight);

		// Act
		Flight result = flightService.updateflightairplane(oldFlight, newAirplane);

		// Assert
		assertNotNull(result);
		assertEquals(oldFlight.getId(), result.getId());

		verify(flightRepository).save(oldFlight);

	}
}
