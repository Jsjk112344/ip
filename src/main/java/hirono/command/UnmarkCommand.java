package hirono.command;

import java.io.IOException;

import hirono.HironoException;
import hirono.storage.Storage;
import hirono.task.TaskList;
import hirono.ui.Ui;

/**
 * Unmarks a task as completed
 */
public class UnmarkCommand extends Command {
    private final int taskId;

    public UnmarkCommand(int taskId) {
        this.taskId = taskId;
    }


    /**
     * Unmarks a specific task as done,
     * if the item is not yet done, there will not be any change in behaviour
     * @param taskList
     * @param ui
     * @param storage
     * @throws IOException
     * @throws HironoException
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws IOException, HironoException {
        String message = taskList.unmarkTask(taskId);
        ui.showMessage(message);
        storage.saveTasks(taskList);

    }
}

