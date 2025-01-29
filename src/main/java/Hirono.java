import java.io.*;

public class Hirono {
    public static String line = "\n--------------------------------------------------";

    public static void main(String[] args) throws IOException, HironoException {
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
        Storage storage = new Storage("./data/hirono.txt");
        try {
            taskList = storage.loadTasks();
            taskList.listTasks();
        } catch (IOException e) {
            writer.println("Error loading tasks. Starting with an empty task list.");
            taskList = new TaskList();
        }


        while (!input.equals("bye")) {
            try {
                if (input.equals("list")) {
                    taskList.listTasks();
                } else if (input.startsWith("delete")) {
                    int taskId = parseTaskId(input, "delete");
                    storage.deleteTask(taskId);
                    writer.println(taskList.deleteTask(taskId));
                } else if (input.startsWith("mark")) {
                    int taskId = parseTaskId(input, "mark");
                    taskList.markTask(taskId);
                } else if (input.startsWith("unmark")) {
                    int taskId = parseTaskId(input, "unmark");
                    taskList.unmarkTask(taskId);
                } else if (input.startsWith("todo")) {
                    writer.println(taskList.addTask(input, "todo"));
                } else if (input.startsWith("deadline")) {
                    writer.println(taskList.addTask(input, "deadline"));
                } else if (input.startsWith("event")) {
                    writer.println(taskList.addTask(input, "event"));
                } else {
                    throw new HironoException("I'm sorry, but I don't know what that means.");
                }
                storage.saveTasks(taskList);

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

    private static int parseTaskId(String input, String command) throws HironoException {
        String[] parts = input.split(" ");
        if (parts.length < 2) {
            throw new HironoException("The " + command + " command requires a task ID.");
        }
        return Integer.parseInt(parts[1]);
    }
}
