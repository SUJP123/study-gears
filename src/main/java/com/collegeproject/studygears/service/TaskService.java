package com.collegeproject.studygears.service;

import com.collegeproject.studygears.dao.TaskDao;
import com.collegeproject.studygears.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskService {

    private final TaskDao taskDao;

    @Autowired
    public TaskService(@Qualifier("postgresTask") TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    public Task addTask(Task task) {
        taskDao.insertTask(task);
        return task;
    }

    public List<Task> getAllTasks() {
        return taskDao.selectAllTasks();
    }

    public List<Task> getTasksByStudentId(UUID studentId) {
        return taskDao.selectTasksByStudentId(studentId);
    }

    public List<Task> getTasksByPriority(UUID studentId) {
        return taskDao.selectTasksByStudentIdOrderByPriority(studentId);
    }

    public List<Task> getTasksByDueDate(UUID studentId) {
        return taskDao.selectTasksByStudentIdOrderByDueDate(studentId);
    }

    public List<Task> getTasksByClassName(UUID studentId) {
        return taskDao.selectTasksByStudentIdOrderByClassName(studentId);
    }

    public Optional<Task> getTaskById(UUID id) {
        return taskDao.selectTaskById(id);
    }

    public boolean deleteTask(UUID id) {
        if (taskDao.existsById(id)) {
            taskDao.deleteTaskById(id);
            return true;
        }
        return false;
    }

    public boolean updateTask(UUID id, Task task) {
        if (taskDao.existsById(id)) {
            taskDao.updateTaskById(id, task);
            return true;
        }
        return false;
    }
}
