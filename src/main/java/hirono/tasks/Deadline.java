package hirono.tasks;

import hirono.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Deadline extends Task {
    private LocalDateTime deadlineTime;

    public Deadline(String description) throws HironoException {
        super(description, "D");
        String[] parts = parseDescription(description);

        try {
            this.deadlineTime = parseDateTime(parts[1]);
        } catch (DateTimeParseException e) {
            throw new HironoException("Invalid date and time format. Use yyyy-MM-dd HHmm (e.g., 2023-12-31 2359).");
        }
    }

    private String[] parseDescription(String description) throws HironoException {
        if (!isValidDeadline(description)) {
            throw new HironoException("The deadline command is not in the correct format: deadline [task name] /by [yyyy-MM-dd HHmm]");
        }
        String[] parts = description.split("/by", 2);
        if (parts.length < 2) {
            throw new HironoException("The deadline command must include a /by clause.");
        }
        return parts;
    }

    private boolean isValidDeadline(String description) {
        String deadlineRegex = "^deadline\\s+.+\\s+/by\\s+.+$";
        return description.matches(deadlineRegex);
    }

    private LocalDateTime parseDateTime(String dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
        return LocalDateTime.parse(dateTime.trim(), formatter);
    }

    public boolean isOnDate(LocalDate date) {
        LocalDate deadlineDate = deadlineTime.toLocalDate();
        return (date.equals(deadlineDate));
    }

    @Override
    public String toFileFormat() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
        return String.format("D | %d | %s | %s", 
                             isDone() ? 1 : 0, 
                             getDescriptionWithoutDeadline(), 
                             deadlineTime.format(formatter));
    }

    private String getDescriptionWithoutDeadline() {
        return getDescription().split("/by")[0].replace("deadline", "").trim();
    }

    @Override
    public String handleDescription(String input) {
        DateTimeFormatter displayFormatter = DateTimeFormatter.ofPattern("d MMM yyyy, h:mma");
        return getDescriptionWithoutDeadline() + " (by: " + deadlineTime.format(displayFormatter) + ")";
    }

    @Override
    public String toString() {
        return "[D]" + super.getStatusIcon() + " " + handleDescription(getDescription());
    }
}
