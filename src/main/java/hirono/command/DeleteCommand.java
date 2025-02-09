package hirono.command;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws IOException, HironoException {
        storage.deleteTask(taskId);  // Keep using storage's deleteTask for consistency
        String message = deleteTask(taskList.getTasks());
        ui.showMessage(message);
    }

    /**
     * Deletes a task from storage file.
     * 
     * @param filePath The path to the storage file
     * @param taskNumber The number of the task to delete
     * @throws HironoException If the file doesn't exist or task number is invalid
     * @throws IOException If an error occurs during file operations
     */
    public static void deleteFromStorage(String filePath, int taskNumber) throws HironoException, IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new HironoException("Task file does not exist.");
        }

        // Read all lines into memory
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }

        // Validate the task number
        if (taskNumber <= 0 || taskNumber > lines.size()) {
            throw new HironoException("Invalid task number. Task does not exist.");
        }

        // Remove the task and update the file
        lines.remove(taskNumber - 1);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (String updatedLine : lines) {
                writer.write(updatedLine);
                writer.newLine();
            }
        }
    }

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

    private void reorderTasks(HashMap<Integer, Task> tasks) {
        TreeMap<Integer, Task> sortedTasks = new TreeMap<>(tasks);
        tasks.clear();
        
        int newId = 1;
        for (Task task : sortedTasks.values()) {
            tasks.put(newId++, task);
        }
    }
}