package com.collegeproject.studygears.service;

import com.collegeproject.studygears.dao.StudentDao;
import com.collegeproject.studygears.model.Student;
import com.collegeproject.studygears.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StudentService {

    private final StudentDao studentDao;
    private final TaskService taskService;

    @Autowired
    public StudentService(@Qualifier("postgresStudent") StudentDao studentDao, TaskService taskService) {
        this.studentDao = studentDao;
        this.taskService = taskService;
    }

    public int addStudent(Student student) {
        return studentDao.insertStudent(student);
    }

    public List<Student> getAllStudents() {
        return studentDao.selectAllStudents();
    }

    public Optional<Student> getStudentById(UUID id) {
        return studentDao.selectStudentById(id);
    }

    public Optional<Student> getStudentByUsername(String username) {
        return studentDao.selectStudentByUsername(username);
    }

    public Optional<Student> getStudentByEmail(String email) {
        return studentDao.selectStudentByEmail(email);
    }

    public int deleteStudent(UUID id) {
        return studentDao.deleteStudentById(id);
    }

    public int updateStudentEmail(UUID id, String newEmail) {
        return studentDao.updateStudentEmailById(id, newEmail);
    }

    public int updateStudentPassword(UUID id, String newPassword) {
        return studentDao.updateStudentPasswordById(id, newPassword);
    }

    public int updateStudentUsername(UUID id, String newUsername) {
        return studentDao.updateStudentUsernameById(id, newUsername);
    }

    public boolean studentExistsById(UUID id) {
        return studentDao.existsById(id);
    }

    public boolean studentExistsByUsername(String username) {
        return studentDao.existsByUsername(username);
    }

    public boolean studentExistsByEmail(String email) {
        return studentDao.existsByEmail(email);
    }

    // Task management methods
    public Task addTask(Task task) {
        return taskService.addTask(task);
    }

    public List<Task> getTasksByStudentId(UUID studentId) {
        return taskService.getTasksByStudentId(studentId);
    }

    public List<Task> getTasksByPriority(UUID studentId) {
        return taskService.getTasksByPriority(studentId);
    }

    public List<Task> getTasksByDueDate(UUID studentId) {
        return taskService.getTasksByDueDate(studentId);
    }

    public List<Task> getTasksByClassName(UUID studentId) {
        return taskService.getTasksByClassName(studentId);
    }
}
