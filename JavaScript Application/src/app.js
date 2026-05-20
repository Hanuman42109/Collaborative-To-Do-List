#!/usr/bin/env node
const yargs = require('yargs/yargs');
const { hideBin } = require('yargs/helpers');
const ds = require('./datastore');

async function main() {
  const argv = yargs(hideBin(process.argv))
    .command('add', 'Add a task', (y) => y
      .option('user', { type: 'string', demandOption: false })
      .option('title', { type: 'string', demandOption: true })
      .option('category', { type: 'string', demandOption: false })
    )
    .command('list', 'List tasks', (y) => y
      .option('user', { type: 'string' })
      .option('category', { type: 'string' })
      .option('status', { type: 'string', choices: ['completed','pending'] })
    )
    .command('complete', 'Mark a task completed', (y) => y.option('id', { type: 'number', demandOption: true }))
    .command('remove', 'Remove a task', (y) => y.option('id', { type: 'number', demandOption: true }))
    .command('assign', 'Assign a task', (y) => y.option('id', { type: 'number', demandOption: true }).option('user', { type: 'string', demandOption: true }))
    .demandCommand(1)
    .help()
    .argv;

  const cmd = argv._[0];
  if (cmd === 'add') {
    const task = await ds.addTask({ title: argv.title, category: argv.category, assignedTo: argv.user, createdBy: argv.user });
    console.log('Added task:', task);
  } else if (cmd === 'list') {
    const tasks = await ds.listTasks({ user: argv.user, category: argv.category, status: argv.status });
    if (tasks.length === 0) console.log('No tasks');
    else tasks.forEach(t => console.log(`${t.id}. [${t.completed ? 'x' : ' '}] ${t.title} (${t.category}) - assigned: ${t.assignedTo || '—'}`));
  } else if (cmd === 'complete') {
    const t = await ds.completeTask(argv.id);
    if (!t) console.error('Task not found'); else console.log('Completed:', t);
  } else if (cmd === 'remove') {
    const ok = await ds.removeTask(argv.id);
    if (!ok) console.error('Task not found'); else console.log('Removed task', argv.id);
  } else if (cmd === 'assign') {
    const t = await ds.assignTask(argv.id, argv.user);
    if (!t) console.error('Task not found'); else console.log('Assigned:', t);
  } else {
    console.error('Unknown command');
  }
}

main().catch(err => { console.error(err); process.exit(1); });
