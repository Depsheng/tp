package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class SearchPersonForKeywordTest {

    @Test
    public void equalsSameObjectReturnsTrue() {
        SearchPersonForKeyword predicate =
                new SearchPersonForKeyword(
                        Map.of(SearchPersonForKeyword.GENERAL_KEY, List.of("Alice")));
        assertTrue(predicate.equals(predicate));
    }

    @Test
    public void equalsSameValuesReturnsTrue() {
        Map<String, List<String>> fieldMap =
                Map.of(SearchPersonForKeyword.GENERAL_KEY, List.of("Alice"));
        SearchPersonForKeyword predicate1 = new SearchPersonForKeyword(fieldMap);
        SearchPersonForKeyword predicate2 = new SearchPersonForKeyword(fieldMap);
        assertEquals(predicate1, predicate2);
    }

    @Test
    public void equalsDifferentValuesReturnsFalse() {
        SearchPersonForKeyword predicate1 =
                new SearchPersonForKeyword(
                        Map.of(SearchPersonForKeyword.GENERAL_KEY, List.of("Alice")));
        SearchPersonForKeyword predicate2 =
                new SearchPersonForKeyword(
                        Map.of(SearchPersonForKeyword.GENERAL_KEY, List.of("Bob")));
        assertNotEquals(predicate1, predicate2);
    }

    @Test
    public void equalsDifferentTypeReturnsFalse() {
        SearchPersonForKeyword predicate =
                new SearchPersonForKeyword(
                        Map.of(SearchPersonForKeyword.GENERAL_KEY, List.of("Alice")));
        assertFalse(predicate.equals(1));
    }

    @Test
    public void equalsNullReturnsFalse() {
        SearchPersonForKeyword predicate =
                new SearchPersonForKeyword(
                        Map.of(SearchPersonForKeyword.GENERAL_KEY, List.of("Alice")));
        assertFalse(predicate.equals(null));
    }

    @Test
    public void equalsDifferentFieldTypesReturnsFalse() {
        SearchPersonForKeyword predicate1 =
                new SearchPersonForKeyword(Map.of("n/", List.of("Alice")));
        SearchPersonForKeyword predicate2 =
                new SearchPersonForKeyword(Map.of("e/", List.of("Alice")));
        assertNotEquals(predicate1, predicate2);
    }

    @Test
    public void testEmptyKeywordMapReturnsFalse() {
        SearchPersonForKeyword predicate =
                new SearchPersonForKeyword(Collections.emptyMap());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));
    }

    @Test
    public void testOnlyEmptyPrefixedKeywordsReturnsFalse() {
        SearchPersonForKeyword predicate =
                new SearchPersonForKeyword(Map.of(
                        "n/", Collections.emptyList(),
                        "p/", Collections.emptyList(),
                        "a/", Collections.emptyList(),
                        "e/", Collections.emptyList(),
                        "d/", Collections.emptyList(),
                        "t/", Collections.emptyList()));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));
    }

    @Test
    public void testAndLogicMultipleFields() {
        SearchPersonForKeyword predicate =
                new SearchPersonForKeyword(Map.of(
                        "n/", List.of("Alice"),
                        "p/", List.of("9123")));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").withPhone("91234567").build()));
    }

    @Test
    public void testAndLogicMultipleFieldsOneFails() {
        SearchPersonForKeyword predicate =
                new SearchPersonForKeyword(Map.of(
                        "n/", List.of("Alice"),
                        "p/", List.of("9999")));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("91234567").build()));
    }

    @Test
    public void testAndLogicWithTagField() {
        SearchPersonForKeyword predicate =
                new SearchPersonForKeyword(Map.of(
                        "n/", List.of("Alice"),
                        "t/", List.of("buyer")));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").withTags("BUYER").build()));
    }

    @Test
    public void testAndLogicWithTagFieldOneFails() {
        SearchPersonForKeyword predicate =
                new SearchPersonForKeyword(Map.of(
                        "n/", List.of("Alice"),
                        "t/", List.of("seller")));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withTags("BUYER").build()));
    }

    @Test
    public void testOrLogicWithinField() {
        SearchPersonForKeyword predicate =
                new SearchPersonForKeyword(Map.of("n/", List.of("Alice", "Bob")));
        assertTrue(predicate.test(new PersonBuilder().withName("Bob").build()));
    }

    @Test
    public void testOrLogicWithinTagField() {
        SearchPersonForKeyword predicate =
                new SearchPersonForKeyword(Map.of("t/", List.of("buyer", "seller")));
        assertTrue(predicate.test(new PersonBuilder().withTags("SELLER").build()));
    }

    @Test
    public void toStringIncludesAllKeywords() {
        SearchPersonForKeyword predicate =
                new SearchPersonForKeyword(Map.of(
                        "n/", List.of("Alice"),
                        "p/", List.of("9123"),
                        "t/", List.of("buyer"),
                        SearchPersonForKeyword.GENERAL_KEY, List.of("friend")));
        String result = predicate.toString();

        assertTrue(result.contains("Alice"));
        assertTrue(result.contains("9123"));
        assertTrue(result.contains("buyer"));
        assertTrue(result.contains("friend"));
        assertTrue(result.contains("fieldKeywordsMap"));
        assertTrue(result.contains("n/"));
        assertTrue(result.contains("p/"));
        assertTrue(result.contains("t/"));
        assertTrue(result.contains(SearchPersonForKeyword.GENERAL_KEY));
    }

    @Test
    public void toStringEmptyKeywordsReturnsExpectedString() {
        SearchPersonForKeyword predicate =
                new SearchPersonForKeyword(Collections.emptyMap());
        String result = predicate.toString();

        assertTrue(result.contains("fieldKeywordsMap"));
        assertTrue(result.contains("{}"));
    }
}
