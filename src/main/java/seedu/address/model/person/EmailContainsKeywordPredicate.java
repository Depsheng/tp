package seedu.address.model.person;

import java.util.List;

/**
 * Tests that a {@code Person}'s {@code Email} contains any of the keywords given.
 */
public class EmailContainsKeywordPredicate extends ObjectContainsKeywordsPredicate<Person> {

    public EmailContainsKeywordPredicate(List<String> keywords) {
        super(keywords);
    }

    @Override
    protected String getFieldValue(Person person) {
        return person.getEmail().value;
    }
}