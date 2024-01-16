package com.backend.fleasy.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.backend.fleasy.model.Passenger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.backend.fleasy.exception.DuplicateUserException;
import com.backend.fleasy.exception.PassengerNotFoundException;
import com.backend.fleasy.repository.PassengerRepository;

@ExtendWith(MockitoExtension.class)
public class PassengerServiceTest {

	@InjectMocks
	private PassengerService passengerService;

	@Mock
	private PassengerRepository passengerRepository;

	@Test
	void whenUserAlreadyExists_create_throwsDuplicateUserException() {
		// Arrange
		Passenger passenger = new Passenger();
		passenger.setEmail("testbot26@gmail.com");
		passenger.setFirstName("test-Bot");
		passenger.setLastName("bot");
		when(passengerRepository.findByEmail(passenger.getEmail())).thenReturn(Optional.of(passenger));

		// Act
		DuplicateUserException exception = assertThrows(DuplicateUserException.class,
				() -> passengerService.createUser(passenger));

		// Assert
		assertEquals("A user with the same email already exists.", exception.getMessage());
		verify(passengerRepository, times(0)).save(passenger);

	}

	@Test
	void whenUserDoesntExist_create() {
		Passenger passenger = new Passenger();
		passenger.setEmail("testbot26@gmail.com");
		passenger.setFirstName("test-Bot");
		passenger.setLastName("bot");

		Passenger savedPassenger = new Passenger();
		savedPassenger.setEmail("testbot26@gmail.com");
		savedPassenger.setFirstName("test-Bot");
		savedPassenger.setLastName("bot");
		savedPassenger.setId(1);

		when(passengerRepository.save(passenger)).thenReturn(savedPassenger);

		// Act
		Passenger result = passengerService.createUser(passenger);

		// Assert
		assertNotNull(result);
		assertEquals(savedPassenger.getId(), result.getId());
		assertEquals(savedPassenger.getFirstName(), result.getFirstName());
		assertEquals(passenger.getFirstName(), result.getFirstName());

		assertEquals(savedPassenger.getLastName(), result.getLastName());
		assertEquals(passenger.getLastName(), result.getLastName());

		assertEquals(savedPassenger.getEmail(), result.getEmail());
		assertEquals(passenger.getEmail(), result.getEmail());

		verify(passengerRepository).save(passenger);
	}

	@Test
	void whenUserDoesntExists_getpassenger_throwsUserNotFoundException() {

		// Act
		PassengerNotFoundException exception = assertThrows(PassengerNotFoundException.class, () -> passengerService.getpassenger(1));

		// Assert
		assertEquals("Passenger with id 1 doesn't exist ", exception.getMessage());

	}

	@Test
	void whenUserExists_getpassenger_returnsTheUser() {
		// Arrange
		Passenger passenger = new Passenger();
		passenger.setEmail("testbot26@gmail.com");
		passenger.setFirstName("test-Bot");
		passenger.setLastName("bot");
		passenger.setId(1);
		when(passengerRepository.findById(1)).thenReturn(Optional.of(passenger));

		// Act
		Passenger result = passengerService.getpassenger(1);

		// Assert
		assertNotNull(result);
		assertEquals(passenger.getId(), result.getId());
	}

	@Test
	public void delete_user() {
		Passenger passenger = new Passenger(1, "testbot26@gmail.com", "bot", "test-Bot");
		when(passengerRepository.findById(1)).thenReturn(Optional.of(passenger));

		passengerService.deleteUser(1);

		when(passengerRepository.findById(1)).thenReturn(Optional.empty());
		// Act
		PassengerNotFoundException exception = assertThrows(PassengerNotFoundException.class, () -> passengerService.getpassenger(1));

		// Assert
		assertEquals("Passenger with id 1 doesn't exist ", exception.getMessage());

	}

}
