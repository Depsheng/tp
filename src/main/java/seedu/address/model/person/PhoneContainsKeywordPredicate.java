package seedu.address.model.person;

import java.util.List;

/**
 * Tests that a {@code Person}'s {@code Phone} contains any of the keywords given.
 */
public class PhoneContainsKeywordPredicate extends ObjectContainsKeywordsPredicate<Person> {

    public PhoneContainsKeywordPredicate(List<String> keywords) {
        super(keywords);
    }

    @Override
    protected List<String> getFieldValue(Person person) {
        return List.of(person.getPhone().value);
    }
}
