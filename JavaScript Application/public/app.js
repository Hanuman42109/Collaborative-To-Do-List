async function api(path, opts) {
  const res = await fetch(path, opts);
  if (!res.ok) throw new Error(await res.text());
  return res.json();
}

async function load() {
  const user = document.getElementById('filterUser').value || undefined;
  const category = document.getElementById('filterCategory').value || undefined;
  const qs = new URLSearchParams();
  if (user) qs.set('user', user);
  if (category) qs.set('category', category);
  const tasks = await api('/api/tasks?' + qs.toString());
  const ul = document.getElementById('tasks');
  ul.innerHTML = '';
  
  if (tasks.length === 0) {
    ul.innerHTML = '<div class="empty-state"><div class="empty-state-icon">📭</div><p>No tasks found. Create one to get started!</p></div>';
    return;
  }
  
  tasks.forEach(t => {
    const li = document.createElement('li');
    li.className = `task-item ${t.completed ? 'completed' : ''}`;
    
    const content = document.createElement('div');
    content.className = 'task-content';
    
    const title = document.createElement('div');
    title.className = 'task-title';
    title.textContent = t.title;
    
    const meta = document.createElement('div');
    meta.className = 'task-meta';
    meta.innerHTML = `
      <div class="meta-item"><span class="meta-label">ID:</span> ${t.id}</div>
      <div class="meta-item"><span class="meta-label">Category:</span> ${t.category}</div>
      <div class="meta-item"><span class="meta-label">Assigned:</span> ${t.assignedTo || 'Unassigned'}</div>
      <div class="meta-item"><span class="meta-label">Created by:</span> ${t.createdBy || '—'}</div>
    `;
    
    content.appendChild(title);
    content.appendChild(meta);
    
    const actions = document.createElement('div');
    actions.className = 'task-actions';
    
    const complete = document.createElement('button');
    complete.className = `action-button btn-complete`;
    complete.textContent = t.completed ? '✓ Completed' : '○ Complete';
    complete.disabled = t.completed;
    complete.style.cursor = t.completed ? 'default' : 'pointer';
    complete.onclick = async () => {
      await api(`/api/tasks/${t.id}/complete`, { method: 'POST' });
      load();
    };
    
    const assignBtn = document.createElement('button');
    assignBtn.className = 'action-button';
    assignBtn.style.background = '#2196F3';
    assignBtn.style.color = 'white';
    assignBtn.textContent = '👤 Assign';
    assignBtn.style.cursor = 'pointer';
    assignBtn.onclick = async () => {
      const who = prompt('Assign to user:');
      if (who) {
        await api(`/api/tasks/${t.id}/assign`, {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({ user: who })
        });
        load();
      }
    };
    
    const del = document.createElement('button');
    del.className = 'action-button btn-delete';
    del.textContent = '🗑 Delete';
    del.style.cursor = 'pointer';
    del.onclick = async () => {
      if (confirm('Are you sure you want to delete this task?')) {
        await api(`/api/tasks/${t.id}`, { method: 'DELETE' });
        load();
      }
    };
    
    actions.appendChild(complete);
    actions.appendChild(assignBtn);
    actions.appendChild(del);
    
    li.appendChild(content);
    li.appendChild(actions);
    ul.appendChild(li);
  });
}

document.getElementById('add').addEventListener('click', async () => {
  const title = document.getElementById('title').value;
  const category = document.getElementById('category').value || 'General';
  const user = document.getElementById('user').value || undefined;
  if (!title) return alert('Please enter a task title');
  await api('/api/tasks', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ title, category, assignedTo: user, createdBy: user })
  });
  document.getElementById('title').value = '';
  load();
});

document.getElementById('refresh').addEventListener('click', load);
load().catch(err => console.error(err));

