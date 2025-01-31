package hirono.commands;


import hirono.HironoException;
import hirono.Storage;
import hirono.Ui;
import hirono.tasks.TaskList;

/**
 * Represents a command to list the events or deadlines on a specific date.
 */
public class FindCommand extends Command {
    private final String input;

    public FindCommand(String input) {
        this.input = input;
    }
     /**
     * Executes the DateCommand by listing the events and deadlines on the specific date,
     *
     * @param taskList The task list to which the new task will be added.
     * @param ui       The UI for interacting with the user.
     * @param storage  The storage to save the updated task list.
     * @throws HironoException If an error occurs during the listing of the tasks (e.g., invalid input).
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws HironoException {
        String message = taskList.findTasks(input);
        ui.showMessage(message);
    }
}

