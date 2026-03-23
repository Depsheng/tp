package seedu.address.model.person;

import java.util.List;

/**
 * Tests that a {@code Person}'s fields contain any of the keywords given.
 */
public class GeneralContainsKeywordPredicate extends ObjectContainsKeywordsPredicate<Person> {

    public GeneralContainsKeywordPredicate(List<String> keywords) {
        super(keywords);
    }

    @Override
    protected String getFieldValue(Person person) {
        return person.getName().fullName + " "
                + person.getPhone().value + " "
                + person.getEmail().value + " "
                + person.getAddress().value + " "
                + person.getDetails().value;
    }
}