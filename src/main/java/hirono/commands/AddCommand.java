package hirono.commands;
<<<<<<< HEAD

import hirono.*;
import hirono.tasks.*;
=======
import hirono.HironoException;
import hirono.Ui;
import hirono.Storage;
import hirono.tasks.TaskList;
>>>>>>> origin/branch-Level-9
import java.io.IOException;

/**
 * Represents a command to add a new task to the task list.
 * This command can handle adding different types of tasks, such as ToDo, Deadline, or Event.
 */
public class AddCommand extends Command {
    private final String description;
    private final String type;

    /**
     * Constructs an AddCommand with the specified task description and type.
     *
     * @param description The description of the task to be added.
     * @param type        The type of the task (e.g., "todo", "deadline", "event").
     */
    public AddCommand(String description, String type) {
        this.description = description;
        this.type = type;
    }

    /**
     * Executes the AddCommand by adding a new task to the task list, showing a confirmation message,
     * and saving the updated task list to storage.
     *
     * @param taskList The task list to which the new task will be added.
     * @param ui       The UI for interacting with the user.
     * @param storage  The storage to save the updated task list.
     * @throws IOException     If an error occurs during saving to the storage file.
     * @throws HironoException If an error occurs during the addition of the task (e.g., invalid input).
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws IOException, HironoException {
        // Add the task to the task list
        String message = taskList.addTask(description, type);

        // Display a confirmation message to the user
        ui.showMessage(message);

        // Save the updated task list to storage
        storage.saveTasks(taskList);
    }
}
