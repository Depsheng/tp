package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Removes the favourite status of a person in the address book.
 * <p>
 * The person to be unmarked is identified using the index number displayed in the current filtered person list.
 * This command updates the person by setting their favourite status to {@code false}.
 */
public class UnmarkAsFavouriteCommand extends Command {

    /** Keyword used to invoke this command. */
    public static final String COMMAND_WORD = "unmark";

    /**
     * Usage message shown to the user.
     */
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes the specified person from favourites.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    /** Success message when a person is successfully unmarked as favourite. */
    public static final String MESSAGE_UNMARK_PERSON_SUCCESS =
            "Removed from favourites, Person: %1$s ";

    /** Error message when the person is not marked as favourite. */
    public static final String MESSAGE_UNMARK_PERSON_DUPLICATE =
            "Person is not marked as favourite: %1$s";

    /** Index of the person in the filtered list to be unmarked. */
    private final Index index;

    /**
     * Creates a {@code UnmarkAsFavouriteCommand} to unmark the person at the specified {@code index}.
     *
     * @param index Index of the person in the filtered person list.
     */
    public UnmarkAsFavouriteCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }

    /**
     * Executes the unmark-as-favourite command.
     * <p>
     * Retrieves the person from the filtered list using the given index, verifies that the person
     * is currently marked as favourite, and replaces the person in the model with a new instance
     * where the favourite status is set to {@code false}.
     *
     * @param model {@code Model} which the command should operate on.
     * @return {@code CommandResult} indicating the outcome of the operation.
     * @throws CommandException if the index is invalid or the person is not marked as favourite.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());

        if (!personToEdit.getIsFavourite()) {
            throw new CommandException(String.format(MESSAGE_UNMARK_PERSON_DUPLICATE,
                    personToEdit.getName()));
        }

        Person markedPerson = new Person(
                personToEdit.getName(),
                personToEdit.getPhone(),
                personToEdit.getEmail(),
                personToEdit.getAddress(),
                personToEdit.getDetails(),
                personToEdit.getTags(),
                false);

        model.setPerson(personToEdit, markedPerson);

        return new CommandResult(String.format(MESSAGE_UNMARK_PERSON_SUCCESS,
                markedPerson.getName()));
    }
}
