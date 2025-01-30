package hirono.commands;
import hirono.HironoException;
import hirono.Ui;
import hirono.Storage;
import hirono.tasks.TaskList;
import java.io.IOException;

public class UnmarkCommand extends Command {
    private final int taskId;

    public UnmarkCommand(int taskId) {
        this.taskId = taskId;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws IOException, HironoException {
        taskList.unmarkTask(taskId);
        ui.showMessage("Task " + taskId + " marked as not done.");
        storage.saveTasks(taskList);

    }
}

