package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class PersonTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Person person = new PersonBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> person.getTags().remove(0));
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ALICE.isSamePerson(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSamePerson(null));

        // same phone, all other attributes different -> returns true
        Person editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // different phone, all other attributes same -> returns false
        editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.isSamePerson(editedAlice));

        // phone differs in case, all other attributes same -> returns true
        Person editedBob = new PersonBuilder(BOB).withPhone(VALID_PHONE_BOB.toLowerCase()).build();
        assertTrue(BOB.isSamePerson(editedBob));

        // phone has trailing spaces, all other attributes same -> returns true
        String phoneWithTrailingSpaces = VALID_PHONE_BOB + " ";
        editedBob = new PersonBuilder(BOB).withPhone(phoneWithTrailingSpaces).build();
        assertTrue(BOB.isSamePerson(editedBob));

    }

    @Test
    public void equals() {
        // same values -> returns true
        Person aliceCopy = new PersonBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different person -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Person editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new PersonBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void toStringMethod() {
        String expected = Person.class.getCanonicalName() + "{name=" + ALICE.getName() + ", phone=" + ALICE.getPhone()
                + ", email=" + ALICE.getEmail() + ", address=" + ALICE.getAddress() + ", details=" + ALICE.getDetails()
                + ", tags=" + ALICE.getTags() + ", isFavourite=" + ALICE.getIsFavourite()
                + ", meeting=" + ALICE.getMeeting().orElse(null) + "}";
        assertEquals(expected, ALICE.toString());
    }

    @Test
    public void equals_sameDetails_returnsTrue() {
        Person person1 = new PersonBuilder(ALICE).withDetails("Same details").build();
        Person person2 = new PersonBuilder(ALICE).withDetails("Same details").build();
        assertTrue(person1.equals(person2));
    }

    @Test
    public void equals_differentDetails_returnsFalse() {
        Person person1 = new PersonBuilder(ALICE).withDetails("Details 1").build();
        Person person2 = new PersonBuilder(ALICE).withDetails("Details 2").build();
        assertFalse(person1.equals(person2));
    }

    @Test
    public void hashCode_sameDetails_sameHashCode() {
        Person person1 = new PersonBuilder(ALICE).withDetails("Same details").build();
        Person person2 = new PersonBuilder(ALICE).withDetails("Same details").build();
        assertEquals(person1.hashCode(), person2.hashCode());
    }

    @Test
    public void hashCode_differentDetails_differentHashCode() {
        Person person1 = new PersonBuilder(ALICE).withDetails("Details 1").build();
        Person person2 = new PersonBuilder(ALICE).withDetails("Details 2").build();
        assertNotEquals(person1.hashCode(), person2.hashCode());
    }

    @Test
    public void equals_differentMeeting_returnsFalse() {
        Person person1 = new PersonBuilder(ALICE).withMeeting("23/03/2030", "14:30").build();
        Person person2 = new PersonBuilder(ALICE).withoutMeeting().build();
        assertFalse(person1.equals(person2));
    }

    @Test
    public void hasMeeting_withoutMeeting_returnsFalse() {
        Person person = new PersonBuilder(ALICE).withoutMeeting().build();
        assertFalse(person.hasMeeting());
        assertTrue(person.getMeeting().isEmpty());
    }

    @Test
    public void hasMeeting_withMeeting_returnsTrue() {
        Person person = new PersonBuilder(ALICE).withMeeting("25/03/2030", "14:30").build();
        assertTrue(person.hasMeeting());
        assertEquals(LocalDateTime.of(2030, 3, 25, 14, 30), person.getMeeting().orElseThrow().getDateTime());
    }
}
