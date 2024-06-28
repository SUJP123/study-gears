package com.collegeproject.studygears.dao;

import com.collegeproject.studygears.model.Task;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("postgresTask")
public class TaskDaoService implements TaskDao {

    private final JdbcTemplate jdbcTemplate;

    public TaskDaoService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int insertTask(UUID id, String title, String description, LocalDate dueDate, Integer priority, String className, UUID studentId) {
        final String sql = "INSERT INTO task (id, title, description, dueDate, priority, className, student_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, id, title, description, dueDate, priority, className, studentId);
    }

    @Override
    public List<Task> selectAllTasks() {
        final String sql = "SELECT id, title, description, dueDate, priority, className, student_id FROM task";
        return jdbcTemplate.query(sql, (resultSet, i) -> {
            UUID id = UUID.fromString(resultSet.getString("id"));
            String title = resultSet.getString("title");
            String description = resultSet.getString("description");
            LocalDate dueDate = resultSet.getDate("dueDate").toLocalDate();
            Integer priority = resultSet.getInt("priority");
            String className = resultSet.getString("className");
            UUID studentId = UUID.fromString(resultSet.getString("student_id"));
            return new Task(id, title, description, dueDate, priority, className, null); // Populate student in service layer if needed
        });
    }

    @Override
    public List<Task> selectTasksByStudentId(UUID studentId) {
        final String sql = "SELECT id, title, description, dueDate, priority, className, student_id FROM task WHERE student_id = ?";
        return jdbcTemplate.query(sql, new Object[]{studentId}, (resultSet, i) -> {
            UUID id = UUID.fromString(resultSet.getString("id"));
            String title = resultSet.getString("title");
            String description = resultSet.getString("description");
            LocalDate dueDate = resultSet.getDate("dueDate").toLocalDate();
            Integer priority = resultSet.getInt("priority");
            String className = resultSet.getString("className");
            return new Task(id, title, description, dueDate, priority, className, null); // Populate student in service layer if needed
        });
    }

    @Override
    public List<Task> selectTasksByStudentIdOrderByPriority(UUID studentId) {
        final String sql = "SELECT id, title, description, dueDate, priority, className, student_id FROM task WHERE student_id = ? ORDER BY priority";
        return jdbcTemplate.query(sql, new Object[]{studentId}, (resultSet, i) -> {
            UUID id = UUID.fromString(resultSet.getString("id"));
            String title = resultSet.getString("title");
            String description = resultSet.getString("description");
            LocalDate dueDate = resultSet.getDate("dueDate").toLocalDate();
            Integer priority = resultSet.getInt("priority");
            String className = resultSet.getString("className");
            return new Task(id, title, description, dueDate, priority, className, null); // Populate student in service layer if needed
        });
    }

    @Override
    public List<Task> selectTasksByStudentIdOrderByDueDate(UUID studentId) {
        final String sql = "SELECT id, title, description, dueDate, priority, className, student_id FROM task WHERE student_id = ? ORDER BY dueDate";
        return jdbcTemplate.query(sql, new Object[]{studentId}, (resultSet, i) -> {
            UUID id = UUID.fromString(resultSet.getString("id"));
            String title = resultSet.getString("title");
            String description = resultSet.getString("description");
            LocalDate dueDate = resultSet.getDate("dueDate").toLocalDate();
            Integer priority = resultSet.getInt("priority");
            String className = resultSet.getString("className");
            return new Task(id, title, description, dueDate, priority, className, null); // Populate student in service layer if needed
        });
    }

    @Override
    public List<Task> selectTasksByStudentIdOrderByClassName(UUID studentId) {
        final String sql = "SELECT id, title, description, dueDate, priority, className, student_id FROM task WHERE student_id = ? ORDER BY className";
        return jdbcTemplate.query(sql, new Object[]{studentId}, (resultSet, i) -> {
            UUID id = UUID.fromString(resultSet.getString("id"));
            String title = resultSet.getString("title");
            String description = resultSet.getString("description");
            LocalDate dueDate = resultSet.getDate("dueDate").toLocalDate();
            Integer priority = resultSet.getInt("priority");
            String className = resultSet.getString("className");
            return new Task(id, title, description, dueDate, priority, className, null); // Populate student in service layer if needed
        });
    }

    @Override
    public Optional<Task> selectTaskById(UUID id) {
        final String sql = "SELECT id, title, description, dueDate, priority, className, student_id FROM task WHERE id = ?";
        Task task = jdbcTemplate.queryForObject(sql, new Object[]{id}, (resultSet, i) -> {
            UUID taskId = UUID.fromString(resultSet.getString("id"));
            String title = resultSet.getString("title");
            String description = resultSet.getString("description");
            LocalDate dueDate = resultSet.getDate("dueDate").toLocalDate();
            Integer priority = resultSet.getInt("priority");
            String className = resultSet.getString("className");
            UUID studentId = UUID.fromString(resultSet.getString("student_id"));
            return new Task(taskId, title, description, dueDate, priority, className, null); // Populate student in service layer if needed
        });
        return Optional.ofNullable(task);
    }

    @Override
    public int deleteTaskById(UUID id) {
        final String sql = "DELETE FROM task WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    @Override
    public int updateTaskById(UUID id, Task task) {
        final String sql = "UPDATE task SET title = ?, description = ?, dueDate = ?, priority = ?, className = ?, student_id = ? WHERE id = ?";
        return jdbcTemplate.update(sql, task.getTitle(), task.getDescription(), task.getDueDate(), task.getPriority(), task.getClassName(), task.getStudent().getId(), id);
    }

    @Override
    public boolean existsById(UUID id) {
        final String sql = "SELECT COUNT(1) FROM task WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, Boolean.class);
    }
}
