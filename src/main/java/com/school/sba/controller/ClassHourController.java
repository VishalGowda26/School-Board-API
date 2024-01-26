package com.school.sba.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.school.sba.entity.ClassHour;
import com.school.sba.service.ClassHourService;
import com.school.sba.util.ResponseStructure;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
public class ClassHourController {

	@Autowired
	ClassHourService classHourService;

	@PostMapping("/academic-program/{programId}/class-hours")
	public ResponseEntity<ResponseStructure<ClassHour>> createClassHour(@RequestBody ClassHour classHour,
			@PathVariable int programId) {
		return classHourService.createClassHour(classHour,programId);

	}
}
