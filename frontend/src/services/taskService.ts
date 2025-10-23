import { Task, CreateTaskRequest, UpdateTaskRequest } from '../types/Task';
import { api } from './authService';

// Helper function to convert frontend task to backend format
const mapFrontendTask = (frontendTask: any): any => {
  try {
    const backendTask = { ...frontendTask };
    
    // Convert completed boolean to status
    if (frontendTask.completed !== undefined) {
      backendTask.status = frontendTask.completed ? 'COMPLETED' : 'PENDING';
      delete backendTask.completed;
    }
    
    return backendTask;
  } catch (error) {
    console.error('Error mapping frontend task:', error);
    throw new Error('Failed to map task data');
  }
};

// Helper function to convert backend task to frontend format
const mapBackendTask = (backendTask: any): Task => {
  try {
    return {
      id: backendTask.id,
      title: backendTask.title,
      description: backendTask.description,
      completed: backendTask.status === 'COMPLETED',
      priority: backendTask.priority,
      status: backendTask.status,
    };
  } catch (error) {
    console.error('Error mapping backend task:', error);
    throw new Error('Failed to map task data');
  }
};

export const taskService = {
  // Get all tasks
  getAllTasks: async (): Promise<Task[]> => {
    const response = await api.get('/tasks');
    return response.data.map(mapBackendTask);
  },

  // Create new task
  createTask: async (task: CreateTaskRequest): Promise<Task> => {
    const backendTask = mapFrontendTask(task);
    const response = await api.post('/tasks', backendTask);
    return mapBackendTask(response.data);
  },

  // Update task
  updateTask: async (id: number, task: UpdateTaskRequest): Promise<Task> => {
    const backendTask = mapFrontendTask(task);
    const response = await api.put(`/tasks/${id}`, backendTask);
    return mapBackendTask(response.data);
  },

  // Delete task
  deleteTask: async (id: number): Promise<void> => {
    await api.delete(`/tasks/${id}`);
  },
};
