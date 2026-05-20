CREATE TABLE users (
  id INTEGER PRIMARY KEY,
  name TEXT NOT NULL
);

CREATE TABLE tasks (
  id INTEGER PRIMARY KEY,
  title TEXT NOT NULL,
  category TEXT,
  completed INTEGER DEFAULT 0,
  assigned_to INTEGER,
  created_by INTEGER,
  created_at TEXT,
  completed_at TEXT
);

INSERT INTO users (id, name) VALUES (1, 'Alice'), (2, 'Bob');
INSERT INTO tasks (id, title, category, completed, assigned_to, created_by, created_at) VALUES
(1, 'Sample task', 'General', 0, 1, 1, '2026-05-20T00:00:00Z');
