# Cross-Language Application Development: Collaborative To-Do List

This repository contains the implementation for a Collaborative To-Do list, built simultaneously in two distinct programming languages: **Java** and **JavaScript**. 

The goal of this dual-language project is to contrast and demonstrate how different paradigms handle core application features such as data storage, categorization, and concurrent data access. Both applications share semantic parity while utilizing language-specific features in their respective implementations.

---

## 📂 Project Structure

```text
Collaborative-To-Do-List/
├── Java Application/        # Fully Object-Oriented CLI App with thread-safe JSON sync
├── JavaScript Application/  # Asynchronous Web-GUI & CLI App with promises
├── .vscode/                 # IDE workspace configuration
├── .gitignore               # Global ignore definitions
└── README.md                # Project landing page
```

---

## 🛠️ Implementations

### ☕ Java Application
A strongly typed, highly robust command-line application demonstrating Java's Object-Oriented principles.
- **Key Feature**: Custom Models/Classes (such as `User.java` and `Task.java`) integrated out-of-the-box with Google's primitive serialization tools.
- **Concurrency**: Simulated concurrent data execution dynamically tested across raw threads.
- [Read more about the Java setup](./Java%20Application/README.md)

### 🟨 JavaScript Application
A highly portable Web and CLI application boasting modern asynchronous logic spanning lightweight backend endpoints.
- **Key Feature**: Fully responsive Web GUI combined robustly with `yargs` CLI arguments.
- **Concurrency**: Native JS Event Loop utilizing `async`/`await` functions mapping straight to identical JSON persistent stores.
- [Read more about the JavaScript setup](./JavaScript%20Application/README.md)

---

## 🗃️ Shared Data Persistence
Both the Java and JavaScript projects are designed to read and write to standard persistent `JSON` stores. Both engines accurately deserialize and mutate string-based schemas synchronously regardless of the host runtime!