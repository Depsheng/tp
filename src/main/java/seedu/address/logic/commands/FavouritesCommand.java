package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Lists all favourite persons in the address book to the user.
 */
public class FavouritesCommand extends Command {

    public static final String COMMAND_WORD = "favourites";

    public static final String MESSAGE_SUCCESS = "Listed all favourite persons";
    public static final String MESSAGE_NO_FAVOURITES =
            "No contacts are marked as favourites. Use the mark command to add favourites first.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        boolean hasFavouriteContacts = model.getAddressBook().getPersonList().stream().anyMatch(Person::getIsFavourite);
        if (!hasFavouriteContacts) {
            throw new CommandException(MESSAGE_NO_FAVOURITES);
        }
        model.updateFilteredPersonList(person -> person.getIsFavourite());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
