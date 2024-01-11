package com.school.sba.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.school.sba.service.SchoolService;

@Controller
public class SchoolController {

	@Autowired
	SchoolService service;

	public void addSchool(int schoolId, Long contactNo, String emailId, String address) {
		service.addSchool(schoolId, contactNo, address, emailId);

	}

	public void getSchoolById(int schoolId) {
		service.getSchoolById(schoolId);
	}

	public void updateSchool(int schoolId, Long contactNo, String emailId, String address) {
		service.updateSchool(schoolId, contactNo, address, emailId);

	}

	public void deleteSchool(int schoolId) {
		service.deleteSchool(schoolId);
	}

}
