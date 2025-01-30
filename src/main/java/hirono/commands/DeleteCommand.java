package hirono.commands;
import hirono.HironoException;
import hirono.Ui;
import hirono.Storage;
import hirono.tasks.TaskList;
import java.io.IOException;

public class DeleteCommand extends Command {
    private final int taskId;

    public DeleteCommand(int taskId) {
        this.taskId = taskId;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws IOException, HironoException {
        storage.deleteTask(taskId);
        String message = taskList.deleteTask(taskId);
        ui.showMessage(message);
    }
}

