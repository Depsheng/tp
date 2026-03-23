package seedu.address.model.person;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

public class PersonContainsKeywordsPredicate implements Predicate<Person> {
    public static final String GENERAL_KEY = "general";

    private final Map<String, List<String>> fieldKeywordsMap;
    private final List<Predicate<Person>> predicates;

    /**
     * Constructs a {@code PersonContainsKeywordsPredicate} using a mapping of field prefixes
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
    public PersonContainsKeywordsPredicate(Map<String, List<String>> fieldKeywordsMap) {
        this.fieldKeywordsMap = fieldKeywordsMap;
        this.predicates = new ArrayList<>();

        for (Map.Entry<String, List<String>> entry : fieldKeywordsMap.entrySet()) {
            String prefix = entry.getKey();
            List<String> keywords = entry.getValue();

            if (keywords == null || keywords.isEmpty()) {
                continue;
            }

            switch (prefix) {
                case GENERAL_KEY:
                    predicates.add(new GeneralContainsKeywordPredicate(keywords));
                    break;
                case "n/":
                    predicates.add(new NameContainsKeywordPredicate(keywords));
                    break;
                case "p/":
                    predicates.add(new PhoneContainsKeywordPredicate(keywords));
                    break;
                case "a/":
                    predicates.add(new AddressContainsKeywordPredicate(keywords));
                    break;
                case "d/":
                    predicates.add(new DetailsContainsKeywordPredicate(keywords));
                    break;
                case "e/":
                    predicates.add(new EmailContainsKeywordPredicate(keywords));
                default:
                    break;
            }
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
     * <p>Two {@code PersonContainsKeywordsPredicate} objects are considered equal if
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

        if (!(other instanceof PersonContainsKeywordsPredicate)) {
            return false;
        }

        PersonContainsKeywordsPredicate otherPredicate = (PersonContainsKeywordsPredicate) other;
        return fieldKeywordsMap.equals(otherPredicate.fieldKeywordsMap);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("fieldKeywordsMap", fieldKeywordsMap).toString();
    }
}