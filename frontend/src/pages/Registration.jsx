import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

function Registration() {
    const [user, setUser] = useState({
        firstName: '',
        lastName: '',
        email: '',
        username: '',
        password: ''
    });
    const navigate = useNavigate();

    const handleChange = (e) => {
        const { name, value } = e.target;
        setUser({ ...user, [name]: value });
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        axios.post('http://localhost:8080/api/students', user)
            .then(response => {
                alert('Registration successful!');
                navigate('/login'); // Redirect to login page after successful registration
            })
            .catch(error => {
                alert('Error: ' + error.message);
            });
    };

    return (
        <div>
            <h2>Register</h2>
            <form onSubmit={handleSubmit}>
                <input type="text" name="firstName" placeholder="First Name" value={user.firstName} onChange={handleChange} required />
                <input type="text" name="lastName" placeholder="Last Name" value={user.lastName} onChange={handleChange} required />
                <input type="email" name="email" placeholder="Email" value={user.email} onChange={handleChange} required />
                <input type="text" name="username" placeholder="Username" value={user.username} onChange={handleChange} required />
                <input type="password" name="password" placeholder="Password" value={user.password} onChange={handleChange} required />
                <button type="submit">Register</button>
            </form>
        </div>
    );
}

export default Registration;
