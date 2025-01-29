import java.time.LocalDate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

class Event extends Task {
    private LocalDateTime fromTime;
    private LocalDateTime toTime;

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

    private String[] parseDescription(String description) throws HironoException {
        if (!isValidEvent(description)) {
            throw new HironoException("The event command is not in the correct format: event [event name] /from [yyyy-MM-dd HHmm] /to [yyyy-MM-dd HHmm]");
        }
        String[] parts = description.split("/from|/to", 3);
        if (parts.length < 3) {
            throw new HironoException("The event command must include both /from and /to clauses.");
        }
        return parts;
    }
    public boolean isOnDate(LocalDate date) {
        LocalDate fromDate = fromTime.toLocalDate();
        LocalDate toDate = toTime.toLocalDate();
        return (date.equals(fromDate) || date.equals(toDate)) || 
            (date.isAfter(fromDate) && date.isBefore(toDate));
    }


    private boolean isValidEvent(String description) {
        String eventRegex = "^event\\s+.+\\s+/from\\s+.+\\s+/to\\s+.+$";
        return description.matches(eventRegex);
    }

    private LocalDateTime parseDateTime(String dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
        return LocalDateTime.parse(dateTime.trim(), formatter);
    }

    @Override
    public String toFileFormat() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
        return String.format("E | %d | %s | %s | %s", 
                             isDone() ? 1 : 0, 
                             getDescriptionWithoutEvent(), 
                             fromTime.format(formatter), 
                             toTime.format(formatter));
    }

    private String getDescriptionWithoutEvent() {
        return getDescription().split("/from")[0].replace("event", "").trim();
    }

    @Override
    public String handleDescription(String input) {
        DateTimeFormatter displayFormatter = DateTimeFormatter.ofPattern("d MMM yyyy, h:mma");
        return getDescriptionWithoutEvent() + 
               " (from: " + fromTime.format(displayFormatter) + 
               " to: " + toTime.format(displayFormatter) + ")";
    }

    @Override
    public String toString() {
        return "[E]" + super.getStatusIcon() + " " + handleDescription(getDescription());
    }
}
