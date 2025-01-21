
class Deadline extends Task {

    String description;

    public Deadline(String description) {
        super(description, "D");
        this.description = description;
    }

    @Override
    public String handleDescription(String input) {
        String[] split = description.split(" ");
        StringBuilder output = new StringBuilder();
        Integer byIndex = -1;
        for (int i = 0; i < split.length; i++) {
            if (split[i].equals("/by")) {
                byIndex = i;
            }
        }
        for (int i = 1; i < byIndex; i++) {
            output.append(split[i] + " ");
        }
        output.append("(by: ");
        for (int i = byIndex + 1; i < split.length; i++) {
            output.append(split[i] + " ");
        }
        
        return output.toString().trim() + ")";
    }

    public String toString() {
        return "[D]" + super.getStatusIcon() + " " + handleDescription(description);
    }

}
