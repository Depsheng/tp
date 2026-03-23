package seedu.address.model.person;

import java.util.List;

/**
 * Tests that a {@code Person}'s {@code Details} contains any of the keywords given.
 */
public class DetailsContainsKeywordPredicate extends ObjectContainsKeywordsPredicate<Person> {

    public DetailsContainsKeywordPredicate(List<String> keywords) {
        super(keywords);
    }

    @Override
    protected String getFieldValue(Person person) {
        return person.getDetails().value;
    }
}