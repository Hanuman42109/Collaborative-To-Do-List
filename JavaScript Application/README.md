# Collaborative To-Do List — JavaScript Application

Overview
- Simple CLI-based collaborative to-do list demonstrating async operations and JSON storage.

Files
- `src/datastore.js`: Async JSON datastore and task operations.
- `src/app.js`: CLI (uses `yargs`) — add/list/complete/remove/assign tasks.
- `src/simulate.js`: Runs concurrent-like operations using `async/await`.
- `data/tasks.json`: JSON data store used at runtime.
- `data/tasks.sql`: Simple SQL schema and sample inserts.

Requirements
- Node.js 14+

Install
```
cd "JavaScript Application"
npm install
```

Run
- Start CLI:
```
npm start -- add --title "Buy milk" --user Alice --category Home
npm start -- list --user Alice
npm start -- complete --id 1
```

GUI
---
- Start the lightweight web GUI:
```
npm run gui
```
- Open http://localhost:3000 in your browser.

The GUI uses the same `data/tasks.json` file as the CLI so changes are reflected across interfaces.

Notes
- Data is persisted to `data/tasks.json` using atomic writes.
- `data/tasks.sql` is provided as a simple schema demonstration and is not used by the CLI.
Hi, This is JavaScript Application.
