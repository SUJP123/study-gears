package com.collegeproject.studygears.dao;

import com.collegeproject.studygears.model.Student;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("postgresStudent")
public class StudentDaoService implements StudentDao {

    private final JdbcTemplate jdbcTemplate;

    public StudentDaoService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int insertStudent(UUID id, String firstName, String lastName, String email, String username, String password) {
        final String sql = "INSERT INTO student (id, firstName, lastName, email, username, password) VALUES (?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, id, firstName, lastName, email, username, password);
    }

    @Override
    public List<Student> selectAllStudents() {
        final String sql = "SELECT id, firstName, lastName, email, username, password FROM student";
        return jdbcTemplate.query(sql, (resultSet, i) -> {
            UUID id = UUID.fromString(resultSet.getString("id"));
            String firstName = resultSet.getString("firstName");
            String lastName = resultSet.getString("lastName");
            String email = resultSet.getString("email");
            String username = resultSet.getString("username");
            String password = resultSet.getString("password");
            return new Student(id, firstName, lastName, email, username, password, null);
        });
    }

    @Override
    public Optional<Student> selectStudentById(UUID id) {
        final String sql = "SELECT id, firstName, lastName, email, username, password FROM student WHERE id = ?";
        Student student = jdbcTemplate.queryForObject(sql, new Object[]{id}, (resultSet, i) -> {
            UUID studentId = UUID.fromString(resultSet.getString("id"));
            String firstName = resultSet.getString("firstName");
            String lastName = resultSet.getString("lastName");
            String email = resultSet.getString("email");
            String username = resultSet.getString("username");
            String password = resultSet.getString("password");
            return new Student(studentId, firstName, lastName, email, username, password, null);
        });
        return Optional.ofNullable(student);
    }

    @Override
    public Optional<Student> selectStudentByUsername(String username) {
        final String sql = "SELECT id, firstName, lastName, email, username, password FROM student WHERE username = ?";
        Student student = jdbcTemplate.queryForObject(sql, new Object[]{username}, (resultSet, i) -> {
            UUID id = UUID.fromString(resultSet.getString("id"));
            String firstName = resultSet.getString("firstName");
            String lastName = resultSet.getString("lastName");
            String email = resultSet.getString("email");
            String usernameResult = resultSet.getString("username");
            String password = resultSet.getString("password");
            return new Student(id, firstName, lastName, email, usernameResult, password, null);
        });
        return Optional.ofNullable(student);
    }

    @Override
    public Optional<Student> selectStudentByEmail(String email) {
        final String sql = "SELECT id, firstName, lastName, email, username, password FROM student WHERE email = ?";
        Student student = jdbcTemplate.queryForObject(sql, new Object[]{email}, (resultSet, i) -> {
            UUID id = UUID.fromString(resultSet.getString("id"));
            String firstName = resultSet.getString("firstName");
            String lastName = resultSet.getString("lastName");
            String emailResult = resultSet.getString("email");
            String username = resultSet.getString("username");
            String password = resultSet.getString("password");
            return new Student(id, firstName, lastName, emailResult, username, password, null);
        });
        return Optional.ofNullable(student);
    }

    @Override
    public int deleteStudentById(UUID id) {
        final String sql = "DELETE FROM student WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    @Override
    public int updateStudentEmailById(UUID id, String newEmail) {
        final String sql = "UPDATE student SET email = ? WHERE id = ?";
        return jdbcTemplate.update(sql, newEmail, id);
    }

    @Override
    public int updateStudentPasswordById(UUID id, String newPassword) {
        final String sql = "UPDATE student SET password = ? WHERE id = ?";
        return jdbcTemplate.update(sql, newPassword, id);
    }

    @Override
    public int updateStudentUsernameById(UUID id, String newUsername) {
        final String sql = "UPDATE student SET username = ? WHERE id = ?";
        return jdbcTemplate.update(sql, newUsername, id);
    }

    @Override
    public boolean existsById(UUID id) {
        final String sql = "SELECT COUNT(1) FROM student WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, Boolean.class);
    }

    @Override
    public boolean existsByUsername(String username) {
        final String sql = "SELECT COUNT(1) FROM student WHERE username = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{username}, Boolean.class);
    }

    @Override
    public boolean existsByEmail(String email) {
        final String sql = "SELECT COUNT(1) FROM student WHERE email = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{email}, Boolean.class);
    }
}
