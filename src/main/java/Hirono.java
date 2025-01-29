import java.io.*;

public class Hirono {
    public static String line = "\n--------------------------------------------------";

    public static void main(String[] args) throws IOException, HironoException {
        Ui ui = new Ui(); 
        Storage storage = new Storage("./data/hirono.txt");
        TaskList taskList;
        ui.showWelcome();
        taskList = storage.loadTasks();

        String input = ui.readCommand();
        while (!input.equals("bye")) {
            try {
                if (input.equals("list")) {
                    taskList.listTasks();
                } else if (input.startsWith("delete")) {
                    int taskId = parseTaskId(input, "delete");
                    storage.deleteTask(taskId);
                    ui.showMessage(taskList.deleteTask(taskId));
                } else if (input.startsWith("mark")) {
                    int taskId = parseTaskId(input, "mark");
                    taskList.markTask(taskId);
                    ui.showMessage("Task " + taskId + " marked as done.");
                } else if (input.startsWith("unmark")) {
                    int taskId = parseTaskId(input, "unmark");
                    taskList.unmarkTask(taskId);
                    ui.showMessage("Task " + taskId + " marked as not done.");
                } else if (input.startsWith("todo")) {
                    ui.showMessage(taskList.addTask(input, "todo"));
                } else if (input.startsWith("deadline")) {
                    ui.showMessage(taskList.addTask(input, "deadline"));
                } else if (input.startsWith("event")) {
                    ui.showMessage(taskList.addTask(input, "event"));
                } else if (input.startsWith("date")) {
                    ui.showMessage(taskList.getEventsOnDate(input));
                } else {
                    throw new HironoException("I'm sorry, but I don't know what that means.");
                }
                storage.saveTasks(taskList);

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

    private static int parseTaskId(String input, String command) throws HironoException {
        String[] parts = input.split(" ");
        if (parts.length < 2) {
            throw new HironoException("The " + command + " command requires a task ID.");
        }
        return Integer.parseInt(parts[1]);
    }
}
