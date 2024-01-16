package com.school.sba.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.school.sba.entity.School;
import com.school.sba.repository.SchoolRepo;

@Service
public class SchoolService {
	@Autowired
	SchoolRepo repo;

	public String addSchool(String schoolName, Long contactNo, String emailId, String address) {
		School s = new School();
		s.setSchoolName(schoolName);
		s.setContactNo(contactNo);
		s.setEmailId(emailId);
		s.setAddress(address);
		repo.save(s);
		return "School " + schoolName + " Details Saved";

	}

	public List<School> getAllSchools() {
		return repo.findAll();
	}

	public School getSchoolById(int schoolId) {
		return repo.findById(schoolId).get();
	}

	public String updateSchool(int schoolId, String schoolName, Long contactNo, String address, String emailId) {
		School updateschool = getSchoolById(schoolId);
		updateschool.setSchoolName(schoolName);
		updateschool.setContactNo(contactNo);
		updateschool.setEmailId(emailId);
		updateschool.setAddress(address);
		repo.save(updateschool);
		return "School " + schoolName + " Details Updated";
	}

	public String deleteSchool(int schoolId) {
		School deleteschool = getSchoolById(schoolId);
		repo.delete(deleteschool);
		return "Successfully deleted";
	}

}
