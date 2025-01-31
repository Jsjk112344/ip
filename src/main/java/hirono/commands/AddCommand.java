package hirono.commands;
import java.io.IOException;

import hirono.HironoException;
import hirono.Storage;
import hirono.Ui;
import hirono.tasks.TaskList;

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

