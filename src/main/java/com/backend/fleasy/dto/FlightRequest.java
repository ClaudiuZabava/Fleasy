package com.backend.fleasy.dto;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Flight request", description = "Required details needed to create a new flight")
public class FlightRequest {

	@NotNull(message = "Schedule cannot be null")
	@ApiModelProperty(value = "schedule", required = true, notes = "The schedule of the destination", example = "D2", position = 3)
	private ScheduleRequest schedule;

	public FlightRequest() {
	}

	public FlightRequest(@NotNull(message = "Schedule cannot be null") ScheduleRequest schedule) {
		this.schedule = schedule;
	}

	public ScheduleRequest getSchedule() {
		return schedule;
	}

	public void setSchedule(ScheduleRequest schedule) {
		this.schedule = schedule;
	}

}
