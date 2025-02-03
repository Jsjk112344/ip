package hirono.commands;

import java.io.IOException;

import hirono.HironoException;
import hirono.Storage;
import hirono.Ui;
import hirono.tasks.TaskList;

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
        taskList.markTask(taskId);
        ui.showMessage("Task " + taskId + " marked as done.");
        storage.saveTasks(taskList);

    }
}
