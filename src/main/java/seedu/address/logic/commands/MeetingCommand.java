package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.DateTimeUtil;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Meeting;
import seedu.address.model.person.Person;

/**
 * Adds a meeting date and time for a client identified by index.
 */
public class MeetingCommand extends Command {

    public static final String COMMAND_WORD = "meeting";
    public static final String MESSAGE_MEETING_ADDED =
            "Added meeting for %1$s on %2$s";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a meeting date and time for the client identified by the displayed index.\n"
            + "Parameters: INDEX (must be a positive integer) DATE_TIME\n"
            + DateTimeUtil.MESSAGE_INVALID_DATE_TIME_FORMAT;

    private final Index index;
    private final Meeting meeting;

    /**
     * Creates a {@code MeetingCommand} for the person at the specified {@code index}.
     *
     * @param index Index of the person in the currently filtered list.
     * @param meeting Meeting to assign.
     */
    public MeetingCommand(Index index, Meeting meeting) {
        requireNonNull(index);
        requireNonNull(meeting);
        this.index = index;
        this.meeting = meeting;
    }

    /**
     * Returns the meeting formatted for user-facing messages.
     */
    public String getFormattedMeeting() {
        return meeting.getFormattedDateTime();
    }

    /**
     * Updates the person identified by the command index with the configured meeting.
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
                meeting);

        model.setPerson(personToEdit, updatedPerson);
        return new CommandResult(String.format(MESSAGE_MEETING_ADDED,
                updatedPerson.getName(), getFormattedMeeting()));
    }

    /**
     * Returns true if both commands target the same person and meeting.
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
                && meeting.equals(otherCommand.meeting);
    }
}
