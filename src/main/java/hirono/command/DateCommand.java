package hirono.command;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import hirono.exception.HironoException;
import hirono.storage.Storage;
import hirono.task.Deadline;
import hirono.task.Event;
import hirono.task.Task;
import hirono.task.TaskList;
import hirono.ui.Ui;

/**
 * Represents a command to list the events or deadlines on a specific date.
 */
public class DateCommand extends Command {
    private final String input;

    public DateCommand(String input) {
        this.input = input;
    }
    /**
     * Executes the DateCommand by listing the events and deadlines on the specific date,
     *
     * @param taskList The task list to which the new task will be added.
     * @param ui       The UI for interacting with the user.
     * @param storage  The storage to save the updated task list.
     * @throws HironoException If an error occurs during the listing of the tasks (e.g., invalid input).
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws HironoException {
        String message = getEventsOnDate(taskList.getTasks());
        ui.showMessage(message);
    }

    /**
     * Lists events and deadlines occurring on a specific date.
     *
     * @param tasks The HashMap of tasks to search through
     * @return A list of events and deadlines on the specified date.
     * @throws HironoException If the date is invalid or incorrectly formatted.
     */
    public String getEventsOnDate(HashMap<Integer, Task> tasks) throws HironoException {
        String[] parts = input.split(" ");
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new HironoException("The date command requires a date in yyyy-MM-dd format.");
        }

        LocalDate date;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            date = LocalDate.parse(parts[1].trim(), formatter);
        } catch (DateTimeParseException e) {
            throw new HironoException("Invalid date format. Please use yyyy-MM-dd (e.g., 2019-12-02).");
        }

        List<Task> eventsOnDate = tasks.values().stream()
                .filter(task -> (task instanceof Event && ((Event) task).isOnDate(date))
                        || (task instanceof Deadline && ((Deadline) task).isOnDate(date)))
                .toList();

        if (eventsOnDate.isEmpty()) {
            return "No events or deadlines found on "
                + date;
        }

        return "Here are the events and deadlines on "
            + date
            + ":\n"
            + IntStream.range(0, eventsOnDate.size())
                        .mapToObj(i -> (i + 1) + ". " + eventsOnDate.get(i).toString())
                        .collect(Collectors.joining("\n"));
    }
}



