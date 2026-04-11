package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class GeneralContainsKeywordPredicateTest {

    @Test
    public void equals() {
        GeneralContainsKeywordPredicate firstPredicate =
                new GeneralContainsKeywordPredicate(List.of("Alice"));
        GeneralContainsKeywordPredicate secondPredicate =
                new GeneralContainsKeywordPredicate(List.of("Bob"));

        assertTrue(firstPredicate.equals(firstPredicate));
        assertTrue(firstPredicate.equals(new GeneralContainsKeywordPredicate(List.of("Alice"))));
        assertFalse(firstPredicate.equals(1));
        assertFalse(firstPredicate.equals(null));
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_generalContainsKeywords_returnsTrue() {
        assertTrue(new GeneralContainsKeywordPredicate(List.of("Alice"))
                .test(new PersonBuilder().withName("Alice Pauline").build()));

        assertTrue(new GeneralContainsKeywordPredicate(List.of("9123"))
                .test(new PersonBuilder().withPhone("91234567").build()));

        assertTrue(new GeneralContainsKeywordPredicate(List.of("example"))
                .test(new PersonBuilder().withEmail("alice@example.com").build()));

        assertTrue(new GeneralContainsKeywordPredicate(List.of("Main"))
                .test(new PersonBuilder().withAddress("123 Main Street").build()));

        assertTrue(new GeneralContainsKeywordPredicate(List.of("friend"))
                .test(new PersonBuilder().withDetails("Good friend").build()));

        assertTrue(new GeneralContainsKeywordPredicate(Arrays.asList("Alice", "Bob", "Charlie"))
                .test(new PersonBuilder().withName("Bob").build()));
    }

    @Test
    public void test_generalDoesNotContainKeywords_returnsFalse() {
        assertFalse(new GeneralContainsKeywordPredicate(List.of("Charlie"))
                .test(new PersonBuilder().withName("Alice").build()));
    }
}
