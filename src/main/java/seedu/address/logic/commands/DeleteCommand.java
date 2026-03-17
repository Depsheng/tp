package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person based on their contact number.\n"
            + "Parameters: Phone Number (must be 8 digits)\n"
            + "Example: " + COMMAND_WORD + " 91234567";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";
    public static final String MESSAGE_PHONE_NOT_FOUND = "No person found with phone number: %1$s";

    private final Phone targetPhone;

    public DeleteCommand(Phone targetPhone) {
        this.targetPhone = targetPhone;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> fullList = model.getAddressBook().getPersonList();
        for (Person person : fullList) {
            if (person.getPhone().equals(targetPhone)) {
                model.deletePerson(person);
                return new CommandResult(
                        String.format(MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(person)));
            }
        }

        throw new CommandException(String.format(MESSAGE_PHONE_NOT_FOUND, targetPhone));
    }

    /**
     * Checks if the target phone number exists in the address book.
     */
    public boolean isValidTarget(Model model) {
        requireNonNull(model);

        List<Person> fullList = model.getAddressBook().getPersonList();

        for (Person person : fullList) {
            if (person.getPhone().equals(targetPhone)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Generates an error message when the target phone number is not found in the address book.
     */
    public String getNotFoundMessage() {
        return String.format(MESSAGE_PHONE_NOT_FOUND, targetPhone);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteCommand)) {
            return false;
        }

        DeleteCommand otherDeleteCommand = (DeleteCommand) other;
        return targetPhone.equals(otherDeleteCommand.targetPhone);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("Phone Number", targetPhone)
                .toString();
    }
}
