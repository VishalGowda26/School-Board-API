package com.school.sba.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import com.school.sba.requestdto.SchoolRequest;
import com.school.sba.responsedto.SchoolResponse;
import com.school.sba.util.ResponseStructure;

public interface SchoolService {
	public ResponseEntity<ResponseStructure<SchoolResponse>> createSchool(SchoolRequest schoolRequest);

	public ResponseEntity<ResponseStructure<SchoolResponse>> deleteSchool(@PathVariable int schoolId);
}
