package hirono.commands;
import hirono.*;
import hirono.tasks.*;
import java.io.IOException;

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

