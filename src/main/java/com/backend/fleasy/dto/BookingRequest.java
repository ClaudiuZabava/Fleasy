package com.backend.fleasy.dto;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Booking request", description = "Required details needed to create a new booking")
public class BookingRequest {

	@NotNull(message = "No of persons cannot be null")
	@ApiModelProperty(value = "noPersons", required = true, notes = "The no of persons of the booking", example = "3", position = 1)
	@Min(1)
	private Integer noPersons;

	@NotNull(message = "Date registered cannot be null")
	@ApiModelProperty(value = "dateRegistered", required = true, notes = "The booking's date registered", example = "2023-09-09 13:45", position = 2)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	private Date dateRegistered;

	@NotNull(message = "Seats list cannont be null")
	@ApiModelProperty(value = "seatIds", required = true, notes = "The booking's seats", example = "[1,2,3]", position = 3)
	private List<Integer> seatIds;

	public BookingRequest() {
	}

	public BookingRequest(@NotNull(message = "No of persons cannot be null") @Min(1) Integer noPersons,
						  @NotNull(message = "Date registered cannot be null") Date dateRegistered,
						  @NotNull(message = "Seats list cannont be null") List<Integer> seatIds) {
		super();
		this.noPersons = noPersons;
		this.dateRegistered = dateRegistered;
		this.seatIds = seatIds;
	}

	public List<Integer> getSeatIds() {
		return seatIds;
	}

	public void setSeatIds(List<Integer> seatIds) {
		this.seatIds = seatIds;
	}

	public Integer getNoPersons() {
		return noPersons;
	}

	public void setNoPersons(Integer noPersons) {
		this.noPersons = noPersons;
	}

	public Date getDateRegistered() {
		return dateRegistered;
	}

	public void setDateRegistered(Date dateRegistered) {
		this.dateRegistered = dateRegistered;
	}

}
