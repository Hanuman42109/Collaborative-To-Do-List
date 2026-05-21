package com.todo;

import com.todo.core.Datastore;
import com.todo.models.Task;
import com.todo.models.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        if (args.length > 0) {
            runCliArgs(args);
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("==================================================");
        System.out.println(" Welcome to the Collaborative To-Do List!");
        System.out.println("==================================================");

        while (true) {
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1. Add a Task");
            System.out.println("2. Remove a Task");
            System.out.println("3. Mark Task as Completed");
            System.out.println("4. Assign Task");
            System.out.println("5. View All Tasks");
            System.out.println("6. Manage Users");
            System.out.println("7. Run Concurrent Simulation");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Enter Task Title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter Category: ");
                    String category = scanner.nextLine();
                    System.out.print("Enter Your Username (Created By): ");
                    String createdBy = scanner.nextLine();
                    System.out.print("Enter Assigned Username (or leave blank for anyone): ");
                    String assignedTo = scanner.nextLine();
                    
                    Task newTask = Datastore.addTask(title, category, assignedTo, createdBy);
                    System.out.println("Task Added Successfully! ID: " + newTask.getId());
                    break;
                case "2":
                    System.out.print("Enter Task ID to remove: ");
                    try {
                        int removeId = Integer.parseInt(scanner.nextLine());
                        boolean ok = Datastore.removeTask(removeId);
                        if (ok) System.out.println("Removed task " + removeId);
                        else System.err.println("Task not found.");
                    } catch (NumberFormatException e) { System.out.println("Invalid ID."); }
                    break;
                case "3":
                    System.out.print("Enter Task ID to mark completed: ");
                    try {
                        int compId = Integer.parseInt(scanner.nextLine());
                        Task t = Datastore.completeTask(compId);
                        if (t != null) System.out.println("Completed task " + compId);
                        else System.err.println("Task not found.");
                    } catch (NumberFormatException e) { System.out.println("Invalid ID."); }
                    break;
                case "4":
                    System.out.print("Enter Task ID: ");
                    String tidStr = scanner.nextLine();
                    System.out.print("Enter Username to assign to: ");
                    String uname = scanner.nextLine();
                    try {
                        Task t = Datastore.assignTask(Integer.parseInt(tidStr), uname);
                        if (t != null) System.out.println("Assigned task " + tidStr + " to " + uname);
                        else System.err.println("Task not found.");
                    } catch (NumberFormatException e) { System.out.println("Invalid ID."); }
                    break;
                case "5":
                    System.out.println("\n--- TASK LIST ---");
                    List<Task> tasks = Datastore.listTasks(null, null, null);
                    if (tasks.isEmpty()) System.out.println("No tasks");
                    else {
                        for (Task t : tasks) {
                            String check = t.isCompleted() ? "x" : " ";
                            String assigned = t.getAssignedTo() != null ? t.getAssignedTo().getName() : "-";
                            System.out.printf("%d. [%s] %s (%s) - assigned: %s\n", 
                                t.getId(), check, t.getTitle(), t.getCategory(), assigned);
                        }
                    }
                    break;
                case "6":
                    System.out.println("\n1. List Users\n2. Add User");
                    System.out.print("Choice: ");
                    String uChoice = scanner.nextLine();
                    if ("1".equals(uChoice)) {
                        List<User> users = Datastore.listUsers();
                        for (User u : users) System.out.println("- " + u.getName());
                    } else if ("2".equals(uChoice)) {
                        System.out.print("Enter Username: ");
                        String newUname = scanner.nextLine();
                        Datastore.addUser(newUname);
                        System.out.println("User added.");
                    }
                    break;
                case "7":
                    try { Simulate.start(); } catch (Exception e) {}
                    break;
                case "8":
                    System.out.println("Exiting Application. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please pick an option from 1 to 8.");
                    break;
            }
        }
    }

    private static void runCliArgs(String[] args) {
        String cmd = args[0];
        Map<String, String> options = new HashMap<>();
        for (int i = 1; i < args.length; i++) {
            if (args[i].startsWith("--") && i + 1 < args.length) {
                options.put(args[i].substring(2), args[i+1]);
                i++;
            }
        }
        try {
            switch (cmd) {
                case "simulate":
                    Simulate.start();
                    break;
                case "add":
                    String title = options.get("title");
                    if (title == null) throw new IllegalArgumentException("--title is required");
                    Task t = Datastore.addTask(title, options.get("category"), options.get("user"), options.get("user"));
                    System.out.println("Added task: " + t.getId());
                    break;
                case "list":
                    List<Task> tasks = Datastore.listTasks(options.get("user"), options.get("category"), options.get("status"));
                    if (tasks.isEmpty()) {
                        System.out.println("No tasks");
                    } else {
                        for (Task task : tasks) {
                            String check = task.isCompleted() ? "x" : " ";
                            String assigned = task.getAssignedTo() != null ? task.getAssignedTo().getName() : "-";
                            System.out.printf("%d. [%s] %s (%s) - assigned: %s\n", 
                                task.getId(), check, task.getTitle(), task.getCategory(), assigned);
                        }
                    }
                    break;
                case "complete":
                    if (!options.containsKey("id")) throw new IllegalArgumentException("--id is required");
                    Task ct = Datastore.completeTask(Integer.parseInt(options.get("id")));
                    if (ct == null) System.err.println("Task not found");
                    else System.out.println("Completed: " + ct.getId());
                    break;
                case "remove":
                    if (!options.containsKey("id")) throw new IllegalArgumentException("--id is required");
                    boolean ok = Datastore.removeTask(Integer.parseInt(options.get("id")));
                    if (!ok) System.err.println("Task not found");
                    else System.out.println("Removed task " + options.get("id"));
                    break;
                case "assign":
                    if (!options.containsKey("id") || !options.containsKey("user")) 
                        throw new IllegalArgumentException("--id and --user are required");
                    Task at = Datastore.assignTask(Integer.parseInt(options.get("id")), options.get("user"));
                    if (at == null) System.err.println("Task not found");
                    else System.out.println("Assigned: " + at.getId() + " to " + options.get("user"));
                    break;
                case "user-add":
                    if (!options.containsKey("name")) throw new IllegalArgumentException("--name is required");
                    Datastore.addUser(options.get("name"));
                    System.out.println("Registered user: " + options.get("name"));
                    break;
                case "user-list":
                    List<User> users = Datastore.listUsers();
                    for (User u : users) {
                        System.out.println("- " + u.getName());
                    }
                    break;
                default:
                    System.err.println("Unknown command");
                    break;
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
