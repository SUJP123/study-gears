import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Home from './pages/Home';
import Registration from './pages/Registration';
import Login from './pages/Login';
import TaskForm from './pages/TaskForm';
import Dashboard from './pages/Dashboard';
import TaskTracker from "./pages/TaskTracker";
import './styles/App.css';

function App() {
    return (
        <Router>
            <div className="App">
                <Routes>
                    <Route path="/" element={<Home />} />
                    <Route path="/register" element={<Registration />} />
                    <Route path="/login" element={<Login />} />
                    <Route path="/dashboard" element={<Dashboard />} />
                    <Route path="/tasks" element={<TaskForm />} />
                    <Route path="/tasks" element={<TaskTracker />} />
                </Routes>
            </div>
        </Router>
    );
}

export default App;
