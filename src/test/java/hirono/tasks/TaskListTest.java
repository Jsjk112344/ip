package hirono.tasks;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class TaskListTest {
    @Test
    public void testAddTask() throws Exception {
        TaskList taskList = new TaskList();
        String result = taskList.addTask("todo read book", "todo");
        assertEquals("Got it. I've added this task:\n" +
                     "[T][ ] read book\n" +
                     "Now you have 1 tasks in the list.", result);
        result = taskList.addTask("deadline reply mom /by 2023-11-02 1800", "deadline");
             
        assertEquals("Got it. I've added this task:\n" +
                     "[D][ ] reply mom (by: 2 Nov 2023, 6:00pm)\n" +
                     "Now you have 2 tasks in the list.", result);
        assertEquals(2, taskList.getTasks().size());
    }


    @Test
    public void testDeleteTask() throws Exception {
        TaskList taskList = new TaskList();
        taskList.addTask("todo read book", "todo");
        String result = taskList.deleteTask(1);
        assertEquals("Noted. I've removed this task:\n" +
                     "[T][ ] read book\n" +
                     "Now you have 0 tasks in the list.", result);
        assertTrue(taskList.getTasks().isEmpty());
    }
}
