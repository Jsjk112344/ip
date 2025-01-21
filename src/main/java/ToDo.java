class ToDo extends Task {
    public ToDo(String description) {
        super(description, "T");
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
