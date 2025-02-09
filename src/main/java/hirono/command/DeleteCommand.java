package hirono.command;

import java.io.IOException;
import java.util.HashMap;
import java.util.TreeMap;

import hirono.exception.HironoException;
import hirono.storage.Storage;
import hirono.task.Task;
import hirono.task.TaskList;
import hirono.ui.Ui;

/**
 * Represents a command to delete a specific item from the list
 */
public class DeleteCommand extends Command {
    private final int taskId;

    public DeleteCommand(int taskId) {
        this.taskId = taskId;
    }

    /**
     * Executes a delete command on the task specific by its task id
     * @param taskList The task list containing the tasks
     * @param ui The UI for displaying messages
     * @param storage The storage for saving tasks
     * @throws IOException If there's an error saving to storage
     * @throws HironoException If the task ID is invalid
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws IOException, HironoException {
        storage.deleteTask(taskId);
        String message = deleteTask(taskList.getTasks());
        ui.showMessage(message);
    }

    /**
     * Deletes a task and returns a confirmation message.
     * 
     * @param tasks The HashMap containing all tasks
     * @return A message confirming the task has been deleted
     * @throws HironoException If the task ID is invalid or out of range
     */
    public String deleteTask(HashMap<Integer, Task> tasks) throws HironoException {
        if (!tasks.containsKey(taskId)) {
            throw new HironoException("The item you are attempting to delete is out of the range of the list.");
        }

        Task deletedTask = tasks.get(taskId);
        tasks.remove(taskId);
        reorderTasks(tasks);

        return String.format("Noted. I've removed this task:\n%s\nNow you have %d tasks in the list.",
                deletedTask.toString(),
                tasks.size());
    }

    /**
     * Reorders the tasks to maintain sequential IDs after a deletion.
     * 
     * @param tasks The HashMap of tasks to reorder
     */
    private void reorderTasks(HashMap<Integer, Task> tasks) {
        TreeMap<Integer, Task> sortedTasks = new TreeMap<>(tasks);
        tasks.clear();
        
        int newId = 1;
        for (Task task : sortedTasks.values()) {
            tasks.put(newId++, task);
        }
    }
}
