package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class PersonContainsKeywordsPredicateTest {

    // --- Equality Tests ---
    @Test
    public void equalsSameObjectReturnsTrue() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.equals(predicate));
    }

    @Test
    public void equalsSameValuesReturnsTrue() {
        List<String> keywords = Collections.singletonList("Alice");
        PersonContainsKeywordsPredicate predicate1 = new PersonContainsKeywordsPredicate(keywords);
        PersonContainsKeywordsPredicate predicate2 = new PersonContainsKeywordsPredicate(keywords);
        assertEquals(predicate1, predicate2);
    }

    @Test
    public void equalsDifferentValuesReturnsFalse() {
        PersonContainsKeywordsPredicate predicate1 =
                new PersonContainsKeywordsPredicate(Collections.singletonList("Alice"));
        PersonContainsKeywordsPredicate predicate2 =
                new PersonContainsKeywordsPredicate(Collections.singletonList("Bob"));
        assertNotEquals(predicate1, predicate2);
    }

    @Test
    public void equalsDifferentTypeReturnsFalse() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertFalse(predicate.equals(1));
    }

    @Test
    public void equalsNullReturnsFalse() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertFalse(predicate.equals(null));
    }

    @Test
    public void equalsDifferentFieldTypesReturnsFalse() {
        PersonContainsKeywordsPredicate predicate1 =
                new PersonContainsKeywordsPredicate(Arrays.asList("n/Alice"));
        PersonContainsKeywordsPredicate predicate2 =
                new PersonContainsKeywordsPredicate(Arrays.asList("e/Alice"));
        assertNotEquals(predicate1, predicate2);
    }

    // --- Empty Keywords ---
    @Test
    public void testEmptyKeywordListReturnsFalse() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));
    }

    @Test
    public void testOnlyEmptyPrefixedKeywordsReturnsFalse() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Arrays.asList("n/", "p/", "a/", "e/", "d/"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));
    }

    // --- General Keywords ---
    @Test
    public void testGeneralKeywordNoMatch() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Collections.singletonList("Charlie"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));
    }

    @Test
    public void testMultipleGeneralKeywordsAnyMatch() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Arrays.asList("Alice", "Bob", "Charlie"));
        assertTrue(predicate.test(new PersonBuilder().withName("Bob").build()));
    }

    @Test
    public void testMultipleGeneralKeywordsNoneMatch() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Arrays.asList("David", "Eve"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));
    }

    // --- Field-Specific Keywords ---
    @Test
    public void testNameKeywordsWithPrefix() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Arrays.asList("n/Alice", "Bob"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void testPhoneKeywordsWithPrefix() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Arrays.asList("p/9123", "4567"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("91234567").build()));
    }

    @Test
    public void testAddressKeywordsWithPrefix() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Arrays.asList("a/Main", "Street"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("123 Main Street").build()));
    }

    @Test
    public void testEmailKeywordsWithPrefix() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Arrays.asList("e/alice", "example"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("alice@example.com").build()));
    }

    @Test
    public void testDetailsKeywordsWithPrefix() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Arrays.asList("d/friend", "Good"));
        assertTrue(predicate.test(new PersonBuilder().withDetails("Good friend").build()));
    }

    // --- Logic Across Fields ---
    @Test
    public void testAndLogicMultipleFields() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Arrays.asList("n/Alice", "p/9123"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").withPhone("91234567").build()));
    }

    @Test
    public void testAndLogicMultipleFieldsOneFails() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Arrays.asList("n/Alice", "p/9999"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("91234567").build()));
    }

    @Test
    public void testOrLogicWithinField() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Arrays.asList("n/Alice", "Bob"));
        assertTrue(predicate.test(new PersonBuilder().withName("Bob").build()));
    }

    // --- Edge Cases ---
    @Test
    public void testEmptyPrefixedKeywordIgnored() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Arrays.asList("n/", "Alice"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").build()));
    }

    @Test
    public void testFieldContextPersistence() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Arrays.asList("n/Alice", "Bob", "Charlie"));
        assertTrue(predicate.test(new PersonBuilder().withName("Bob").build()));
        assertTrue(predicate.test(new PersonBuilder().withName("Charlie").build()));
    }

    @Test
    public void testCaseInsensitiveMatchingAllFields() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Arrays.asList(
                        "n/alice", "p/9123", "a/MAIN", "e/ALICE", "d/FRIEND"));
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
        assertTrue(new PersonContainsKeywordsPredicate(Arrays.asList("n/lic"))
                .test(new PersonBuilder().withName("Alice").build()));
        assertTrue(new PersonContainsKeywordsPredicate(Arrays.asList("p/234"))
                .test(new PersonBuilder().withPhone("12345678").build()));
        assertTrue(new PersonContainsKeywordsPredicate(Arrays.asList("a/ain"))
                .test(new PersonBuilder().withAddress("123 Main Street").build()));
        assertTrue(new PersonContainsKeywordsPredicate(Arrays.asList("e/exam"))
                .test(new PersonBuilder().withEmail("test@example.com").build()));
        assertTrue(new PersonContainsKeywordsPredicate(Arrays.asList("d/rien"))
                .test(new PersonBuilder().withDetails("Good friend").build()));
    }

    @Test
    public void testSpecialCharactersInKeywords() {
        assertTrue(new PersonContainsKeywordsPredicate(Arrays.asList("e/@example"))
                .test(new PersonBuilder().withEmail("test@example.com").build()));
        assertTrue(new PersonContainsKeywordsPredicate(Arrays.asList("a/#08-111"))
                .test(new PersonBuilder().withAddress("123, Jurong West Ave 6, #08-111").build()));
    }

    @Test
    public void testNumericKeywordsInNameField() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Collections.singletonList("n/007"));
        assertTrue(predicate.test(new PersonBuilder().withName("Agent 007").build()));
    }

    @Test
    public void testTextKeywordsInPhoneField() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Collections.singletonList("p/abc"));
        assertFalse(predicate.test(new PersonBuilder().withPhone("91234567").build()));
    }

    // --- Utility Methods ---
    @Test
    public void toStringIncludesAllKeywords() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Arrays.asList("n/Alice", "p/9123", "friend"));
        String result = predicate.toString();

        // Check that all keyword lists are represented
        assertTrue(result.contains("Alice"));
        assertTrue(result.contains("9123"));
        assertTrue(result.contains("friend"));
        assertTrue(result.contains("generalKeywords"));
        assertTrue(result.contains("nameKeywords"));
        assertTrue(result.contains("phoneKeywords"));
        assertTrue(result.contains("addressKeywords"));
        assertTrue(result.contains("emailKeywords"));
        assertTrue(result.contains("detailsKeywords"));
    }

    @Test
    public void toStringEmptyKeywordsReturnsExpectedString() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Collections.emptyList());
        String result = predicate.toString();

        // All lists should be empty
        assertTrue(result.contains("generalKeywords=[]"));
        assertTrue(result.contains("nameKeywords=[]"));
        assertTrue(result.contains("phoneKeywords=[]"));
        assertTrue(result.contains("addressKeywords=[]"));
        assertTrue(result.contains("emailKeywords=[]"));
        assertTrue(result.contains("detailsKeywords=[]"));
    }

    @Test
    public void equalsSameFieldKeywordsReturnsTrue() {
        PersonContainsKeywordsPredicate predicate1 =
                new PersonContainsKeywordsPredicate(Arrays.asList("n/Alice", "p/9123"));
        PersonContainsKeywordsPredicate predicate2 =
                new PersonContainsKeywordsPredicate(Arrays.asList("n/Alice", "p/9123"));
        assertEquals(predicate1, predicate2);
    }

    @Test
    public void equalsDifferentFieldKeywordsReturnsFalse() {
        PersonContainsKeywordsPredicate predicate1 =
                new PersonContainsKeywordsPredicate(Arrays.asList("n/Alice", "p/9123"));
        PersonContainsKeywordsPredicate predicate2 =
                new PersonContainsKeywordsPredicate(Arrays.asList("n/Bob", "p/9123"));
        assertNotEquals(predicate1, predicate2);
    }
}

