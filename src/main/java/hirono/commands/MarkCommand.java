package hirono.commands;
import hirono.HironoException;
import hirono.Ui;
import hirono.Storage;
import hirono.tasks.TaskList;
import java.io.IOException;

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
