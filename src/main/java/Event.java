class Event extends Task {
    public Event(String description) throws HironoException {
        super(description, "E");
        if (!isValidEvent(description)) {
            throw new HironoException("The event command is not in the correct format: event [event name] /from [time] /to [time]");
        }
    }

    private boolean isValidEvent(String description) {
        String eventRegex = "^event\\s+.+\\s+/from\\s+.+\\s+/to\\s+.+$";
        return description.matches(eventRegex);
    }
    @Override
    public String toFileFormat() {
        String[] parts = handleDescription(getDescription()).split("\\(from: | to: ");
        return String.format("E | %d | %s | %s | %s", isDone() ? 1 : 0, parts[0].trim(), parts[1].trim(), parts[2].replace(")", "").trim());
    }


    @Override
    public String handleDescription(String input) {
        String[] parts = input.split("/from|/to");
        return parts[0].replace("event", "").trim() +
               " (from: " + parts[1].trim() + " to: " + parts[2].trim() + ")";
    }
}
