# Collaborative To-Do List (JavaScript)

![Node.js](https://img.shields.io/badge/Node.js-%3E%3D14.0.0-success)
![Express](https://img.shields.io/badge/Express-4.x-blue)
![CLI](https://img.shields.io/badge/CLI-Ready-orange)

A lightweight collaborative to-do list application built with JavaScript. This project offers both a versatile Command Line Interface (CLI) and a web page GUI to manage tasks, users, and categories. 

The application utilizes local JSON storage with simulated asynchronous operations to serve both concurrent-like CLI interactions and a modern web server.

---

## 🌟 Key Features

- **Dual Interfaces:** Seamlessly switch between a full-featured CLI and a web GUI. Both use the exact same data source.
- **Task Management:** Add, list, complete, and assign tasks with ease.
- **User Management:** Register and manage users. Tasks without an assignment default to `anyone`.
- **Category Sorting:** Group tasks natively into categories (e.g., Home, Work).
- **Asynchronous Data Store:** Utilizes a non-blocking `async/await` JSON file adapter to simulate real-world scalable data persistence.

## 📁 Project Structure

- `src/datastore.js`: Asynchronous adapter handling persistent CRUD operations.
- `src/app.js`: Core CLI interface powered by `yargs`.
- `src/server.js`: Lightweight Express.js web GUI server.
- `src/simulate.js`: Script demonstrating concurrent-like async task manipulations.
- `data/tasks.json`: Local JSON data store driving the entire application.
- `data/tasks.sql`: Sample relational schema reference.

## 🚀 Getting Started

### Prerequisites

- **Node.js**: v14.0 or higher.

### Installation

1. Navigate to the JavaScript Application directory.
2. Install the required dependencies:

```bash
cd "JavaScript Application"
npm install
```

## 💻 Usage

### Web GUI (Recommended)

Start the lightweight Express server to interact with the application through your browser:

```bash
npm run dev
```

Then visit [http://localhost:3000](http://localhost:3000) in your web browser.

### Command Line Interface (CLI)

The CLI offers robust tools for quick manipulation directly from your terminal:

**User Management**
- Add a user: `npm start -- user-add --name Frank`
- List users: `npm start -- user-list`

**Task Management**
- Add a task: `npm start -- add --title "Buy milk" --user Alice --category Home`
- List tasks for a user: `npm start -- list --user Alice`
- Complete a task: `npm start -- complete --id 1`

## 🛠 Notes

- State is preserved constantly inside `data/tasks.json` with atom-like file persistence.
- Modifying tasks via CLI updates the GUI in real-time on your next refresh.
