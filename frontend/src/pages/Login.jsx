import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

function Login() {
    const [credentials, setCredentials] = useState({
        username: '',
        password: ''
    });
    const navigate = useNavigate();

    const handleChange = (e) => {
        const { name, value } = e.target;
        setCredentials({ ...credentials, [name]: value });
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        axios.post('http://localhost:8080/api/students/login', credentials)
            .then(response => {
                alert('Login successful!');
                const studentId = response.data.id; // Get the student ID from the response
                localStorage.setItem('studentId', studentId); // Store the student ID
                navigate('/dashboard'); // Redirect to dashboard after successful login
            })
            .catch(error => {
                alert('Error: ' + error.response.data);
            });
    };

    return (
        <div>
            <h2>Login</h2>
            <form onSubmit={handleSubmit}>
                <input type="text" name="username" placeholder="Username" value={credentials.username} onChange={handleChange} required />
                <input type="password" name="password" placeholder="Password" value={credentials.password} onChange={handleChange} required />
                <button type="submit">Login</button>
            </form>
        </div>
    );
}

export default Login;
