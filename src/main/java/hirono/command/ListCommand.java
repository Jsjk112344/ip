package hirono.command;

import hirono.storage.Storage;
import hirono.task.TaskList;
import hirono.ui.Ui;

/**
 * Represents the command to list all tasks in the task list.
 * When executed, it displays all tasks to the user.
 */
public class ListCommand extends Command {

    /**
     * Executes the ListCommand by listing all tasks in the task list.
     *
     * @param taskList The task list containing the tasks to be displayed.
     * @param ui       The UI for interacting with the user.
     * @param storage  The storage used for saving tasks. (Unused in this command)
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        String message = taskList.listTasks();
        ui.showMessage(message);
    }
}
