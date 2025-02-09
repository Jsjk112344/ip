package hirono.command;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import hirono.exception.HironoException;
import hirono.storage.Storage;
import hirono.task.Task;
import hirono.task.TaskList;
import hirono.ui.Ui;

/**
 * Represents a command to find tasks containing a specific search term.
 */
public class FindCommand extends Command {
    private final String input;

    public FindCommand(String input) {
        this.input = input;
    }

    /**
     * Executes the FindCommand by searching for tasks containing the specified term.
     *
     * @param taskList The task list to search through.
     * @param ui       The UI for interacting with the user.
     * @param storage  The storage to save the updated task list.
     * @throws HironoException If an error occurs during the search (e.g., invalid input).
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws HironoException {
        String message = findTasks(taskList.getTasks());
        ui.showMessage(message);
    }

    /**
     * Finds tasks that match the specified search term.
     *
     * @param tasks The HashMap of tasks to search through.
     * @return A formatted string containing the matching tasks.
     * @throws HironoException If the search term is missing or invalid.
     */
    public String findTasks(HashMap<Integer, Task> tasks) throws HironoException {
        String[] parts = input.split(" ", 2);
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new HironoException("The find command requires a search term. Please use: find [search term]");
        }

        String searchTerm = parts[1].trim().toLowerCase();

        List<Task> matchingTasks = tasks.values().stream()
                .filter(task -> task.getDescription().toLowerCase().contains(searchTerm))
                .toList();

        if (matchingTasks.isEmpty()) {
            return "No tasks found matching \""
                + searchTerm
                + "\".";
        }

        return "Here are the matching tasks:\n"
            + IntStream.range(0, matchingTasks.size())
                .mapToObj(i -> (i + 1) + ". " + matchingTasks.get(i).toString())
                    .collect(Collectors.joining("\n"));
    }
}

// In TaskList.java, replace the existing findTasks method with:
