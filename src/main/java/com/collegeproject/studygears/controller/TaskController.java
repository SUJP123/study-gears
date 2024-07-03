package com.collegeproject.studygears.controller;

import com.collegeproject.studygears.model.Task;
import com.collegeproject.studygears.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = {"https://study-gears-11k9qh3pf-sujp123s-projects.vercel.app"}, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
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

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable UUID id) {
        Optional<Task> task = taskService.getTaskById(id);
        return task.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable UUID id, @RequestBody Task updatedTask) {
        Optional<Task> task = taskService.updateTask(id, updatedTask);
        return task.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable UUID id) {
        boolean isDeleted = taskService.deleteTask(id);
        return isDeleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Task>> getTasksByStudentId(@PathVariable UUID studentId) {
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
