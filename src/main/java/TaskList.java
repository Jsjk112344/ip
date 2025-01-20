import java.util.HashMap;
import java.util.Map;

class TaskList {
    private HashMap<Integer, Task> tasks = new HashMap<>();
    private int taskCounter = 1;

    public void addTask(String description) {
        tasks.put(taskCounter, new Task(description));
        System.out.println("Added: " + description);
        taskCounter++;
    }

    public void markTask(int taskId) {
        Task task = tasks.get(taskId);
        if (task != null) {
            task.markAsDone();
            System.out.println("Nice! I've marked this task as done:");
            System.out.println(taskId + ". " + task);
        } else {
            System.out.println("Task ID not found!");
        }
    }

    public void unmarkTask(int taskId) {
        Task task = tasks.get(taskId);
        if (task != null) {
            task.unmark();
            System.out.println("OK, I've marked this task as not done yet:");
            System.out.println(taskId + ". " + task);
        } else {
            System.out.println("Task ID not found!");
        }
    }

    public void listTasks() {
        System.out.println("Here are the tasks in your list:");
        for (Map.Entry<Integer, Task> entry : tasks.entrySet()) {
            System.out.println(entry.getKey() + ". " + entry.getValue());
        }
    }
}
