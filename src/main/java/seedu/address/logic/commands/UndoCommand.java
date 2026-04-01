package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Restores the address book to its most recently saved state.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_SUCCESS = "Undid \"%1$s\" command.";
    public static final String MESSAGE_NO_HISTORY = "No commands to undo.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.canUndoAddressBook()) {
            throw new CommandException(MESSAGE_NO_HISTORY);
        }

        String undoneCommand = model.undoAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, undoneCommand));
    }
}
