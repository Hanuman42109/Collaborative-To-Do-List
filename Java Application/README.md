# Collaborative To-Do List Application (Java)

## Overview
This project is an interactive Java-based collaborative to-do list application, fully feature-compatible with its JavaScript counterpart. 
It features a robust command-line interface (CLI) to manage tasks and a concurrent simulation engine.

- Object-Oriented Programming (Classes & Packages)
- Command-line argument parsing
- Concurrency logic
- JSON File Handling (`data/tasks.json` via Gson)

## Features
- **Direct CLI:** Add, list, remove, or mark tasks complete instantly from your terminal.
- **Multiple users:** Query and act as different users.
- **JSON Compatibility:** Fully interoperable `data/tasks.json` with the JS Application.

## Project Structure

```text
Java Application/
├── src/
│   └── com/
│       └── todo/
│           ├── Main.java               (Application Entry & Interactive CLI)
│           ├── Simulate.java           (Concurrency Demo)
│           ├── core/
│           │   └── Datastore.java      (Thread-safe JSON Store)
│           └── models/
│               └── Task.java           (Task Data Object)
├── lib/
│   └── gson-2.10.1.jar                 (JSON serialization)
├── data/
│   └── tasks.json                      (JSON Data persistent file)
├── build.bat                           (Utility to compile app)
├── run.bat                             (Utility to launch app)
└── README.md
```

## How to Run

1. Open your terminal or command prompt.
2. Navigate to the `Java Application` directory.

### Compile
Run the simple compilation script:

```bash
.\build.bat
```

### Run
Launch the application with beautifully simple commands using the script provided:

```powershell
# Best UX: Open the interactive number menu!
.\run.bat

# Silent execution mode
.\run.bat list
.\run.bat add --title "Clean Room" --category "Personal" --user "Alice"
.\run.bat complete --id 1
```
