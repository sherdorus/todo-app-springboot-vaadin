# 📝 Todo App

![Java](https://img.shields.io/badge/Java-17-orange?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-brightgreen?logo=springboot)
![Vaadin](https://img.shields.io/badge/Vaadin-24-blue?logo=vaadin)
![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)

A modern, full-featured Todo application built with Spring Boot and Vaadin. This application provides a clean, responsive interface for managing your tasks with priority levels, due dates, and comprehensive filtering options.

---

## Live Demo

**Try it live:** [Todo App on Render](https://todo-app-springboot-vaadin.onrender.com)  
*(It may take 20–30 seconds to wake up if the Render free instance is asleep.)*

> The demo uses an in-memory H2 database — data resets automatically after each redeploy.

---

## Deployment Details

- **Platform:** Render
- **Runtime:** Java 17
- **Containerization:** Docker
- **CI/CD:** GitHub Actions (auto build & deploy on push to `main`)
- **Build Command:** `./mvnw clean package -DskipTests`
- **Start Command:** `java -jar target/todo-app-0.0.1-SNAPSHOT.jar`

---

## Features

- **Task Management** – Create, edit, delete, and mark tasks as completed
- 🏷**Priority System** – Low, Medium, High, and Urgent
- **Due Dates** – Schedule tasks with date and time
- **Search & Filters** – Find tasks and view by status (active, completed, overdue)
- **Statistics Dashboard** – Real-time overview of your progress
- **Dark Theme** – Modern Lumo dark UI
- **Responsive Design** – Works on both desktop and mobile
- **Dockerized Deployment** – Easy to build and run anywhere
- **CI/CD** – Automated testing and deployment via GitHub Actions

---

## Technology Stack

- **Backend**:
  - Spring Boot 3.x
  - Spring Data JPA
  - MapStruct
  - Jakarta Validation
  - Lombok

- **Frontend**:
  - Vaadin Flow (Lumo Dark Theme)

- **Database**:
  - H2 (in-memory by default)

- **Build & Deploy**:
  - Maven
  - Docker
  - GitHub Actions
  - Render Cloud Platform

---

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher

---

## Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/sherdorus/todo-console.git
cd todo-console
```

### 2. Build the Application

```bash
mvn clean install
```

### 3. Run the Application

```bash
mvn spring-boot:run
```

### 4. Access the Application

Open your browser and navigate to: `http://localhost:8080`

## Docker

### Run with Docker

```bash
# Build image
docker build -t todo-app .

# Run container
docker run -p 8080:8080 todo-app
```

## Production Build

Create a production JAR:
```bash
mvn clean package -Pproduction
```
Run it manually:
```bash
java -jar target/todo-app-0.0.1-SNAPSHOT.jar
```

## Project Structure

```
src/
├── main/
│   └── java/
│       └── io/sherdor/todoapp/
│           ├── config/
│           ├── controller/
│           ├── dto/
│           ├── entity/
│           ├── enums/
│           ├── mappers/
│           ├── repositories/
│           ├── service/
│           └── view/

```

## REST API Endpoints

The application exposes a REST API for programmatic access:

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/todos` | Get all todos |
| `GET` | `/api/todos/active` | Get active todos |
| `GET` | `/api/todos/completed` | Get completed todos |
| `GET` | `/api/todos/overdue` | Get overdue todos |
| `GET` | `/api/todos/today` | Get todos due today |
| `GET` | `/api/todos/{id}` | Get todo by ID |
| `POST` | `/api/todos` | Create new todo |
| `PUT` | `/api/todos/{id}` | Update todo |
| `DELETE` | `/api/todos/{id}` | Delete todo |
| `PATCH` | `/api/todos/{id}/complete` | Mark todo as completed |
| `PATCH` | `/api/todos/{id}/incomplete` | Mark todo as incomplete |
| `GET` | `/api/todos/search?search={term}` | Search todos |
| `GET` | `/api/todos/stats` | Get statistics |

## Features Overview

### Task Creation
- **Title**: Required field (max 255 characters)
- **Description**: Optional detailed description (max 1000 characters)
- **Priority**: Low, Medium, High, or Urgent
- **Due Date**: Optional date and time picker

### Task Management
- **Mark as Complete/Incomplete**: Toggle task status with visual feedback
- **Edit Tasks**: Double-click or use edit button to modify tasks
- **Delete Tasks**: Remove tasks with confirmation dialog
- **Search**: Find tasks by title or description content

### Visual Indicators
- **Completed Tasks**: Checkmark indicator and strikethrough styling
- **Overdue Tasks**: Red highlighting for tasks past due date
- **Priority Colors**: Visual priority indicators in the interface

### Statistics Dashboard
- **Total Tasks**: Complete count of all tasks
- **Completed**: Number of finished tasks
- **Active**: Currently pending tasks
- **Overdue**: Tasks past their due date

## ⚙Configuration

### H2 Database Console

The H2 database console is enabled and accessible at: `http://localhost:8080/h2-console`

**Connection Details:**
- **JDBC URL**: `jdbc:h2:mem:todoapp`
- **Username**: `sa`
- **Password**: `password`

## Building for Production


## Contributing

1. Fork the repository
2. Create a feature branch: `git checkout -b feature/amazing-feature`
3. Commit your changes: `git commit -m 'Add amazing feature'`
4. Push to the branch: `git push origin feature/amazing-feature`
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments

- [Spring Boot](https://spring.io/projects/spring-boot) for the robust backend framework
- [Vaadin](https://vaadin.com/) for the powerful Java UI framework
- [MapStruct](https://mapstruct.org/) for efficient object mapping
- [Lombok](https://projectlombok.org/) for reducing boilerplate code

---

**Made with ❤️ using Spring Boot and Vaadin**