package hirono.commands;

import hirono.Storage;
import hirono.Ui;
import hirono.tasks.TaskList;

/**
 * Represents the command to exit the application.
 * When executed, it displays a goodbye message to the user.
 */
public class ExitCommand extends Command {

    /**
     * Executes the ExitCommand by showing a goodbye message.
     *
     * @param taskList The task list being managed. (Unused in this command)
     * @param ui       The UI for interacting with the user.
     * @param storage  The storage used for saving tasks. (Unused in this command)
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        ui.showGoodbye();
    }

    /**
     * Indicates that this command will terminate the application.
     *
     * @return true, as this is an exit command.
     */
    @Override
    public boolean isExit() {
        return true;
    }
}
