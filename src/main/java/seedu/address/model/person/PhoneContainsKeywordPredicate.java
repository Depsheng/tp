package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Phone} contains any of the keywords given.
 */
public class PhoneContainsKeywordPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public PhoneContainsKeywordPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> person.getPhone().value.contains(keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PhoneContainsKeywordPredicate)) {
            return false;
        }

        PhoneContainsKeywordPredicate otherPhoneContainsKeywordPredicate = (PhoneContainsKeywordPredicate) other;
        return keywords.equals(otherPhoneContainsKeywordPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
