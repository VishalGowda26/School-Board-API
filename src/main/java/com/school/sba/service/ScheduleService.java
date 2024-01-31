package com.school.sba.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.school.sba.entity.Schedule;
import com.school.sba.requestdto.ScheduleRequest;
import com.school.sba.responsedto.ScheduleResponse;
import com.school.sba.util.ResponseStructure;

public interface ScheduleService {
	public ResponseEntity<ResponseStructure<ScheduleResponse>> addSchedule(@PathVariable int schoolId,
			@RequestBody ScheduleRequest schedulerequest);

	public ResponseEntity<ResponseStructure<ScheduleResponse>> fetchSchedule(@PathVariable int schoolId);

	public ResponseEntity<ResponseStructure<ScheduleResponse>> updateSchedule(@PathVariable int scheduleId,
			@RequestBody ScheduleRequest scheduleRequest);

	public ResponseEntity<ResponseStructure<ScheduleResponse>> deleteSchedule(@RequestBody Schedule schedule);

}
