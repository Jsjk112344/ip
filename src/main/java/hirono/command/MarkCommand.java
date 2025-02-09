// In MarkCommand.java
package hirono.command;

import java.io.IOException;
import java.util.HashMap;

import hirono.exception.HironoException;
import hirono.storage.Storage;
import hirono.task.Task;
import hirono.task.TaskList;
import hirono.ui.Ui;

/**
 * Marks a task as completed
 */
public class MarkCommand extends Command {
    private final int taskId;

    public MarkCommand(int taskId) {
        this.taskId = taskId;
    }

    /**
     * Marks a specific task as done,
     * if the item is already done, there will not be any change in behaviour
     * @param taskList The task list containing the tasks
     * @param ui The UI for displaying messages
     * @param storage The storage for saving tasks
     * @throws IOException If there's an error saving to storage
     * @throws HironoException If the task ID is invalid
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws IOException, HironoException {
        String message = markTask(taskList.getTasks());
        ui.showMessage(message);
        storage.saveTasks(taskList);
    }

    /**
     * Marks a task as done and returns a confirmation message.
     *
     * @param tasks The HashMap containing all tasks
     * @return A message confirming the task has been marked as done
     */
    public String markTask(HashMap<Integer, Task> tasks) {
        Task task = tasks.get(taskId);
        if (task == null) {
            return "Task ID not found!";
        }

        task.markAsDone();
        return String.format("Nice! I've marked this task as done:\n%d. %s",
                taskId,
                task.toString());
    }
}
