import React, { useState, useEffect } from 'react';
import { Task, CreateTaskRequest } from './types/Task';
import { User } from './types/Auth';
import { taskService } from './services/taskService';
import { authService } from './services/authService';
import TaskForm from './components/TaskForm';
import TaskList from './components/TaskList';
import Login from './components/Login';
import './App.css';

const App: React.FC = () => {
  const [tasks, setTasks] = useState<Task[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [filter, setFilter] = useState<'all' | 'completed' | 'pending'>('all');
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [user, setUser] = useState<User | null>(null);

  // Check authentication status on component mount
  useEffect(() => {
    checkAuthStatus();
  }, []);

  // Load tasks when authenticated
  useEffect(() => {
    if (isAuthenticated) {
      loadTasks();
    }
  }, [isAuthenticated]);

  const checkAuthStatus = () => {
    const authenticated = authService.isAuthenticated();
    const currentUser = authService.getCurrentUser();
    setIsAuthenticated(authenticated);
    setUser(currentUser);
    setLoading(false);
  };

  const handleLoginSuccess = () => {
    checkAuthStatus();
  };

  const handleLogout = () => {
    authService.logout();
    setIsAuthenticated(false);
    setUser(null);
    setTasks([]);
  };

  const loadTasks = async () => {
    try {
      setLoading(true);
      setError(null);
      const fetchedTasks = await taskService.getAllTasks();
      setTasks(fetchedTasks);
    } catch (err) {
      setError('Failed to load tasks. Please check if the backend server is running.');
      // eslint-disable-next-line no-console
      console.error('Error loading tasks:', err);
    } finally {
      setLoading(false);
    }
  };

  const handleCreateTask = async (taskData: CreateTaskRequest) => {
    try {
      setError(null);
      const newTask = await taskService.createTask(taskData);
      setTasks(prevTasks => [...prevTasks, newTask]);
    } catch (err) {
      setError('Failed to create task. Please try again.');
      // eslint-disable-next-line no-console
      console.error('Error creating task:', err);
    }
  };

  const handleUpdateTask = async (id: number, taskData: Partial<Task>) => {
    try {
      setError(null);
      const updatedTask = await taskService.updateTask(id, taskData);
      setTasks(prevTasks =>
        prevTasks.map(task => task.id === id ? updatedTask : task)
      );
    } catch (err) {
      setError('Failed to update task. Please try again.');
      // eslint-disable-next-line no-console
      console.error('Error updating task:', err);
    }
  };

  const handleDeleteTask = async (id: number) => {
    try {
      setError(null);
      await taskService.deleteTask(id);
      setTasks(prevTasks => prevTasks.filter(task => task.id !== id));
    } catch (err) {
      setError('Failed to delete task. Please try again.');
      // eslint-disable-next-line no-console
      console.error('Error deleting task:', err);
    }
  };

  const filteredTasks = tasks.filter(task => {
    switch (filter) {
      case 'completed':
        return task.completed;
      case 'pending':
        return !task.completed;
      default:
        return true;
    }
  });

  const completedCount = tasks.filter(task => task.completed).length;
  const pendingCount = tasks.filter(task => !task.completed).length;

  // Show login page if not authenticated
  if (!isAuthenticated) {
    return <Login onLoginSuccess={handleLoginSuccess} />;
  }

  return (
    <div className="container">
      <header>
        <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
          <div>
            <h1>Task Manager</h1>
            <p>Welcome back, {user?.username}! Built with React + TypeScript + Spring Boot 4.0 + Java 25</p>
          </div>
          <button 
            onClick={handleLogout}
            className="btn btn-secondary"
            style={{ padding: '8px 16px', fontSize: '14px' }}
          >
            Logout
          </button>
        </div>
      </header>

      {error && (
        <div className="error">
          {error}
          <button 
            onClick={loadTasks} 
            style={{ marginLeft: '10px', padding: '5px 10px' }}
            className="btn"
          >
            Retry
          </button>
        </div>
      )}

      <div className="filter-buttons">
        <button
          className={`btn ${filter === 'all' ? 'btn-success' : 'btn-secondary'}`}
          onClick={() => setFilter('all')}
        >
          All ({tasks.length})
        </button>
        <button
          className={`btn ${filter === 'pending' ? 'btn-success' : 'btn-secondary'}`}
          onClick={() => setFilter('pending')}
        >
          Pending ({pendingCount})
        </button>
        <button
          className={`btn ${filter === 'completed' ? 'btn-success' : 'btn-secondary'}`}
          onClick={() => setFilter('completed')}
        >
          Completed ({completedCount})
        </button>
      </div>

      <TaskForm onSubmit={handleCreateTask} loading={loading} />
      
      <TaskList
        tasks={filteredTasks}
        onUpdateTask={handleUpdateTask}
        onDeleteTask={handleDeleteTask}
        loading={loading}
      />
    </div>
  );
};

export default App;
