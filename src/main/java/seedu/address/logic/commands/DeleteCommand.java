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
            + "Parameters: Ensure Phone Number matches exactly \n"
            + "Example: " + COMMAND_WORD + " 91234567";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";
    public static final String MESSAGE_PHONE_NOT_FOUND = "No person found with this specific phone number: %1$s";
    public static final String MESSAGE_CONFIRMATION_PROMPT = "Are you sure you want to delete this contact? %1$s (y/n)";
    public static final String MESSAGE_CONFIRMATION_REQUIRED =
            "All commands are locked until confirmation is done. Please enter 'y' to confirm or 'n' to cancel.";
    public static final String MESSAGE_DELETION_CANCELLED = "Deletion cancelled.";

    private static DeleteCommand pendingDeleteCommand;
    private static String pendingDeleteCommandText;

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

    @Override
    public boolean modifiesAddressBook() {
        return true;
    }

    /**
     * Stores this command as the pending delete and returns the confirmation prompt.
     */
    public CommandResult requestConfirmation(Model model, String commandText) throws CommandException {
        requireNonNull(model);
        requireNonNull(commandText);

        Person personToDelete = getTargetPerson(model);

        pendingDeleteCommand = this;
        pendingDeleteCommandText = commandText.trim();
        return new CommandResult(String.format(MESSAGE_CONFIRMATION_PROMPT, personToDelete.getName()));
    }

    /**
     * Returns true if a delete confirmation is pending.
     */
    public static boolean hasPendingConfirmation() {
        return pendingDeleteCommand != null;
    }

    /**
     * Handles the confirmation input for the pending delete command.
     */
    public static CommandResult confirmationCommand(Model model, String commandText) throws CommandException {
        requireNonNull(model);
        requireNonNull(commandText);

        if (pendingDeleteCommand == null) {
            throw new IllegalStateException("No pending delete command to confirm.");
        }

        if (commandText.equalsIgnoreCase("y")) {
            DeleteCommand commandToExecute = pendingDeleteCommand;
            pendingDeleteCommand = null;
            pendingDeleteCommandText = null;
            return commandToExecute.execute(model);
        }

        if (commandText.equalsIgnoreCase("n")) {
            pendingDeleteCommand = null;
            pendingDeleteCommandText = null;
            return new CommandResult(MESSAGE_DELETION_CANCELLED);
        }

        return new CommandResult(MESSAGE_CONFIRMATION_REQUIRED);
    }

    /**
     * Checks if the target phone number exists in the address book.
     */
    public boolean isValidTarget(Model model) {
        requireNonNull(model);

        return getTargetPersonOrNull(model) != null;
    }

    /**
     * Generates an error message when the target phone number is not found in the address book.
     */
    public String getNotFoundMessage() {
        return String.format(MESSAGE_PHONE_NOT_FOUND, targetPhone);
    }

    /**
     * Returns the original delete command text that is currently awaiting confirmation.
     */
    public static String getPendingCommandText() {
        return pendingDeleteCommandText;
    }

    private Person getTargetPerson(Model model) throws CommandException {
        Person person = getTargetPersonOrNull(model);
        if (person == null) {
            throw new CommandException(getNotFoundMessage());
        }
        return person;
    }

    private Person getTargetPersonOrNull(Model model) {
        List<Person> fullList = model.getAddressBook().getPersonList();

        for (Person person : fullList) {
            if (person.getPhone().equals(targetPhone)) {
                return person;
            }
        }

        return null;
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
