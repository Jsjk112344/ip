import java.io.*;

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
            // writer.println(line);

            if (input.equals("list")) {
                taskList.listTasks();
            } else if (input.startsWith("mark")) {
                int taskId = Integer.parseInt(input.split(" ")[1]);
                taskList.markTask(taskId);
            } else if (input.startsWith("unmark")) {
                int taskId = Integer.parseInt(input.split(" ")[1]);
                taskList.unmarkTask(taskId);
            } 
            
            else {
                writer.println(taskList.addTask(input, input.split(" ")[0]));
                writer.flush();
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
}
