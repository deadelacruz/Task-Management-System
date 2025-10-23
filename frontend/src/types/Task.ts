export type Priority = 'LOW' | 'MEDIUM' | 'HIGH' | 'URGENT';
export type TaskStatus = 'PENDING' | 'IN_PROGRESS' | 'COMPLETED' | 'CANCELLED';

export interface Task {
  id?: number;
  title: string;
  description?: string;
  completed: boolean;
  priority?: Priority;
  status?: TaskStatus;
}

export interface CreateTaskRequest {
  title: string;
  description?: string;
  completed?: boolean;
  priority: Priority;
  status?: TaskStatus;
}

export interface UpdateTaskRequest {
  title?: string;
  description?: string;
  completed?: boolean;
  priority?: Priority;
  status?: TaskStatus;
}
