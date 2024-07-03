import React, { useState, useEffect } from 'react';
import axios from 'axios';
import TaskTracker from './TaskTracker';
import Chatbot from './Chatbot';
import '../styles/Dashboard.css';

function Dashboard() {
    const [user, setUser] = useState(null);
    const [view, setView] = useState('tasks'); // 'tasks' or 'chatbot'

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
        } else {
            alert('You must be logged in to view the dashboard.');
            window.location.href = '/login';
        }
    }, []);

    const handleToggleView = () => {
        setView(view === 'tasks' ? 'chatbot' : 'tasks');
    };

    return (
        <div className="dashboard">
            <h2>User Dashboard</h2>
            {user && (
                <div className="user-info">
                    <p>Welcome, {user.firstName} {user.lastName}!</p>
                    <p>Email: {user.email}</p>
                    <p>Username: {user.username}</p>
                </div>
            )}
            <button className="toggle-button" onClick={handleToggleView}>
                {view === 'tasks' ? 'Switch to Study Bot' : 'Switch to Task List'}
            </button>
            {view === 'tasks' ? (
                <TaskTracker studentId={localStorage.getItem('studentId')} />
            ) : (
                <Chatbot />
            )}
        </div>
    );
}

export default Dashboard;
