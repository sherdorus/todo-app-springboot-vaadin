# 📝 Todo App

A modern, full-stack Todo application built with **Spring Boot** and **Vaadin** that provides both a REST API and a beautiful web interface for task management.

## 🚀 Features

### Core Functionality
- ✅ **CRUD Operations** - Create, read, update, and delete tasks
- 🔍 **Search & Filter** - Find tasks by title or description
- 📊 **Priority System** - Organize tasks by priority (Low, Medium, High, Urgent)
- 📅 **Due Dates** - Set and track task deadlines
- ⚡ **Status Management** - Mark tasks as complete/incomplete
- 📈 **Statistics Dashboard** - View task statistics at a glance

### Advanced Features
- 🚨 **Overdue Detection** - Automatically identify overdue tasks
- 📱 **Responsive Design** - Works seamlessly on desktop and mobile
- 🎨 **Modern UI** - Clean, intuitive interface with dark theme support
- 🔄 **Real-time Updates** - Instant UI updates without page refresh
- 💾 **Data Persistence** - H2 in-memory database with JPA
- 🌐 **REST API** - Full RESTful API for external integrations

## 🛠️ Tech Stack

### Backend
- **Java 17+** - Modern Java features and syntax
- **Spring Boot 3.x** - Application framework
- **Spring Data JPA** - Data persistence layer
- **H2 Database** - In-memory database for quick setup
- **Bean Validation** - Input validation
- **Lombok** - Reduces boilerplate code

### Frontend
- **Vaadin 24** - Modern Java web framework
- **Lumo Theme** - Responsive, accessible UI components
- **Custom CSS** - Enhanced styling and animations

### Development Tools
- **Maven** - Dependency management and build tool
- **SLF4J + Logback** - Logging framework

## 🏗️ Architecture

```
src/main/java/io/sherdor/todoapp/
├── config/
│   ├── AppShellConfig.java          # Vaadin app configuration
│   └── DataInitializer.java         # Sample data loader
├── controller/
│   └── TodoController.java          # REST API endpoints
├── entity/
│   └── Todo.java                    # JPA entity with validation
├── repository/
│   └── TodoRepository.java          # Data access layer
├── service/
│   └── TodoService.java             # Business logic layer
└── view/
    └── TodoMainView.java            # Vaadin UI components
```

## 🚦 Quick Start

### Prerequisites
- Java 17 or higher
- Maven 3.6+

### Installation & Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/todo-app.git
   cd todo-app
   ```

2. **Build the project**
   ```bash
   mvn clean install
   ```

3. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

4. **Access the application**
    - **Web Interface**: http://localhost:8080
    - **H2 Database Console**: http://localhost:8080/h2-console
    - **REST API**: http://localhost:8080/api/todos

### Database Configuration
The application uses H2 in-memory database with the following default settings:
- **URL**: `jdbc:h2:mem:todoapp`
- **Username**: `sa`
- **Password**: `password`

## 📡 API Endpoints

### Todos Management
```http
GET    /api/todos              # Get all todos
GET    /api/todos/active       # Get active todos
GET    /api/todos/completed    # Get completed todos
GET    /api/todos/{id}         # Get todo by ID
POST   /api/todos              # Create new todo
PUT    /api/todos/{id}         # Update todo
DELETE /api/todos/{id}         # Delete todo
```

### Status Management
```http
PATCH  /api/todos/{id}/complete    # Mark todo as completed
PATCH  /api/todos/{id}/incomplete  # Mark todo as incomplete
```

### Search & Filter
```http
GET    /api/todos/search?q={query}  # Search todos by title/description
GET    /api/todos/overdue           # Get overdue todos
GET    /api/todos/today             # Get today's todos
GET    /api/todos/stats             # Get statistics
```

### Example API Usage

**Create a new todo:**
```bash
curl -X POST http://localhost:8080/api/todos \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Complete project",
    "description": "Finish the todo app project",
    "priority": "HIGH",
    "dueDate": "2024-12-31T23:59:59"
  }'
```

**Search todos:**
```bash
curl "http://localhost:8080/api/todos/search?q=project"
```

## 🎯 Usage Examples

### Web Interface
1. **Creating Tasks**: Click "Add Task" button, fill in the form, and save
2. **Managing Tasks**: Use action buttons to complete, edit, or delete tasks
3. **Searching**: Type in the search box to filter tasks in real-time
4. **Statistics**: Click on stat counters to filter tasks by status

### Sample Data
The application automatically loads sample data on first run, including:
- Tasks with different priorities
- Completed and active tasks
- Overdue tasks for testing
- Tasks with and without due dates

## 📁 Project Structure Details

### Key Components

**Todo Entity (`Todo.java`)**
- JPA entity with validation annotations
- Builder pattern for easy object creation
- Automatic timestamp management
- Priority enum with display names

**TodoService (`TodoService.java`)**
- Business logic implementation
- Transaction management
- Statistics calculation
- Search functionality

**TodoController (`TodoController.java`)**
- RESTful API endpoints
- Proper HTTP status codes
- Error handling and validation
- CORS configuration

**TodoMainView (`TodoMainView.java`)**
- Vaadin UI components
- Real-time updates
- Responsive grid layout
- Interactive dialogs and forms

## 🔧 Configuration

### Application Properties
```properties
# Server Configuration
server.port=8080

# Database Configuration
spring.datasource.url=jdbc:h2:mem:todoapp
spring.h2.console.enabled=true

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### Customization
- **Themes**: Modify `frontend/themes/todo/styles.css` for custom styling
- **Database**: Switch to PostgreSQL/MySQL by updating `application.properties`
- **Validation**: Add custom validation in `Todo.java` entity

## 🧪 Testing

The application includes comprehensive error handling and validation:
- **Input Validation**: Bean Validation annotations
- **Error Responses**: Proper HTTP status codes
- **Exception Handling**: Graceful error handling in UI and API
- **Data Integrity**: JPA constraints and transactions

## 📱 Responsive Design

The application is fully responsive and works on:
- **Desktop**: Full-featured interface
- **Tablet**: Optimized layout
- **Mobile**: Touch-friendly interface with adapted controls

## 🔒 Security Considerations

For production deployment, consider:
- Adding Spring Security for authentication
- Implementing CSRF protection
- Using HTTPS
- Securing H2 console or switching to production database
- Adding rate limiting for API endpoints

## 🚀 Deployment

### Local Development
```bash
mvn spring-boot:run
```

### Production Build
```bash
mvn clean package -Pproduction
java -jar target/todo-app-1.0.0.jar
```

### Docker Deployment
```dockerfile
FROM openjdk:17-jdk-slim
COPY target/todo-app-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the [MIT License](LICENSE).