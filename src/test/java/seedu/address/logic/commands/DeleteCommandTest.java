package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Phone;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validPhoneUnfilteredList_success() {
        DeleteCommand deleteCommand = new DeleteCommand(ALICE.getPhone());

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(ALICE));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(ALICE);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPhoneUnfilteredList_throwsCommandException() {
        Phone invalidPhone = new Phone("99999999");
        DeleteCommand deleteCommand = new DeleteCommand(invalidPhone);

        assertCommandFailure(deleteCommand, model, String.format(DeleteCommand.MESSAGE_PHONE_NOT_FOUND, invalidPhone));
    }

    @Test
    public void equals() {
        DeleteCommand deleteAliceCommand = new DeleteCommand(ALICE.getPhone());
        DeleteCommand deleteBensonCommand = new DeleteCommand(BENSON.getPhone());

        // same object -> returns true
        assertTrue(deleteAliceCommand.equals(deleteAliceCommand));

        // same values -> returns true
        DeleteCommand deleteAliceCommandCopy = new DeleteCommand(ALICE.getPhone());
        assertTrue(deleteAliceCommand.equals(deleteAliceCommandCopy));

        // different types -> returns false
        assertFalse(deleteAliceCommand.equals(1));

        // null -> returns false
        assertFalse(deleteAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteAliceCommand.equals(deleteBensonCommand));
    }


    @Test
    public void execute_validPhoneInList_iteratesAndDeletes() {
        DeleteCommand deleteCommand = new DeleteCommand(ALICE.getPhone());

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(ALICE));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(ALICE);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
        assertFalse(expectedModel.hasPerson(ALICE));
    }

    @Test
    public void toStringMethod() {
        DeleteCommand deleteCommand = new DeleteCommand(ALICE.getPhone());
        String expected = DeleteCommand.class.getCanonicalName() + "{Phone Number=" + ALICE.getPhone() + "}";
        assertEquals(expected, deleteCommand.toString());
    }
}
