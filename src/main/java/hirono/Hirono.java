package hirono;

import java.io.IOException;

import hirono.commands.Command;
import hirono.tasks.TaskList;

/**
 * The main class for the Hirono application.
 * This class serves as the entry point for the program,
 * orchestrating the UI, task management, command parsing, and storage.
 */
public class Hirono {

    /**
     * The main method that runs the Hirono application.
     * It initializes the UI, task list, storage, and parser components,
     * and continuously processes user commands until an exit command is issued.
     *
     * @throws IOException     If there is an error reading from or writing to the storage file.
     * @throws HironoException If an error occurs during command execution.
     */
    public static void main(String[] args) throws IOException, HironoException {
        // Initialize components
        Ui ui = new Ui();
        Storage storage = new Storage("./data/hirono.txt");
        Parser parser = new Parser();
        TaskList taskList;

        // Show welcome message
        ui.showWelcome();

        // Load tasks from storage
        try {
            taskList = storage.loadTasks();
            ui.showMessage("Tasks loaded successfully!");
        } catch (IOException e) {
            ui.showMessage("Error loading tasks. Starting with an empty task list.");
            taskList = new TaskList();
        }

        // Main command loop
        String input = ui.readCommand();
        while (true) {
            try {
                // Parse and execute the command
                Command command = parser.parse(input);
                command.execute(taskList, ui, storage);

                // Exit the loop if the command is an exit command
                if (command.isExit()) {
                    break;
                }
            } catch (HironoException e) {
                // Show error message for invalid commands or execution issues
                ui.showError(e.getMessage());
            }

            // Display divider and read next command
            ui.showDivider();
            input = ui.readCommand();
        }
    }
}
