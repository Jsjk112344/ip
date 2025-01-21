abstract class Task {
    private boolean isDone;
    private String description;
    private String type; 

    public Task(String description, String type) {
        this.description = description;
        this.isDone = false; 
        this.type = type;
    }

    public void markAsDone() {
        isDone = true;
    }

    public void unmark() {
        isDone = false;
    }

    public String getStatusIcon() {
        return isDone ? "[X]" : "[ ]";
    }

    public String getTypeIcon() {
        return "[" + type + "]";
    }

    public String getDescription() {
        return description;
    }

    public abstract String handleDescription(String input);

    @Override
    public String toString() {
        return getTypeIcon() + getStatusIcon() + " " + handleDescription(description);
    }
}
