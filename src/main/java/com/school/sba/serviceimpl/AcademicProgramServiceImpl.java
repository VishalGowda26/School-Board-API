package com.school.sba.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.school.sba.entity.AcademicProgram;
import com.school.sba.entity.School;
import com.school.sba.entity.User;
import com.school.sba.enums.UserRole;
import com.school.sba.exception.ConstraintViolationException;
import com.school.sba.exception.UnauthorizedException;
import com.school.sba.exception.UserNotFoundByIdException;
import com.school.sba.repository.AcademicProgramRepo;
import com.school.sba.repository.ClassHourRepo;
import com.school.sba.repository.SchoolRepo;
import com.school.sba.repository.UserRepo;
import com.school.sba.requestdto.AcademicProgramRequest;
import com.school.sba.responsedto.AcademicProgramResponse;
import com.school.sba.responsedto.UserResponse;
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
	ClassHourRepo hourRepo;

	@Autowired
	UserServiceImpl userService;

	@Autowired
	ResponseStructure<AcademicProgramResponse> structure;

	@Autowired
	ResponseStructure<List<AcademicProgramResponse>> responseStructure;

	@Autowired
	ResponseStructure<List<UserResponse>> userStructure;

	/*------------------------------> SaveProgram associated to school <--------------------------------------*/

	@Override
	public ResponseEntity<ResponseStructure<AcademicProgramResponse>> saveAcademicProgram(
			AcademicProgramRequest programRequest, int schoolId) {
		AcademicProgram program = programRepo.save(mapToAcademicProgram(programRequest));
		School school = schoolRepo.findById(schoolId)
				.orElseThrow(() -> new UserNotFoundByIdException("User Not Found"));
		school.getAcademicPrograms().add(program);
		program.setSchool(school);
		programRepo.save(program);
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

	/*------------------------------> Update AcademicProgram <--------------------------------------*/

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

	/*---------------------> Add User To Academic Program associated to school <----------------------------*/

	@Override
	public ResponseEntity<ResponseStructure<AcademicProgramResponse>> addUserToAcademicProgram(int userId,
			int programId) {
		AcademicProgram academicProgram = programRepo.findById(programId)
				.orElseThrow(() -> new UsernameNotFoundException(
						"Academic Program with given ID is not registered in the database"));
		return userRepo.findById(userId).map(u -> {
			if (u.getUserrole().equals(UserRole.ADMIN)) {
				throw new UnauthorizedException(
						"User with given ID is an admin so the program cannot be registered to it",
						HttpStatus.BAD_REQUEST, "No such mapping possible");
			} else if (!(academicProgram.getSubjects().contains(u.getSubject()))) {
				throw new UnauthorizedException(
						"User with given ID contains a subject which is not present in the respective academic program",
						HttpStatus.BAD_REQUEST, "No such mapping possible");
			} else {
				academicProgram.getUserList().add(u);
				programRepo.save(academicProgram);
				structure.setData(mapToAcademicResponseProgram(academicProgram));
				structure.setStatus(HttpStatus.ACCEPTED.value());
				structure.setMessage("Added user to academic program");
				return new ResponseEntity<ResponseStructure<AcademicProgramResponse>>(structure, HttpStatus.ACCEPTED);
			}
		}).orElseThrow(() -> new UsernameNotFoundException("User with given ID is not registered in the database"));

	}

	/*---------------------> Fetch list of Teachers in Academic Program <----------------------------*/

	@Override
	public ResponseEntity<ResponseStructure<List<UserResponse>>> fetchUsersList(int programId, UserRole userRole) {
		AcademicProgram academicProgram = programRepo.findById(programId)
				.orElseThrow(() -> new UsernameNotFoundException("Program With given Id not Found"));
		List<User> roleAndAcademicPrograms = userRepo.findByUserroleAndProgramList(userRole, academicProgram);
		List<UserResponse> responses = new ArrayList<>();
		roleAndAcademicPrograms.forEach(userrole -> {
			responses.add(userService.mapToUserResponse(userrole));
		});
		userStructure.setData(responses);
		userStructure.setMessage("Users with given role Successfully Fetched");
		userStructure.setStatus(HttpStatus.FOUND.value());
		return new ResponseEntity<ResponseStructure<List<UserResponse>>>(userStructure, HttpStatus.FOUND);
	}

	/*------------------------------> Delete Program <--------------------------------------------*/

	@Override
	public ResponseEntity<ResponseStructure<AcademicProgramResponse>> deleteProgram(int programId) {
		AcademicProgram program = programRepo.findById(programId)
				.orElseThrow(() -> new UserNotFoundByIdException("User with Given Id not Found"));
		if (program != null) {
			program.setDeleted(true);
			programRepo.delete(program);
			structure.setData(mapToAcademicResponseProgram(program));
			structure.setMessage("Program Successfully Deleted");
			structure.setStatus(HttpStatus.GONE.value());
		}
		return new ResponseEntity<ResponseStructure<AcademicProgramResponse>>(structure, HttpStatus.GONE);
	}

	/*------------------------------> Delete Program Permanently <--------------------------------------------*/

	public String permanentDeleteProgram() {
		List<AcademicProgram> program = programRepo.findByIsDeleted(true);
		program.forEach(p -> {
			hourRepo.deleteAll(p.getClassHourList());
			programRepo.delete(p);
		});
		return "Program Deleted";
	}
	
}
