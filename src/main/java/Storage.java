import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Storage {
    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
        ensureFileExists();
    }

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

    public void deleteTask(Integer taskNumber) throws HironoException, IOException {
        File file = new File(filePath);

        // Step 1: Check if the file exists, throw an exception if it doesn't
        if (!file.exists()) {
            throw new HironoException("Task file does not exist.");
        }

        // Step 2: Read all lines into memory
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }

        if (taskNumber <= 0 || taskNumber > lines.size()) {
            throw new HironoException("Invalid task number. Task does not exist.");
        }
        lines.remove(taskNumber - 1); 

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (String updatedLine : lines) {
                writer.write(updatedLine);
                writer.newLine();
            }
        }
    }


    // Saves the task list to the file
    public void saveTasks(TaskList taskList) throws IOException {
        FileWriter writer = new FileWriter(filePath);
        for (Map.Entry<Integer, Task> entry : taskList.getTasks().entrySet()) {
            Task task = entry.getValue();
            writer.write(task.toFileFormat() + System.lineSeparator());
        }
        writer.close();
    }

    // Loads tasks from the file and returns a TaskList
    public TaskList loadTasks() throws HironoException, IOException {
        TaskList taskList = new TaskList();
        File file = new File(filePath);

        if (!file.exists()) {
            // Create the file if it doesn't exist
            file.getParentFile().mkdirs();
            file.createNewFile();
        }

        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split("\\|");
            String type = parts[0].trim();
            boolean isDone = parts[1].trim().equals("1");
            String description = parts[2].trim();

            switch (type) {
                case "T":
                    ToDo todo = new ToDo("todo " + description);
                    if (isDone) todo.markAsDone();
                    taskList.addLoadedTask(todo);
                    break;
                case "D":
                    Deadline deadline = new Deadline("deadline " + description + " /by " + parts[3].trim());
                    if (isDone) deadline.markAsDone();
                    taskList.addLoadedTask(deadline);
                    break;
                case "E":
                    Event event = new Event("event " + description + " /from " + parts[3].trim() + " /to " + parts[4].trim());
                    if (isDone) event.markAsDone();
                    taskList.addLoadedTask(event);
                    break;
            }
        }
        scanner.close();
        return taskList;
    }
}
