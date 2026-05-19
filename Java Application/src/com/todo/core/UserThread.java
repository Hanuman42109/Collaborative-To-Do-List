package com.todo.core;

import com.todo.models.Task;

public class UserThread extends Thread {

    private TaskManager manager;
    private Task task;

    public UserThread(TaskManager manager, Task task) {
        this.manager = manager;
        this.task = task;
    }

    @Override
    public void run() {
        manager.addTask(task);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("Thread interrupted.");
        }

        manager.markTaskCompleted(task.getTaskId());
    }
}
