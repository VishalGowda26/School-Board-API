package com.school.sba.serviceimpl;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.school.sba.entity.ClassHour;
import com.school.sba.entity.Schedule;
import com.school.sba.entity.School;
import com.school.sba.entity.Subject;
import com.school.sba.entity.User;
import com.school.sba.enums.ClassStatus;
import com.school.sba.enums.UserRole;
import com.school.sba.exception.ConstraintViolationException;
import com.school.sba.exception.ScheduleNotFoundBySchoolIdException;
import com.school.sba.repository.AcademicProgramRepo;
import com.school.sba.repository.ClassHourRepo;
import com.school.sba.repository.SubjectRepo;
import com.school.sba.repository.UserRepo;
import com.school.sba.requestdto.ClassHourRequest;
import com.school.sba.service.ClassHourService;
import com.school.sba.util.ResponseStructure;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ClassHourServiceImpl implements ClassHourService {
	@Autowired
	ClassHourRepo classHourRepo;

	@Autowired
	AcademicProgramRepo academicProgramRepo;

	@Autowired
	ResponseStructure<ClassHour> structure;

	@Autowired
	UserRepo userRepo;

	@Autowired
	SubjectRepo subjectRepo;

	@Autowired
	ResponseStructure<List<ClassHourRequest>> lstructure;

	/*-------------------------------------> Create ClassHour <--------------------------------------------*/

	@Override
	public ResponseEntity<ResponseStructure<ClassHour>> createClassHour(int programId) {
		return academicProgramRepo.findById(programId).map(academicProgram -> {
			School school = academicProgram.getSchool();
			Schedule schedule = school.getSchedule();
			if (schedule != null) {
				int classHourPerDay = schedule.getClassHoursPerDay();
				int classHourLength = (int) schedule.getClassHoursLengthInMinutes().toMinutes();
				LocalDateTime localDateTime = LocalDateTime.now().with(schedule.getOpeansAt());
				LocalTime lunchTimeStart = schedule.getLunchTime();
				LocalTime lunchTimeEnd = lunchTimeStart.plusMinutes(schedule.getLunchLengthInMinutes().toMinutes());
				LocalTime breakTimeStart = schedule.getBreakTime();
				LocalTime breakTimeEnd = breakTimeStart.plusMinutes(schedule.getBreakLengthInMinutes().toMinutes());
				for (int day = 1; day <= 6; day++) {
					for (int hour = 0; hour < classHourPerDay + 2; hour++) {
						ClassHour classHour = new ClassHour();
						if ((!localDateTime.toLocalTime().equals(lunchTimeStart)
								&& !validateLunchTime(localDateTime, schedule))) {

							if ((!localDateTime.toLocalTime().equals(breakTimeStart))
									&& !validateBreakTime(localDateTime, schedule)) {

								LocalDateTime beginsAt = localDateTime;
								LocalDateTime endsAt = beginsAt.plusMinutes(classHourLength);
								classHour.setBeginsAt(beginsAt);
								classHour.setEndsAt(endsAt);
								classHour.setClassStatus(ClassStatus.NOT_SCHEDULED);
								localDateTime = endsAt;
							} else {
								classHour.setBeginsAt(localDateTime);
								classHour.setEndsAt(LocalDateTime.now().with(breakTimeEnd));
								classHour.setClassStatus(ClassStatus.BREAK_TIME);
								localDateTime = localDateTime
										.plusMinutes(schedule.getBreakLengthInMinutes().toMinutes());
							}
						} else {
							classHour.setBeginsAt(localDateTime);
							classHour.setEndsAt(LocalDateTime.now().with(lunchTimeEnd));
							classHour.setClassStatus(ClassStatus.LUNCH_TIME);
							localDateTime = localDateTime.plusMinutes(schedule.getLunchLengthInMinutes().toMinutes());
						}
						classHour.setAcademicProgram(academicProgram);
						classHourRepo.save(classHour);
					}
					localDateTime = localDateTime.plusDays(1).with(schedule.getOpeansAt());
				}

			} else
				throw new ScheduleNotFoundBySchoolIdException(
						"The school does not contain any schedule, please provide a schedule to the school");

			return new ResponseEntity<ResponseStructure<ClassHour>>(structure, HttpStatus.CREATED);
		}).orElseThrow(() -> new UsernameNotFoundException("Program not found foe the givin id"));
	}

	private boolean validateBreakTime(LocalDateTime localDateTime, Schedule schedule) {
		LocalTime breakTimeStart = schedule.getBreakTime();
		LocalTime breakTimeEnd = breakTimeStart.plusMinutes(schedule.getBreakLengthInMinutes().toMinutes());
		return (localDateTime.toLocalTime().isAfter(breakTimeStart)
				&& localDateTime.toLocalTime().isBefore(breakTimeEnd));
	}

	private boolean validateLunchTime(LocalDateTime localDateTime, Schedule schedule) {
		LocalTime lunchTimeStart = schedule.getLunchTime();
		LocalTime lunchTimeEnd = lunchTimeStart.plusMinutes(schedule.getLunchLengthInMinutes().toMinutes());
		return (localDateTime.toLocalTime().isAfter(lunchTimeStart)
				&& localDateTime.toLocalTime().isBefore(lunchTimeEnd));
	}

	/*----------------------------------> Update ClassHour <--------------------------------------------*/

	@Override
	public ResponseEntity<ResponseStructure<List<ClassHourRequest>>> updateClassHour(
			List<ClassHourRequest> updateRequests) {
		updateRequests.forEach((req) -> {
			int userId = req.getUserId();
			User user = userRepo.findById(userId).orElseThrow(
					() -> new UsernameNotFoundException("User with given ID is not registered in the database"));
			int roomNo = req.getRoomNo();
			int hourId = req.getClassHourId();
			ClassHour classHour = classHourRepo.findById(hourId).orElseThrow(
					() -> new UsernameNotFoundException("ClassHour with given ID is not registered in the database"));
			int subjectId = req.getSubjectId();
			Subject subject = subjectRepo.findById(subjectId).orElseThrow(
					() -> new UsernameNotFoundException("Subject with given ID is not registered in the database"));
			if (!classHourRepo.existsByRoomNoAndBeginsAtBetween(roomNo, classHour.getBeginsAt(),
					classHour.getEndsAt())) {
				if (user.getUserrole().equals(UserRole.TEACHER)) {
					classHour.setRoomNo(roomNo);
					classHour.setSubject(subject);
					classHour.setUser(user);
					classHour.setClassStatus(ClassStatus.UPCOMMING);
					classHourRepo.save(classHour);
					lstructure.setMessage("ClassHour Updated successfully for the given User");
					lstructure.setData(updateRequests);
					lstructure.setStatus(HttpStatus.CREATED.value());

				} else {
					throw new ConstraintViolationException("Invalid User Id");
				}
			} else {
				throw new UsernameNotFoundException("Class Hour already contains Room No");
			}
		});
		return new ResponseEntity<ResponseStructure<List<ClassHourRequest>>>(lstructure, HttpStatus.CREATED);
	}

	/*--------------------------------------> Delete ClassHour <--------------------------------------------*/

}
