package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class DetailsContainsKeywordPredicateTest {

    @Test
    public void equals() {
        DetailsContainsKeywordPredicate firstPredicate =
                new DetailsContainsKeywordPredicate(List.of("friend"));
        DetailsContainsKeywordPredicate secondPredicate =
                new DetailsContainsKeywordPredicate(List.of("colleague"));

        assertTrue(firstPredicate.equals(firstPredicate));
        assertTrue(firstPredicate.equals(new DetailsContainsKeywordPredicate(List.of("friend"))));
        assertFalse(firstPredicate.equals(1));
        assertFalse(firstPredicate.equals(null));
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_detailsContainsKeywords_returnsTrue() {
        assertTrue(new DetailsContainsKeywordPredicate(List.of("friend"))
                .test(new PersonBuilder().withDetails("Good friend").build()));

        assertTrue(new DetailsContainsKeywordPredicate(Arrays.asList("friend", "Good"))
                .test(new PersonBuilder().withDetails("Good friend").build()));

        assertTrue(new DetailsContainsKeywordPredicate(List.of("rien"))
                .test(new PersonBuilder().withDetails("Good friend").build()));

        assertTrue(new DetailsContainsKeywordPredicate(List.of("FRIEND"))
                .test(new PersonBuilder().withDetails("good friend").build()));
    }

    @Test
    public void test_detailsDoesNotContainKeywords_returnsFalse() {
        assertFalse(new DetailsContainsKeywordPredicate(List.of("enemy"))
                .test(new PersonBuilder().withDetails("Good friend").build()));
    }
}
