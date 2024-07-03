import React, { useState, useEffect, useCallback } from 'react';
import axios from 'axios';
import '../styles/TaskTracker.css';
import TaskForm from './TaskForm';

const TaskTracker = ({ studentId }) => {
    const [tasks, setTasks] = useState([]);
    const [filteredTasks, setFilteredTasks] = useState([]);
    const [showForm, setShowForm] = useState(false);
    const [selectedTasks, setSelectedTasks] = useState([]);
    const [searchTerm, setSearchTerm] = useState('');
    const [filterCriteria, setFilterCriteria] = useState('');

    const fetchTasks = useCallback(() => {
        axios.get(`https://study-gears-6cac3ab804b6.herokuapp.com/api/tasks/student/${studentId}`)
            .then(response => {
                const fetchedTasks = response.data.map(task => ({
                    ...task,
                    dueDate: new Date(task.dueDate),
                }));
                setTasks(fetchedTasks);
                setFilteredTasks(fetchedTasks);
            })
            .catch(error => {
                console.error('Error fetching tasks:', error);
            });
    }, [studentId]);

    useEffect(() => {
        fetchTasks();
    }, [fetchTasks]);

    const handleTaskSave = (task) => {
        const taskWithDate = {
            ...task,
            dueDate: task.dueDate ? new Date(task.dueDate).toISOString().split('T')[0] : new Date().toISOString().split('T')[0]
        };

        if (task.id) {
            axios.put(`https://study-gears-6cac3ab804b6.herokuapp.com/api/tasks/${task.id}`, taskWithDate)
                .then(() => {
                    fetchTasks();
                })
                .catch(error => {
                    console.error('Error updating task:', error);
                });
        } else {
            axios.post(`https://study-gears-6cac3ab804b6.herokuapp.com/api/students/${studentId}/tasks`, taskWithDate)
                .then(() => {
                    fetchTasks();
                })
                .catch(error => {
                    console.error('Error creating task:', error);
                });
        }
        setShowForm(false);
        setSelectedTasks([]);
    };

    const handleEditTask = (task) => {
        setSelectedTasks([task]);
        setShowForm(true);
    };

    const handleDeleteTask = (taskId) => {
        axios.delete(`https://study-gears-6cac3ab804b6.herokuapp.com/api/tasks/${taskId}`)
            .then(() => {
                fetchTasks();
            })
            .catch(error => {
                console.error('Error deleting task:', error);
            });
    };

    const handleSearch = (e) => {
        setSearchTerm(e.target.value);
        filterTasks(e.target.value, filterCriteria);
    };

    const handleFilterChange = (e) => {
        setFilterCriteria(e.target.value);
        filterTasks(searchTerm, e.target.value);
    };

    const filterTasks = (searchTerm, filterCriteria) => {
        let filtered = tasks;
        if (searchTerm) {
            filtered = filtered.filter(task => task.title.toLowerCase().includes(searchTerm.toLowerCase()));
        }
        if (filterCriteria) {
            switch (filterCriteria) {
                case 'dueDate':
                    filtered = filtered.sort((a, b) => new Date(a.dueDate) - new Date(b.dueDate));
                    break;
                case 'priority':
                    filtered = filtered.sort((a, b) => a.priority - b.priority);
                    break;
                default:
                    break;
            }
        }
        setFilteredTasks(filtered);
    };

    return (
        <div className="task-tracker">
            <h3>Your Tasks</h3>
            <button className="add-task-button" onClick={() => setShowForm(true)}>Add New Task</button>
            <input
                type="text"
                placeholder="Search tasks"
                value={searchTerm}
                onChange={handleSearch}
            />
            <select onChange={handleFilterChange} value={filterCriteria}>
                <option value="">No filter</option>
                <option value="dueDate">Due Date</option>
                <option value="priority">Priority</option>
            </select>
            <ul className="task-list">
                {filteredTasks.map(task => (
                    <li key={task.id} className="task-item">
                        <h4>{task.title}</h4>
                        <p>Description: {task.description}</p>
                        <p>Priority: {task.priority}</p>
                        <p>Class: {task.className}</p>
                        <p>Due Date: {task.dueDate.toDateString()}</p>
                        <p>Start Date: {task.startDate}</p>
                        <p>Reminder: {task.reminder ? 'Yes' : 'No'}</p>
                        <button className="edit-button" onClick={() => handleEditTask(task)}>Edit</button>
                        <button className="delete-button" onClick={() => handleDeleteTask(task.id)}>Delete</button>
                    </li>
                ))}
            </ul>
            {showForm && (
                <TaskForm
                    studentId={studentId}
                    task={selectedTasks[0]}
                    onSave={handleTaskSave}
                    onCancel={() => {
                        setShowForm(false);
                        setSelectedTasks([]);
                    }}
                />
            )}
        </div>
    );
};

export default TaskTracker;