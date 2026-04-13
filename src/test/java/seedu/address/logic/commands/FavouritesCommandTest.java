package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code FavouritesCommand}.
 */
public class FavouritesCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_noFavourites_throwsCommandException() {
        assertCommandFailure(new FavouritesCommand(), model, FavouritesCommand.MESSAGE_NO_FAVOURITES);
    }

    @Test
    public void execute_withFavourites_showsOnlyFavourites() {
        model.setPerson(ALICE, new PersonBuilder(ALICE).withIsFavourite(true).build());
        model.setPerson(BENSON, new PersonBuilder(BENSON).withIsFavourite(true).build());

        expectedModel.setPerson(ALICE, new PersonBuilder(ALICE).withIsFavourite(true).build());
        expectedModel.setPerson(BENSON, new PersonBuilder(BENSON).withIsFavourite(true).build());
        expectedModel.updateFilteredPersonList(person -> person.getIsFavourite());

        assertCommandSuccess(new FavouritesCommand(), model, FavouritesCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
