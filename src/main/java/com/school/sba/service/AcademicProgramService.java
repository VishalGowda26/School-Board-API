package com.school.sba.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import com.school.sba.enums.UserRole;
import com.school.sba.requestdto.AcademicProgramRequest;
import com.school.sba.responsedto.AcademicProgramResponse;
import com.school.sba.responsedto.UserResponse;
import com.school.sba.util.ResponseStructure;

public interface AcademicProgramService {
	public ResponseEntity<ResponseStructure<AcademicProgramResponse>> saveAcademicProgram(AcademicProgramRequest req,
			int schoolId);

	public ResponseEntity<ResponseStructure<AcademicProgramResponse>> fetchAllAcademicProgram(int schoolId);

	public ResponseEntity<ResponseStructure<AcademicProgramResponse>> updateProgram(@PathVariable int programId,
			@PathVariable int userId);

	public ResponseEntity<ResponseStructure<AcademicProgramResponse>> addUserToAcademicProgram(
			@PathVariable int programId, @PathVariable int userId);

	public ResponseEntity<ResponseStructure<UserResponse>> fetchUsersList(@PathVariable int programId,
			@PathVariable UserRole userRole);
}
