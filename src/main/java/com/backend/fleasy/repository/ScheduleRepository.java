package com.backend.fleasy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.fleasy.model.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer>{

}
