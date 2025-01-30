package hirono.commands;
import hirono.Ui;
import hirono.Storage;
import hirono.tasks.TaskList;


public class ExitCommand extends Command {
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        ui.showGoodbye();
    }

    @Override
    public boolean isExit() {
        return true;
    }
}




