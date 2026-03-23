package seedu.address.model.person;

import java.util.List;

/**
 * Tests that a {@code Person}'s {@code Address} contains any of the keywords given.
 */
public class AddressContainsKeywordPredicate extends ObjectContainsKeywordsPredicate<Person> {

    public AddressContainsKeywordPredicate(List<String> keywords) {
        super(keywords);
    }

    @Override
    protected String getFieldValue(Person person) {
        return person.getAddress().value;
    }
}