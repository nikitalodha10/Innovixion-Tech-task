import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

class Task {
    private String name;
    private Date dueDate;
    private int priority;

    public Task(String name, Date dueDate, int priority) {
        this.name = name;
        this.dueDate = dueDate;
        this.priority = priority;
    }

    public String getName() {
        return name;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public String toString() {
        return "Task: " + name + ", Due Date: " + dueDate + ", Priority: " + priority;
    }
}

class TaskManager {
    private List<Task> tasks;

    public TaskManager() {
        this.tasks = new ArrayList<>();
    }

    public void addTask(Task task) {
        tasks.add(task);
        sortTasks();
    }

    public void displayTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks found.");
        } else {
            for (Task task : tasks) {
                System.out.println(task);
            }
        }
    }

    private void sortTasks() {
        Collections.sort(tasks, Comparator.comparing(Task::getDueDate).thenComparing(Task::getPriority));
    }
}

public class TaskSchedulerApp {
    private static TaskManager taskManager = new TaskManager();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Timer timer = new Timer();

        // Schedule the task to check for overdue tasks every minute
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                checkOverdueTasks();
            }
        }, 0, 60 * 1000);

        while (true) {
            System.out.println("\nTask Scheduler Menu:");
            System.out.println("1. Add Task");
            System.out.println("2. View Tasks");
            System.out.println("3. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    addTask(scanner);
                    break;
                case 2:
                    viewTasks();
                    break;
                case 3:
                    timer.cancel(); // Stop the timer before exiting
                    System.out.println("Exiting Task Scheduler. Goodbye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addTask(Scanner scanner) {
        System.out.print("Enter task name: ");
        String name = scanner.nextLine();

        System.out.print("Enter due date (yyyy-MM-dd HH:mm): ");
        String dueDateStr = scanner.nextLine();
        Date dueDate = new Date(Date.parse(dueDateStr));

        System.out.print("Enter priority (1-5, where 1 is the highest priority): ");
        int priority = scanner.nextInt();

        Task task = new Task(name, dueDate, priority);
        taskManager.addTask(task);

        System.out.println("Task added successfully!");
    }

    private static void viewTasks() {
        System.out.println("\nAll Tasks:");
        taskManager.displayTasks();
    }

    private static void checkOverdueTasks() {
        Date now = new Date();
        List<Task> overdueTasks = new ArrayList<>();

        for (Task task : taskManager) {
            if (task.getDueDate().before(now)) {
                overdueTasks.add(task);
            }
        }

        if (!overdueTasks.isEmpty()) {
            System.out.println("\nOverdue Tasks:");
            for (Task task : overdueTasks) {
                System.out.println(task);
            }
        }
    }
}
