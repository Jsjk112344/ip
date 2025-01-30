package hirono;

import hirono.tasks.TaskList;
import hirono.tasks.ToDo;
import hirono.tasks.Deadline;
import hirono.tasks.Event;
import hirono.HironoException;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class StorageTest {
    private static final String TEST_FILE_PATH = "./data/test-hirono.txt";
    private Storage storage;
    private TaskList taskList;

    @BeforeEach
    public void setUp() {
        storage = new Storage(TEST_FILE_PATH);
        taskList = new TaskList();
    }

    @AfterEach
    public void tearDown() {
        File testFile = new File(TEST_FILE_PATH);
        if (testFile.exists()) {
            testFile.delete();  // Delete test file after each test to avoid conflicts
        }
    }

    
    /** 
     * @throws IOException
     * @throws HironoException
     */
    @Test
    public void testSaveAndLoadTasks() throws IOException, HironoException {
        // Add tasks
        taskList.addTask("todo read book", "todo");
        taskList.addTask("deadline submit report /by 2023-11-02 1800", "deadline");
        taskList.addTask("event team meeting /from 2023-11-02 1400 /to 2023-11-02 1600", "event");

        // Save tasks
        storage.saveTasks(taskList);

        // Load tasks into a new TaskList
        TaskList loadedTaskList = storage.loadTasks();
        assertEquals(3, loadedTaskList.getTasks().size());

        // Check if tasks are correctly restored
        assertEquals("[T][ ] read book", loadedTaskList.getTasks().get(1).toString());
        assertEquals("[D][ ] submit report (by: 2 Nov 2023, 6:00pm)", loadedTaskList.getTasks().get(2).toString());
        assertEquals("[E][ ] team meeting (from: 2 Nov 2023, 2:00pm to: 2 Nov 2023, 4:00pm)", loadedTaskList.getTasks().get(3).toString());
    }

    @Test
    public void testLoadFromEmptyFile() throws IOException, HironoException {
        // Create an empty test file
        File testFile = new File(TEST_FILE_PATH);
        testFile.createNewFile();

        // Attempt to load tasks from an empty file
        TaskList loadedTaskList = storage.loadTasks();
        assertEquals(0, loadedTaskList.getTasks().size());
    }



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
        assertEquals("[D][ ] submit report (by: 2 Nov 2023, 6:00pm)", loadedTaskList.getTasks().get(1).toString());
    }
}
