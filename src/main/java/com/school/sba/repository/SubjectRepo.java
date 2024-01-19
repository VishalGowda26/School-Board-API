package com.school.sba.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.school.sba.entity.Subject;
import java.util.Optional;


public interface SubjectRepo extends JpaRepository<Subject, Integer> {
 Optional<Subject> findBySubjectName(String subjectName);
}
