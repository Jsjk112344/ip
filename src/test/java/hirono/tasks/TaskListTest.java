package hirono.tasks;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


import hirono.tasks.TaskList;
import hirono.HironoException;

public class TaskListTest {
    private TaskList taskList;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    public void setUp() {
        taskList = new TaskList();
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    public void testAddTask() throws Exception {
        String result = taskList.addTask("todo read book", "todo");
        assertEquals("Got it. I've added this task:\n" +
                     "[T][ ] read book\n" +
                     "Now you have 1 tasks in the list.", result);
        result = taskList.addTask("deadline reply mom /by 2023-11-02 1800", "deadline");
             
        assertEquals("Got it. I've added this task:\n" +
                     "[D][ ] reply mom (by: 2 Nov 2023, 6:00pm)\n" +
                     "Now you have 2 tasks in the list.", result);
        result = taskList.addTask("event joshua's birthday party /from 2023-11-02 1800 /to 2023-11-02 2000", "event");
             
        assertEquals("Got it. I've added this task:\n" +
                     "[E][ ] joshua's birthday party (from: 2 Nov 2023, 6:00pm to: 2 Nov 2023, 8:00pm)\n" +
                     "Now you have 3 tasks in the list.", result);

        assertEquals(3, taskList.getTasks().size());
    }


    @Test
    public void testDeleteTask() throws Exception {
        taskList.addTask("todo read book", "todo");
        String result = taskList.deleteTask(1);
        assertEquals("Noted. I've removed this task:\n" +
                     "[T][ ] read book\n" +
                     "Now you have 0 tasks in the list.", result);
        assertTrue(taskList.getTasks().isEmpty());
    }
    
    @Test
    public void testDeleteTaskEdgeCases() throws Exception {

        // Deleting from an empty list
        try {
            taskList.deleteTask(1);
        } catch (HironoException e) {
            assertEquals("The item you are attempting to delete is out of the range of the list.", e.getMessage());
        }

        // Deleting a non-existent task
        taskList.addTask("todo read book", "todo");
        try {
            taskList.deleteTask(2); // Task ID 2 does not exist
        } catch (HironoException e) {
            assertEquals("The item you are attempting to delete is out of the range of the list.", e.getMessage());
        }
    }

    public void testGetEventsOnDate() throws Exception {
        // Add tasks to the task list
        taskList.addTask("event team meeting /from 2023-11-02 1400 /to 2023-11-02 1600", "event");
        taskList.addTask("deadline submit report /by 2023-11-02 1800", "deadline");
        taskList.addTask("todo read book", "todo"); // Not tied to a date
        taskList.addTask("event birthday party /from 2023-12-02 1800 /to 2023-12-02 2100", "event");

        // Test for a date with events and deadlines
        String result = taskList.getEventsOnDate("date 2023-11-02");
        assertEquals("Here are the events on 2023-11-02:\n" +
                     "1. [E][ ] team meeting (from: 2 Nov 2023, 2:00pm to: 2 Nov 2023, 4:00pm)\n" +
                     "2. [D][ ] submit report (by: 2 Nov 2023, 6:00pm)", result);

        // Test for a date with no events or deadlines
        result = taskList.getEventsOnDate("date 2023-10-01");
        assertEquals("No events found on 2023-10-01", result);

        // Test for another date with only an event
        result = taskList.getEventsOnDate("date 2023-12-02");
        assertEquals("Here are the events on 2023-12-02:\n" +
                     "1. [E][ ] birthday party (from: 2 Dec 2023, 6:00pm to: 2 Dec 2023, 9:00pm)", result);
    }
    @Test
    public void testFindTasks() throws Exception {
        // Add tasks to the task list
        taskList.addTask("todo read book", "todo");
        taskList.addTask("deadline submit report /by 2023-11-02 1800", "deadline");
        taskList.addTask("event team meeting /from 2023-11-02 1400 /to 2023-11-02 1600", "event");
        taskList.addTask("todo borrow book", "todo");
        taskList.addTask("event book launch /from 2023-11-05 1800 /to 2023-11-05 2000", "event");

        // Test finding tasks with a single word
        String result = taskList.findTasks("find book");
        assertEquals("Here are the matching tasks:\n" +
                    "1. [T][ ] read book\n" +
                    "2. [T][ ] borrow book\n" +
                    "3. [E][ ] book launch (from: 5 Nov 2023, 6:00pm to: 5 Nov 2023, 8:00pm)", result);

        // Test finding tasks with a phrase
        result = taskList.findTasks("find team meeting");
        assertEquals("Here are the matching tasks:\n" +
                    "1. [E][ ] team meeting (from: 2 Nov 2023, 2:00pm to: 2 Nov 2023, 4:00pm)", result);

        // Test finding tasks with no matches
        result = taskList.findTasks("find nonexistent task");
        assertEquals("No tasks found matching \"nonexistent task\".", result);
    }


    @Test
    public void testListTasksEmpty() {
        // Call listTasks on an empty task list
        taskList.listTasks();

        // Capture the output
        String result = outputStream.toString().trim();

        // Expected output
        String expected = "Here are the tasks in your list:";

        // Assert the output matches the expected result
        assertEquals(expected, result);
    }


}

