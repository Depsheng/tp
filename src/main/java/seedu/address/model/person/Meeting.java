package seedu.address.model.person;

import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a meeting scheduled with a person.
 * Guarantees: immutable; is valid as declared in {@link #isValidMeetingDateTime(LocalDateTime)}
 */
public class Meeting {

    public static final String MESSAGE_MEETING_IN_PAST = "Meeting cannot be scheduled in the past";
    public static final String MESSAGE_MEETING_DATETIME_CONSTRAINTS = "Meeting date and time must be"
            + "valid and not in the past";

    private static final DateTimeFormatter DISPLAY_FORMATTER = DateTimeFormatter.ofPattern("dd MMM yyyy h:mm a");

    private final LocalDateTime dateTime;

    /**
     * Constructs a {@code Meeting}.
     *
     * @param dateTime A valid meeting date and time.
     */
    public Meeting(LocalDateTime dateTime) {
        checkArgument(isValidMeetingDateTime(dateTime), MESSAGE_MEETING_DATETIME_CONSTRAINTS);
        this.dateTime = dateTime;
    }

    /**
     * Returns true if a given LocalDateTime is a valid meeting time.
     * Meeting times must not be in the past.
     */
    public static boolean isValidMeetingDateTime(LocalDateTime dateTime) {
        return dateTime != null && !dateTime.isBefore(LocalDateTime.now());
    }

    /**
     * Returns the meeting date and time.
     */
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    /**
     * Returns the meeting date and time formatted for display.
     */
    public String getFormattedDateTime() {
        return dateTime.format(DISPLAY_FORMATTER);
    }

    /**
     * Returns the meeting date and time in a concise format for commands.
     */
    public String getCommandFormat() {
        return dateTime.format(DateTimeFormatter.ofPattern("d/M/yyyy HH:mm"));
    }

    @Override
    public String toString() {
        return getFormattedDateTime();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Meeting)) {
            return false;
        }

        Meeting otherMeeting = (Meeting) other;
        return dateTime.equals(otherMeeting.dateTime);
    }

    @Override
    public int hashCode() {
        return dateTime.hashCode();
    }
}
