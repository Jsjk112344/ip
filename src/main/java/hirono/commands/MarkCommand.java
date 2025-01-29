package hirono.commands;
import hirono.*;
import hirono.tasks.*;
import java.io.IOException;

public class MarkCommand extends Command {
    private final int taskId;

    public MarkCommand(int taskId) {
        this.taskId = taskId;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws IOException, HironoException {
        taskList.markTask(taskId);
        ui.showMessage("Task " + taskId + " marked as done.");
        storage.saveTasks(taskList);

    }
}
