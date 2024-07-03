import React, { useState } from 'react';
import axios from 'axios';
import '../styles/Login.css';

function Login() {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    const handleSubmit = (e) => {
        e.preventDefault();
        axios.post('https://study-gears-6cac3ab804b6.herokuapp.com/api/students/login', {
            username,
            password,
        })
            .then((response) => {
                localStorage.setItem('studentId', response.data.id);
                alert('Login successful');
                window.location.href = '/dashboard';
            })
            .catch(error => {
                console.error('Error logging in:', error);
            });
    };

    return (
        <div className="login">
            <br></br>
            <form className="login-form" onSubmit={handleSubmit}>
                <h2>Login</h2>
                <input type="text" placeholder="Username" value={username} onChange={(e) => setUsername(e.target.value)}
                       required/>
                <input type="password" placeholder="Password" value={password}
                       onChange={(e) => setPassword(e.target.value)} required/>
                <button type="submit">Login</button>
            </form>
            <br></br>
            <br></br>
            <br></br>
            <br></br>
            <br></br>
            <br></br>
            <br></br>
            <br></br>
            <br></br>
            <br></br>
            <br></br>
            <br></br>
            <p>This WebApp Was Created By Sujay Patel</p>
            <br></br>
            <br></br>
            <br></br>
            <br></br>
        </div>
    );
}

export default Login;
