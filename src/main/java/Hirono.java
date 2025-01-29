import java.io.*;

public class Hirono {
    public static String line = "\n--------------------------------------------------";

    public static void main(String[] args) throws IOException, HironoException {
        Ui ui = new Ui();
        Storage storage = new Storage("./data/hirono.txt");
        Parser parser = new Parser();
        TaskList taskList;

        ui.showWelcome();

        // Load tasks
        try {
            taskList = storage.loadTasks();
            ui.showMessage("Tasks loaded successfully!");
        } catch (IOException e) {
            ui.showMessage("Error loading tasks. Starting with an empty task list.");
            taskList = new TaskList();
        }

        String input = ui.readCommand();
        while (!parser.isExitCommand(input)) {
            try {
                parser.handleCommand(input, taskList, ui, storage);
            } catch (HironoException e) {
                ui.showError(e.getMessage());
            } catch (NumberFormatException e) {
                ui.showError("Task ID must be a valid number.");
            }

            ui.showDivider();
            input = ui.readCommand();
        }

        ui.showGoodbye();
    }
}
