package hirono.storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

import hirono.command.DeleteCommand;
import hirono.exception.HironoException;
import hirono.task.Deadline;
import hirono.task.Event;
import hirono.task.Task;
import hirono.task.TaskList;
import hirono.task.ToDo;

/**
 * Handles the saving and loading of tasks to and from a file.
 * This class is responsible for managing the persistence of tasks, ensuring
 * the task list is stored in and retrieved from the storage file.
 */
public class Storage {
    private final String filePath;

    /**
     * Constructs a Storage object for managing the specified file path.
     *
     * @param filePath The file path where tasks will be stored and retrieved.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
        ensureFileExists();
    }

    public String getFilePath() {
        return filePath;
    }

    /**
     * Ensures the storage file and its parent directory exist.
     * Creates the file and directory if they do not exist.
     */
    private void ensureFileExists() {
        File file = new File(filePath);
        try {
            if (!file.exists()) {
                File parentDir = file.getParentFile();
                if (parentDir != null && !parentDir.exists()) {
                    parentDir.mkdirs(); // Create parent directory if it doesn't exist
                }
                file.createNewFile(); // Create the file
            }
        } catch (IOException e) {
            System.out.println("Error creating the file: " + e.getMessage());
        }
    }


    /**
     * Saves the current task list to the storage file.
     *
     * @param taskList The task list to save.
     * @throws IOException If an error occurs during saving to the file.
     */
    public void saveTasks(TaskList taskList) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            for (Map.Entry<Integer, Task> entry : taskList.getTasks().entrySet()) {
                Task task = entry.getValue();
                writer.write(task.toFileFormat() + System.lineSeparator());
            }
        }
    }

    /**
     * Deletes a task from the storage file by its task number.
     * This method is maintained for testing purposes and delegates to DeleteCommand.
     *
     * @param taskNumber The number of the task to delete (1-based index).
     * @throws HironoException If the file does not exist or the task number is invalid.
     * @throws IOException If an error occurs during file I/O operations.
     */
    public void deleteTask(Integer taskNumber) throws HironoException, IOException {
        DeleteCommand.deleteFromStorage(filePath, taskNumber);
    }

    /**
     * Loads tasks from the storage file and returns them as a TaskList.
     *
     * @return The TaskList containing the tasks loaded from the file.
     * @throws IOException      If an error occurs during reading from the file.
     * @throws HironoException If the task format in the file is invalid.
     */
    public TaskList loadTasks() throws HironoException, IOException {
        TaskList taskList = new TaskList();
        File file = new File(filePath);

        // Ensure the file exists
        if (!file.exists()) {
            File parentDir = file.getParentFile();
            if (parentDir != null) {
                parentDir.mkdirs();
            }
            file.createNewFile();
        }

        try (Scanner scanner = new Scanner(file)) {
            // Parse each line and add the task to the TaskList
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split("\\|");
                String type = parts[0].trim();
                boolean isDone = parts[1].trim().equals("1");
                String description = parts[2].trim();

                switch (type) {
                case "T":
                    ToDo todo = new ToDo("todo " + description);
                    if (isDone) {
                        todo.markAsDone();
                    }
                    taskList.addLoadedTask(todo);
                    break;

                case "D":
                    Deadline deadline = new Deadline(
                        "deadline " + description + " /by " + parts[3].trim()
                    );
                    if (isDone) {
                        deadline.markAsDone();
                    }
                    taskList.addLoadedTask(deadline);
                    break;

                case "E":
                    Event event = new Event(
                        "event " + description + " /from " + parts[3].trim() + " /to " + parts[4].trim()
                    );
                    if (isDone) {
                        event.markAsDone();
                    }
                    taskList.addLoadedTask(event);
                    break;

                default:
                    throw new HironoException("Invalid task type in file.");
                }
            }
        }

        return taskList;
    }
}
