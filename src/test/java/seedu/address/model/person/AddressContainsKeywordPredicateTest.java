package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class AddressContainsKeywordPredicateTest {

    @Test
    public void equals() {
        AddressContainsKeywordPredicate firstPredicate =
                new AddressContainsKeywordPredicate(List.of("Main"));
        AddressContainsKeywordPredicate secondPredicate =
                new AddressContainsKeywordPredicate(List.of("Street"));

        assertTrue(firstPredicate.equals(firstPredicate));
        assertTrue(firstPredicate.equals(new AddressContainsKeywordPredicate(List.of("Main"))));
        assertFalse(firstPredicate.equals(1));
        assertFalse(firstPredicate.equals(null));
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_addressContainsKeywords_returnsTrue() {
        assertTrue(new AddressContainsKeywordPredicate(List.of("Main"))
                .test(new PersonBuilder().withAddress("123 Main Street").build()));

        assertTrue(new AddressContainsKeywordPredicate(Arrays.asList("Main", "Street"))
                .test(new PersonBuilder().withAddress("123 Main Street").build()));

        assertTrue(new AddressContainsKeywordPredicate(List.of("ain"))
                .test(new PersonBuilder().withAddress("123 Main Street").build()));

        assertTrue(new AddressContainsKeywordPredicate(List.of("#08-111"))
                .test(new PersonBuilder().withAddress("123, Jurong West Ave 6, #08-111").build()));

        assertTrue(new AddressContainsKeywordPredicate(List.of("MAIN"))
                .test(new PersonBuilder().withAddress("123 main street").build()));
    }

    @Test
    public void test_addressDoesNotContainKeywords_returnsFalse() {
        assertFalse(new AddressContainsKeywordPredicate(List.of("Clementi"))
                .test(new PersonBuilder().withAddress("123 Main Street").build()));
    }
}
