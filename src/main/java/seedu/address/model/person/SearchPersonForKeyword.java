package seedu.address.model.person;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person} matches a set of keyword-based search conditions.
 *
 * <p>The predicate supports both general and field-specific searches. Keywords are grouped
 * by prefixes (e.g. {@code n/}, {@code p/}, {@code e/}, {@code a/}, {@code d/}), where each
 * prefix corresponds to a specific field of a {@code Person}. General keywords (without a
 * prefix) are matched against all searchable fields.
 *
 * <p>For each prefix, a corresponding field-specific predicate is created. A {@code Person}
 * is considered a match only if all applicable predicates return {@code true} (i.e. logical AND
 * across different fields).
 *
 * <p>Entries with empty or null keyword lists are ignored.
 */
public class SearchPersonForKeyword implements Predicate<Person> {
    public static final String GENERAL_KEY = "general";

    private final Map<String, List<String>> fieldKeywordsMap;
    private final List<Predicate<Person>> predicates;

    /**
     * Constructs a {@code SearchPersonForKeyword} using a mapping of field prefixes
     * to their corresponding keyword lists.
     *
     * <p>Each entry in the map represents a field-specific search condition. Based on the prefix,
     * the appropriate predicate is created and stored internally.
     *
     * <p>Supported prefixes include:
     * <ul>
     *     <li>{@code GENERAL_KEY} for general keyword search</li>
     *     <li>{@code "n/"} for name</li>
     *     <li>{@code "p/"} for phone</li>
     *     <li>{@code "e/"} for email</li>
     *     <li>{@code "a/"} for address</li>
     *     <li>{@code "d/"} for details</li>
     * </ul>
     *
     * <p>Entries with null or empty keyword lists are ignored.
     *
     * @param fieldKeywordsMap A map of prefixes to lists of keywords to match against a {@code Person}.
     */
    public SearchPersonForKeyword(Map<String, List<String>> fieldKeywordsMap) {
        this.fieldKeywordsMap = fieldKeywordsMap;
        this.predicates = new ArrayList<>();
        buildPredicates();
    }

    /**
     * Iterates over all entries in {@code fieldKeywordsMap} and builds the corresponding
     * field-specific predicates, appending each to {@code predicates}.
     *
     * <p>Entries with null or empty keyword lists are skipped. Each recognized prefix maps
     * to a specific {@code Predicate<Person>} implementation. An assertion error is raised
     * if an unexpected prefix is encountered, as this indicates a programming error.
     */
    private void buildPredicates() {
        for (Map.Entry<String, List<String>> entry : fieldKeywordsMap.entrySet()) {
            String prefix = entry.getKey();
            List<String> keywords = entry.getValue();

            if (keywords == null || keywords.isEmpty()) {
                continue;
            }

            Predicate<Person> predicate = buildPredicateForPrefix(prefix, keywords);
            predicates.add(predicate);
        }
    }

    /**
     * Maps a single field prefix and its associated keywords to the corresponding
     * {@code Predicate<Person>} implementation.
     *
     * @param prefix   The field prefix (e.g. {@code "n/"}, {@code "p/"}, {@code GENERAL_KEY}).
     * @param keywords The non-empty list of keywords to match against the field.
     * @return The appropriate {@code Predicate<Person>} for the given prefix.
     * @throws AssertionError If the prefix is not one of the recognized values.
     */
    private Predicate<Person> buildPredicateForPrefix(String prefix, List<String> keywords) {
        switch (prefix) {
        case GENERAL_KEY:
            return new GeneralContainsKeywordPredicate(keywords);
        case "n/":
            return new NameContainsKeywordPredicate(keywords);
        case "p/":
            return new PhoneContainsKeywordPredicate(keywords);
        case "a/":
            return new AddressContainsKeywordPredicate(keywords);
        case "d/":
            return new DetailsContainsKeywordPredicate(keywords);
        case "e/":
            return new EmailContainsKeywordPredicate(keywords);
        default:
            assert false : "Unexpected prefix: " + prefix;
            return null; // unreachable, but required for compilation
        }
    }

    /**
     * Evaluates whether the given {@code Person} satisfies all stored predicates.
     *
     * <p>A {@code Person} is considered a match if:
     * <ul>
     *     <li>There is at least one predicate, and</li>
     *     <li>All predicates return {@code true} for the given {@code Person}</li>
     * </ul>
     *
     * @param person The {@code Person} to test against the predicates.
     * @return {@code true} if the person matches all conditions, {@code false} otherwise.
     */
    @Override
    public boolean test(Person person) {
        return !predicates.isEmpty()
                && predicates.stream().allMatch(predicate -> predicate.test(person));
    }

    /**
     * Compares this predicate with another object for equality.
     *
     * <p>Two {@code SearchPersonForKeyword} objects are considered equal if
     * they have the same field-to-keywords mapping.
     *
     * @param other The object to compare with.
     * @return {@code true} if the given object is equal to this predicate, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof SearchPersonForKeyword)) {
            return false;
        }

        SearchPersonForKeyword otherPredicate = (SearchPersonForKeyword) other;
        return fieldKeywordsMap.equals(otherPredicate.fieldKeywordsMap);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("fieldKeywordsMap", fieldKeywordsMap).toString();
    }
}
