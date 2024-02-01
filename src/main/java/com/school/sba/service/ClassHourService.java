package com.school.sba.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import com.school.sba.entity.ClassHour;
import com.school.sba.requestdto.ClassHourRequest;
import com.school.sba.util.ResponseStructure;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

public interface ClassHourService {
	public ResponseEntity<ResponseStructure<ClassHour>> createClassHour(@PathVariable int programId);

	public ResponseEntity<ResponseStructure<List<ClassHourRequest>>> updateClassHour(
			@RequestBody List<ClassHourRequest> updateRequests);

	ResponseEntity<ResponseStructure<List<ClassHour>>> createClassHoursForNextweek(int programId);

}
