# Task Management System - React + Spring Boot 4.0 + Java 25

A modern, full-stack task management application built with cutting-edge technologies and clean architecture principles.

## 🚀 Quick Start

### Prerequisites
- **Java 25** (or Java 21+)
- **Node.js 18+** and **npm**
- **Gradle 8+** (included via wrapper)

### Run the Application

```bash
# Start the backend server
.\gradlew.bat bootRun

# The application will be available at http://localhost:8083
```

### Default Login Credentials

The application creates 3 default users automatically:

| Username | Password | Role |
|----------|----------|------|
| `admin` | `admin123` | Administrator |
| `user` | `user123` | User |
| `jane` | `jane123` | User |

## 🎯 Features

### Core Functionality
- **Task Management**: Create, read, update, and delete tasks
- **Task Filtering**: Filter tasks by status (All, Pending, Completed)
- **Priority Levels**: LOW, MEDIUM, HIGH, URGENT
- **Task Status**: PENDING, IN_PROGRESS, COMPLETED, CANCELLED
- **User Authentication**: JWT-based secure authentication
- **Real-time Updates**: Dynamic task management with instant feedback

### User Interface
- **Modern React UI**: Built with React 18 and TypeScript
- **Responsive Design**: Works on desktop, tablet, and mobile
- **Color-coded Buttons**: Intuitive button styling with hover effects
- **Task Cards**: Clean, organized task display
- **Form Validation**: Client-side and server-side validation

### Technical Features
- **Clean Architecture**: Domain, Application, Infrastructure, and Presentation layers
- **JWT Security**: Secure token-based authentication
- **RESTful API**: Well-structured API endpoints
- **Type Safety**: Full TypeScript implementation
- **Error Handling**: Comprehensive error handling and user feedback
- **Data Validation**: Custom validators for business rules

## 🏗️ Architecture

```
┌─────────────────┐    HTTP/REST    ┌─────────────────┐
│   React.js      │ ◄─────────────► │  Spring Boot    │
│   Frontend      │                 │  Backend        │
│   (Port 3000)   │                 │  (Port 8083)    │
└─────────────────┘                 └─────────────────┘
         │                                   │
         │                                   │
    ┌─────────┐                         ┌─────────┐
    │  Build  │                         │  Build  │
    │  Static │                         │  JAR    │
    │  Files  │                         │  File   │
    └─────────┘                         └─────────┘
```

## 📡 API Endpoints

### Authentication
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/auth/login` | User login |
| POST | `/api/auth/register` | User registration |

### Task Management
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/tasks` | Get all tasks |
| POST | `/api/tasks` | Create new task |
| PUT | `/api/tasks/{id}` | Update task |
| DELETE | `/api/tasks/{id}` | Delete task |
| GET | `/api/tasks/status/{status}` | Get tasks by status |

## 🗄️ Database

- **Database**: H2 in-memory database
- **H2 Console**: http://localhost:8083/h2-console
- **JDBC URL**: `jdbc:h2:mem:testdb`
- **Username**: `sa`
- **Password**: `password`

## 🛠️ Development

### Backend Development

```bash
# Run Spring Boot application
.\gradlew.bat bootRun

# Run tests
.\gradlew.bat test

# Build the project
.\gradlew.bat build
```

### Frontend Development

```bash
# Install dependencies
cd frontend
npm install

# Start development server
npm start

# Run type checking
npm run type-check

# Run linting
npm run lint
```

### Full-Stack Development

```bash
# Terminal 1: Backend
.\gradlew.bat bootRun

# Terminal 2: Frontend
cd frontend && npm start
```

## 🏭 Production Build

### Build Standalone Application

```bash
# Build everything (React + Spring Boot)
.\gradlew.bat build

# Run the standalone JAR
java -jar build/libs/demo-0.0.1-SNAPSHOT.jar
```

The application will be available at `http://localhost:8083` with both frontend and backend served from the same port.

## 📦 Project Structure

