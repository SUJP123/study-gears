import React from 'react';
import { Link } from 'react-router-dom';

function Home() {
    return (
        <div>
            <h1>Welcome to the Student Task Tracker</h1>
            <p>
                This is a platform where students can track their tasks and deadlines.
            </p>
            <Link to="/register">Register</Link> | <Link to="/login">Login</Link>
        </div>
    );
}

export default Home;
