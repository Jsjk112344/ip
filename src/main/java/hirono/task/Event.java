package hirono.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import hirono.HironoException;

/**
 * Represents an event task that includes a description, a start time (/from), and an end time (/to).
 * Inherits from the {@link Task} class.
 */
public class Event extends Task {
    private LocalDateTime fromTime;
    private LocalDateTime toTime;

    /**
     * Constructs an Event object with a specified description.
     *
     * @param description The task description in the format:
     *                    "event [event name] /from [yyyy-MM-dd HHmm] /to [yyyy-MM-dd HHmm]".
     * @throws HironoException If the description format is invalid, the date/time cannot be parsed,
     *                         or the /from time is after the /to time.
     */
    public Event(String description) throws HironoException {
        super(description, "E");
        String[] parts = parseDescription(description);

        try {
            this.fromTime = parseDateTime(parts[1]);
            this.toTime = parseDateTime(parts[2]);
            if (fromTime.isAfter(toTime)) {
                throw new HironoException("The /from time cannot be after the /to time.");
            }
        } catch (DateTimeParseException e) {
            throw new HironoException("Invalid date and time format. Use yyyy-MM-dd HHmm (e.g., 2023-12-31 2359).");
        }
    }

    /**
     * Parses the task description into the event name, start time, and end time.
     *
     * @param description The task description in the format:
     *                    "event [event name] /from [yyyy-MM-dd HHmm] /to [yyyy-MM-dd HHmm]".
     * @return A string array containing the event name, start time, and end time.
     * @throws HironoException If the description format is invalid or missing the /from and /to clauses.
     */
    private String[] parseDescription(String description) throws HironoException {
        if (!isValidEvent(description)) {
            throw new HironoException("The event command is not in the correct format:"
                + "event [event name] /from [yyyy-MM-dd HHmm] /to [yyyy-MM-dd HHmm]");
        }
        String[] parts = description.split("/from|/to", 3);
        if (parts.length < 3) {
            throw new HironoException("The event command must include both /from and /to clauses.");
        }
        return parts;
    }

    /**
     * Checks if the event occurs on a specific date.
     *
     * @param date The date to check.
     * @return {@code true} if the event occurs on the given date, {@code false} otherwise.
     */
    public boolean isOnDate(LocalDate date) {
        LocalDate fromDate = fromTime.toLocalDate();
        LocalDate toDate = toTime.toLocalDate();
        return (date.equals(fromDate) || date.equals(toDate))
            || (date.isAfter(fromDate) && date.isBefore(toDate));
    }

    /**
     * Checks if the task description is in the valid event format.
     *
     * @param description The task description to validate.
     * @return {@code true} if the description matches the event format, {@code false} otherwise.
     */
    private boolean isValidEvent(String description) {
        String eventRegex = "^event\\s+.+\\s+/from\\s+.+\\s+/to\\s+.+$";
        return description.matches(eventRegex);
    }

    /**
     * Parses the event's start or end time from the input string.
     *
     * @param dateTime The date and time in the format "yyyy-MM-dd HHmm".
     * @return A {@link LocalDateTime} object representing the parsed date and time.
     */
    private LocalDateTime parseDateTime(String dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
        return LocalDateTime.parse(dateTime.trim(), formatter);
    }

    /**
     * Converts the event task to a file-compatible format.
     *
     * @return A string representation of the event task for saving to a file.
     */
    @Override
    public String toFileFormat() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
        return String.format("E | %d | %s | %s | %s",
                             isDone() ? 1 : 0,
                             getDescriptionWithoutEvent(),
                             fromTime.format(formatter),
                             toTime.format(formatter));
    }

    /**
     * Gets the task description without the "/from" and "/to" clauses.
     *
     * @return The description of the task without the "/from" and "/to" clauses.
     */
    private String getDescriptionWithoutEvent() {
        return getDescription().split("/from")[0].replace("event", "").trim();
    }

    /**
     * Formats the task description for display purposes.
     *
     * @param input The original task description.
     * @return A formatted string including the task name, start time, and end time.
     */
    @Override
    public String handleDescription(String input) {
        DateTimeFormatter displayFormatter = DateTimeFormatter.ofPattern("d MMM yyyy, h:mma");
        return getDescriptionWithoutEvent()
            + " (from: " + fromTime.format(displayFormatter)
            + " to: " + toTime.format(displayFormatter) + ")";
    }

    /**
     * Converts the event task to a string representation for display.
     *
     * @return A string representation of the event task.
     */
    @Override
    public String toString() {
        return "[E]" + super.getStatusIcon() + " " + handleDescription(getDescription());
    }
}
