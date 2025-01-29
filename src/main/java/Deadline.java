class Deadline extends Task {
    public Deadline(String description) throws HironoException {
        super(description, "D");
        if (!isValidDeadline(description)) {
            throw new HironoException("The deadline command is not in the correct format: deadline [deadline name] /by [time]");
        }
    }

    private boolean isValidDeadline(String description) {
        String deadlineRegex = "^deadline\\s+.+\\s+/by\\s+.+$";
        return description.matches(deadlineRegex);
    }
    
    @Override
    public String toFileFormat() {
        String[] parts = handleDescription(getDescription()).split("\\(by: ");
        return String.format("D | %d | %s | %s", isDone() ? 1 : 0, parts[0].trim(), parts[1].replace(")", "").trim());
    }


    @Override
    public String handleDescription(String input) {
        String[] parts = input.split("/by");
        String taskDescription = parts[0].replace("deadline", "").trim(); // Remove "deadline"
        String byTime = parts[1].trim(); // Get the deadline time
        return taskDescription + " (by: " + byTime + ")";
    }

    @Override
    public String toString() {
        return "[D]" + super.getStatusIcon() + " " + handleDescription(getDescription());
    }
}
