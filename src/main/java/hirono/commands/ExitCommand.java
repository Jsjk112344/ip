package hirono.commands;
import hirono.*;
import hirono.tasks.*;


public class ExitCommand extends Command {
    
    /** 
     * @param taskList
     * @param ui
     * @param storage
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        ui.showGoodbye();
    }

    @Override
    public boolean isExit() {
        return true;
    }
}




