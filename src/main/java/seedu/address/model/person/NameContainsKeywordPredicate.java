package seedu.address.model.person;

import java.util.List;

/**
 * Tests that a {@code Person}'s {@code Name} contains any of the keywords given.
 */
public class NameContainsKeywordPredicate extends ObjectContainsKeywordsPredicate<Person> {

    public NameContainsKeywordPredicate(List<String> keywords) {
        super(keywords);
    }

    @Override
    protected String getFieldValue(Person person) {
        return person.getName().fullName;
    }
}