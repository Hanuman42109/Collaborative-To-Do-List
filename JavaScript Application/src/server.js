const express = require('express');
const path = require('path');
const bodyParser = require('body-parser');
const ds = require('./datastore');

const app = express();
const PORT = process.env.PORT || 3000;

app.use(bodyParser.json());
app.use(express.static(path.join(__dirname, '..', 'public')));

// API
app.get('/api/tasks', async (req, res) => {
  const { user, category, status } = req.query;
  const tasks = await ds.listTasks({ user, category, status });
  res.json(tasks);
});

app.post('/api/tasks', async (req, res) => {
  const { title, category, assignedTo, createdBy } = req.body;
  if (!title) return res.status(400).json({ error: 'title required' });
  const t = await ds.addTask({ title, category, assignedTo, createdBy });
  res.json(t);
});

app.post('/api/tasks/:id/complete', async (req, res) => {
  const id = Number(req.params.id);
  const t = await ds.completeTask(id);
  if (!t) return res.status(404).json({ error: 'not found' });
  res.json(t);
});

app.post('/api/tasks/:id/assign', async (req, res) => {
  const id = Number(req.params.id);
  const { user } = req.body;
  const t = await ds.assignTask(id, user);
  if (!t) return res.status(404).json({ error: 'not found' });
  res.json(t);
});

app.delete('/api/tasks/:id', async (req, res) => {
  const id = Number(req.params.id);
  const ok = await ds.removeTask(id);
  if (!ok) return res.status(404).json({ error: 'not found' });
  res.json({ ok: true });
});

app.listen(PORT, () => console.log(`GUI server running at http://localhost:${PORT}`));
