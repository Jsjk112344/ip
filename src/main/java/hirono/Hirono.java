package hirono;
import hirono.commands.*;
import hirono.tasks.*;
import java.io.*;

public class Hirono {
    public static void main(String[] args) throws IOException, HironoException {
        Ui ui = new Ui();
        Storage storage = new Storage("./data/hirono.txt");
        Parser parser = new Parser();
        TaskList taskList;

        ui.showWelcome();

        try {
            taskList = storage.loadTasks();
            ui.showMessage("Tasks loaded successfully!");
        } catch (IOException e) {
            ui.showMessage("Error loading tasks. Starting with an empty task list.");
            taskList = new TaskList();
        }

        String input = ui.readCommand();
        while (true) {
            try {
                Command command = parser.parse(input);
                command.execute(taskList, ui, storage);
                if (command.isExit()) {
                    break;
                }
            } catch (HironoException e) {
                ui.showError(e.getMessage());
            }
            ui.showDivider();
            input = ui.readCommand();
        }
    }
}
