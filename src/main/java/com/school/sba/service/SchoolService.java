package com.school.sba.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.school.sba.entity.School;
import com.school.sba.repository.SchoolRepo;

@Service
public class SchoolService {
	@Autowired
	SchoolRepo repo;

	public String addSchool(int schoolId, Long contactNo, String emailId, String address) {
		School s = new School();
		s.setAddress(address);
		s.setContactNo(contactNo);
		s.setEmailId(emailId);
		repo.save(s);
		return "School Details Saved";

	}

	public School getSchoolById(int schoolId) {
		return repo.findById(schoolId).get();
	}

	public String updateSchool(int schoolId, Long contactNo, String address, String emailId) {
		School updateschool = getSchoolById(schoolId);
		updateschool.setAddress(address);
		updateschool.setContactNo(contactNo);
		updateschool.setEmailId(emailId);
		repo.save(updateschool);
		return "School Details Updated";
	}

	public String deleteSchool(int schoolId) {
		School deleteschool = getSchoolById(schoolId);
		repo.delete(deleteschool);
		return "Successfully deleted";
	}

}
