Project Description
===================
Tiedy is a to-do app aimed to solve the modern problem of time management.
It is optimized for everyday home chore tasks, with a leveling system to novitiate users to compete on the leader board.

Project Structure
=================

```mermaid
classDiagram
    class Task {
        -int id
        -String title
        -String description
        -Status status
        -User assignedTo
        -Date deadline
        -start()
        -complete()
    }

    class User {
        -int userId
        -String username
        -String email
        +login()
        +logout()
    }

    class Admin {
        +manageUsers()
        +viewAllTasks()
    }

    class RegularUser {
        +viewAssignedTasks()
        +updateTaskStatus()
    }

    class TaskManager {
        +assignTask()
        +removeTask()
        +updateTask()
    }

    class TaskApp {
        +assignTask()
        +removeTask()
        +updateTask()
        +getAllTasks()
    }

    class Status {
        New
        Open
        In Progress
        Postponed
        Closed
    }
    class Severity {
        Critical
        High
        Medium
        Low
    }

    class ScoreBoard {
        -List<User> users
        +showTopUsers()
    }

    <<Abstract>> User
    <<interface>> TaskManager
    <<enumeration>> Status
    <<enumeration>> Severity
    TaskManager <|.. TaskApp
    Task --> User: assignedTo
    Task --> Status: has
    Task --> Severity: has
    User <|-- Admin
    User <|-- RegularUser
    ScoreBoard --> User
    style Task fill: #bfb, stroke: #6f6, stroke-width: 2px, color: #000, stroke-dasharray: 5 5
    style User fill: #bfb, stroke: #6f6, stroke-width: 2px, color: #000, stroke-dasharray: 5 5
    style TaskManager fill: #9ff, stroke: #369, stroke-width: 2px, color: #000, stroke-dasharray: 5 5
    style Status fill: #ffb, stroke: #663, stroke-width: 2px, color: #000, stroke-dasharray: 5 5
    style Severity fill: #ffb, stroke: #663, stroke-width: 2px, color: #000, stroke-dasharray: 5 5

    class Admin:::Ash
    class RegularUser:::Ash
    class TaskApp:::Ash
```

Running the project
===================
This project uses JDK 21 and maven 3.9.9 as the build system.
To build and run the program from scratch, clone the repository and run the following maven command:
```bash
mvn -q clean javafx:run
```
