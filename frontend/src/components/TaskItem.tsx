import React, { useState } from 'react';
import { Task, Priority, TaskStatus } from '../types/Task';
import './TaskItem.css';

interface TaskItemProps {
  task: Task;
  onUpdate: (id: number, task: Partial<Task>) => void;
  onDelete: (id: number) => void;
}

const TaskItem: React.FC<TaskItemProps> = ({ task, onUpdate, onDelete }) => {
  const [isEditing, setIsEditing] = useState(false);
  const [editTitle, setEditTitle] = useState(task.title);
  const [editDescription, setEditDescription] = useState(task.description || '');
  const [editPriority, setEditPriority] = useState<Priority>(task.priority || 'MEDIUM');

  const handleToggleComplete = () => {
    const newStatus: TaskStatus = task.completed ? 'PENDING' : 'COMPLETED';
    onUpdate(task.id!, { status: newStatus });
  };

  const handleSaveEdit = () => {
    if (editTitle.trim()) {
      onUpdate(task.id!, {
        title: editTitle.trim(),
        description: editDescription.trim(),
        priority: editPriority,
      });
      setIsEditing(false);
    }
  };

  const handleCancelEdit = () => {
    setEditTitle(task.title);
    setEditDescription(task.description || '');
    setEditPriority(task.priority || 'MEDIUM');
    setIsEditing(false);
  };

  return (
    <div className={`task-item ${task.completed ? 'completed' : ''}`}>
      <div className="task-content">
        {isEditing ? (
          <div className="edit-form">
            <div className="form-group">
              <input
                type="text"
                value={editTitle}
                onChange={(e) => setEditTitle(e.target.value)}
                placeholder="Task title"
                className="edit-input"
              />
            </div>
            <div className="form-group">
              <textarea
                value={editDescription}
                onChange={(e) => setEditDescription(e.target.value)}
                rows={2}
                placeholder="Description (optional)"
                className="edit-textarea"
              />
            </div>
            <div className="form-group">
              <select
                value={editPriority}
                onChange={(e) => setEditPriority(e.target.value as Priority)}
                className="edit-input"
              >
                <option value="LOW">Low</option>
                <option value="MEDIUM">Medium</option>
                <option value="HIGH">High</option>
                <option value="URGENT">Urgent</option>
              </select>
            </div>
          </div>
        ) : (
          <div>
            <div className="task-title">{task.title}</div>
            {task.description && (
              <div className="task-description">{task.description}</div>
            )}
            {task.priority && (
              <div className="task-priority">
                Priority: <span className={`priority-${task.priority.toLowerCase()}`}>{task.priority}</span>
              </div>
            )}
          </div>
        )}
      </div>
      
      <div className="task-actions">
        {isEditing ? (
          <>
            <button className="btn btn-success" onClick={handleSaveEdit}>
              Save
            </button>
            <button className="btn" onClick={handleCancelEdit}>
              Cancel
            </button>
          </>
        ) : (
          <>
            <button
              className={`btn ${task.completed ? 'btn-success' : ''}`}
              onClick={handleToggleComplete}
            >
              {task.completed ? 'Completed' : 'Mark Complete'}
            </button>
            <button className="btn" onClick={() => setIsEditing(true)}>
              Edit
            </button>
            <button
              className="btn btn-danger"
              onClick={() => onDelete(task.id!)}
            >
              Delete
            </button>
          </>
        )}
      </div>
    </div>
  );
};

export default TaskItem;
