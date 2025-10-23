import React from 'react';
import { Task } from '../types/Task';
import TaskItem from './TaskItem';
import './TaskList.css';

interface TaskListProps {
  tasks: Task[];
  onUpdateTask: (id: number, task: Partial<Task>) => void;
  onDeleteTask: (id: number) => void;
  loading?: boolean;
}

const TaskList: React.FC<TaskListProps> = ({ 
  tasks, 
  onUpdateTask, 
  onDeleteTask, 
  loading = false 
}) => {
  if (loading) {
    return (
      <div className="task-list">
        <div className="loading">Loading tasks...</div>
      </div>
    );
  }

  if (tasks.length === 0) {
    return (
      <div className="task-list">
        <div className="no-tasks">
          <p>No tasks found. Create your first task above!</p>
        </div>
      </div>
    );
  }

  return (
    <div className="task-list">
      <h3>Tasks ({tasks.length})</h3>
      <div className="task-list-content">
        {tasks.map((task) => (
          <TaskItem
            key={task.id}
            task={task}
            onUpdate={onUpdateTask}
            onDelete={onDeleteTask}
          />
        ))}
      </div>
    </div>
  );
};

export default TaskList;
