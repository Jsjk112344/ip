package hirono;

import java.io.IOException;

public class Parser {

    public boolean isExitCommand(String input) {
        return input.equals("bye");
    }

    public void handleCommand(String input, TaskList taskList, Ui ui, Storage storage) throws HironoException, IOException {
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
    }

    private int parseTaskId(String input, String command) throws HironoException {
        String[] parts = input.split(" ");
        if (parts.length < 2) {
            throw new HironoException("The " + command + " command requires a task ID.");
        }
        return Integer.parseInt(parts[1]);
    }
}
