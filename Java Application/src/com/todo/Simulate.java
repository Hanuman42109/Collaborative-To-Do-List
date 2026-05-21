package com.todo;

import com.todo.core.Datastore;
import com.todo.models.Task;
import java.util.List;

public class Simulate {

    static class Worker extends Thread {
        private Runnable[] ops;

        public Worker(String threadName, Runnable... ops) {
            super(threadName);
            this.ops = ops;
        }

        @Override
        public void run() {
            try {
                for (Runnable op : ops) {
                    op.run();
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) { }
        }
    }

    public static void start() throws Exception {
        System.out.println("Simulating concurrent users...");

        Worker w1 = new Worker("Alice", 
            () -> {
                Task t = Datastore.addTask("Buy milk (by Alice)", "Home", "Alice", "Alice");
                System.out.println("[Alice] added " + t.getId());
            },
            () -> {
                Task t = Datastore.addTask("Prepare slides (by Bob)", "Work", "Bob", "Alice");
                System.out.println("[Alice] added " + t.getId());
            }
        );

        Worker w2 = new Worker("Bob", 
            () -> {
                Task t = Datastore.addTask("Fix bug (by Bob)", "Work", "Bob", "Bob");
                System.out.println("[Bob] added " + t.getId());
            },
            () -> {
                try { Thread.sleep(300); } catch (InterruptedException e) {}
                Task t = Datastore.completeTask(1);
                System.out.println("[Bob] completed 1 -> " + (t != null));
            }
        );

        w1.start();
        w2.start();
        
        w1.join();
        w2.join();
        
        System.out.println("Simulation complete. Current tasks:");
        List<Task> tasks = Datastore.listTasks(null, null, null);
        for (Task t : tasks) {
            String check = t.isCompleted() ? "x" : " ";
            String assigned = t.getAssignedTo() != null ? t.getAssignedTo().getName() : "-";
            System.out.printf("%d. [%s] %s - %s\n", t.getId(), check, t.getTitle(), assigned);
        }
    }
}
