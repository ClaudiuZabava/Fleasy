package com.backend.fleasy.service;

import com.backend.fleasy.model.Airplane;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.backend.fleasy.exception.AirplaneNotFoundException;
import com.backend.fleasy.repository.AirplaneRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AirplaneServiceTest {

	@InjectMocks
	private AirplaneService airplaneService;

	@Mock
	private AirplaneRepository airplaneRepository;
	
	@Test
    void airplane_create() {
		Airplane airplane = new Airplane();
        airplane.setName("1A");
        airplane.setCapacity(2);
       
        
        Airplane savedAirplane = new Airplane();
        savedAirplane.setName("1A");
        savedAirplane.setCapacity(2);
        savedAirplane.setId(1);
        when(airplaneRepository.save(airplane)).thenReturn(savedAirplane);

        // Act
        Airplane result = airplaneService.createairplane(airplane);

        // Assert
        assertNotNull(result);
        assertEquals(savedAirplane.getId(), result.getId());
        assertEquals(savedAirplane.getName(), result.getName());
        assertEquals(airplane.getName(), result.getName());
        
        assertEquals(savedAirplane.getCapacity(), result.getCapacity());
        assertEquals(airplane.getCapacity(), result.getCapacity());
        
        verify(airplaneRepository).save(airplane);
    }

	@Test
	void whenairplaneDoesntExists_getairplane_throwsairplaneNotFoundException() {

		// Act
		AirplaneNotFoundException exception = assertThrows(AirplaneNotFoundException.class, () -> airplaneService.getairplane(1));

		// Assert
		assertEquals("Airplane with id 1 doesn't exist ", exception.getMessage());

	}

	@Test
	void whenairplaneExists_getairplane_returnsTheairplane() {
		// Arrange
		Airplane airplane = new Airplane();
		airplane.setId(1);
		when(airplaneRepository.findById(1)).thenReturn(Optional.of(airplane));

		// Act
		Airplane result = airplaneService.getairplane(1);

		// Assert
		assertNotNull(result);
		assertEquals(airplane.getId(), result.getId());
	}
}
