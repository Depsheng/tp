package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class NameContainsKeywordPredicateTest {

    @Test
    public void equals() {
        NameContainsKeywordPredicate firstPredicate =
                new NameContainsKeywordPredicate(List.of("Alice"));
        NameContainsKeywordPredicate secondPredicate =
                new NameContainsKeywordPredicate(List.of("Bob"));

        assertTrue(firstPredicate.equals(firstPredicate));
        assertTrue(firstPredicate.equals(new NameContainsKeywordPredicate(List.of("Alice"))));
        assertFalse(firstPredicate.equals(1));
        assertFalse(firstPredicate.equals(null));
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        assertTrue(new NameContainsKeywordPredicate(List.of("Alice"))
                .test(new PersonBuilder().withName("Alice Pauline").build()));

        assertTrue(new NameContainsKeywordPredicate(Arrays.asList("Alice", "Bob"))
                .test(new PersonBuilder().withName("Bob").build()));

        assertTrue(new NameContainsKeywordPredicate(List.of("lic"))
                .test(new PersonBuilder().withName("Alice").build()));

        assertTrue(new NameContainsKeywordPredicate(List.of("alice"))
                .test(new PersonBuilder().withName("ALICE").build()));

        assertTrue(new NameContainsKeywordPredicate(List.of("007"))
                .test(new PersonBuilder().withName("Agent 007").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        assertFalse(new NameContainsKeywordPredicate(List.of("Charlie"))
                .test(new PersonBuilder().withName("Alice Pauline").build()));

        assertFalse(new NameContainsKeywordPredicate(Arrays.asList("David", "Eve"))
                .test(new PersonBuilder().withName("Alice Pauline").build()));
    }
}
