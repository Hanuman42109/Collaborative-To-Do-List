package com.todo.core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.todo.models.Task;
import com.todo.models.User;
import com.todo.models.UserAdapter;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Datastore {
    private static final String DATA_FILE = "data/tasks.json";
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(User.class, new UserAdapter())
            .setPrettyPrinting()
            .create();
    private static final Object lock = new Object();

    static class DataWrapper {
        List<User> users = new ArrayList<>();
        List<Task> tasks = new ArrayList<>();
    }

    private static DataWrapper loadData() {
        synchronized (lock) {
            try {
                if (!Files.exists(Paths.get(DATA_FILE))) {
                    return new DataWrapper();
                }
                try (FileReader reader = new FileReader(DATA_FILE)) {
                    DataWrapper data = gson.fromJson(reader, DataWrapper.class);
                    if (data == null) return new DataWrapper();
                    if (data.users == null) data.users = new ArrayList<>();
                    if (data.tasks == null) data.tasks = new ArrayList<>();
                    return data;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return new DataWrapper();
            }
        }
    }

    private static void saveData(DataWrapper data) {
        synchronized (lock) {
            try {
                Files.createDirectories(Paths.get(DATA_FILE).getParent());
                try (FileWriter writer = new FileWriter(DATA_FILE)) {
                    gson.toJson(data, writer);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static List<User> listUsers() {
        return loadData().users;
    }

    public static User addUser(String username) {
        DataWrapper data = loadData();
        boolean exists = false;
        for (User u : data.users) {
            if (u.getName().equalsIgnoreCase(username)) {
                exists = true;
                break;
            }
        }
        if (!exists) {
            data.users.add(new User(username));
            saveData(data);
        }
        return new User(username);
    }

    public static List<Task> listTasks(String user, String category, String status) {
        DataWrapper data = loadData();
        List<Task> result = new ArrayList<>();
        for (Task t : data.tasks) {
            boolean match = true;
            if (user != null && !user.isEmpty()) {
                if (t.getAssignedTo() == null || !user.equalsIgnoreCase(t.getAssignedTo().getName())) {
                    match = false;
                }
            }
            if (category != null && !category.isEmpty() && !category.equalsIgnoreCase(t.getCategory())) match = false;
            if (status != null && !status.isEmpty()) {
                if (status.equalsIgnoreCase("completed") && !t.isCompleted()) match = false;
                if (status.equalsIgnoreCase("pending") && t.isCompleted()) match = false;
            }
            if (match) result.add(t);
        }
        return result;
    }

    public static Task addTask(String title, String category, String assignedToStr, String createdByStr) {
        DataWrapper data = loadData();
        int maxId = 0;
        for (Task t : data.tasks) {
            if (t.getId() > maxId) maxId = t.getId();
        }
        int newId = maxId + 1;
        String createdAt = Instant.now().toString();
        
        if (assignedToStr == null || assignedToStr.isEmpty()) {
            assignedToStr = "anyone";
        }
        
        User assignedTo = new User(assignedToStr);
        User createdBy = createdByStr != null ? new User(createdByStr) : null;

        Task newTask = new Task(newId, title, category, assignedTo, createdBy, createdAt);
        data.tasks.add(newTask);
        saveData(data);
        return newTask;
    }

    public static Task completeTask(int id) {
        DataWrapper data = loadData();
        for (Task t : data.tasks) {
            if (t.getId() == id) {
                t.markCompleted(Instant.now().toString());
                saveData(data);
                return t;
            }
        }
        return null;
    }

    public static Task assignTask(int id, String user) {
        DataWrapper data = loadData();
        for (Task t : data.tasks) {
            if (t.getId() == id) {
                t.setAssignedTo(new User(user));
                saveData(data);
                return t;
            }
        }
        return null;
    }

    public static boolean removeTask(int id) {
        DataWrapper data = loadData();
        boolean removed = data.tasks.removeIf(t -> t.getId() == id);
        if (removed) {
            saveData(data);
        }
        return removed;
    }
}
