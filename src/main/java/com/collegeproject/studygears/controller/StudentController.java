package com.collegeproject.studygears.controller;

import com.collegeproject.studygears.model.Student;
import com.collegeproject.studygears.model.Task;
import com.collegeproject.studygears.service.StudentService;
import com.collegeproject.studygears.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/students")
@CrossOrigin( origins = {"http://localhost:3000"}, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
public class StudentController {

    private final StudentService studentService;
    private final TaskService taskService;

    @Autowired
    public StudentController(StudentService studentService, TaskService taskService) {
        this.studentService = studentService;
        this.taskService = taskService;
    }

    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable UUID id) {
        Optional<Student> student = studentService.getStudentById(id);
        return student.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @PostMapping
    public ResponseEntity<Student> addStudent(@RequestBody Student student) {
        int result = studentService.addStudent(student);
        if (result == 1) {
            return new ResponseEntity<>(student, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable UUID id, @RequestBody Student updatedStudent) {
        Optional<Student> existingStudent = studentService.getStudentById(id);
        if (existingStudent.isPresent()) {
            studentService.updateStudentEmail(id, updatedStudent.getEmail());
            studentService.updateStudentPassword(id, updatedStudent.getPassword());
            studentService.updateStudentUsername(id, updatedStudent.getUsername());
            return ResponseEntity.ok(updatedStudent);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable UUID id) {
        boolean exists = studentService.studentExistsById(id);
        if (exists) {
            studentService.deleteStudent(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Task management endpoints
    @PostMapping("/{studentId}/tasks")
    public ResponseEntity<Task> addTask(@PathVariable UUID studentId, @RequestBody Task task) {
        Optional<Student> student = studentService.getStudentById(studentId);
        if (student.isPresent()) {
            task.setStudent(student.get());
            Task newTask = taskService.addTask(task);
            return new ResponseEntity<>(newTask, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{studentId}/tasks")
    public ResponseEntity<List<Task>> getTasksByStudentId(@PathVariable UUID studentId) {
        List<Task> tasks = taskService.getTasksByStudentId(studentId);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("/{studentId}/tasks/priority")
    public ResponseEntity<List<Task>> getTasksByPriority(@PathVariable UUID studentId) {
        List<Task> tasks = taskService.getTasksByPriority(studentId);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("/{studentId}/tasks/dueDate")
    public ResponseEntity<List<Task>> getTasksByDueDate(@PathVariable UUID studentId) {
        List<Task> tasks = taskService.getTasksByDueDate(studentId);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("/{studentId}/tasks/className")
    public ResponseEntity<List<Task>> getTasksByClassName(@PathVariable UUID studentId) {
        List<Task> tasks = taskService.getTasksByClassName(studentId);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Student student) {
        Optional<Student> existingStudent = studentService.getStudentByUsername(student.getUsername());
        if (existingStudent.isPresent()) {
            Student foundStudent = existingStudent.get();
            if (foundStudent.getPassword().equals(student.getPassword())) {
                return new ResponseEntity<>(foundStudent, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Invalid password", HttpStatus.UNAUTHORIZED);
            }
        } else {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }
}