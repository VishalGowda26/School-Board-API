package com.school.sba.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.school.sba.entity.Subject;
import com.school.sba.requestdto.SubjectRequest;
import com.school.sba.responsedto.AcademicProgramResponse;
import com.school.sba.responsedto.UserResponse;
import com.school.sba.util.ResponseStructure;

public interface SubjectService {
	public ResponseEntity<ResponseStructure<AcademicProgramResponse>> addSubjects(
			@RequestBody SubjectRequest subjectRequest, @PathVariable int programId);

	public ResponseEntity<ResponseStructure<AcademicProgramResponse>> updateSubjects(
			@RequestBody SubjectRequest subjectRequest, @PathVariable int programId);

	public ResponseEntity<ResponseStructure<List<Subject>>> getSubjects(@PathVariable int programId);

	public ResponseEntity<ResponseStructure<List<Subject>>> fetchSubjects();

	public ResponseEntity<ResponseStructure<UserResponse>> assignSubjects(@PathVariable int subjectId,
			@PathVariable int userId);
}
