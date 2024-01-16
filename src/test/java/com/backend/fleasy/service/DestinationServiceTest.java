package com.backend.fleasy.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.backend.fleasy.model.Destination;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.backend.fleasy.exception.DestinationNotFoundException;
import com.backend.fleasy.model.DestinationType;
import com.backend.fleasy.repository.DestinationRepository;


@ExtendWith(MockitoExtension.class)
public class DestinationServiceTest {

	@InjectMocks
	private DestinationService destinationService;

	@Mock
	private DestinationRepository destinationRepository;
	
	@Test
    void destination_create() {
		Destination destination = new Destination();
        destination.setName("1A");
        destination.setType(DestinationType.D2);
       
        
        Destination savedDestination = new Destination();
        savedDestination.setName("1A");
        savedDestination.setType(DestinationType.D2);
        savedDestination.setId(1);
        when(destinationRepository.save(destination)).thenReturn(savedDestination);

        // Act
        Destination result = destinationService.createdestination(destination);

        // Assert
        assertNotNull(result);
        assertEquals(savedDestination.getId(), result.getId());
        assertEquals(savedDestination.getName(), result.getName());
        assertEquals(destination.getName(), result.getName());
        
        assertEquals(savedDestination.getType(), result.getType());
        assertEquals(destination.getType(), result.getType());
        
        verify(destinationRepository).save(destination);
    }

	@Test
	void whendestinationDoesntExists_getdestination_throwsdestinationNotFoundException() {

		// Act
		DestinationNotFoundException exception = assertThrows(DestinationNotFoundException.class, () -> destinationService.getdestination(1));

		// Assert
		assertEquals("Destination with id 1 doesn't exist ", exception.getMessage());

	}

	@Test
	void whendestinationExists_getdestination_returnsThedestination() {
		// Arrange
		Destination destination = new Destination();
		destination.setId(1);
		when(destinationRepository.findById(1)).thenReturn(Optional.of(destination));

		// Act
		Destination result = destinationService.getdestination(1);

		// Assert
		assertNotNull(result);
		assertEquals(destination.getId(), result.getId());
	}
}
