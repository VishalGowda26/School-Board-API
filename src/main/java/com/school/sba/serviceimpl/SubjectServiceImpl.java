package com.school.sba.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.school.sba.entity.AcademicProgram;
import com.school.sba.entity.Subject;
import com.school.sba.exception.ConstraintViolationException;
import com.school.sba.repository.AcademicProgramRepo;
import com.school.sba.repository.SubjectRepo;
import com.school.sba.requestdto.SubjectRequest;
import com.school.sba.responsedto.AcademicProgramResponse;
import com.school.sba.service.SubjectService;
import com.school.sba.util.ResponseStructure;

@Service
public class SubjectServiceImpl implements SubjectService {
	@Autowired
	AcademicProgramRepo academicProgramRepo;

	@Autowired
	SubjectRepo subjectRepo;

	@Autowired
	ResponseStructure<List<Subject>> sstructure;

	@Autowired
	ResponseStructure<AcademicProgramResponse> structure;

	@Autowired
	AcademicProgramServiceImpl academicProgramServiceImpl;

	/*---------------------> Add Subject to Academic Programs associated to school <-------------------------*/

	@Override
	public ResponseEntity<ResponseStructure<AcademicProgramResponse>> addSubjects(SubjectRequest subjectRequest,
			int programId) {
		return academicProgramRepo.findById(programId).map(program -> {// found academic program
			List<Subject> subjects = new ArrayList<Subject>();
			subjectRequest.getSubjectNames().forEach(name -> { // iterating over each subject name
				Subject subject = subjectRepo.findBySubjectName(name).orElseGet(() -> { // if not found create new
																						// subject
					Subject subject2 = new Subject();
					subject2.setSubjectName(name);
					subjectRepo.save(subject2);
					subjects.add(subject2);
					return subject2;
				});
				subjects.add(subject);// add existing subject to subjects list
			});
			program.setSubjects(subjects);// setting subjects list to academic Program
			academicProgramRepo.save(program);// saving updated program to database
			structure.setData(academicProgramServiceImpl.mapToAcademicResponseProgram(program));
			structure.setMessage("Created subect list to Academic Program");
			structure.setStatus(HttpStatus.CREATED.value());
			return new ResponseEntity<ResponseStructure<AcademicProgramResponse>>(structure, HttpStatus.CREATED);
		}).orElseThrow(() -> new ConstraintViolationException("User not found"));
	}

	/*---------------------> Add Subject to Academic Programs associated to school <-------------------------*/

	@Override
	public ResponseEntity<ResponseStructure<AcademicProgramResponse>> updateSubjects(SubjectRequest subjectRequest,
			int programId) {
		return academicProgramRepo.findById(programId).map(program -> {
			List<Subject> subjects = new ArrayList<Subject>();
			subjectRequest.getSubjectNames().forEach(name -> {
				Subject subject = subjectRepo.findBySubjectName(name).orElseGet(() -> {

					Subject subject2 = new Subject();
					subject2.setSubjectName(name);
					subjectRepo.save(subject2);
					return subject2;
				});
				subjects.add(subject);
			});
			program.setSubjects(subjects);
			academicProgramRepo.save(program);
			structure.setData(academicProgramServiceImpl.mapToAcademicResponseProgram(program));
			structure.setMessage("Updated the subect list to Academic Program");
			structure.setStatus(HttpStatus.OK.value());
			return new ResponseEntity<ResponseStructure<AcademicProgramResponse>>(structure, HttpStatus.CREATED);
		}).orElseThrow(() -> new ConstraintViolationException("User not found"));
	}

	/*---------------------> Fetch all Subjects associated with Academic Programs <-------------------------*/

	@Override
	public ResponseEntity<ResponseStructure<List<Subject>>> getSubjects(int programId) {
		AcademicProgram academicProgram = academicProgramRepo.findById(programId)
				.orElseThrow(() -> new ConstraintViolationException("No program with given Id"));
		List<Subject> slist = academicProgram.getSubjects();
		sstructure.setData(slist);
		sstructure.setMessage("Subject list for given Id found");
		sstructure.setStatus(HttpStatus.FOUND.value());
		return new ResponseEntity<ResponseStructure<List<Subject>>>(sstructure, HttpStatus.FOUND);
	}

}
