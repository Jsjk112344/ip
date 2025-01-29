import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    public String getEventsOnDate(String input) throws HironoException {
        // Split the input by space and validate format
        String[] parts = input.split(" ");
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new HironoException("The date command requires a date in yyyy-MM-dd format.");
        }

        LocalDate date;
        try {
            // Parse the date
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            date = LocalDate.parse(parts[1].trim(), formatter);
        } catch (DateTimeParseException e) {
            throw new HironoException("Invalid date format. Please use yyyy-MM-dd (e.g., 2019-12-02).");
        }

        // Filter events and deadlines on the specified date
        List<Task> eventsOnDate = new ArrayList<>();
        for (Task task : tasks.values()) {
            if (task instanceof Event) {
                Event event = (Event) task;
                if (event.isOnDate(date)) {
                    eventsOnDate.add(event);
                }
            } else if (task instanceof Deadline) {
                Deadline deadline = (Deadline) task;
                if (deadline.isOnDate(date)) {
                    eventsOnDate.add(deadline);
                }
            }
        }

        // Build and return the result
        if (eventsOnDate.isEmpty()) {
            return "No events or deadlines found on " + date;
        } else {
            StringBuilder result = new StringBuilder("Here are the events and deadlines on " + date + ":\n");
            int counter = 1;
            for (Task task : eventsOnDate) {
                result.append(counter).append(". ").append(task.toString()).append("\n");
                counter++;
            }
            return result.toString().trim();
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
