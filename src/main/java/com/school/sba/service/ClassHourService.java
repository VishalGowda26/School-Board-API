package com.school.sba.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import com.school.sba.entity.ClassHour;
import com.school.sba.util.ResponseStructure;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

public interface ClassHourService {
	public ResponseEntity<ResponseStructure<ClassHour>> createClassHour(@RequestBody ClassHour classHour,
			@PathVariable int programId);

}
