package hirono.commands;
import hirono.*;
import hirono.tasks.*;


public class ListCommand extends Command {

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        // ui.showMessage("Here are the tasks in your list:");
        taskList.listTasks();
    }
}
