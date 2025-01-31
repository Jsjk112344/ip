package hirono.commands;
import hirono.Ui;
import hirono.Storage;
import hirono.tasks.TaskList;

public class ListCommand extends Command {

    /** 
     * Lists out all the items in the saved list
     * @param taskList
     * @param ui
     * @param storage
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        // ui.showMessage("Here are the tasks in your list:");
        taskList.listTasks();
    }
}
