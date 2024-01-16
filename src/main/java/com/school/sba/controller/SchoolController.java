package com.school.sba.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.school.sba.entity.School;
import com.school.sba.service.SchoolService;

@Controller
public class SchoolController {

	@Autowired
	SchoolService service;

	public void addSchool(String schoolName, Long contactNo, String emailId, String address) {
		service.addSchool(schoolName, contactNo, address, emailId);

	}

	public void getSchoolById(int schoolId) {
		service.getSchoolById(schoolId);
	}

	public List<School> getAllSchools() {
		return service.getAllSchools();
		
	}

	public void updateSchool(int schoolId, String schoolName, Long contactNo, String emailId, String address) {
		service.updateSchool(schoolId,schoolName, contactNo, address, emailId);

	}

	public void deleteSchool(int schoolId) {
		service.deleteSchool(schoolId);
	}

}
