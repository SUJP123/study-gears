import React from 'react';
import { Link } from 'react-router-dom';
import '../styles/Home.css';
import GEARS from '../assets/gears.png';

function HomePage() {
    return (
        <div className="home" style={{backgroundImage: `url(${GEARS})`}}>
                <h1>Welcome to Study Gears</h1>
                <br></br>
                <br></br>
                <p>Your robotic assistant for managing tasks and studying efficiently.</p>
                <br></br>
                <br></br>
                <br></br>
                <p>Schedule duedates and deadlines for tasks, homework, or projects and
                        ask studyBot questions too help you maximize your study time. StudyBot will
                        help you by providing explanations that make topics clearer and easier to
                        understand and provide links to youtube videos to help you study.</p>
                <br></br>
                <br></br>
                <br></br>
                <p>Don't have an account, Register. If you already do, Log in!</p>
                <Link to="/register" className="button">Register</Link>
                <Link to="/login" className="button">Login</Link>
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
        </div>
    );
}

export default HomePage;