package hirono.commands;
import hirono.*;
import hirono.tasks.*;
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

