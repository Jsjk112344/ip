package hirono.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import hirono.exception.HironoException;
import hirono.task.TaskList;

public class TaskListTest {
    private TaskList taskList;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    public void setUp() {
        taskList = new TaskList();
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testAddTask() throws Exception {
        String result = taskList.addTask("todo read book", "todo");
        assertEquals("Got it. I've added this task:\n"
            + "[T][ ] read book\n"
            + "Now you have 1 tasks in the list.", result);
        result = taskList.addTask("deadline reply mom /by 2023-11-02 1800", "deadline");

        assertEquals("Got it. I've added this task:\n"
            + "[D][ ] reply mom (by: 2 Nov 2023, 6:00pm)\n"
            + "Now you have 2 tasks in the list.", result);
        result = taskList.addTask("event joshua's birthday party /from 2023-11-02 1800 /to 2023-11-02 2000", "event");

        assertEquals("Got it. I've added this task:\n"
            + "[E][ ] joshua's birthday party (from: 2 Nov 2023, 6:00pm to: 2 Nov 2023, 8:00pm)\n"
            + "Now you have 3 tasks in the list.", result);

        assertEquals(3, taskList.getTasks().size());
    }


    @Test
    public void testDeleteTask() throws Exception {
        taskList.addTask("todo read book", "todo");
        String result = taskList.deleteTask(1);
        assertEquals("Noted. I've removed this task:\n"
            + "[T][ ] read book\n"
            + "Now you have 0 tasks in the list.", result);
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
        assertEquals("Here are the events on 2023-11-02:\n"
            + "1. [E][ ] team meeting (from: 2 Nov 2023, 2:00pm to: 2 Nov 2023, 4:00pm)\n"
            + "2. [D][ ] submit report (by: 2 Nov 2023, 6:00pm)", result);

        // Test for a date with no events or deadlines
        result = taskList.getEventsOnDate("date 2023-10-01");
        assertEquals("No events found on 2023-10-01", result);

        // Test for another date with only an event
        result = taskList.getEventsOnDate("date 2023-12-02");
        assertEquals("Here are the events on 2023-12-02:\n"
            + "1. [E][ ] birthday party (from: 2 Dec 2023, 6:00pm to: 2 Dec 2023, 9:00pm)", result);
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
        assertEquals("Here are the matching tasks:\n"
            + "1. [T][ ] read book\n"
            + "2. [T][ ] borrow book\n"
            + "3. [E][ ] book launch (from: 5 Nov 2023, 6:00pm to: 5 Nov 2023, 8:00pm)", result);

        // Test finding tasks with a phrase
        result = taskList.findTasks("find team meeting");
        assertEquals("Here are the matching tasks:\n"
            + "1. [E][ ] team meeting (from: 2 Nov 2023, 2:00pm to: 2 Nov 2023, 4:00pm)", result);

        // Test finding tasks with no matches
        result = taskList.findTasks("find nonexistent task");
        assertEquals("No tasks found matching \"nonexistent task\".", result);
    }


    @Test
    public void testListTasksEmpty() {
        // Call listTasks on an empty task list

        // Capture the output
        String result = taskList.listTasks();

        // Expected output
        String expected = "Here are the tasks in your list:\n";

        // Assert the output matches the expected result
        assertEquals(expected, result);
    }

    @Test
    public void testEditTask() throws Exception {
        // Add some initial tasks
        taskList.addTask("todo read book", "todo");
        taskList.addTask("deadline submit report /by 2023-11-02 1800", "deadline");
        taskList.addTask("event team meeting /from 2023-11-02 1400 /to 2023-11-02 1600", "event");

        // Test editing a todo task
        String result = taskList.editTask("edit 1: todo finish homework");
        assertEquals("Got it. I've edited the task to:\n"
            + "1. [T][ ] finish homework", result);

        // Test editing a deadline task
        result = taskList.editTask("edit 2: deadline complete assignment /by 2023-12-01 2359");
        assertEquals("Got it. I've edited the task to:\n"
            + "2. [D][ ] complete assignment (by: 1 Dec 2023, 11:59pm)", result);

        // Test editing an event task
        result = taskList.editTask("edit 3: event project meeting /from 2023-12-05 1000 /to 2023-12-05 1200");
        assertEquals("Got it. I've edited the task to:\n"
            + "3. [E][ ] project meeting (from: 5 Dec 2023, 10:00am to: 5 Dec 2023, 12:00pm)", result);

        // Test preserving done status
        taskList.markTask(1); // Mark the first task as done
        result = taskList.editTask("edit 1: todo study math");
        assertEquals("Got it. I've edited the task to:\n"
            + "1. [T][X] study math", result);
    }

    @Test
    public void testEditTaskEdgeCases() throws Exception {
        // Add a test task
        taskList.addTask("todo read book", "todo");

        // Test invalid task ID
        try {
            taskList.editTask("edit 999: todo new task");
            assertTrue(false, "Expected HironoException for invalid task ID");
        } catch (HironoException e) {
            assertEquals("Task ID not found!", e.getMessage());
        }

        // Test invalid edit format (missing colon)
        try {
            taskList.editTask("edit 1 todo new task");
            assertTrue(false, "Expected HironoException for invalid format");
        } catch (HironoException e) {
            assertEquals("Invalid edit format. Please use: edit <task ID>: <new task info>", e.getMessage());
        }

        // Test type mismatch (trying to change todo to deadline)
        try {
            taskList.editTask("edit 1: deadline submit report /by 2023-12-01");
            assertTrue(false, "Expected HironoException for type mismatch");
        } catch (HironoException e) {
            assertEquals("Cannot change task type. Original task was: todo", e.getMessage());
        }

        // Test missing task description
        try {
            taskList.editTask("edit 1: todo");
            assertTrue(false, "Expected HironoException for missing description");
        } catch (HironoException e) {
            assertEquals("Invalid task format. Please provide task type and description.", e.getMessage());
        }
    }


}

