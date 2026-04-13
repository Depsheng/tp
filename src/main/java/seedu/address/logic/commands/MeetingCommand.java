package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

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
    public static final String CLEAR_KEYWORD = "clear";
    public static final String MESSAGE_MEETING_ADDED =
            "Added meeting for %1$s on %2$s";
    public static final String MESSAGE_MEETING_CLEARED =
            "Cleared meeting for %1$s";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds or clears a meeting for the client identified by the displayed index.\n"
            + "Parameters: INDEX (must be a positive integer) DATE_TIME / " + CLEAR_KEYWORD + "\n"
            + "Example: " + COMMAND_WORD + " 1 25/03/2030 14:30\n"
            + "Example: " + COMMAND_WORD + " 1 " + CLEAR_KEYWORD + "\n"
            + DateTimeUtil.MESSAGE_INVALID_DATE_TIME_FORMAT;
    public static final String MESSAGE_NO_MEETINGS = "There are currently no scheduled meetings for %1$s";

    private final Index index;
    private final Meeting meeting;
    private final boolean isClearMeeting;

    /**
     * Creates a {@code MeetingCommand} for the person at the specified {@code index}.
     *
     * @param index Index of the person in the currently filtered list.
     * @param meeting Meeting to assign.
     */
    public MeetingCommand(Index index, Meeting meeting) {
        this(index, meeting, false);
    }

    private MeetingCommand(Index index, Meeting meeting, boolean isClearMeeting) {
        requireNonNull(index);
        if (!isClearMeeting) {
            requireNonNull(meeting);
        }
        this.index = index;
        this.meeting = meeting;
        this.isClearMeeting = isClearMeeting;
    }

    /**
     * Creates a {@code MeetingCommand} that clears the meeting of the specified person.
     */
    public static MeetingCommand clear(Index index) {
        requireNonNull(index);
        return new MeetingCommand(index, null, true);
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
        if (isClearMeeting && !personToEdit.hasMeeting()) {
            return new CommandResult(String.format(MESSAGE_NO_MEETINGS, personToEdit.getName()));
        }

        Person updatedPerson = new Person(
                personToEdit.getName(),
                personToEdit.getPhone(),
                personToEdit.getEmail(),
                personToEdit.getAddress(),
                personToEdit.getDetails(),
                personToEdit.getTags(),
                personToEdit.getIsFavourite(),
                isClearMeeting ? null : meeting);

        model.setPerson(personToEdit, updatedPerson);
        if (isClearMeeting) {
            return new CommandResult(String.format(MESSAGE_MEETING_CLEARED, updatedPerson.getName()));
        }
        return new CommandResult(String.format(MESSAGE_MEETING_ADDED,
                updatedPerson.getName(), getFormattedMeeting()));
    }

    @Override
    public boolean modifiesAddressBook() {
        return true;
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
                && isClearMeeting == otherCommand.isClearMeeting
                && Objects.equals(meeting, otherCommand.meeting);
    }
}
