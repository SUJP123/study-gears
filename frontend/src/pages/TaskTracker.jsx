import React, { useState, useEffect, useCallback } from 'react';
import axios from 'axios';
import TaskForm from './TaskForm';

const TaskTracker = ({ studentId }) => {
    const [tasks, setTasks] = useState([]);
    const [selectedTask, setSelectedTask] = useState(null);
    const [showForm, setShowForm] = useState(false);

    const fetchTasks = useCallback(() => {
        axios.get(`http://localhost:8080/api/tasks/student/${studentId}`)
            .then(response => {
                setTasks(response.data);
            })
            .catch(error => {
                console.error('Error fetching tasks:', error);
            });
    }, [studentId]);

    useEffect(() => {
        fetchTasks();
    }, [fetchTasks]);

    const handleTaskSave = (task) => {
        if (task.id) {
            axios.put(`http://localhost:8080/api/tasks/${task.id}`, task)
                .then(() => {
                    fetchTasks(); // Refetch tasks after updating
                })
                .catch(error => {
                    console.error('Error updating task:', error);
                });
        } else {
            axios.post(`http://localhost:8080/api/students/${studentId}/tasks`, task)
                .then(() => {
                    fetchTasks(); // Refetch tasks after adding
                })
                .catch(error => {
                    console.error('Error creating task:', error);
                });
        }
        setShowForm(false);
        setSelectedTask(null);
    };

    const handleEditTask = (task) => {
        setSelectedTask(task);
        setShowForm(true);
    };

    const handleDeleteTask = (taskId) => {
        if (!taskId) {
            console.error('Invalid task ID');
            return;
        }

        axios.delete(`http://localhost:8080/api/tasks/${taskId}`)
            .then(() => {
                fetchTasks(); // Refetch tasks after deleting
            })
            .catch(error => {
                console.error('Error deleting task:', error);
            });
    };

    return (
        <div>
            <h3>Your Tasks</h3>
            <button onClick={() => setShowForm(true)}>Add New Task</button>
            <ul>
                {tasks.map(task => (
                    <li key={task.id}>
                        <h4>{task.title}</h4>
                        <p>{task.description}</p>
                        <p>Priority: {task.priority}</p>
                        <p>Class: {task.className}</p>
                        <p>Due Date: {task.dueDate}</p>
                        <p>Start Date: {task.startDate}</p>
                        <p>Reminder: {task.reminder ? 'Yes' : 'No'}</p>
                        <button onClick={() => handleEditTask(task)}>Edit</button>
                        <button onClick={() => handleDeleteTask(task.id)}>Delete</button>
                    </li>
                ))}
            </ul>
            {showForm && (
                <TaskForm
                    studentId={studentId}
                    task={selectedTask}
                    onSave={handleTaskSave}
                    onCancel={() => {
                        setShowForm(false);
                        setSelectedTask(null);
                    }}
                />
            )}
        </div>
    );
};

export default TaskTracker;
