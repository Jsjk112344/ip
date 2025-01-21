class Event extends Task {
    public Event(String description) throws HironoException {
        super(description, "E");
        if (!isValidEvent(description)) {
            throw new HironoException("The event command is not in the correct format.");
        }
    }

    private boolean isValidEvent(String description) {
        String eventRegex = "^event\\s+.+\\s+/from\\s+.+\\s+\\d+(am|pm)?\\s+/to\\s+\\d+(am|pm)?$";
        return description.matches(eventRegex);
    }

    @Override
    public String handleDescription(String input) {
        String[] parts = input.split("/from|/to");
        return parts[0].replace("event", "").trim() +
               " (from: " + parts[1].trim() + " to: " + parts[2].trim() + ")";
    }
}
