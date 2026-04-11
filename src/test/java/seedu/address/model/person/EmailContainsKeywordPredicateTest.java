package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class EmailContainsKeywordPredicateTest {

    @Test
    public void equals() {
        EmailContainsKeywordPredicate firstPredicate =
                new EmailContainsKeywordPredicate(List.of("alice"));
        EmailContainsKeywordPredicate secondPredicate =
                new EmailContainsKeywordPredicate(List.of("bob"));

        assertTrue(firstPredicate.equals(firstPredicate));
        assertTrue(firstPredicate.equals(new EmailContainsKeywordPredicate(List.of("alice"))));
        assertFalse(firstPredicate.equals(1));
        assertFalse(firstPredicate.equals(null));
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_emailContainsKeywords_returnsTrue() {
        assertTrue(new EmailContainsKeywordPredicate(List.of("alice"))
                .test(new PersonBuilder().withEmail("alice@example.com").build()));

        assertTrue(new EmailContainsKeywordPredicate(Arrays.asList("alice", "example"))
                .test(new PersonBuilder().withEmail("alice@example.com").build()));

        assertTrue(new EmailContainsKeywordPredicate(List.of("exam"))
                .test(new PersonBuilder().withEmail("test@example.com").build()));

        assertTrue(new EmailContainsKeywordPredicate(List.of("@example"))
                .test(new PersonBuilder().withEmail("test@example.com").build()));

        assertTrue(new EmailContainsKeywordPredicate(List.of("ALICE"))
                .test(new PersonBuilder().withEmail("alice@example.com").build()));
    }

    @Test
    public void test_emailDoesNotContainKeywords_returnsFalse() {
        assertFalse(new EmailContainsKeywordPredicate(List.of("yahoo"))
                .test(new PersonBuilder().withEmail("alice@example.com").build()));
    }
}
