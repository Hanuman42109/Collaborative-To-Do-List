package com.todo.models;

public class Task {
    private int id;
    private String title;
    private String category;
    private boolean completed;
    private User assignedTo;
    private User createdBy;
    private String createdAt;
    private String completedAt;

    public Task() {}

    public Task(int id, String title, String category, User assignedTo, User createdBy, String createdAt) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.assignedTo = assignedTo;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.completed = false;
        this.completedAt = null;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getCategory() { return category; }
    public boolean isCompleted() { return completed; }
    public User getAssignedTo() { return assignedTo; }
    public User getCreatedBy() { return createdBy; }
    public String getCreatedAt() { return createdAt; }
    public String getCompletedAt() { return completedAt; }

    public synchronized void markCompleted(String timestamp) {
        this.completed = true;
        this.completedAt = timestamp;
    }

    public synchronized void markPending() {
        this.completed = false;
        this.completedAt = null;
    }

    public synchronized void setAssignedTo(User assignedTo) {
        this.assignedTo = assignedTo;
    }

    @Override
    public String toString() {
        return "Task ID: " + id + "\nTitle: " + title + "\nCategory: " + category + 
            "\nAssigned To: " + (assignedTo != null ? assignedTo.getName() : "null") + "\nCompleted: " + completed;
    }
}
