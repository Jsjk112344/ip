package hirono.command;

import java.io.IOException;

import hirono.HironoException;
import hirono.storage.Storage;
import hirono.task.TaskList;
import hirono.ui.Ui;

/**
 * Marks a task as completed
 */
public class MarkCommand extends Command {
    private final int taskId;

    public MarkCommand(int taskId) {
        this.taskId = taskId;
    }


    /**
     * Marks a specific task as done,
     * if the item is already done, there will not be any change in behaviour
     * @param taskList
     * @param ui
     * @param storage
     * @throws IOException
     * @throws HironoException
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws IOException, HironoException {
        String message = taskList.markTask(taskId);
        ui.showMessage(message);
        storage.saveTasks(taskList);

    }
}
