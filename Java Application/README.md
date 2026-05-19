# Collaborative To-Do List Application (Java)

## Overview
This project is an interactive Java-based collaborative to-do list application. 
It features a command-line interface (CLI) to manage tasks and users, demonstrating:

- Object-Oriented Programming (Classes & Packages)
- Command-line Interactivity (`java.util.Scanner`)
- Concurrency & Multithreading logic
- File Handling (Saving to `.txt` files)
- Task Management

## Features
- **Interactive Menu:** A clean terminal-based menu for a seamless user experience.
- **Multiple users:** Dynamically create users for assignments.
- **Task Management:** Add, remove, and view current tasks.
- **Status Tracking:** Mark tasks as completed or pending.
- **Task categorization:** Label tasks properly when they are created.
- **Save State:** Save tasks persistently to a local `tasks.txt` file.

## Project Structure
The code follows a standard Java package-driven structure (`com.todo`):

```text
Java Application/
├── src/
│   └── com/
│       └── todo/
│           ├── Main.java               (Application Entry Point & CLI)
│           ├── core/
│           │   ├── TaskManager.java    (Thread-safe Task Operations)
│           │   └── UserThread.java     (Simulated Concurrency Threading)
│           └── models/
│               ├── Task.java           (Task Data Object)
│               └── User.java           (User Data Object)
├── tasks.txt                           (Saved persistence file)
├── .gitignore
└── README.md
```

## How to Run

1. Open your terminal (e.g., PowerShell or Command Prompt).
2. Navigate to the `Java Application` directory.

### Compile
Compile all Java source code into a central `bin` directory for clean execution:

```bash
mkdir bin
javac -d bin -sourcepath src src/com/todo/Main.java
```

### Run
Launch the interactive command-line interface using the compiled `bin` folder:

```powershell
java -cp bin com.todo.Main
```
