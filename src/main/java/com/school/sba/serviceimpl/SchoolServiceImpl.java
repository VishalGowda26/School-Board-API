package com.school.sba.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.school.sba.entity.School;
import com.school.sba.enums.UserRole;
import com.school.sba.exception.ConstraintViolationException;
import com.school.sba.exception.UserNotFoundByIdException;
import com.school.sba.repository.SchoolRepo;
import com.school.sba.repository.UserRepo;
import com.school.sba.requestdto.SchoolRequest;
import com.school.sba.responsedto.SchoolResponse;
import com.school.sba.service.SchoolService;
import com.school.sba.util.ResponseStructure;

@Service
public class SchoolServiceImpl implements SchoolService {

	@Autowired
	SchoolRepo schoolrepo;

	@Autowired
	UserRepo userRepo;

	@Autowired
	ResponseStructure<SchoolResponse> structure;

	/*------------------------------> School Request <--------------------------------------------*/

	private School mapToSchool(SchoolRequest schoolRequest) {
		return School.builder().schoolName(schoolRequest.getSchoolName()).address(schoolRequest.getAddress())
				.contactNo(schoolRequest.getContactNo()).emailId(schoolRequest.getEmailId()).build();

	}

	/*------------------------------> School Response <--------------------------------------------*/
	private SchoolResponse mapToSchoolResponse(School school) {
		return SchoolResponse.builder().schoolId(school.getSchoolId()).schoolName(school.getSchoolName())
				.address(school.getAddress()).emailId(school.getEmailId()).contactNo(school.getContactNo()).build();

	}

	/*------------------------------> Add School <--------------------------------------------*/
	@Override
	public ResponseEntity<ResponseStructure<SchoolResponse>> createSchool(SchoolRequest schoolRequest) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		return userRepo.findByUsername(username).map(u -> {
			if (u.getUserrole().equals(UserRole.ADMIN)) {
				if (u.getSchool() == null) {
					School school = mapToSchool(schoolRequest);
					school = schoolrepo.save(school); //
					u.setSchool(school);
					userRepo.save(u);
					structure.setStatus(HttpStatus.CREATED.value());
					structure.setMessage("Data Saved Successfully");
					structure.setData(mapToSchoolResponse(school));
					return new ResponseEntity<ResponseStructure<SchoolResponse>>(structure, HttpStatus.CREATED);
				} else
					throw new ConstraintViolationException("There is already a school there should only be one school");
			} else
				throw new ConstraintViolationException("Cannot be created Only Admin can Create School");
		}).orElseThrow(() -> new UserNotFoundByIdException("No details Found"));
	}

	/*------------------------------> Delete School <--------------------------------------------*/
	@Override
	public ResponseEntity<ResponseStructure<SchoolResponse>> deleteSchool(int schoolId) {
		School school = schoolrepo.findById(schoolId)
				.orElseThrow(() -> new UserNotFoundByIdException("School Not Found By Id"));
		if (school != null) {
			school.setDeleted(true);
			schoolrepo.delete(school);
			structure.setData(mapToSchoolResponse(school));
			structure.setMessage("School Successfully Deleted");
			structure.setStatus(HttpStatus.GONE.value());
		}
		return new ResponseEntity<ResponseStructure<SchoolResponse>>(structure, HttpStatus.GONE);
	}
}
