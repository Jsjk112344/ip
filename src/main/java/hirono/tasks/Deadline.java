package hirono.tasks;

import hirono.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a deadline task that includes a description and a deadline date and time.
 * Inherits from the {@link Task} class.
 */
public class Deadline extends Task {
    private LocalDateTime deadlineTime;

    /**
     * Constructs a Deadline object with a specified description.
     *
     * @param description The task description in the format: 
     *                    "deadline [task name] /by [yyyy-MM-dd HHmm]".
     * @throws HironoException If the description format is invalid or the date/time cannot be parsed.
     */
    public Deadline(String description) throws HironoException {
        super(description, "D");
        String[] parts = parseDescription(description);

        try {
            this.deadlineTime = parseDateTime(parts[1]);
        } catch (DateTimeParseException e) {
            throw new HironoException("Invalid date and time format. Use yyyy-MM-dd HHmm (e.g., 2023-12-31 2359).");
        }
    }

    /**
     * Parses the task description into the task name and deadline.
     *
     * @param description The task description in the format: 
     *                    "deadline [task name] /by [yyyy-MM-dd HHmm]".
     * @return A string array containing the task name and deadline.
     * @throws HironoException If the description format is invalid or missing the `/by` clause.
     */
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

    /**
     * Checks if the task description is in the valid deadline format.
     *
     * @param description The task description to validate.
     * @return {@code true} if the description matches the deadline format, {@code false} otherwise.
     */
    private boolean isValidDeadline(String description) {
        String deadlineRegex = "^deadline\\s+.+\\s+/by\\s+.+$";
        return description.matches(deadlineRegex);
    }

    /**
     * Parses the deadline date and time from the input string.
     *
     * @param dateTime The deadline in the format "yyyy-MM-dd HHmm".
     * @return A {@link LocalDateTime} object representing the parsed deadline.
     */
    private LocalDateTime parseDateTime(String dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
        return LocalDateTime.parse(dateTime.trim(), formatter);
    }

    /**
     * Checks if the deadline occurs on a specific date.
     *
     * @param date The date to check.
     * @return {@code true} if the deadline is on the given date, {@code false} otherwise.
     */
    public boolean isOnDate(LocalDate date) {
        LocalDate deadlineDate = deadlineTime.toLocalDate();
        return (date.equals(deadlineDate));
    }

    /**
     * Converts the deadline task to a file-compatible format.
     *
     * @return A string representation of the deadline task for saving to a file.
     */
    @Override
    public String toFileFormat() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
        return String.format("D | %d | %s | %s", 
                             isDone() ? 1 : 0, 
                             getDescriptionWithoutDeadline(), 
                             deadlineTime.format(formatter));
    }

    /**
     * Gets the task description without the "/by" clause.
     *
     * @return The description of the task without the "/by" clause.
     */
    private String getDescriptionWithoutDeadline() {
        return getDescription().split("/by")[0].replace("deadline", "").trim();
    }

    /**
     * Formats the task description for display purposes.
     *
     * @param input The original task description.
     * @return A formatted string including the task name and deadline.
     */
    @Override
    public String handleDescription(String input) {
        DateTimeFormatter displayFormatter = DateTimeFormatter.ofPattern("d MMM yyyy, h:mma");
        return getDescriptionWithoutDeadline() + " (by: " + deadlineTime.format(displayFormatter) + ")";
    }

    /**
     * Converts the deadline task to a string representation for display.
     *
     * @return A string representation of the deadline task.
     */
    @Override
    public String toString() {
        return "[D]" + super.getStatusIcon() + " " + handleDescription(getDescription());
    }
}
