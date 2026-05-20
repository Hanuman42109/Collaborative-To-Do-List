const fs = require('fs').promises;
const path = require('path');

const DATA_DIR = path.join(__dirname, '..', 'data');
const DATA_FILE = path.join(DATA_DIR, 'tasks.json');

async function ensureDataFile() {
  try {
    await fs.mkdir(DATA_DIR, { recursive: true });
    await fs.access(DATA_FILE);
  } catch (err) {
    const init = { users: ["Alice","Bob"], tasks: [] };
    await fs.writeFile(DATA_FILE, JSON.stringify(init, null, 2));
  }
}

async function loadData() {
  await ensureDataFile();
  const raw = await fs.readFile(DATA_FILE, 'utf8');
  return JSON.parse(raw);
}

async function saveData(data) {
  await ensureDataFile();
  const tmp = DATA_FILE + '.tmp';
  await fs.writeFile(tmp, JSON.stringify(data, null, 2));
  await fs.rename(tmp, DATA_FILE);
}

async function addTask({ title, category = 'General', assignedTo = null, createdBy = null }) {
  const data = await loadData();
  const id = (data.tasks.reduce((m, t) => Math.max(m, t.id || 0), 0) || 0) + 1;
  const task = {
    id,
    title,
    category,
    completed: false,
    assignedTo,
    createdBy,
    createdAt: new Date().toISOString()
  };
  data.tasks.push(task);
  await saveData(data);
  return task;
}

async function removeTask(id) {
  const data = await loadData();
  const before = data.tasks.length;
  data.tasks = data.tasks.filter(t => t.id !== id);
  const removed = data.tasks.length < before;
  if (removed) await saveData(data);
  return removed;
}

async function completeTask(id) {
  const data = await loadData();
  const t = data.tasks.find(x => x.id === id);
  if (!t) return null;
  t.completed = true;
  t.completedAt = new Date().toISOString();
  await saveData(data);
  return t;
}

async function assignTask(id, user) {
  const data = await loadData();
  const t = data.tasks.find(x => x.id === id);
  if (!t) return null;
  t.assignedTo = user;
  await saveData(data);
  return t;
}

async function listTasks(filter = {}) {
  const data = await loadData();
  let tasks = data.tasks.slice();
  if (filter.user) tasks = tasks.filter(t => t.assignedTo === filter.user || t.createdBy === filter.user);
  if (filter.category) tasks = tasks.filter(t => t.category === filter.category);
  if (filter.status === 'completed') tasks = tasks.filter(t => t.completed);
  if (filter.status === 'pending') tasks = tasks.filter(t => !t.completed);
  return tasks;
}

module.exports = { addTask, removeTask, completeTask, assignTask, listTasks, loadData, saveData };
