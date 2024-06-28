package com.collegeproject.studygears.repository;

import com.collegeproject.studygears.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, UUID> {
}

