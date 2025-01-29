package hirono;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class Ui {
    private final BufferedReader reader;
    private final PrintWriter writer;
    private final String line = "\n--------------------------------------------------";

    public Ui() {
        this.reader = new BufferedReader(new InputStreamReader(System.in));
        this.writer = new PrintWriter(System.out, true);
    }

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

    public String readCommand() throws IOException {
        return reader.readLine();
    }

    public void showMessage(String message) {
        writer.println(message);
    }

    public void showError(String errorMessage) {
        writer.println("Error: " + errorMessage);
    }

    public void showDivider() {
        writer.println(line);
    }

    public void showGoodbye() {
        writer.println(line);
        writer.println("Bye. Hope to see you again soon!");
        writer.println(line);
    }
}
