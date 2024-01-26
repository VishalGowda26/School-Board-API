package com.school.sba.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.school.sba.entity.AcademicProgram;
import com.school.sba.entity.School;
import com.school.sba.entity.User;
import com.school.sba.enums.UserRole;
import com.school.sba.exception.ConstraintViolationException;
import com.school.sba.exception.UserNotFoundByIdException;
import com.school.sba.repository.AcademicProgramRepo;
import com.school.sba.repository.SchoolRepo;
import com.school.sba.repository.UserRepo;
import com.school.sba.requestdto.AcademicProgramRequest;
import com.school.sba.responsedto.AcademicProgramResponse;
import com.school.sba.service.AcademicProgramService;
import com.school.sba.util.ResponseStructure;

@Service
public class AcademicProgramServiceImpl implements AcademicProgramService {

	@Autowired
	AcademicProgramRepo programRepo;

	@Autowired
	UserRepo userRepo;

	@Autowired
	SchoolRepo schoolRepo;

	@Autowired
	ResponseStructure<AcademicProgramResponse> structure;

	@Autowired
	ResponseStructure<List<AcademicProgramResponse>> responseStructure;

	/*------------------------------> SaveProgram associated to school <--------------------------------------*/

	@Override
	public ResponseEntity<ResponseStructure<AcademicProgramResponse>> saveAcademicProgram(
			AcademicProgramRequest programRequest, int schoolId) {
		AcademicProgram program = programRepo.save(mapToAcademicProgram(programRequest));
		School school = schoolRepo.findById(schoolId)
				.orElseThrow(() -> new UserNotFoundByIdException("User Not Found"));
		school.getAcademicPrograms().add(program);
		schoolRepo.save(school);
		structure.setData(mapToAcademicResponseProgram(program));
		structure.setMessage("Academic Program saved to the database");
		structure.setStatus(HttpStatus.CREATED.value());
		return new ResponseEntity<ResponseStructure<AcademicProgramResponse>>(structure, HttpStatus.CREATED);
	}

	/*---------------------> Fetch all the Academic Programs associated to school <-------------------------*/

	@Override
	public ResponseEntity<ResponseStructure<AcademicProgramResponse>> fetchAllAcademicProgram(int schoolId) {
		School school = schoolRepo.findById(schoolId)
				.orElseThrow(() -> new UserNotFoundByIdException("User Not Found"));
		List<AcademicProgram> listAcademicProgram = school.getAcademicPrograms();
		List<AcademicProgramResponse> responses = new ArrayList<>();
		for (AcademicProgram academicProgram : listAcademicProgram) {
			responses.add(mapToAcademicResponseProgram(academicProgram));
		}
		responseStructure.setData(responses);
		responseStructure.setMessage("Academic Program saved to the database");
		responseStructure.setStatus(HttpStatus.CREATED.value());
		return new ResponseEntity<ResponseStructure<AcademicProgramResponse>>(structure, HttpStatus.CREATED);
	}

	/*------------------------------> Program Request <--------------------------------------------*/

	public AcademicProgram mapToAcademicProgram(AcademicProgramRequest programRequest) {
		return AcademicProgram.builder().programType(programRequest.getProgramType())
				.programName(programRequest.getProgramName()).beginsAt(programRequest.getBeginsAt())
				.endsAt(programRequest.getEndsAt()).build();
	}
	/*------------------------------> Program Response <--------------------------------------------*/

	public AcademicProgramResponse mapToAcademicResponseProgram(AcademicProgram academicProgram) {
		return AcademicProgramResponse.builder().programId(academicProgram.getProgramId())
				.programType(academicProgram.getProgramType()).programNameString(academicProgram.getProgramName())
				.beginsAt(academicProgram.getBeginsAt()).endsAt(academicProgram.getEndsAt()).build();
	}

	@Override
	public ResponseEntity<ResponseStructure<AcademicProgramResponse>> updateProgram(int programId, int userId) {
		User user = userRepo.findById(userId).orElseThrow(() -> new UserNotFoundByIdException("User Not Found"));
		AcademicProgram academicProgram = programRepo.findById(programId)
				.orElseThrow(() -> new UserNotFoundByIdException("Program Not Found"));
		if (user.getUserrole().equals(UserRole.ADMIN)) {
			throw new ConstraintViolationException("Program cannot be added to Admin");
		} else {
			academicProgram.getUserList().add(user);
			programRepo.save(academicProgram);
			structure.setData(mapToAcademicResponseProgram(academicProgram));
			structure.setMessage("Data Successfully added");
			structure.setStatus(HttpStatus.CREATED.value());
		}
		return new ResponseEntity<ResponseStructure<AcademicProgramResponse>>(structure, HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<ResponseStructure<AcademicProgramResponse>> addUserToAcademicProgram(int userId,
			int programId) {
		User user = userRepo.findById(userId)
				.orElseThrow(() -> new UserNotFoundByIdException("User with given id is not found"));
		AcademicProgram academicProgram = programRepo.findById(programId)
				.orElseThrow(() -> new UserNotFoundByIdException("Program not Found with given Id"));
		if (user.getUserrole() != (UserRole.ADMIN)) {
			if (academicProgram.getSubjects().contains(user.getSubject())) {
				academicProgram.getUserList().add(user);
				programRepo.save(academicProgram);
				structure.setData(mapToAcademicResponseProgram(academicProgram));
				structure.setMessage("Program Successfully added");
				structure.setStatus(HttpStatus.OK.value());
			} else {
				throw new ConstraintViolationException("No Such subject Associated to Program");
			}
		} else {
			throw new ConstraintViolationException("Program cannot be added to Admin");
		}
		return new ResponseEntity<ResponseStructure<AcademicProgramResponse>>(structure, HttpStatus.OK);
	}

}
