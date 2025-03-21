Project Description
===================
Tiedy is a to-do app aimed to solve the modern problem of time management.
It is optimized for everyday home chore tasks, with a leveling system to novitiate users to compete on the leader board.

Project Structure
=================

```mermaid
flowchart LR
    subgraph JavaFX UI
        FXMLViews[.fxml Files]
        Controllers[Controllers]
    end

    subgraph Backend Logic
        Service[Application Logic]
        JBCrypt[JBcrypt for Password Hashing]
        Jackson[Jackson JSON APIs]
        JSONFiles[(JSON File Storage)]
    end

    FXMLViews --> Controllers
    Controllers --> Service
    Service --> JBCrypt
    Service --> Jackson
    Jackson --> JSONFiles
```

Running the project
===================
Java 21 is required to run this project.
After ensuring the correct java version has been installed, run the following commands:

```bash
./mvnw -q
```
