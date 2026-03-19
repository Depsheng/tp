package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class PersonContainsKeywordsPredicateTest {

    // --- Equality Tests ---
    @Test
    public void equals_sameObject_returnsTrue() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.equals(predicate));
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        List<String> keywords = Collections.singletonList("Alice");
        PersonContainsKeywordsPredicate predicate1 = new PersonContainsKeywordsPredicate(keywords);
        PersonContainsKeywordsPredicate predicate2 = new PersonContainsKeywordsPredicate(keywords);
        assertEquals(predicate1, predicate2);
    }

    @Test
    public void equals_differentValues_returnsFalse() {
        PersonContainsKeywordsPredicate predicate1 =
                new PersonContainsKeywordsPredicate(Collections.singletonList("Alice"));
        PersonContainsKeywordsPredicate predicate2 =
                new PersonContainsKeywordsPredicate(Collections.singletonList("Bob"));
        assertNotEquals(predicate1, predicate2);
    }

    @Test
    public void equals_differentType_returnsFalse() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertFalse(predicate.equals(1));
    }

    @Test
    public void equals_null_returnsFalse() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertFalse(predicate.equals(null));
    }

    // --- Empty Keywords ---
    @Test
    public void test_emptyKeywordList_returnsFalse() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));
    }

    @Test
    public void test_onlyEmptyPrefixedKeywords_returnsFalse() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Arrays.asList("n/", "p/", "a/", "e/", "d/"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));
    }

    // --- General Keywords ---
    @Test
    public void test_generalKeyword_matchesAnyField() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Collections.singletonList("unique"));
        assertTrue(predicate.test(new PersonBuilder().withName("unique name").build()));
        assertTrue(predicate.test(new PersonBuilder().withPhone("123unique456").build()));
        assertTrue(predicate.test(new PersonBuilder().withAddress("unique street").build()));
        assertTrue(predicate.test(new PersonBuilder().withEmail("unique@test.com").build()));
        assertTrue(predicate.test(new PersonBuilder().withDetails("unique person").build()));
    }

    @Test
    public void test_generalKeyword_noMatch_returnsFalse() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Collections.singletonList("Charlie"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));
    }

    @Test
    public void test_multipleGeneralKeywords_anyMatch_returnsTrue() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Arrays.asList("Alice", "Bob", "Charlie"));
        assertTrue(predicate.test(new PersonBuilder().withName("Bob").build()));
    }

    @Test
    public void test_multipleGeneralKeywords_noneMatch_returnsFalse() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Arrays.asList("David", "Eve"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));
    }

    // --- Field-Specific Keywords ---
    @Test
    public void test_nameKeywordsWithPrefix_returnsTrue() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Arrays.asList("n/Alice", "Bob"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_phoneKeywordsWithPrefix_returnsTrue() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Arrays.asList("p/9123", "4567"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("91234567").build()));
    }

    @Test
    public void test_addressKeywordsWithPrefix_returnsTrue() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Arrays.asList("a/Main", "Street"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("123 Main Street").build()));
    }

    @Test
    public void test_emailKeywordsWithPrefix_returnsTrue() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Arrays.asList("e/alice", "example"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("alice@example.com").build()));
    }

    @Test
    public void test_detailsKeywordsWithPrefix_returnsTrue() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Arrays.asList("d/friend", "Good"));
        assertTrue(predicate.test(new PersonBuilder().withDetails("Good friend").build()));
    }

    // --- Logic Across Fields ---
    @Test
    public void test_andLogic_multipleFields_returnsTrue() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Arrays.asList("n/Alice", "p/9123"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").withPhone("91234567").build()));
    }

    @Test
    public void test_andLogic_multipleFields_oneFails_returnsFalse() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Arrays.asList("n/Alice", "p/9999"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("91234567").build()));
    }

    @Test
    public void test_orLogic_withinField_returnsTrue() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Arrays.asList("n/Alice", "Bob"));
        assertTrue(predicate.test(new PersonBuilder().withName("Bob").build()));
    }

    // --- Edge Cases ---
    @Test
    public void test_emptyPrefixedKeyword_ignored() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Arrays.asList("n/", "Alice"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").build()));
    }

    @Test
    public void test_fieldContextPersistence_returnsTrue() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Arrays.asList("n/Alice", "Bob", "Charlie"));
        assertTrue(predicate.test(new PersonBuilder().withName("Bob").build()));
        assertTrue(predicate.test(new PersonBuilder().withName("Charlie").build()));
    }

    @Test
    public void test_caseInsensitiveMatching_allFields() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Arrays.asList("n/alice", "p/9123", "a/MAIN", "e/ALICE", "d/FRIEND"));
        assertTrue(predicate.test(new PersonBuilder()
                .withName("Alice")
                .withPhone("91234567")
                .withAddress("123 main street")
                .withEmail("alice@example.com")
                .withDetails("good friend")
                .build()));
    }

    @Test
    public void test_partialSubstringMatching_allFields() {
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
    public void test_specialCharactersInKeywords() {
        assertTrue(new PersonContainsKeywordsPredicate(Arrays.asList("e/@example"))
                .test(new PersonBuilder().withEmail("test@example.com").build()));
        assertTrue(new PersonContainsKeywordsPredicate(Arrays.asList("a/#08-111"))
                .test(new PersonBuilder().withAddress("123, Jurong West Ave 6, #08-111").build()));
    }

    @Test
    public void test_numericKeywordsInNameField_returnsTrue() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Collections.singletonList("n/007"));
        assertTrue(predicate.test(new PersonBuilder().withName("Agent 007").build()));
    }

    @Test
    public void test_textKeywordsInPhoneField_returnsFalse() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Collections.singletonList("p/abc"));
        assertFalse(predicate.test(new PersonBuilder().withPhone("91234567").build()));
    }

    // --- Utility Methods ---
    @Test
    public void toString_includesAllKeywords() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Arrays.asList("n/Alice", "p/9123", "friend"));
        String result = predicate.toString();
        assertTrue(result.contains("Alice"));
        assertTrue(result.contains("