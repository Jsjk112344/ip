package hirono.commands;

import hirono.HironoException;
import hirono.Ui;
import hirono.Storage;
import hirono.tasks.TaskList;

public class DateCommand extends Command {
    private final String input;

    public DateCommand(String input) {
        this.input = input;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws HironoException {
        String message = taskList.getEventsOnDate(input);
        ui.showMessage(message);
    }
}