```
demo/
├── src/main/java/com/example/demo/
│   ├── domain/                    # Domain Layer (Business Logic)
│   │   ├── entity/               # Domain Entities (Task, User)
│   │   ├── valueobject/          # Value Objects (Priority, TaskStatus, UserRole)
│   │   └── repository/           # Repository Interfaces
│   ├── application/              # Application Layer (Use Cases)
│   │   ├── dto/                  # Data Transfer Objects
│   │   ├── mapper/               # Entity-DTO Mappers
│   │   └── usecase/              # Use Case Implementations
│   │       ├── auth/             # Authentication use cases
│   │       └── task/             # Task management use cases
│   ├── infrastructure/           # Infrastructure Layer
│   │   ├── config/               # Configuration Classes
│   │   ├── exception/            # Exception Handling
│   │   ├── security/             # Security Components (JWT)
│   │   └── validation/           # Custom Validators
│   └── presentation/             # Presentation Layer
│       └── controller/           # REST Controllers
├── src/main/resources/
│   ├── application.properties    # Application configuration
│   └── static/                   # React build output
├── frontend/
│   ├── src/
│   │   ├── components/           # React components
│   │   │   ├── Login.tsx         # Login component
│   │   │   ├── TaskForm.tsx      # Task creation form
│   │   │   ├── TaskList.tsx      # Task list display
│   │   │   └── TaskItem.tsx      # Individual task item
│   │   ├── services/             # API services
│   │   │   ├── authService.ts    # Authentication service
│   │   │   └── taskService.ts    # Task management service
│   │   ├── types/                # TypeScript type definitions
│   │   │   ├── Auth.ts           # Authentication types
│   │   │   └── Task.ts           # Task-related types
│   │   └── App.tsx               # Main application component
│   ├── package.json
│   └── tsconfig.json
├── build.gradle.kts              # Gradle build configuration
└── README.md
```

## 🔧 Configuration

### Application Properties

Key configurations in `src/main/resources/application.properties`:

- **Server Port**: 8083
- **Database**: H2 in-memory database
- **JPA/Hibernate**: Auto-create tables, show SQL
- **JWT Security**: Token-based authentication
- **CORS**: Configured for frontend-backend communication
- **Actuator**: Health checks and metrics

### Frontend Configuration

- **API Base URL**: `http://localhost:8083/api`
- **TypeScript**: Strict mode enabled
- **ESLint**: Code quality and consistency
- **Prettier**: Code formatting

## 🎨 UI Features

### Button Color System
- **Primary**: Blue buttons for main actions
- **Success**: Green buttons for positive actions
- **Danger**: Red buttons for delete actions
- **Secondary**: Gray buttons for secondary actions
- **Hover Effects**: Smooth animations and visual feedback

### Task Management Interface
- **Task Cards**: Clean, organized display
- **Priority Indicators**: Color-coded priority levels
- **Status Filtering**: Easy task filtering
- **Inline Editing**: Edit tasks directly in the interface
- **Responsive Design**: Works on all screen sizes

## 🧪 Testing

```bash
# Run backend tests
.\gradlew.bat test

# Run frontend tests
cd frontend && npm test

# Run type checking
cd frontend && npm run type-check

# Run linting
cd frontend && npm run lint
```

## 🔍 Monitoring

- **Health Check**: http://localhost:8083/actuator/health
- **Metrics**: http://localhost:8083/actuator/metrics
- **Info**: http://localhost:8083/actuator/info
- **H2 Console**: http://localhost:8083/h2-console

## 🛡️ Security

- **JWT Authentication**: Secure token-based authentication
- **Password Encryption**: BCrypt password hashing
- **CORS Configuration**: Secure cross-origin requests
- **Input Validation**: Server-side validation for all inputs
- **SQL Injection Protection**: JPA/Hibernate parameterized queries

## 🚀 Deployment

### Standalone JAR Deployment

```bash
# Build the application
.\gradlew.bat build

# Run the JAR file
java -jar build/libs/demo-0.0.1-SNAPSHOT.jar
```

### Docker Deployment (Optional)

```dockerfile
FROM openjdk:25-jdk-slim
COPY build/libs/demo-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

## 📝 Sample Data

The application automatically creates:

- **3 Default Users**: admin, user, jane
- **10 Sample Tasks**: Various statuses and priorities
- **User Roles**: Admin and regular users
- **Task Assignments**: Tasks assigned to different users

## 🔧 Troubleshooting

### Common Issues

1. **Port 8083 already in use**: Change the port in `application.properties`
2. **Frontend not loading**: Ensure the React build is copied to static resources
3. **Authentication issues**: Check JWT configuration and token expiration
4. **Database issues**: H2 console is available for debugging

### Development Tips

- Use the H2 console to inspect the database
- Check browser developer tools for API calls
- Monitor application logs for backend issues
- Use the actuator endpoints for health checks

## 📄 License

This project is for demonstration purposes.

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

---

**Built with ❤️ using Java 25, Spring Boot 4.0, React 18, and TypeScript**