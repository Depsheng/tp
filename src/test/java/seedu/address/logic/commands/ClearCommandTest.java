package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ClearCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @AfterEach
    public void clearPendingConfirmation() throws CommandException {
        if (ClearCommand.hasPendingConfirmation()) {
            ClearCommand.confirmationCommand(model, "n");
        }
    }

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.setAddressBook(new AddressBook());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void modifiesAddressBook_returnsTrue() {
        assertTrue(new ClearCommand().modifiesAddressBook());
    }

    @Test
    public void confirmationCommand_noPendingClear_throwsIllegalStateException() {
        assertThrows(IllegalStateException.class,
                "No pending clear command to confirm.", () ->
                        ClearCommand.confirmationCommand(model, "y"));
    }

    @Test
    public void confirmationCommand_invalidInput_returnsConfirmationRequired() throws CommandException {
        ClearCommand clearCommand = new ClearCommand();
        clearCommand.requestConfirmation(model, "clear");

        CommandResult result = ClearCommand.confirmationCommand(model, "maybe");

        assertEquals(ClearCommand.MESSAGE_CONFIRMATION_REQUIRED, result.getFeedbackToUser());
        assertTrue(ClearCommand.hasPendingConfirmation());
    }

}
