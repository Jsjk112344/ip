package hirono.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * Handles all user interactions, including displaying messages, reading commands,
 * and showing system messages such as errors and dividers.
 */
public class Ui {
    private final BufferedReader reader;
    private final PrintWriter writer;
    private final String line = "\n--------------------------------------------------";
    private String latestMessage = ""; // To store the latest message for GUI.

    /**
     * Constructs a Ui object for managing user interactions.
     * Initializes the reader and writer for input and output.
     */
    public Ui() {
        this.reader = new BufferedReader(new InputStreamReader(System.in));
        this.writer = new PrintWriter(System.out, true);
    }

    /**
     * Displays the welcome message and the logo for the application.
     */
    public void showWelcome() {
        String logo =
                  "                            .-'''-.                   .-'''-.     \n"
                + "                           '   _    \\                '   _    \\   \n"
                + "   .        .--.         /   /` '.   \\    _..._    /   /` '.   \\  \n"
                + " .'|        |__|        .   |     \\  '  .'     '. .   |     \\  '  \n"
                + "<  |        .--..-,.--. |   '      |  '.   .-.   .|   '      |  ' \n"
                + " | |        |  ||  .-. |\\    \\     / / |  '   '  |\\    \\     / /  \n"
                + " | | .'''-. |  || |  | | `.   ` ..' /  |  |   |  | `.   ` ..' /   \n"
                + " | |/.'''. \\|  || |  | |    '-...-'`   |  |   |  |    '-...-'`    \n"
                + " |  /    | ||  || |  '-                |  |   |  |                \n"
                + " | |     | ||__|| |                    |  |   |  |                \n"
                + " | |     | |    | |                    |  |   |  |                \n"
                + " | '.    | '.   |_|                    |  |   |  |                \n"
                + " '---'   '---'                         '--'   '--'                \n";
        showMessage("Hello from\n" + logo + line + "\nHello! I'm Hirono\nWhat can I do for you?\n" + line);
    }

    /**
     * Reads a command input from the user.
     *
     * @return The user command as a String.
     * @throws IOException If an input/output error occurs while reading the command.
     */
    public String readCommand() throws IOException {
        return reader.readLine();
    }

    /**
     * Displays a message to the user.
     *
     * @param message The message to display.
     */
    public void showMessage(String message) {
        writer.println(message);
        latestMessage = message; // Update latest message for GUI.
    }

    /**
     * Displays an error message to the user.
     *
     * @param errorMessage The error message to display.
     */
    public void showError(String errorMessage) {
        showMessage("Error: " + errorMessage);
    }

    /**
     * Displays a divider line to separate different sections in the output.
     */
    public void showDivider() {
        showMessage(line);
    }

    /**
     * Displays the goodbye message when the user exits the application.
     */
    public void showGoodbye() {
        showMessage(line + "\nBye. Hope to see you again soon!\n" + line);
    }

    /**
     * Gets the latest message for GUI interactions.
     *
     * @return The latest message as a String.
     */
    public String getLatestMessage() {
        return latestMessage;
    }
}
