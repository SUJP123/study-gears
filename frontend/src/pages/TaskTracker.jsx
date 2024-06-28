import React, { useState, useEffect } from 'react';
import axios from 'axios';

function TaskTracker() {
    const [tasks, setTasks] = useState([]);
    const [newTask, setNewTask] = useState({
        title: '',
        description: '',
        dueDate: '',
        priority: '',
        className: ''
    });

    useEffect(() => {
        const token = localStorage.getItem('token');
        if (token) {
            axios.get('http://localhost:8080/api/students/tasks', {
                headers: {
                    'Authorization': token
                }
            })
                .then(response => {
                    setTasks(response.data);
                })
                .catch(error => {
                    console.error('Error fetching tasks:', error);
                });
        } else {
            alert('You must be logged in to view tasks.');
            window.location.href = '/login';
        }
    }, []);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setNewTask({ ...newTask, [name]: value });
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        const token = localStorage.getItem('token');
        axios.post('http://localhost:8080/api/students/tasks', newTask, {
            headers: {
                'Authorization': token
            }
        })
            .then(response => {
                setTasks([...tasks, response.data]);
                setNewTask({
                    title: '',
                    description: '',
                    dueDate: '',
                    priority: '',
                    className: ''
                });
            })
            .catch(error => {
                console.error('Error adding task:', error);
            });
    };

    return (
        <div>
            <h2>Task Tracker</h2>
            <form onSubmit={handleSubmit}>
                <input type="text" name="title" placeholder="Title" value={newTask.title} onChange={handleChange} required />
                <input type="text" name="description" placeholder="Description" value={newTask.description} onChange={handleChange} required />
                <input type="date" name="dueDate" value={newTask.dueDate} onChange={handleChange} required />
                <input type="number" name="priority" placeholder="Priority" value={newTask.priority} onChange={handleChange} required />
                <input type="text" name="className" placeholder="Class Name" value={newTask.className} onChange={handleChange} required />
                <button type="submit">Add Task</button>
            </form>
            <h3>Your Tasks</h3>
            <ul>
                {tasks.map(task => (
                    <li key={task.id}>
                        <h4>{task.title}</h4>
                        <p>{task.description}</p>
                        <p>Due Date: {task.dueDate}</p>
                        <p>Priority: {task.priority}</p>
                        <p>Class: {task.className}</p>
                    </li>
                ))}
            </ul>
        </div>
    );
}

export default TaskTracker;
