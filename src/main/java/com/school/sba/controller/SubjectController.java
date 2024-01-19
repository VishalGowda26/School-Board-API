package com.school.sba.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.school.sba.entity.Subject;
import com.school.sba.requestdto.SubjectRequest;
import com.school.sba.responsedto.AcademicProgramResponse;
import com.school.sba.serviceimpl.SubjectServiceImpl;
import com.school.sba.util.ResponseStructure;

@RestController
public class SubjectController {

	@Autowired
	SubjectServiceImpl subjectService;

	@PostMapping("/academic-programs/{programId}/subjects")
	public ResponseEntity<ResponseStructure<AcademicProgramResponse>> addSubjects(
			@RequestBody SubjectRequest subjectRequest, @PathVariable int programId) {
		return subjectService.addSubjects(subjectRequest, programId);

	}

	@PutMapping("/academic-programs/{programId}/subjects")
	public ResponseEntity<ResponseStructure<AcademicProgramResponse>> updateSubjects(
			@RequestBody SubjectRequest subjectRequest, @PathVariable int programId) {
		return subjectService.addSubjects(subjectRequest, programId);

	}

	@GetMapping("/academic-programs/{programId}/subjects")
	public ResponseEntity<ResponseStructure<List<Subject>>>  getSubjects(@PathVariable int programId) {
		return subjectService.getSubjects(programId);

	}
}
