package hirono.commands;

import java.io.IOException;

import hirono.HironoException;
import hirono.Storage;
import hirono.Ui;
import hirono.tasks.TaskList;

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
        taskList.unmarkTask(taskId);
        ui.showMessage("Task " + taskId + " marked as not done.");
        storage.saveTasks(taskList);

    }
}

