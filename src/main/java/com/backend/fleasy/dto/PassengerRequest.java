package com.backend.fleasy.dto;

import javax.validation.constraints.*;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Passenger request", description = "Required details needed to create a new user")
public class PassengerRequest {

	@NotBlank(message = "Email cannot be null")
	@ApiModelProperty(value = "email", required = true, notes = "The email of the user", example = "test1@gmail.com", position = 1)
	private String email;

	@NotBlank(message = "Last name cannot be null")
	@ApiModelProperty(value = "lastName", required = true, notes = "The last name of the user", example = "test1", position = 2)
	private String lastName;

	@NotBlank(message = "First name cannot be null")
	@ApiModelProperty(value = "firstName", required = true, notes = "The first name of the user", example = "TestName", position = 3)
	private String firstName;

	public PassengerRequest() {

	}

	public PassengerRequest(@NotBlank(message = "Email cannot be null") String email,
							@NotBlank(message = "Last name cannot be null") String lastName,
							@NotBlank(message = "First name cannot be null") String firstName) {
		this.email = email;
		this.lastName = lastName;
		this.firstName = firstName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

}
