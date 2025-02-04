package hirono.command;

import java.io.IOException;

import hirono.HironoException;
import hirono.storage.Storage;
import hirono.task.TaskList;
import hirono.ui.Ui;

/**
 * Represents a command to add a new task to the task list.
 * This command handles adding different types of tasks such as ToDo, Deadline, or Event.
 */
public class AddCommand extends Command {
    private final String description;
    private final String type;

    /**
     * Constructs an AddCommand with the specified task description and type.
     *
     * @param description The description of the task to be added.
     * @param type        The type of task (e.g., "todo", "deadline", "event").
     */
    public AddCommand(String description, String type) {
        this.description = description;
        this.type = type;
    }

    /**
     * Executes the AddCommand by adding a task to the task list, saving the updated list,
     * and displaying a confirmation message.
     *
     * @param taskList The task list to which the new task will be added.
     * @param ui       The UI for interacting with the user.
     * @param storage  The storage to save the updated task list.
     * @throws IOException     If an I/O error occurs while saving the task list.
     * @throws HironoException If an error occurs while adding the task (e.g., invalid input format).
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws IOException, HironoException {
        String message = taskList.addTask(description, type);
        ui.showMessage(message);
        storage.saveTasks(taskList);
    }
}
