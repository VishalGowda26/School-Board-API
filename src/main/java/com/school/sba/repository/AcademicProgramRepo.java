package com.school.sba.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.school.sba.entity.AcademicProgram;
import com.school.sba.entity.User;
import com.school.sba.enums.UserRole;

public interface AcademicProgramRepo extends JpaRepository<AcademicProgram, Integer> {
	Optional<User> findByProgramIdAndUserRole(UserRole userRole, int programId);
}