package hirono.command;

import java.io.IOException;

import hirono.HironoException;
import hirono.storage.Storage;
import hirono.task.TaskList;
import hirono.ui.Ui;

/**
 * Represents a command to delete a specific item from the list
 */
public class DeleteCommand extends Command {
    private final int taskId;

    public DeleteCommand(int taskId) {
        this.taskId = taskId;
    }

    /**
     * Executes a delete command on the task specific by its task id
     * @param taskList
     * @param ui
     * @param storage
     * @throws IOException
     * @throws HironoException
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws IOException, HironoException {
        storage.deleteTask(taskId);
        String message = taskList.deleteTask(taskId);
        ui.showMessage(message);
    }
}

