package com.school.sba.entity;

import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Schedule {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int scheduleId;
	private LocalTime opeansAt;
	private LocalTime closesAt;
	private int classHoursPerDay;
	private int classHoursLength;
	private LocalTime breakTime;
	private int breakLength;
	private LocalTime lunchTime;
	private int lunchLength;

}
