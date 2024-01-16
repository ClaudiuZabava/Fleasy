package com.backend.fleasy.mapper;

import org.springframework.stereotype.Component;

import com.backend.fleasy.dto.ScheduleRequest;
import com.backend.fleasy.model.Schedule;

@Component
public class ScheduleMapper {

	public Schedule scheduleRequestToSchedule(ScheduleRequest scheduleRequest) {

		return new Schedule(scheduleRequest.getStartingHour(), scheduleRequest.getEndingHour(),
				scheduleRequest.getDate());
	}
}
