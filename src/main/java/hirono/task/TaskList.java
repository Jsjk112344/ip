package hirono.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hirono.command.AddCommand;
import hirono.command.DateCommand;
import hirono.command.FindCommand;
import hirono.command.MarkCommand;
import hirono.exception.HironoException;

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
        AddCommand addCommand = new AddCommand(description, type);
        Task task = addCommand.createTask();
        int taskId = addTaskAndGetId(task);
        return String.format("Got it. I've added this task:\n%s\nNow you have %d tasks in the list.",
                task.toString(),
                taskId);
    }

    /**
     * Adds a task to the list and returns the new task count.
     *
     * @param task The task to add
     * @return The current number of tasks in the list
     */
    public int addTaskAndGetId(Task task) {
        // Find the first available slot in the sequence
        int newId = 1;
        while (tasks.containsKey(newId)) {
            newId++;
        }
        tasks.put(newId, task);
        taskCounter = Math.max(taskCounter, newId + 1);
        return newId;
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
        MarkCommand markCommand = new MarkCommand(taskId);
        return markCommand.markTask(tasks);
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
        FindCommand findCommand = new FindCommand(input);
        return findCommand.findTasks(tasks);
    }
    

    /**
     * Lists events and deadlines occurring on a specific date.
     *
     * @param input The user input containing the date in yyyy-MM-dd format.
     * @return A list of events and deadlines on the specified date.
     * @throws HironoException If the date is invalid or incorrectly formatted.
     */
    public String getEventsOnDate(String input) throws HironoException {
        DateCommand dateCommand = new DateCommand(input);
        return dateCommand.getEventsOnDate(tasks);
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
