package com.collegeproject.studygears.dao;

import com.collegeproject.studygears.model.Student;
import com.collegeproject.studygears.model.Task;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StudentDao {

    int insertStudent(UUID id, String firstName, String lastName, String email, String username, String password, List<Task> tasks);

    default int insertStudent(Student student) {
        UUID id = UUID.randomUUID();
        return insertStudent(id, student.getFirstName(),
                student.getLastName(), student.getEmail(),
                student.getUsername(), student.getPassword(), new ArrayList<>());
    }

    List<Student> selectAllStudents();

    Optional<Student> selectStudentById(UUID id);

    Optional<Student> selectStudentByUsername(String username);

    Optional<Student> selectStudentByEmail(String email);

    int deleteStudentById(UUID id);

    int updateStudentEmailById(UUID id, String newEmail);

    int updateStudentPasswordById(UUID id, String newPassword);

    int updateStudentUsernameById(UUID id, String newUsername);

    boolean existsById(UUID id);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}

