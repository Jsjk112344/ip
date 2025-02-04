package hirono;

import java.io.IOException;

import hirono.command.Command;
import hirono.parser.Parser;
import hirono.storage.Storage;
import hirono.task.TaskList;
import hirono.ui.Ui;

/**
 * The main class for the Hirono application.
 * This class serves as the entry point for the program,
 * orchestrating the UI, task management, command parsing, and storage.
 */
public class Hirono {

    private final Ui ui;
    private final Storage storage;
    private final Parser parser;
    private TaskList taskList;

    /**
     * Constructs a new instance of the Hirono application.
     * Initializes the UI, storage, parser, and task list components.
     *
     * @throws IOException If there is an error reading from the storage file.
     */
    public Hirono() throws IOException, HironoException {
        this.ui = new Ui();
        this.storage = new Storage("./data/hirono.txt");
        this.parser = new Parser();

        // Load tasks from storage or initialize an empty task list
        try {
            this.taskList = storage.loadTasks();
            ui.showMessage("Tasks loaded successfully!");
        } catch (IOException e) {
            ui.showMessage("Error loading tasks. Starting with an empty task list.");
            this.taskList = new TaskList();
        }
    }

    /**
     * Starts the main loop for the Hirono application.
     * Continuously processes user commands until an exit command is issued.
     *
     * @throws IOException     If there is an error reading from or writing to the storage file.
     * @throws HironoException If an error occurs during command execution.
     */
    public void run() throws IOException, HironoException {
        // Show welcome message
        ui.showWelcome();

        // Main command loop for CLI
        while (true) {
            try {
                String input = ui.readCommand();
                String response = processInput(input);

                // Display response
                ui.showMessage(response);

                // Exit the loop if the command is an exit command
                if (response.equals("Bye. Hope to see you again soon!")) {
                    break;
                }
            } catch (IOException | HironoException e) {
                ui.showError(e.getMessage());
            }
            ui.showDivider();
        }
    }

    /**
     * Processes the user input and returns the response from Hirono.
     *
     * @param input The user input string.
     * @return The response string from Hirono.
     */
    public String getResponse(String input) {
        try {
            return processInput(input);
        } catch (IOException | HironoException e) {
            return "Error: " + e.getMessage();
        }
    }

    /**
     * Processes the user input by parsing and executing the command.
     *
     * @param input The user input string.
     * @return The response string from Hirono.
     * @throws IOException     If there is an error during file operations.
     * @throws HironoException If the input is invalid.
     */
    private String processInput(String input) throws IOException, HironoException {
        Command command = parser.parse(input);
        command.execute(taskList, ui, storage);

        // Return the latest message from the UI
        return ui.getLatestMessage();
    }
}
