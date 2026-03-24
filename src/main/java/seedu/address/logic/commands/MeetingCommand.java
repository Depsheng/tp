package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Adds a meeting date and time for a client identified by index.
 */
public class MeetingCommand extends Command {

    public static final String COMMAND_WORD = "meeting";
    public static final String MESSAGE_MEETING_ADDED =
            "Added meeting for %1$s on %2$s at %3$s";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a meeting date and time for the client identified by the displayed index.\n"
            + "Parameters: INDEX (must be a positive integer) DD/MM/YYYY HH:MM\n"
            + "Example: " + COMMAND_WORD + " 1 23/03/2026 14:30";

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    private final Index index;
    private final LocalDate date;
    private final LocalTime time;

    /**
     * Creates a {@code MeetingCommand} for the person at the specified {@code index}.
     *
     * @param index Index of the person in the currently filtered list.
     * @param date Meeting date to assign.
     * @param time Meeting time to assign.
     */
    public MeetingCommand(Index index, LocalDate date, LocalTime time) {
        requireNonNull(index);
        requireNonNull(date);
        requireNonNull(time);
        this.index = index;
        this.date = date;
        this.time = time;
    }

    /**
     * Returns the meeting date formatted for user-facing messages.
     */
    public String getFormattedDate() {
        return date.format(DATE_FORMATTER);
    }

    /**
     * Returns the meeting time formatted for user-facing messages.
     */
    public String getFormattedTime() {
        return time.format(TIME_FORMATTER);
    }

    /**
     * Updates the person identified by the command index with the configured meeting date and time.
     *
     * @param model {@code Model} which the command should operate on.
     * @return {@code CommandResult} describing the assigned meeting.
     * @throws CommandException if the provided index does not match any displayed person.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person updatedPerson = new Person(
                personToEdit.getName(),
                personToEdit.getPhone(),
                personToEdit.getEmail(),
                personToEdit.getAddress(),
                personToEdit.getDetails(),
                personToEdit.getTags(),
                personToEdit.getIsFavourite(),
                date,
                time);

        model.setPerson(personToEdit, updatedPerson);
        return new CommandResult(String.format(MESSAGE_MEETING_ADDED,
                updatedPerson.getName(), getFormattedDate(), getFormattedTime()));
    }

    /**
     * Returns true if both commands target the same person and meeting slot.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof MeetingCommand otherCommand)) {
            return false;
        }

        return index.equals(otherCommand.index)
                && date.equals(otherCommand.date)
                && time.equals(otherCommand.time);
    }
}
