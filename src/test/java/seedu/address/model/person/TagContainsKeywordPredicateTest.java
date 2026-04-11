package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class TagContainsKeywordPredicateTest {

    @Test
    public void equals() {
        TagContainsKeywordPredicate firstPredicate =
                new TagContainsKeywordPredicate(List.of("buyer"));
        TagContainsKeywordPredicate secondPredicate =
                new TagContainsKeywordPredicate(List.of("seller"));

        assertTrue(firstPredicate.equals(firstPredicate));
        assertTrue(firstPredicate.equals(new TagContainsKeywordPredicate(List.of("buyer"))));
        assertFalse(firstPredicate.equals(1));
        assertFalse(firstPredicate.equals(null));
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_tagContainsKeywords_returnsTrue() {
        assertTrue(new TagContainsKeywordPredicate(List.of("buyer"))
                .test(new PersonBuilder().withTags("BUYER").build()));

        assertTrue(new TagContainsKeywordPredicate(Arrays.asList("buyer", "seller"))
                .test(new PersonBuilder().withTags("SELLER").build()));

        assertTrue(new TagContainsKeywordPredicate(List.of("buy"))
                .test(new PersonBuilder().withTags("BUYER").build()));

        assertTrue(new TagContainsKeywordPredicate(List.of("buyer"))
                .test(new PersonBuilder().withTags("buyer").build()));
    }

    @Test
    public void test_tagDoesNotContainKeywords_returnsFalse() {
        assertFalse(new TagContainsKeywordPredicate(List.of("landlord"))
                .test(new PersonBuilder().withTags("BUYER").build()));

        assertFalse(new TagContainsKeywordPredicate(List.of("buyer"))
                .test(new PersonBuilder().withTags("RENTER").build()));
    }
}
