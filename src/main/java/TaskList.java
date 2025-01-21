import java.util.HashMap;
import java.util.Map;


class TaskList {
    private HashMap<Integer, Task> tasks = new HashMap<>();
    private int taskCounter = 1;
    private String type;

    public String addTask(String description, String type) {
        StringBuilder output = new StringBuilder();
        if (type.equals("deadline")) {
            tasks.put(taskCounter, new Deadline(description));
        } else if (type.equals("todo")) {
            tasks.put(taskCounter, new ToDo(description));
        } else if (type.equals("event")) {
            tasks.put(taskCounter, new Event(description));
        }
        output.append("Got it. I've added this task:\n");
        output.append(tasks.get(taskCounter).toString() + "\n");
        output.append("Now you have " + taskCounter + " tasks in the list.");
        taskCounter++;
        return output.toString();
    }

    public void markTask(int taskId) {
        Task task = tasks.get(taskId);
        if (task != null) {
            task.markAsDone();
            System.out.println("Nice! I've marked this task as done:");
            System.out.println(taskId + ". " + task.toString());
        } else {
            System.out.println("Task ID not found!");
        }
    }

    public void unmarkTask(int taskId) {
        Task task = tasks.get(taskId);
        if (task != null) {
            task.unmark();
            System.out.println("OK, I've marked this task as not done yet:");
            System.out.println(taskId + ". " + task);
        } else {
            System.out.println("Task ID not found!");
        }
    }

    public void listTasks() {
        System.out.println("Here are the tasks in your list:");
        for (Map.Entry<Integer, Task> entry : tasks.entrySet()) {
            System.out.println(entry.getKey() + ". " + entry.getValue());
        }
    }
}
