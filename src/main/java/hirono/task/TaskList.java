package hirono.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hirono.HironoException;

/**
 * Manages the list of tasks, providing functionality to add, delete, find,
 * list, and manipulate tasks.
 */
public class TaskList {
    private HashMap<Integer, Task> tasks = new HashMap<>();
    private int taskCounter = 1;

    /**
     * Adds a new task to the list.
     *
     * @param description The description of the task.
     * @param type        The type of the task (todo, deadline, event).
     * @return A confirmation message that the task has been added.
     * @throws HironoException If the task type is invalid.
     */
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
        assert tasks.containsKey(taskCounter) : "Task should be successfully added to the list";

        output.append("Got it. I've added this task:\n");
        output.append(tasks.get(taskCounter).toString()).append("\n");
        output.append("Now you have ").append(taskCounter).append(" tasks in the list.");
        taskCounter++;
        return output.toString();
    }

    /**
     * Adds a task that has been loaded from storage without incrementing the task counter.
     *
     * @param task The task to add.
     */
    public void addLoadedTask(Task task) {
        tasks.put(taskCounter, task);
        taskCounter++;
    }

    /**
     * Deletes a task from the list.
     *
     * @param taskId The ID of the task to delete.
     * @return A confirmation message that the task has been removed.
     * @throws HironoException If the task ID is invalid or out of range.
     */
    public String deleteTask(Integer taskId) throws HironoException {
        if (!tasks.containsKey(taskId)) {
            throw new HironoException("The item you are attempting to delete is out of the range of the list.");
        }

        StringBuilder output = new StringBuilder();
        output.append("Noted. I've removed this task:\n");
        output.append(tasks.get(taskId)).append("\n");
        tasks.remove(taskId);
        reorderTasks();

        assert !tasks.containsKey(taskId) : "Task should be removed from the list";
        output.append("Now you have ").append(tasks.size()).append(" tasks in the list.");
        return output.toString();
    }

    /**
     * Retrieves all tasks in the list.
     *
     * @return A HashMap containing all tasks.
     */
    public HashMap<Integer, Task> getTasks() {
        return this.tasks;
    }

    /**
     * Marks a task as done.
     *
     * @param taskId The ID of the task to mark as done.
     */
    public String markTask(int taskId) {
        StringBuilder output = new StringBuilder();
        Task task = tasks.get(taskId);
        if (task != null) {
            task.markAsDone();
            output.append("Nice! I've marked this task as done:\n");
            output.append(taskId + ". " + task.toString() + "\n");
        } else {
            output.append("Task ID not found!");
        }
        return output.toString();
    }

    /**
     * Unmarks a task as not done.
     *
     * @param taskId The ID of the task to unmark.
     */
    public String unmarkTask(int taskId) {
        Task task = tasks.get(taskId);
        StringBuilder output = new StringBuilder();
        if (task != null) {
            task.unmark();

            output.append("OK, I've marked this task as not done yet:\n");
            output.append(taskId + ". " + task);
        } else {
            output.append("Task ID not found!");
        }
        return output.toString();
    }

    /**
     * Lists all tasks in the list.
     */
    public String listTasks() {
        StringBuilder output = new StringBuilder();
        output.append("Here are the tasks in your list:\n");
        for (Map.Entry<Integer, Task> entry : tasks.entrySet()) {
            output.append(entry.getKey() + ". " + entry.getValue() + "\n");
        }
        return output.toString();
    }

    /**
     * Finds tasks that match the specified search term.
     *
     * @param input The user input containing the search term.
     * @return A list of matching tasks.
     * @throws HironoException If the search term is missing or invalid.
     */
    public String findTasks(String input) throws HironoException {
        String[] parts = input.split(" ", 2);
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new HironoException("The find command requires a search term. Please use: find [search term]");
        }

        String searchTerm = parts[1].trim().toLowerCase();
        List<Task> matchingTasks = new ArrayList<>();

        for (Task task : tasks.values()) {
            if (task.getDescription().toLowerCase().contains(searchTerm)) {
                matchingTasks.add(task);
            }
        }

        if (matchingTasks.isEmpty()) {
            return "No tasks found matching \"" + searchTerm + "\".";
        } else {
            StringBuilder result = new StringBuilder("Here are the matching tasks:\n");
            int counter = 1;
            for (Task task : matchingTasks) {
                result.append(counter).append(". ").append(task.toString()).append("\n");
                counter++;
            }
            return result.toString().trim();
        }
    }

    /**
     * Lists events and deadlines occurring on a specific date.
     *
     * @param input The user input containing the date in yyyy-MM-dd format.
     * @return A list of events and deadlines on the specified date.
     * @throws HironoException If the date is invalid or incorrectly formatted.
     */
    public String getEventsOnDate(String input) throws HironoException {
        String[] parts = input.split(" ");
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new HironoException("The date command requires a date in yyyy-MM-dd format.");
        }

        LocalDate date;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            date = LocalDate.parse(parts[1].trim(), formatter);
        } catch (DateTimeParseException e) {
            throw new HironoException("Invalid date format. Please use yyyy-MM-dd (e.g., 2019-12-02).");
        }

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

    /**
     * Reorders the task list after a deletion to maintain sequential IDs.
     */
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
