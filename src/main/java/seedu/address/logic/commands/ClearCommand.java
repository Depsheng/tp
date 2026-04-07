package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Clears all contacts in the address book.\n"
            + "Parameters: none\n"
            + "Example: " + COMMAND_WORD;
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";
    public static final String MESSAGE_CONFIRMATION_PROMPT = "Are you sure you want to clear your contact book? (y/n)";
    public static final String MESSAGE_CONFIRMATION_REQUIRED = "Please enter 'y' to confirm or 'n' to cancel.";
    public static final String MESSAGE_CLEAR_CANCELLED = "Clear cancelled.";

    private static ClearCommand pendingClearCommand;
    private static String pendingClearCommandText;


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setAddressBook(new AddressBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean modifiesAddressBook() {
        return true;
    }

    /**
     * Stores this command as the pending clear and returns the confirmation prompt.
     */
    public CommandResult requestConfirmation(Model model, String commandText) throws CommandException {
        requireNonNull(model);
        requireNonNull(commandText);

        pendingClearCommand = this;
        pendingClearCommandText = commandText.trim();
        return new CommandResult(MESSAGE_CONFIRMATION_PROMPT);
    }

    /**
     * Returns true if a clear confirmation is pending.
     */
    public static boolean hasPendingConfirmation() {
        return pendingClearCommand != null;
    }

    /**
     * Handles the confirmation input for the pending clear command.
     */
    public static CommandResult confirmationCommand(Model model, String commandText) throws CommandException {
        requireNonNull(model);
        requireNonNull(commandText);

        if (pendingClearCommand == null) {
            throw new IllegalStateException("No pending clear command to confirm.");
        }

        if (commandText.equalsIgnoreCase("y")) {
            ClearCommand commandToExecute = pendingClearCommand;
            pendingClearCommand = null;
            pendingClearCommandText = null;
            return commandToExecute.execute(model);
        }

        if (commandText.equalsIgnoreCase("n")) {
            pendingClearCommand = null;
            pendingClearCommandText = null;
            return new CommandResult(MESSAGE_CLEAR_CANCELLED);
        }

        return new CommandResult(MESSAGE_CONFIRMATION_REQUIRED);
    }

    /**
     * Returns the original clear command text that is currently awaiting confirmation.
     */
    public static String getPendingCommandText() {
        return pendingClearCommandText;
    }
}
