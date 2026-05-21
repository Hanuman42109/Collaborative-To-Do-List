package com.todo;

import com.todo.core.TaskManager;
import com.todo.models.Task;
import com.todo.models.User;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        TaskManager manager = new TaskManager();
        Scanner scanner = new Scanner(System.in);
        int taskIdCounter = 101; 

        System.out.println("==================================================");
        System.out.println(" Welcome to the Collaborative To-Do List!");
        System.out.println("==================================================");

        while (true) {
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1. Add a Task");
            System.out.println("2. Remove a Task");
            System.out.println("3. Mark Task as Completed");
            System.out.println("4. View All Tasks");
            System.out.println("5. Save and Exit");
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Enter Task Title: ");
                    String title = scanner.nextLine();

                    System.out.print("Enter Task Description: ");
                    String desc = scanner.nextLine();

                    System.out.print("Enter Category: ");
                    String category = scanner.nextLine();

                    System.out.print("Enter Your Username (e.g. Alice): ");
                    String username = scanner.nextLine();

                    // Generate user ID based on hash
                    User user = new User(Math.abs(username.hashCode()), username);

                    Task newTask = new Task(taskIdCounter++, title, desc, category, user);
                    manager.addTask(newTask);
                    break;

                case "2":
                    System.out.print("Enter the Task ID to remove: ");
                    try {
                        int removeId = Integer.parseInt(scanner.nextLine());
                        manager.removeTask(removeId);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Task ID must be a number.");
                    }
                    break;

                case "3":
                    System.out.print("Enter the Task ID to mark completed: ");
                    try {
                        int compId = Integer.parseInt(scanner.nextLine());
                        manager.markTaskCompleted(compId);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Task ID must be a number.");
                    }
                    break;

                case "4":
                    manager.displayTasks();
                    break;

                case "5":
                    manager.saveTasksToFile();
                    System.out.println("Exiting Application. Goodbye!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice. Please pick an option from 1 to 5.");
                    break;
            }
        }
    }
}
