package hirono;

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
        writer.println("Hello from\n" + logo);
        writer.println(line);
        writer.println("Hello! I'm Hirono");
        writer.println("What can I do for you?");
        writer.println(line);
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
    }

    /**
     * Displays an error message to the user.
     *
     * @param errorMessage The error message to display.
     */
    public void showError(String errorMessage) {
        writer.println("Error: " + errorMessage);
    }

    /**
     * Displays a divider line to separate different sections in the output.
     */
    public void showDivider() {
        writer.println(line);
    }

    /**
     * Displays the goodbye message when the user exits the application.
     */
    public void showGoodbye() {
        writer.println(line);
        writer.println("Bye. Hope to see you again soon!");
        writer.println(line);
    }
}
