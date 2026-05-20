const ds = require('./datastore');

function wait(ms) { return new Promise(res => setTimeout(res, ms)); }

async function worker(name, ops) {
  for (const op of ops) {
    if (op.type === 'add') {
      const t = await ds.addTask({ title: `${op.title} (by ${name})`, category: op.category, assignedTo: op.assignedTo, createdBy: name });
      console.log(`[${name}] added`, t.id);
    } else if (op.type === 'complete') {
      await wait(op.delay || 0);
      const t = await ds.completeTask(op.id);
      console.log(`[${name}] completed`, op.id, '->', !!t);
    } else if (op.type === 'assign') {
      const t = await ds.assignTask(op.id, op.user);
      console.log(`[${name}] assigned`, op.id, 'to', op.user);
    }
  }
}

async function main() {
  console.log('Simulating concurrent users...');
  const w1 = worker('Alice', [
    { type: 'add', title: 'Buy milk', category: 'Home', assignedTo: 'Alice' },
    { type: 'add', title: 'Prepare slides', category: 'Work', assignedTo: 'Bob' },
  ]);

  const w2 = worker('Bob', [
    { type: 'add', title: 'Fix bug', category: 'Work', assignedTo: 'Bob' },
    { type: 'complete', id: 1, delay: 300 },
  ]);

  await Promise.all([w1, w2]);
  console.log('Simulation complete. Current tasks:');
  const tasks = await ds.listTasks({});
  tasks.forEach(t => console.log(`${t.id}. [${t.completed ? 'x' : ' '}] ${t.title} - ${t.assignedTo || '—'}`));
}

main().catch(err => { console.error(err); process.exit(1); });
