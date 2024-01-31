package com.school.sba.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.school.sba.entity.ClassHour;
import com.school.sba.requestdto.ClassHourRequest;
import com.school.sba.service.ClassHourService;
import com.school.sba.util.ResponseStructure;

@RestController
public class ClassHourController {

	@Autowired
	ClassHourService classHourService;

	@PostMapping("/academic-program/{programId}/class-hours")
	public ResponseEntity<ResponseStructure<ClassHour>> createClassHour(@PathVariable int programId) {
		return classHourService.createClassHour(programId);

	}

	@PutMapping(path = "/class-hours")
	public ResponseEntity<ResponseStructure<List<ClassHourRequest>>> updateClassHour(
			@RequestBody List<ClassHourRequest> updateRequests) {
		return classHourService.updateClassHour(updateRequests);
	}

}
