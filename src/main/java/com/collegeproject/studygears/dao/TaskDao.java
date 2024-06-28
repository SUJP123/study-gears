package com.collegeproject.studygears.dao;

import com.collegeproject.studygears.model.Task;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskDao {

    int insertTask(UUID id, String title, String description, LocalDate dueDate, Integer priority, String className, UUID studentId);

    default int insertTask(Task task) {
        UUID id = UUID.randomUUID();
        return insertTask(id, task.getTitle(), task.getDescription(), task.getDueDate(), task.getPriority(), task.getClassName(), task.getStudent().getId());
    }

    List<Task> selectAllTasks();

    List<Task> selectTasksByStudentId(UUID studentId);

    List<Task> selectTasksByStudentIdOrderByPriority(UUID studentId);

    List<Task> selectTasksByStudentIdOrderByDueDate(UUID studentId);

    List<Task> selectTasksByStudentIdOrderByClassName(UUID studentId);

    Optional<Task> selectTaskById(UUID id);

    int deleteTaskById(UUID id);

    int updateTaskById(UUID id, Task task);

    boolean existsById(UUID id);
}
