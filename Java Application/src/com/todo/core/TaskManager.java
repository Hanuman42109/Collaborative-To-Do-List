package com.todo.core;

import com.todo.models.Task;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TaskManager {

    private List<Task> tasks =
            Collections.synchronizedList(new ArrayList<>());

    public synchronized void addTask(Task task) {
        tasks.add(task);
        System.out.println("Task Added: " + task.getTaskId());
    }

    public synchronized void removeTask(int taskId) {
        // If removeIf returns false, it means task wasn't there, but for simplicity skipping check.
        boolean removed = tasks.removeIf(task -> task.getTaskId() == taskId);
        if (removed) {
            System.out.println("Task Removed: " + taskId);
        } else {
            System.out.println("Task ID " + taskId + " not found.");
        }
    }

    public synchronized void markTaskCompleted(int taskId) {
        boolean found = false;
        for (Task task : tasks) {
            if (task.getTaskId() == taskId) {
                task.markCompleted();
                System.out.println("Task Completed: " + taskId);
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Task ID " + taskId + " not found.");
        }
    }

    public synchronized void displayTasks() {
        System.out.println("\n===== TASK LIST =====");
        if (tasks.isEmpty()) {
            System.out.println("No tasks to display.");
            return;
        }
        for (Task task : tasks) {
            System.out.println(task);
        }
    }

    public synchronized void saveTasksToFile() {
        try {
            FileWriter writer = new FileWriter("tasks.txt");
            for (Task task : tasks) {
                writer.write(task.toString() + "\n");
            }
            writer.close();
            System.out.println("Tasks saved to tasks.txt");
        } catch (IOException e) {
            System.out.println("Error writing to file.");
        }
    }
}
