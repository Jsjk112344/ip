package hirono.command;

import java.io.IOException;

import hirono.HironoException;
import hirono.storage.Storage;
import hirono.task.TaskList;
import hirono.ui.Ui;

/**
* Represents the parent of all command classes
*/
public abstract class Command {

    /**
     * Executes the command with the given context.
     *
     * @param taskList The task list to operate on.
     * @param ui       The UI to interact with the user.
     * @param storage  The storage to save/load tasks.
     * @throws HironoException If an error occurs during command execution.
     */
    public abstract void execute(TaskList taskList, Ui ui, Storage storage) throws IOException, HironoException;

    /**
     * Determines if the command is an exit command.
     *
     * @return true if it is an exit command, false otherwise.
     */
    public boolean isExit() {
        return false;
    }
}
