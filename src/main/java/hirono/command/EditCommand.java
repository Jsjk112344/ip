package hirono.command;

import java.io.IOException;
import java.util.HashMap;

import hirono.exception.HironoException;
import hirono.storage.Storage;
import hirono.task.Deadline;
import hirono.task.Event;
import hirono.task.Task;
import hirono.task.TaskList;
import hirono.task.ToDo;
import hirono.ui.Ui;

/**
 * Represents a command to edit an existing task in the list.
 */
public class EditCommand extends Command {
    private final String input;

    public EditCommand(String input) {
        this.input = input;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws IOException, HironoException {
        String message = editTask(taskList.getTasks());
        ui.showMessage(message);
        storage.saveTasks(taskList);
    }

    /**
     * Edits a task based on the input command.
     *
     * @param tasks The HashMap containing all tasks
     * @return A message confirming the task has been edited
     * @throws HironoException If the input format is invalid or task types don't match
     */
    public String editTask(HashMap<Integer, Task> tasks) throws HironoException {
        // Parse task ID and new task info
        String[] parts = input.split(":");
        if (parts.length != 2) {
            throw new HironoException("Invalid edit format. Please use: edit <task ID>: <new task info>");
        }

        try {
            int taskId = Integer.parseInt(parts[0].trim().split("\\s+")[1]);
            Task existingTask = tasks.get(taskId);

            if (existingTask == null) {
                throw new HironoException("Task ID not found!");
            }

            String newTaskInfo = parts[1].trim();

            String[] taskParts = newTaskInfo.split("\\s+", 2);
            if (taskParts.length < 2) {
                throw new HironoException("Invalid task format. Please provide task type and description.");
            }

            String newType = taskParts[0].toLowerCase();

            if (!isMatchingTaskType(existingTask, newType)) {
                throw new HironoException("Cannot change task type. Original task was: "
                    + getTaskTypeName(existingTask));
            }

            Task newTask = createTask(newType, newTaskInfo);

            // Preserve done status
            if (existingTask.isDone()) {
                newTask.markAsDone();
            }

            // Update task in list
            tasks.put(taskId, newTask);

            return String.format("Got it. I've edited the task to:\n%d. %s",
                taskId,
                newTask.toString());

        } catch (NumberFormatException e) {
            throw new HironoException("Invalid task ID. Please provide a valid number.");
        }
    }

    /**
     * Checks if the new task type matches the existing task type.
     * @param existingTask the current task in the list
     * @param newType the type specified in the edit command
     * @return ture if the types match, false otherwise
     */
    private boolean isMatchingTaskType(Task existingTask, String newType) {
        return (existingTask instanceof ToDo && newType.equals("todo"))
            || (existingTask instanceof Deadline && newType.equals("deadline"))
            || (existingTask instanceof Event && newType.equals("event"));
    }

    /**
     * Gets the type name of a task as a string.
     */
    private String getTaskTypeName(Task task) {
        if (task instanceof ToDo) {
            return "todo";
        }
        if (task instanceof Deadline) {
            return "deadline";
        }
        if (task instanceof Event) {
            return "event";
        }
        return "unknown";
    }

    /**
     * Creates a new task based on type and the full task info string.
     */
    private Task createTask(String type, String fullTaskInfo) throws HironoException {
        return switch (type) {
        case "todo" -> new ToDo(fullTaskInfo);
        case "deadline" -> new Deadline(fullTaskInfo);
        case "event" -> new Event(fullTaskInfo);
        default -> throw new HironoException("Invalid task type.");
        };
    }
}
