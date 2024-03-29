package com.backend.fleasy.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Airplane request", description = "Required details needed to create a new airplane")
public class AirplaneRequest {

	@NotBlank(message = "Name cannot be null")
	@ApiModelProperty(value = "name", required = true, notes = "The name of the airplane", example = "1A", position = 1)
	private String name;

	@NotNull(message = "Capacity cannot be null")
	@Min(1)
	@ApiModelProperty(value = "capacity", required = true, notes = "The capacity of the airplane", example = "40", position = 2)
	private int capacity;

	public AirplaneRequest() {
	}

	public AirplaneRequest(@NotBlank(message = "Name cannot be null") String name,
						   @NotNull(message = "Capacity cannot be null") @Min(1) int capacity) {
		super();
		this.name = name;
		this.capacity = capacity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

}
