package hirono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import hirono.exception.HironoException;
import hirono.storage.Storage;
import hirono.task.TaskList;

/**
 * Tests the Storage class for saving, loading, and deleting tasks.
 */
public class StorageTest {
    private static final String TEST_FILE_PATH = "./data/test-hirono.txt";
    private Storage storage;
    private TaskList taskList;

    /**
     * Sets up the test environment before each test case.
     * Initializes the Storage and TaskList objects.
     */
    @BeforeEach
    public void setUp() {
        storage = new Storage(TEST_FILE_PATH);
        taskList = new TaskList();
    }

    /**
     * Cleans up the test environment after each test case.
     * Deletes the test file if it exists.
     */
    @AfterEach
    public void tearDown() {
        File testFile = new File(TEST_FILE_PATH);
        if (testFile.exists()) {
            testFile.delete(); // Delete test file after each test to avoid conflicts
        }
    }

    /**
     * Tests the saveTasks and loadTasks methods for correctness.
     *
     * @throws IOException     If an I/O error occurs.
     * @throws HironoException If an error occurs during task operations.
     */
    @Test
    public void testSaveAndLoadTasks() throws IOException, HironoException {
        // Add tasks
        taskList.addTask("todo read book", "todo");
        taskList.addTask("deadline submit report /by 2023-11-02 1800", "deadline");
        taskList.addTask(
            "event team meeting /from 2023-11-02 1400 /to 2023-11-02 1600",
            "event"
        );

        // Save tasks
        storage.saveTasks(taskList);

        // Load tasks into a new TaskList
        TaskList loadedTaskList = storage.loadTasks();
        assertEquals(3, loadedTaskList.getTasks().size());

        // Check if tasks are correctly restored
        assertEquals(
            "[T][ ] read book",
            loadedTaskList.getTasks().get(1).toString()
        );
        assertEquals(
            "[D][ ] submit report (by: 2 Nov 2023, 6:00pm)",
            loadedTaskList.getTasks().get(2).toString()
        );
        assertEquals(
            "[E][ ] team meeting (from: 2 Nov 2023, 2:00pm to: 2 Nov 2023, 4:00pm)",
            loadedTaskList.getTasks().get(3).toString()
        );
    }

    /**
     * Tests loading tasks from an empty file.
     *
     * @throws IOException     If an I/O error occurs.
     * @throws HironoException If an error occurs during task operations.
     */
    @Test
    public void testLoadFromEmptyFile() throws IOException, HironoException {
        // Create an empty test file
        File testFile = new File(TEST_FILE_PATH);
        testFile.createNewFile();

        // Attempt to load tasks from an empty file
        TaskList loadedTaskList = storage.loadTasks();
        assertEquals(0, loadedTaskList.getTasks().size());
    }

    /**
     * Tests the deleteTask method for removing a task from storage.
     *
     * @throws Exception If an error occurs during task deletion.
     */
    @Test
    public void testDeleteTaskFromStorage() throws Exception {
        // Add and save tasks
        taskList.addTask("todo read book", "todo");
        taskList.addTask("deadline submit report /by 2023-11-02 1800", "deadline");
        storage.saveTasks(taskList);

        // Delete task 1 from storage
        storage.deleteTask(1);

        // Load and check if task 1 was removed
        TaskList loadedTaskList = storage.loadTasks();
        assertEquals(1, loadedTaskList.getTasks().size());
        assertEquals(
            "[D][ ] submit report (by: 2 Nov 2023, 6:00pm)",
            loadedTaskList.getTasks().get(1).toString()
        );
    }
}
