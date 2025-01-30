package hirono.commands;
import hirono.HironoException;
import hirono.Ui;
import hirono.Storage;
import hirono.tasks.TaskList;
import java.io.IOException;

public class AddCommand extends Command {
    private final String description;
    private final String type;

    public AddCommand(String description, String type) {
        this.description = description;
        this.type = type;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws IOException, HironoException {
        String message = taskList.addTask(description, type);
        ui.showMessage(message);
        storage.saveTasks(taskList);

    }
}


