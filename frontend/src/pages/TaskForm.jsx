import React, { useState, useEffect } from 'react';

function TaskForm({ studentId, task, onSave, onCancel }) {
    const [title, setTitle] = useState('');
    const [description, setDescription] = useState('');
    const [dueDate, setDueDate] = useState('');
    const [priority, setPriority] = useState(1);
    const [className, setClassName] = useState('');
    const [startDate, setStartDate] = useState('');
    const [reminder, setReminder] = useState(false);

    useEffect(() => {
        if (task) {
            setTitle(task.title);
            setDescription(task.description);
            setDueDate(task.dueDate);
            setPriority(task.priority);
            setClassName(task.className);
            setStartDate(task.startDate);
            setReminder(task.reminder);
        }
    }, [task]);

    const handleSubmit = (e) => {
        e.preventDefault();
        const newTask = { id: task?.id, title, description, dueDate, priority, className, startDate, reminder, student: { id: studentId } };
        onSave(newTask);
    };

    return (
        <form onSubmit={handleSubmit}>
            <input type="text" value={title} onChange={(e) => setTitle(e.target.value)} placeholder="Title" required />
            <textarea value={description} onChange={(e) => setDescription(e.target.value)} placeholder="Description" />
            <input type="date" value={dueDate} onChange={(e) => setDueDate(e.target.value)} required />
            <input type="number" value={priority} onChange={(e) => setPriority(e.target.value)} placeholder="Priority" min="1" />
            <input type="text" value={className} onChange={(e) => setClassName(e.target.value)} placeholder="Class" />
            <input type="date" value={startDate} onChange={(e) => setStartDate(e.target.value)} />
            <label>
                <input type="checkbox" checked={reminder} onChange={(e) => setReminder(e.target.checked)} />
                Set Reminder
            </label>
            <button type="submit">Save Task</button>
            <button type="button" onClick={onCancel}>Cancel</button>
        </form>
    );
}

export default TaskForm;