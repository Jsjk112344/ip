package hirono.command;

import java.io.IOException;
import java.util.HashMap;

import hirono.exception.HironoException;
import hirono.storage.Storage;
import hirono.task.Task;
import hirono.task.TaskList;
import hirono.ui.Ui;

/**
 * Unmarks a task as completed
 */
public class UnmarkCommand extends Command {
    private final int taskId;

    public UnmarkCommand(int taskId) {
        this.taskId = taskId;
    }

    /**
     * Unmarks a specific task as done,
     * if the item is not yet done, there will not be any change in behaviour
     * @param taskList The task list containing the tasks
     * @param ui The UI for displaying messages
     * @param storage The storage for saving tasks
     * @throws IOException If there's an error saving to storage
     * @throws HironoException If the task ID is invalid
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws IOException, HironoException {
        String message = unmarkTask(taskList.getTasks());
        ui.showMessage(message);
        storage.saveTasks(taskList);
    }

    /**
     * Unmarks a task as not done and returns a confirmation message.
     *
     * @param tasks The HashMap containing all tasks
     * @return A message confirming the task has been unmarked
     */
    public String unmarkTask(HashMap<Integer, Task> tasks) throws HironoException {
        Task task = tasks.get(taskId);
        if (task == null) {
            throw new HironoException("Task ID Not Found!");
        }

        task.unmark();
        return String.format("OK, I've marked this task as not done yet:\n%d. %s",
                taskId,
                task.toString());
    }
}
