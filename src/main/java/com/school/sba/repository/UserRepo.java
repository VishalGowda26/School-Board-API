package com.school.sba.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.school.sba.entity.AcademicProgram;
import com.school.sba.entity.User;
import com.school.sba.enums.UserRole;
import com.school.sba.entity.School;


public interface UserRepo extends JpaRepository<User, Integer> {

	boolean existsByUserrole(UserRole userrole);

	Optional<User> findByUsername(String username);

	Optional<User> findByUserrole(UserRole userrole);

	List<User> findByUserroleAndProgramList(UserRole userrole, AcademicProgram programList);
	
	List<User> findByIsDeleted(boolean deleted);
	
	List<User> findBySchool(School school);
}
