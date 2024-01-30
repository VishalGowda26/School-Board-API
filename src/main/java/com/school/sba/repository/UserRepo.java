package com.school.sba.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.school.sba.entity.User;
import com.school.sba.enums.UserRole;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

	boolean existsByUserrole(UserRole userrole);

	Optional<User> findByUsername(String username);

	Optional<User> findByUserrole(UserRole userrole);
}
