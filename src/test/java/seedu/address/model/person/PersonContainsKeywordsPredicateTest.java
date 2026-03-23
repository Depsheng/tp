package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class PersonContainsKeywordsPredicateTest {

    // --- Equality Tests ---
    @Test
    public void equalsSameObjectReturnsTrue() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(
                        Map.of(PersonContainsKeywordsPredicate.GENERAL_KEY, List.of("Alice")));
        assertTrue(predicate.equals(predicate));
    }

    @Test
    public void equalsSameValuesReturnsTrue() {
        Map<String, List<String>> fieldMap =
                Map.of(PersonContainsKeywordsPredicate.GENERAL_KEY, List.of("Alice"));
        PersonContainsKeywordsPredicate predicate1 = new PersonContainsKeywordsPredicate(fieldMap);
        PersonContainsKeywordsPredicate predicate2 = new PersonContainsKeywordsPredicate(fieldMap);
        assertEquals(predicate1, predicate2);
    }

    @Test
    public void equalsDifferentValuesReturnsFalse() {
        PersonContainsKeywordsPredicate predicate1 =
                new PersonContainsKeywordsPredicate(
                        Map.of(PersonContainsKeywordsPredicate.GENERAL_KEY, List.of("Alice")));
        PersonContainsKeywordsPredicate predicate2 =
                new PersonContainsKeywordsPredicate(
                        Map.of(PersonContainsKeywordsPredicate.GENERAL_KEY, List.of("Bob")));
        assertNotEquals(predicate1, predicate2);
    }

    @Test
    public void equalsDifferentTypeReturnsFalse() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(
                        Map.of(PersonContainsKeywordsPredicate.GENERAL_KEY, List.of("Alice")));
        assertFalse(predicate.equals(1));
    }

    @Test
    public void equalsNullReturnsFalse() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(
                        Map.of(PersonContainsKeywordsPredicate.GENERAL_KEY, List.of("Alice")));
        assertFalse(predicate.equals(null));
    }

    @Test
    public void equalsDifferentFieldTypesReturnsFalse() {
        PersonContainsKeywordsPredicate predicate1 =
                new PersonContainsKeywordsPredicate(Map.of("n/", List.of("Alice")));
        PersonContainsKeywordsPredicate predicate2 =
                new PersonContainsKeywordsPredicate(Map.of("e/", List.of("Alice")));
        assertNotEquals(predicate1, predicate2);
    }

    // --- Empty Keywords ---
    @Test
    public void testEmptyKeywordMapReturnsFalse() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Collections.emptyMap());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));
    }

    @Test
    public void testOnlyEmptyPrefixedKeywordsReturnsFalse() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Map.of(
                        "n/", Collections.emptyList(),
                        "p/", Collections.emptyList(),
                        "a/", Collections.emptyList(),
                        "e/", Collections.emptyList(),
                        "d/", Collections.emptyList()));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));
    }

    // --- General Keywords ---
    @Test
    public void testGeneralKeywordNoMatch() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(
                        Map.of(PersonContainsKeywordsPredicate.GENERAL_KEY, List.of("Charlie")));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));
    }

    @Test
    public void testMultipleGeneralKeywordsAnyMatch() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(
                        Map.of(PersonContainsKeywordsPredicate.GENERAL_KEY,
                                List.of("Alice", "Bob", "Charlie")));
        assertTrue(predicate.test(new PersonBuilder().withName("Bob").build()));
    }

    @Test
    public void testMultipleGeneralKeywordsNoneMatch() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(
                        Map.of(PersonContainsKeywordsPredicate.GENERAL_KEY,
                                List.of("David", "Eve")));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));
    }

    // --- Field-Specific Keywords ---
    @Test
    public void testNameKeywordsWithPrefix() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Map.of("n/", List.of("Alice", "Bob")));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void testPhoneKeywordsWithPrefix() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Map.of("p/", List.of("9123", "4567")));
        assertTrue(predicate.test(new PersonBuilder().withPhone("91234567").build()));
    }

    @Test
    public void testAddressKeywordsWithPrefix() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Map.of("a/", List.of("Main", "Street")));
        assertTrue(predicate.test(new PersonBuilder().withAddress("123 Main Street").build()));
    }

    @Test
    public void testEmailKeywordsWithPrefix() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Map.of("e/", List.of("alice", "example")));
        assertTrue(predicate.test(new PersonBuilder().withEmail("alice@example.com").build()));
    }

    @Test
    public void testDetailsKeywordsWithPrefix() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Map.of("d/", List.of("friend", "Good")));
        assertTrue(predicate.test(new PersonBuilder().withDetails("Good friend").build()));
    }

    // --- Logic Across Fields ---
    @Test
    public void testAndLogicMultipleFields() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Map.of(
                        "n/", List.of("Alice"),
                        "p/", List.of("9123")));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").withPhone("91234567").build()));
    }

    @Test
    public void testAndLogicMultipleFieldsOneFails() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Map.of(
                        "n/", List.of("Alice"),
                        "p/", List.of("9999")));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("91234567").build()));
    }

    @Test
    public void testOrLogicWithinField() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Map.of("n/", List.of("Alice", "Bob")));
        assertTrue(predicate.test(new PersonBuilder().withName("Bob").build()));
    }

    // --- Edge Cases ---
    @Test
    public void testEmptyPrefixedKeywordIgnored() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Map.of(
                        "n/", Arrays.asList("", "Alice")));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").build()));
    }

    @Test
    public void testFieldContextPersistenceReplacedByExplicitGrouping() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Map.of("n/", List.of("Alice", "Bob", "Charlie")));
        assertTrue(predicate.test(new PersonBuilder().withName("Bob").build()));
        assertTrue(predicate.test(new PersonBuilder().withName("Charlie").build()));
    }

    @Test
    public void testCaseInsensitiveMatchingAllFields() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Map.of(
                        "n/", List.of("alice"),
                        "p/", List.of("9123"),
                        "a/", List.of("MAIN"),
                        "e/", List.of("ALICE"),
                        "d/", List.of("FRIEND")));
        assertTrue(predicate.test(new PersonBuilder()
                .withName("Alice")
                .withPhone("91234567")
                .withAddress("123 main street")
                .withEmail("alice@example.com")
                .withDetails("good friend")
                .build()));
    }

    @Test
    public void testPartialSubstringMatchingAllFields() {
        assertTrue(new PersonContainsKeywordsPredicate(Map.of("n/", List.of("lic")))
                .test(new PersonBuilder().withName("Alice").build()));
        assertTrue(new PersonContainsKeywordsPredicate(Map.of("p/", List.of("234")))
                .test(new PersonBuilder().withPhone("12345678").build()));
        assertTrue(new PersonContainsKeywordsPredicate(Map.of("a/", List.of("ain")))
                .test(new PersonBuilder().withAddress("123 Main Street").build()));
        assertTrue(new PersonContainsKeywordsPredicate(Map.of("e/", List.of("exam")))
                .test(new PersonBuilder().withEmail("test@example.com").build()));
        assertTrue(new PersonContainsKeywordsPredicate(Map.of("d/", List.of("rien")))
                .test(new PersonBuilder().withDetails("Good friend").build()));
    }

    @Test
    public void testSpecialCharactersInKeywords() {
        assertTrue(new PersonContainsKeywordsPredicate(Map.of("e/", List.of("@example")))
                .test(new PersonBuilder().withEmail("test@example.com").build()));
        assertTrue(new PersonContainsKeywordsPredicate(Map.of("a/", List.of("#08-111")))
                .test(new PersonBuilder().withAddress("123, Jurong West Ave 6, #08-111").build()));
    }

    @Test
    public void testNumericKeywordsInNameField() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Map.of("n/", List.of("007")));
        assertTrue(predicate.test(new PersonBuilder().withName("Agent 007").build()));
    }

    @Test
    public void testTextKeywordsInPhoneField() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Map.of("p/", List.of("abc")));
        assertFalse(predicate.test(new PersonBuilder().withPhone("91234567").build()));
    }

    // --- Utility Methods ---
    @Test
    public void toStringIncludesAllKeywords() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Map.of(
                        "n/", List.of("Alice"),
                        "p/", List.of("9123"),
                        PersonContainsKeywordsPredicate.GENERAL_KEY, List.of("friend")));
        String result = predicate.toString();

        assertTrue(result.contains("Alice"));
        assertTrue(result.contains("9123"));
        assertTrue(result.contains("friend"));
        assertTrue(result.contains("fieldKeywordsMap"));
        assertTrue(result.contains("n/"));
        assertTrue(result.contains("p/"));
        assertTrue(result.contains(PersonContainsKeywordsPredicate.GENERAL_KEY));
    }

    @Test
    public void toStringEmptyKeywordsReturnsExpectedString() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Collections.emptyMap());
        String result = predicate.toString();

        assertTrue(result.contains("fieldKeywordsMap"));
        assertTrue(result.contains("{}"));
    }

    @Test
    public void equalsSameFieldKeywordsReturnsTrue() {
        PersonContainsKeywordsPredicate predicate1 =
                new PersonContainsKeywordsPredicate(Map.of(
                        "n/", List.of("Alice"),
                        "p/", List.of("9123")));
        PersonContainsKeywordsPredicate predicate2 =
                new PersonContainsKeywordsPredicate(Map.of(
                        "n/", List.of("Alice"),
                        "p/", List.of("9123")));
        assertEquals(predicate1, predicate2);
    }

    @Test
    public void equalsDifferentFieldKeywordsReturnsFalse() {
        PersonContainsKeywordsPredicate predicate1 =
                new PersonContainsKeywordsPredicate(Map.of(
                        "n/", List.of("Alice"),
                        "p/", List.of("9123")));
        PersonContainsKeywordsPredicate predicate2 =
                new PersonContainsKeywordsPredicate(Map.of(
                        "n/", List.of("Bob"),
                        "p/", List.of("9123")));
        assertNotEquals(predicate1, predicate2);
    }
}