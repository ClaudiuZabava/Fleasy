package com.backend.fleasy.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.backend.fleasy.exception.DestinationNotFoundException;
import com.backend.fleasy.model.Destination;
import com.backend.fleasy.repository.DestinationRepository;

@Service
public class DestinationService {

	private DestinationRepository destinationRepository;

	public DestinationService(DestinationRepository destinationRepository) {
		this.destinationRepository = destinationRepository;
	}

	public Destination createdestination(Destination destination) {
		return destinationRepository.save(destination);
	}

	public Destination getdestination(Integer id) {
		Optional<Destination> destinationOptional = destinationRepository.findById(id);
		if (destinationOptional.isPresent()) {
			return destinationOptional.get();
		} else {
			throw new DestinationNotFoundException(id);
		}
	}

	public List<Destination> getAlldestinations() {
		return destinationRepository.findAll();
	}
}
