package hirono.task;

/**
 * Represents a general task with a description, type, and completion status.
 * This is an abstract class to be extended by specific task types such as ToDo, Deadline, or Event.
 */
public abstract class Task {
    private boolean isDone;
    private String description;
    private String type;

    /**
     * Constructs a Task with the given description and type.
     *
     * @param description The description of the task.
     * @param type        The type of the task (e.g., ToDo, Deadline, Event).
     */
    public Task(String description, String type) {
        this.description = description;
        this.isDone = false;
        this.type = type;
    }

    /**
     * Marks the task as done.
     */
    public void markAsDone() {
        isDone = true;
    }

    /**
     * Checks if the task is marked as done.
     *
     * @return True if the task is done, otherwise false.
     */
    public boolean isDone() {
        return this.isDone;
    }

    /**
     * Unmarks the task, indicating it is not done.
     */
    public void unmark() {
        isDone = false;
    }

    /**
     * Returns the status icon for the task.
     * An "X" indicates the task is done, and a blank space indicates it is not done.
     *
     * @return The status icon of the task.
     */
    public String getStatusIcon() {
        return isDone ? "[X]" : "[ ]";
    }

    /**
     * Returns the type icon for the task.
     * The type icon is represented in square brackets (e.g., [T] for ToDo).
     *
     * @return The type icon of the task.
     */
    public String getTypeIcon() {
        return "[" + type + "]";
    }

    /**
     * Returns the description of the task.
     *
     * @return The description of the task.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Processes the task description for display purposes.
     * This method must be implemented by subclasses.
     *
     * @param input The raw input description of the task.
     * @return The formatted description for display.
     */
    public abstract String handleDescription(String input);

    /**
     * Converts the task to its file format representation for saving to storage.
     * The format includes type, completion status, and description.
     *
     * @return The file format string of the task.
     */
    public String toFileFormat() {
        return String.format("%s | %d | %s", getTypeIcon().substring(1, 2), isDone ? 1 : 0, getDescription());
    }

    /**
     * Returns the string representation of the task, including its type, status, and description.
     *
     * @return The string representation of the task.
     */
    @Override
    public String toString() {
        return getTypeIcon() + getStatusIcon() + " " + handleDescription(description);
    }
}
