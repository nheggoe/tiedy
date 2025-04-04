Project Description
===================
Tiedy is a to-do app aimed to solve the modern problem of time management.
It is optimized for everyday home chore tasks, with a leveling system to novitiate users to compete on the leader board.

Project Structure
=================

```mermaid
flowchart TB
    subgraph Frontend
        UI[JavaFX UI Components]
        Controllers[UI Controllers]
        Session[User Session]
    end

    subgraph Backend
        Facade[Data Access Facade]

        subgraph "Repository Layer"
            UserRepo[User Repository]
            TaskRepo[Task Repository]
            GroupRepo[Group Repository]
        end

        subgraph "Domain Model"
            User[User & Level System]
            Task[Task & Task Builder]
            Group[Group]
        end

        subgraph "Persistence"
            JsonService[JSON Service]
            FileUtil[File Utilities]
            Storage[(JSON Files)]
        end
    end

    UI <--> Controllers
    Controllers <--> Session
    Controllers <--> Facade
    Facade <--> UserRepo & TaskRepo & GroupRepo
    UserRepo --> User
    TaskRepo --> Task
    GroupRepo --> Group
    UserRepo & TaskRepo & GroupRepo <--> JsonService
    JsonService <--> FileUtil
    FileUtil <--> Storage
```

Running the project
===================
Java 21 is required to run this project.
After ensuring the correct java version has been installed, run the following command:

```bash
./mvnw -q
```