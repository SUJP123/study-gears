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
    public int insertTask(UUID id, String title, String description, LocalDate dueDate, Integer priority, String className, UUID studentId, LocalDate startDate, Boolean reminder) {
        final String sql = "INSERT INTO task (id, title, description, due_date, priority, class_name, student_id, start_date, reminder) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, id, title, description, dueDate, priority, className, studentId, startDate, reminder);
    }

    @Override
    public int insertTask(Task task) {
        return TaskDao.super.insertTask(task);
    }

    @Override
    public List<Task> selectAllTasks() {
        final String sql = "SELECT id, title, description, due_date, priority, class_name, student_id, start_date, reminder FROM task";
        return jdbcTemplate.query(sql, (resultSet, i) -> {
            UUID id = UUID.fromString(resultSet.getString("id"));
            String title = resultSet.getString("title");
            String description = resultSet.getString("description");
            LocalDate dueDate = resultSet.getDate("due_date").toLocalDate();
            Integer priority = resultSet.getInt("priority");
            String className = resultSet.getString("class_name");
            UUID studentId = UUID.fromString(resultSet.getString("student_id"));
            LocalDate startDate = resultSet.getDate("start_date").toLocalDate();
            Boolean reminder = resultSet.getBoolean("reminder");
            return new Task(id, title, description, dueDate, priority, className, null, startDate, reminder);
        });
    }

    @Override
    public List<Task> selectTasksByStudentId(UUID studentId) {
        final String sql = "SELECT id, title, description, due_date, priority, class_name, student_id, start_date, reminder FROM task WHERE student_id = ?";
        return jdbcTemplate.query(sql, new Object[]{studentId}, (resultSet, i) -> {
            UUID id = UUID.fromString(resultSet.getString("id"));
            String title = resultSet.getString("title");
            String description = resultSet.getString("description");
            LocalDate dueDate = resultSet.getDate("due_date").toLocalDate();
            Integer priority = resultSet.getInt("priority");
            String className = resultSet.getString("class_name");
            LocalDate startDate = resultSet.getDate("start_date").toLocalDate();
            Boolean reminder = resultSet.getBoolean("reminder");
            return new Task(id, title, description, dueDate, priority, className, null, startDate, reminder);
        });
    }

    @Override
    public List<Task> selectTasksByStudentIdOrderByPriority(UUID studentId) {
        final String sql = "SELECT id, title, description, due_date, priority, class_name, student_id, start_date, reminder FROM task WHERE student_id = ? ORDER BY priority";
        return jdbcTemplate.query(sql, new Object[]{studentId}, (resultSet, i) -> {
            UUID id = UUID.fromString(resultSet.getString("id"));
            String title = resultSet.getString("title");
            String description = resultSet.getString("description");
            LocalDate dueDate = resultSet.getDate("due_date").toLocalDate();
            Integer priority = resultSet.getInt("priority");
            String className = resultSet.getString("class_name");
            LocalDate startDate = resultSet.getDate("start_date").toLocalDate();
            Boolean reminder = resultSet.getBoolean("reminder");
            return new Task(id, title, description, dueDate, priority, className, null, startDate, reminder);
        });
    }

    @Override
    public List<Task> selectTasksByStudentIdOrderByDueDate(UUID studentId) {
        final String sql = "SELECT id, title, description, due_date, priority, class_name, student_id, start_date, reminder FROM task WHERE student_id = ? ORDER BY due_date";
        return jdbcTemplate.query(sql, new Object[]{studentId}, (resultSet, i) -> {
            UUID id = UUID.fromString(resultSet.getString("id"));
            String title = resultSet.getString("title");
            String description = resultSet.getString("description");
            LocalDate dueDate = resultSet.getDate("due_date").toLocalDate();
            Integer priority = resultSet.getInt("priority");
            String className = resultSet.getString("class_name");
            LocalDate startDate = resultSet.getDate("start_date").toLocalDate();
            Boolean reminder = resultSet.getBoolean("reminder");
            return new Task(id, title, description, dueDate, priority, className, null, startDate, reminder);
        });
    }

    @Override
    public List<Task> selectTasksByStudentIdOrderByClassName(UUID studentId) {
        final String sql = "SELECT id, title, description, due_date, priority, class_name, student_id, start_date, reminder FROM task WHERE student_id = ? ORDER BY class_name";
        return jdbcTemplate.query(sql, new Object[]{studentId}, (resultSet, i) -> {
            UUID id = UUID.fromString(resultSet.getString("id"));
            String title = resultSet.getString("title");
            String description = resultSet.getString("description");
            LocalDate dueDate = resultSet.getDate("due_date").toLocalDate();
            Integer priority = resultSet.getInt("priority");
            String className = resultSet.getString("class_name");
            LocalDate startDate = resultSet.getDate("start_date").toLocalDate();
            Boolean reminder = resultSet.getBoolean("reminder");
            return new Task(id, title, description, dueDate, priority, className, null, startDate, reminder);
        });
    }

    @Override
    public Optional<Task> selectTaskById(UUID id) {
        final String sql = "SELECT id, title, description, due_date, priority, class_name, student_id, start_date, reminder FROM task WHERE id = ?";
        Task task = jdbcTemplate.queryForObject(sql, new Object[]{id}, (resultSet, i) -> {
            UUID taskId = UUID.fromString(resultSet.getString("id"));
            String title = resultSet.getString("title");
            String description = resultSet.getString("description");
            LocalDate dueDate = resultSet.getDate("due_date").toLocalDate();
            Integer priority = resultSet.getInt("priority");
            String className = resultSet.getString("class_name");
            UUID studentId = UUID.fromString(resultSet.getString("student_id"));
            LocalDate startDate = resultSet.getDate("start_date").toLocalDate();
            Boolean reminder = resultSet.getBoolean("reminder");
            return new Task(taskId, title, description, dueDate, priority, className, null, startDate, reminder);
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
        final String sql = "UPDATE task SET title = ?, description = ?, due_date = ?, priority = ?, class_name = ?, student_id = ?, start_date = ?, reminder = ? WHERE id = ?";
        return jdbcTemplate.update(sql, task.getTitle(), task.getDescription(), task.getDueDate(), task.getPriority(), task.getClassName(), task.getStudent().getId(), task.getStartDate(), task.getReminder(), id);
    }

    @Override
    public boolean existsById(UUID id) {
        final String sql = "SELECT COUNT(1) FROM task WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, Boolean.class);
    }
}
