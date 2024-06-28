package com.collegeproject.studygears.repository;

import com.collegeproject.studygears.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {
    List<Task> findByStudentIdOrderByPriorityAsc(UUID studentId);
    List<Task> findByStudentIdOrderByDueDateAsc(UUID studentId);
    List<Task> findByStudentIdOrderByClassNameAsc(UUID studentId);

}

