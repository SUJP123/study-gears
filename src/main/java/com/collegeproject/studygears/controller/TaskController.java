package com.collegeproject.studygears.controller;

import com.collegeproject.studygears.model.Task;
import com.collegeproject.studygears.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<Task> addTask(@RequestBody Task task) {
        Task newTask = taskService.addTask(task);
        return new ResponseEntity<>(newTask, HttpStatus.CREATED);
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Task>> getTasksByStudent(@PathVariable UUID studentId) {
        List<Task> tasks = taskService.getTasksByStudentId(studentId);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("/student/{studentId}/priority")
    public ResponseEntity<List<Task>> getTasksByPriority(@PathVariable UUID studentId) {
        List<Task> tasks = taskService.getTasksByPriority(studentId);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("/student/{studentId}/dueDate")
    public ResponseEntity<List<Task>> getTasksByDueDate(@PathVariable UUID studentId) {
        List<Task> tasks = taskService.getTasksByDueDate(studentId);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("/student/{studentId}/className")
    public ResponseEntity<List<Task>> getTasksByClassName(@PathVariable UUID studentId) {
        List<Task> tasks = taskService.getTasksByClassName(studentId);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }
}
