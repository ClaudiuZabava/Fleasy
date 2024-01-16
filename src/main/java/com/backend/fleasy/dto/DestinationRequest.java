package com.backend.fleasy.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.backend.fleasy.model.DestinationType;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Destination request", description = "Required details needed to create a new destination")
public class DestinationRequest {

	@NotBlank(message = "Name of the destination cannot be null")
	@ApiModelProperty(value = "name", required = true, notes = "The name of the destination", example = "Avatar 2", position = 1)
	private String name;

	@Enumerated(EnumType.STRING)
	@NotNull(message = "Type cannot be null")
	@ApiModelProperty(value = "type", required = true, notes = "The type of the destination", example = "D2", position = 2)
	private DestinationType type;

	public DestinationRequest() {
	}

	public DestinationRequest(@NotBlank(message = "Name of the destination cannot be null") String name,
							  @NotNull(message = "type cannot be null") DestinationType type) {
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DestinationType getType() {
		return type;
	}

	public void setType(DestinationType type) {
		this.type = type;
	}

}
