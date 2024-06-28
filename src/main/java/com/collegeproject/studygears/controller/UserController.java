package com.collegeproject.studygears.controller;

import com.collegeproject.studygears.model.Student;
import com.collegeproject.studygears.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {

    private final StudentService studentService;

    @Autowired
    public UserController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/user")
    public ResponseEntity<Student> getUserDetails(@RequestHeader("Authorization") String token) {
        String username = extractUsernameFromToken(token);
        Optional<Student> student = studentService.getStudentByUsername(username);
        return student.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    private String extractUsernameFromToken(String token) {
        // This is a simplified example. In a real application, you would decode the JWT or token to extract the username.
        // Adjust this method according to your token structure.
        if (token.startsWith("Bearer ")) {
            return token.substring(7); // Remove "Bearer " prefix
        }
        return token;
    }
}
