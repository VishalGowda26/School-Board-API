package com.school.sba.serviceimpl;

import java.time.Duration;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.school.sba.entity.AcademicProgram;
import com.school.sba.entity.ClassHour;
import com.school.sba.entity.Schedule;
import com.school.sba.repository.AcademicProgramRepo;
import com.school.sba.repository.ClassHourRepo;
import com.school.sba.service.ClassHourService;
import com.school.sba.util.ResponseStructure;

@Service
public class ClassHourServiceImpl implements ClassHourService {
	@Autowired
	ClassHourRepo classHourRepo;

	@Autowired
	AcademicProgramRepo academicProgramRepo;

	@Autowired
	ResponseStructure<ClassHour> structure;

	@Override
	public ResponseEntity<ResponseStructure<ClassHour>> createClassHour(ClassHour classHour, int programId) {
		AcademicProgram academicProgram = academicProgramRepo.findById(programId)
				.orElseThrow(() -> new UsernameNotFoundException("Program not found foe the givin id"));

		return new ResponseEntity<ResponseStructure<ClassHour>>(structure, HttpStatus.CREATED);
	}

	private Double validateClassHrs() {
		Schedule schedule = new Schedule();
		double totalHrs = Duration.between(schedule.getOpeansAt(), schedule.getClosesAt())
				.minus(schedule.getLunchLengthInMinutes()).minus(schedule.getBreakLengthInMinutes()).toMinutes();
		return totalHrs / schedule.getClassHoursLengthInMinutes().toMinutes();
	}

	private Duration validateBreakTime() {
		Schedule schedule = new Schedule();
		LocalTime opeansAt = schedule.getOpeansAt();
		LocalTime breakTime = schedule.getBreakTime();
		Duration breakLengthInMinutes = schedule.getBreakLengthInMinutes();
		LocalTime lunchTime = schedule.getLunchTime();
		Duration lunchLengthInMinutes = schedule.getLunchLengthInMinutes();
		for (int i = 0; i < validateClassHrs(); i++) {
			if (opeansAt == breakTime) {
				opeansAt = opeansAt.plusMinutes(breakLengthInMinutes.toMinutes());
			} else if (opeansAt == lunchTime) {
				opeansAt = opeansAt.plusMinutes(lunchLengthInMinutes.toMinutes());
		
				

	}

}
