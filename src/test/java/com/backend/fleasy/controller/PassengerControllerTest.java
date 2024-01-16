package com.backend.fleasy.controller;

import com.backend.fleasy.dto.PassengerRequest;
import com.backend.fleasy.mapper.PassengerMapper;
import com.backend.fleasy.model.Passenger;
import com.backend.fleasy.service.BookingService;
import com.backend.fleasy.service.PassengerService;
import com.fasterxml.jackson.databind.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.test.web.servlet.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = PassengerController.class) // this tells Spring Boot to auto-configure a Spring web context
												// for integration tests for the PassengerController class
public class PassengerControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;
	@MockBean
	private PassengerService passengerService;
	@MockBean
	private BookingService resevrationService;
	@MockBean
	private PassengerMapper passengerMapper;

	@Test
	public void createUser() throws Exception {
		PassengerRequest request = new PassengerRequest("testbot26@gmail.com", "bot", "test-Bot");

		when(passengerService.createUser(any())).thenReturn(new Passenger(1, "testbot26@gmail.com", "bot", "test-Bot"));

		mockMvc.perform(
				post("/users").contentType("application/json").content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.lastName").value(request.getLastName()))
				.andExpect(jsonPath("$.firstName").value(request.getFirstName()))
				.andExpect(jsonPath("$.email").value(request.getEmail()));
	}

	@Test
	public void getpassenger() throws Exception {
		when(passengerService.getpassenger(any())).thenReturn(new Passenger(1, "testbot26@gmail.com", "bot", "test-Bot"));

		mockMvc.perform(get("/users/{id}", 1).contentType("application/json")).andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1)).andExpect(jsonPath("$.lastName").value("bot"))
				.andExpect(jsonPath("$.firstName").value("test-Bot"))
				.andExpect(jsonPath("$.email").value("testbot26@gmail.com"));

	}

	@Test
	public void deleteUser() throws Exception {

		mockMvc.perform(delete("/users/{id}", 1).contentType("application/json"))
				.andExpect(content().string("Succesfully deleted")).andExpect(status().isOk());
	}

}
