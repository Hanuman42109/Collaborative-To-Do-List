package com.todo.models;

public class Task {
    private int taskId;
    private String title;
    private String description;
    private String category;
    private boolean completed;
    private User assignedUser;

    public Task(int taskId, String title, String description,
                String category, User assignedUser) {
        this.taskId = taskId;
        this.title = title;
        this.description = description;
        this.category = category;
        this.assignedUser = assignedUser;
        this.completed = false;
    }

    public int getTaskId() {
        return taskId;
    }

    public synchronized void markCompleted() {
        completed = true;
    }

    public synchronized void markPending() {
        completed = false;
    }

    public boolean isCompleted() {
        return completed;
    }

    public String getCategory() {
        return category;
    }

    public User getAssignedUser() {
        return assignedUser;
    }

    @Override
    public String toString() {
        return "Task ID: " + taskId +
                "\nTitle: " + title +
                "\nDescription: " + description +
                "\nCategory: " + category +
                "\nCompleted: " + completed +
                "\nAssigned User: " + assignedUser.getUsername() +
                "\n-----------------------------------";
    }
}
