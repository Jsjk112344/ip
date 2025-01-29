import java.util.HashMap;
import java.util.Map;

public class TaskList {
    private HashMap<Integer, Task> tasks = new HashMap<>();
    private int taskCounter = 1;

    public String addTask(String description, String type) throws HironoException {
        StringBuilder output = new StringBuilder();
        Task task;
        switch (type) {
            case "todo":
                task = new ToDo(description);
                break;
            case "deadline":
                task = new Deadline(description);
                break;
            case "event":
                task = new Event(description);
                break;
            default:
                throw new HironoException("Invalid task type.");
        }
        tasks.put(taskCounter, task);
        output.append("Got it. I've added this task:\n");
        output.append(tasks.get(taskCounter).toString() + "\n");
        output.append("Now you have " + taskCounter + " tasks in the list.");
        taskCounter++;
        return output.toString();
    }
    // Adds a task loaded from the file without incrementing the counter
    public void addLoadedTask(Task task) {
        tasks.put(taskCounter, task);
        taskCounter++;
    }


    public String deleteTask(Integer taskId) throws HironoException {
        if (!tasks.containsKey(taskId)) {
            throw new HironoException("The item you are attempting to delete is out of the range of the list.");
        }

        StringBuilder output = new StringBuilder();
        output.append("Noted. I've removed this task:\n");
        output.append(tasks.get(taskId) + "\n");
        tasks.remove(taskId); 
        reorderTasks(); 
        output.append("Now you have " + tasks.size() + " tasks in the list.");
        return output.toString();
    }

    public HashMap<Integer, Task> getTasks() {
        return this.tasks;
    }

    // Marks a task as done
    public void markTask(int taskId) {
        Task task = tasks.get(taskId);
        if (task != null) {
            task.markAsDone();
            System.out.println("Nice! I've marked this task as done:");
            System.out.println(taskId + ". " + task.toString());
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

    private void reorderTasks() {
        HashMap<Integer, Task> reorderedTasks = new HashMap<>();
        int newTaskId = 1;

        for (Map.Entry<Integer, Task> entry : tasks.entrySet()) {
            reorderedTasks.put(newTaskId, entry.getValue());
            newTaskId++;
        }

        tasks = reorderedTasks; 
    }
}
