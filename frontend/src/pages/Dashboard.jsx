import React, { useState, useEffect } from 'react';
import axios from 'axios';
import TaskTracker from './TaskTracker';

function Dashboard() {
    const [user, setUser] = useState(null);
    const [question, setQuestion] = useState('');
    const [answer, setAnswer] = useState('');

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

    const handleQuestionSubmit = (e) => {
        e.preventDefault();
        axios.post('http://localhost:8080/api/studybot/ask', { question })
            .then(response => {
                setAnswer(response.data.answer);
            })
            .catch(error => {
                console.error('Error fetching answer:', error);
            });
    };

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
            <TaskTracker studentId={localStorage.getItem('studentId')} />
            <h3>Study Bot</h3>
            <form onSubmit={handleQuestionSubmit}>
                <input
                    type="text"
                    value={question}
                    onChange={(e) => setQuestion(e.target.value)}
                    placeholder="Ask a question"
                    required
                />
                <button type="submit">Ask</button>
            </form>
            {answer && (
                <div>
                    <h4>Answer:</h4>
                    <p>{answer}</p>
                </div>
            )}
        </div>
    );
}

export default Dashboard;