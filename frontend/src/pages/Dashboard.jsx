import React, { useState, useEffect } from 'react';
import axios from 'axios';

function Dashboard() {
    const [user, setUser] = useState(null);
    const [tasks, setTasks] = useState([]);

    useEffect(() => {
        const studentId = localStorage.getItem('studentId');
        if (studentId) {
            axios.get(`http://localhost:8080/api/students/${studentId}`)
                .then(response => {
                    setUser(response.data);
                })
                .catch(error => {
                    console.error('Error fetching user details:', error);
                });

            axios.get(`http://localhost:8080/api/students/${studentId}/tasks`)
                .then(response => {
                    setTasks(response.data);
                })
                .catch(error => {
                    console.error('Error fetching tasks:', error);
                });
        } else {
            alert('You must be logged in to view the dashboard.');
            window.location.href = '/login';
        }
    }, []);

    return (
        <div>
            <h2>User Dashboard</h2>
            {user && (
                <div>
                    <p>Welcome, {user.firstName} {user.lastName}!</p>
                    <p>Email: {user.email}</p>
                    <p>Username: {user.username}</p>
                </div>
            )}
            <h3>Your Tasks</h3>
            <ul>
                {tasks.map(task => (
                    <li key={task.id}>
                        <h4>{task.title}</h4>
                        <p>{task.description}</p>
                        <p>Priority: {task.priority}</p>
                        <p>Class: {task.className}</p>
                        <p>Due Date: {task.dueDate}</p>
                    </li>
                ))}
            </ul>
        </div>
    );
}

export default Dashboard;
