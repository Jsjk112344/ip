import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Hirono {
    public static String line = "\n--------------------------------------------------";

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter writer = new PrintWriter(System.out);

        // Logo
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
        System.out.println("Hello from\n" + logo);
        writer.println(line);

        writer.println("Hello! I'm Hirono");
        writer.println("What can I do for you?");
        writer.println(line);
        writer.flush();

        TaskList taskList = new TaskList();
        String input = reader.readLine();

        while (!input.equals("bye")) {
            try {
                if (input.equals("list")) {
                    taskList.listTasks();
                } else if (input.startsWith("mark")) {
                    int taskId = parseTaskId(input, "mark");
                    taskList.markTask(taskId);
                } else if (input.startsWith("unmark")) {
                    int taskId = parseTaskId(input, "unmark");
                    taskList.unmarkTask(taskId);
                } else if (input.startsWith("todo")) {
                    writer.println(taskList.addTask(input, "todo"));
                } else if (input.startsWith("deadline")) {
                    isValidDeadline(input);
                    writer.println(taskList.addTask(input, "deadline"));
                } else if (input.startsWith("event")) {
                    isValidEvent(input);
                    writer.println(taskList.addTask(input, "event"));
                } else {
                    throw new HironoException("I'm sorry, but I don't know what that means.");
                }
            } catch (HironoException e) {
                writer.println("Error: " + e.getMessage());
            } catch (NumberFormatException e) {
                writer.println("Error: Task ID must be a valid number.");
            }

            writer.println(line);
            writer.flush();
            input = reader.readLine();
        }

        writer.println(line);
        writer.println("Bye. Hope to see you again soon!");
        writer.println(line + "\n");

        writer.close();
        reader.close();
    }

    private static boolean isValidEvent(String description) throws HironoException{
        String eventRegex = "^event\\s+.+\\s+/from\\s+.+\\s+\\d+(am|pm)?\\s+/to\\s+\\d+(am|pm)?$";
        Pattern pattern = Pattern.compile(eventRegex);
        Matcher matcher = pattern.matcher(description);
        if (!matcher.matches()) {
            throw new HironoException("The event command is not in the correct format.");
        }
        return matcher.matches();
    }
    private static boolean isValidDeadline(String description) throws HironoException{
        String eventRegex = "^deadline\s+.+\s+/by\s+.+$";
        Pattern pattern = Pattern.compile(eventRegex);
        Matcher matcher = pattern.matcher(description);
        if (!matcher.matches()) {
            throw new HironoException("The deadline command is not in the correct format.");
        }
        return matcher.matches();
    }

    
    private static int parseTaskId(String input, String command) throws HironoException {
        String[] parts = input.split(" ");
        if (parts.length < 2) {
            throw new HironoException("The " + command + " command requires a task ID.");
        }
        return Integer.parseInt(parts[1]);
    }

    private static String parseTaskDescription(String input, String command) throws HironoException {
        String[] parts = input.split(" ", 2);
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new HironoException("The description of a " + command + " cannot be empty.");
        }
        return parts[1].trim();
    }
}
