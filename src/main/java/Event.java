
class Event extends Task {

    String description;

    public Event(String description) {
        super(description, "D");
        this.description = description;
    }

    @Override
    public String handleDescription(String input) {
        String[] split = description.split(" ");
        StringBuilder output = new StringBuilder();
        Integer fromIndex = -1;
        Integer toIndex = -1;
        for (int i = 0; i < split.length; i++) {
            if (split[i].equals("/from")) {
                fromIndex = i;
            } if (split[i].equals("/to")) {
                toIndex = i;
            }
        }
        for (int i = 1; i < fromIndex; i++) {
            output.append(split[i] + " ");
        }
        output.append("(from: ");
        for (int i = fromIndex + 1; i < toIndex; i++) {
            output.append(split[i] + " ");
        }
        output.append("to: ");
        for (int i = toIndex + 1; i < split.length; i++) {
            output.append(split[i] + " ");
        }
        return output.toString().trim() + ")";
    }

    public String toString() {
        return "[E]" + super.getStatusIcon() + " " + handleDescription(description);
    }

}
