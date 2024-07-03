import React, { useState } from 'react';
import axios from 'axios';
import '../styles/Registration.css';

function Registration() {
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [email, setEmail] = useState('');
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    const handleSubmit = (e) => {
        e.preventDefault();
        axios.post('https://study-gears-6cac3ab804b6.herokuapp.com/api/students', {
            firstName,
            lastName,
            email,
            username,
            password,
        })
            .then(() => {
                alert('Registration successful');
                window.location.href = '/login';
            })
            .catch(error => {
                console.error('Error registering:', error);
            });
    };

    return (
        <div className="register">
            <br></br>
            <form className="registration-form" onSubmit={handleSubmit}>
                <h2>Register</h2>
                <input type="text" placeholder="First Name" value={firstName}
                       onChange={(e) => setFirstName(e.target.value)} required/>
                <input type="text" placeholder="Last Name" value={lastName}
                       onChange={(e) => setLastName(e.target.value)} required/>
                <input type="email" placeholder="Email" value={email} onChange={(e) => setEmail(e.target.value)}
                       required/>
                <input type="text" placeholder="Username" value={username} onChange={(e) => setUsername(e.target.value)}
                       required/>
                <input type="password" placeholder="Password" value={password}
                       onChange={(e) => setPassword(e.target.value)} required/>
                <button type="submit">Register</button>
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

export default Registration;
