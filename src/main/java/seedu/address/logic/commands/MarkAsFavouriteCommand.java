package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;





/**
 * Marks a person in the address book as a favourite.
 * <p>
 * The person to be marked is identified using the index number displayed in the current filtered person list.
 * If the specified person is already marked as a favourite, the command will throw a {@code CommandException}.
 */
public class MarkAsFavouriteCommand extends Command {

    /** Keyword used to invoke this command. */
    public static final String COMMAND_WORD = "mark";

    /**
     * Usage message shown to the user.
     */
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the person as favourite based on their index.\n"
            + "Parameters: Index Number (must be a valid index)\n"
            + "Example: " + COMMAND_WORD + " 1";

    /** Success message when a person is successfully marked as favourite. */
    public static final String MESSAGE_MARK_PERSON_SUCCESS = "Add to favourites, Person: %1$s ";

    /** Error message when the person is already marked as favourite. */
    public static final String MESSAGE_MARK_PERSON_DUPLICATE = "Person already in favourites: %1$s";

    /** Index of the person in the filtered list to be marked. */
    private final Index index;

    /**
     * Creates a {@code MarkAsFavouriteCommand} to mark the person at the specified {@code index}.
     *
     * @param index Index of the person in the filtered person list.
     */
    public MarkAsFavouriteCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }

    /**
     * Executes the mark command.
     * The method retrieves the person from the filtered list using the provided index,
     * checks if the person is already marked as favourite, and if not, creates a new
     * {@code Person} object with the favourite flag set to {@code true}. The updated person
     * is then replaced in the model.
     *
     * @param model {@code Model} which the command should operate on.
     * @return {@code CommandResult} indicating success with the person's name.
     * @throws CommandException if the index is invalid or the person is already marked as favourite.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());

        if (personToEdit.getIsFavourite()) {
            throw new CommandException(String.format(MESSAGE_MARK_PERSON_DUPLICATE,
                    personToEdit.getName()));
        }

        Person markedPerson = new Person(
                personToEdit.getName(),
                personToEdit.getPhone(),
                personToEdit.getEmail(),
                personToEdit.getAddress(),
                personToEdit.getDetails(),
                personToEdit.getTags(),
                true,
                personToEdit.getMeeting().orElse(null));

        model.setPerson(personToEdit, markedPerson);

        return new CommandResult(String.format(MESSAGE_MARK_PERSON_SUCCESS,
                markedPerson.getName()));
    }

    @Override
    public boolean modifiesAddressBook() {
        return true;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof MarkAsFavouriteCommand otherCommand)) {
            return false;
        }

        return index.equals(otherCommand.index);
    }
}
