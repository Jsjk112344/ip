package hirono.task;

import hirono.exception.HironoException;

/**
 * Describes a basic task to do
 */
public class ToDo extends Task {
    /**
     * Main ToDO constructor
     */
    public ToDo(String description) throws HironoException {
        super(description, "T");
        if (!isValidToDo(description)) {
            throw new HironoException("The todo command is not in the correct format:"
                + "todo <description>");
        }
    }


    private boolean isValidToDo(String description) {
        String todoRegex = "^todo\\s+.+$";
        return description.matches(todoRegex);
    }
    /**
     * @return String
     */
    @Override
    public String toFileFormat() {
        String processedDescription = handleDescription(getDescription());
        return String.format("%s | %d | %s",
                            getTypeIcon().substring(1, 2),
                            isDone() ? 1 : 0,
                            processedDescription);
    }

    @Override
    public String handleDescription(String input) {
        // Remove the first word ("todo") from the input
        String[] splitted = input.split(" ", 2);
        return splitted.length > 1 ? splitted[1] : ""; // Return the remaining description
    }

    @Override
    public String toString() {
        return "[T]" + super.getStatusIcon() + " " + handleDescription(getDescription());
    }
}
