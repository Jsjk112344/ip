package hirono.command;

import java.io.IOException;
import java.util.HashMap;

import hirono.exception.HironoException;
import hirono.storage.Storage;
import hirono.task.Task;
import hirono.task.TaskList;
import hirono.task.ToDo;
import hirono.task.Deadline;
import hirono.task.Event;
import hirono.ui.Ui;

public class AddCommand extends Command {
    private final String description;
    private final String type;

    public AddCommand(String description, String type) {
        this.description = description;
        this.type = type;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws IOException, HironoException {
        Task newTask = createTask();
        String message = addTask(taskList, newTask);
        ui.showMessage(message);
        storage.saveTasks(taskList);
    }

    /**
     * Creates a new task based on the type and description.
     *
     * @return The newly created task
     * @throws HironoException If the task type is invalid
     */
    public Task createTask() throws HironoException {
        return switch (type) {
            case "todo" -> new ToDo(description);
            case "deadline" -> new Deadline(description);
            case "event" -> new Event(description);
            default -> throw new HironoException("Invalid task type.");
        };
    }

    /**
     * Adds a task to the task list and generates a confirmation message.
     *
     * @param taskList The task list to add to
     * @param task The task to add
     * @return A confirmation message
     */
    private String addTask(TaskList taskList, Task task) {
        int taskId = taskList.addTaskAndGetId(task);
        return String.format("Got it. I've added this task:\n%s\nNow you have %d tasks in the list.",
                task.toString(),
                taskId);
    }
}