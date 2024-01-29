package com.school.sba.serviceimpl;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.school.sba.entity.Schedule;
import com.school.sba.entity.School;
import com.school.sba.exception.ConstraintViolationException;
import com.school.sba.exception.UserNotFoundByIdException;
import com.school.sba.repository.ScheduleRepo;
import com.school.sba.repository.SchoolRepo;
import com.school.sba.requestdto.ScheduleRequest;
import com.school.sba.responsedto.ScheduleResponse;
import com.school.sba.service.ScheduleService;
import com.school.sba.util.ResponseStructure;

@Service
public class ScheduleServiceImpl implements ScheduleService {

	@Autowired
	ScheduleRepo scheduleRepo;

	@Autowired
	SchoolRepo schoolRepo;

	@Autowired
	ResponseStructure<ScheduleResponse> structure;

	/*------------------------------> Schedule Response <--------------------------------------------*/

	public ScheduleResponse mapToScheduleResponse(Schedule schedule) {
		return ScheduleResponse.builder().opeansAt(schedule.getOpeansAt()).closesAt(schedule.getClosesAt())
				.classHoursPerDay(schedule.getClassHoursPerDay())
				.classHoursLengthInMinutes((int) schedule.getClassHoursLengthInMinutes().toMinutes())
				.breakTime(schedule.getBreakTime())
				.breakLengthInMinutes((int) schedule.getBreakLengthInMinutes().toMinutes())
				.lunchTime(schedule.getLunchTime()).scheduleId(schedule.getScheduleId())
				.lunchLengthInMinutes((int) schedule.getLunchLengthInMinutes().toMinutes()).build();
	}

	/*------------------------------> Schedule Request <--------------------------------------------*/

	public Schedule mapToSchedule(ScheduleRequest scheduleRequest) {
		return Schedule.builder().opeansAt(scheduleRequest.getOpeansAt())
				.closesAt(scheduleRequest.getClosesAt())
				.classHoursLengthInMinutes(Duration.ofMinutes(scheduleRequest.getClassHoursLengthInMinutes()))
				.classHoursPerDay(scheduleRequest.getClassHoursPerDay()).breakTime(scheduleRequest.getBreakTime())
				.breakLengthInMinutes(Duration.ofMinutes(scheduleRequest.getBreakLengthInMinutes()))
				.lunchLengthInMinutes(Duration.ofMinutes(scheduleRequest.getLunchLengthInMinutes()))
				.lunchTime(scheduleRequest.getLunchTime()).build();

	}

	/*------------------------------> Adding Schedule to School <--------------------------------------------*/

	@Override
	public ResponseEntity<ResponseStructure<ScheduleResponse>> addSchedule(int schoolId,
			ScheduleRequest schedulerequest) {
		School school = schoolRepo.findById(schoolId)
				.orElseThrow(() -> new UserNotFoundByIdException("Details not found"));
		if (school.getSchedule() == null) {
			Schedule schedule = scheduleRepo.save(mapToSchedule(schedulerequest));
			school.setSchedule(schedule);
			schoolRepo.save(school);
			structure.setStatus(HttpStatus.CREATED.value());
			structure.setMessage("Schedule Successfully Added to School");
			structure.setData(mapToScheduleResponse(schedule));
		} else {
			throw new ConstraintViolationException(
					"There should only be one Schedule to school which is already created");
		}
		
		return new ResponseEntity<ResponseStructure<ScheduleResponse>>(structure, HttpStatus.CREATED);

	}

	/*------------------------------> Finding Schedule of a School <--------------------------------------------*/

	@Override
	public ResponseEntity<ResponseStructure<ScheduleResponse>> fetchSchedule(int schoolId) {
		School school = schoolRepo.findById(schoolId)
				.orElseThrow(() -> new UserNotFoundByIdException("Details not found"));
		Schedule schedule = school.getSchedule();
		structure.setStatus(HttpStatus.FOUND.value());
		structure.setMessage("Data fetched Successfully");
		structure.setData(mapToScheduleResponse(schedule));
		return new ResponseEntity<ResponseStructure<ScheduleResponse>>(structure, HttpStatus.FOUND);
	}

	/*------------------------------> Updating Schedule <--------------------------------------------*/

	@Override
	public ResponseEntity<ResponseStructure<ScheduleResponse>> updateSchedule(int scheduleId,
			ScheduleRequest scheduleRequest) {
		Schedule schedule2 = scheduleRepo.findById(scheduleId)
				.orElseThrow(() -> new UserNotFoundByIdException("No User Found"));
		Schedule schedule = mapToSchedule(scheduleRequest);
		schedule.setScheduleId(scheduleId);
		scheduleRepo.save(schedule);
		structure.setStatus(HttpStatus.OK.value());
		structure.setMessage("Data Updated Successfully");
		structure.setData(mapToScheduleResponse(schedule2));
		return new ResponseEntity<ResponseStructure<ScheduleResponse>>(structure, HttpStatus.OK);
	}

}