package seedu.address.model.person;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests if a {@code Person} matches the given general or field-specific keywords.
*/
public class PersonContainsKeywordsPredicate implements Predicate<Person> {
    private static final String GENERAL_FIELD = "general";
    private static final String NAME_FIELD = "name";
    private static final String PHONE_FIELD = "phone";
    private static final String ADDRESS_FIELD = "address";
    private static final String EMAIL_FIELD = "email";

    private static final String NAME_PREFIX = "n/";
    private static final String PHONE_PREFIX = "p/";
    private static final String ADDRESS_PREFIX = "a/";
    private static final String EMAIL_PREFIX = "e/";

    private final List<String> nameKeywords = new ArrayList<>();
    private final List<String> phoneKeywords = new ArrayList<>();
    private final List<String> addressKeywords = new ArrayList<>();
    private final List<String> emailKeywords = new ArrayList<>();
    private final List<String> generalKeywords = new ArrayList<>();

    /**
     * Constructs a {@code PersonContainsKeywordsPredicate} using the given keywords.
     *
     * <p>Keywords may be prefixed with {@code n/}, {@code p/}, or {@code a/}
     * to indicate name, phone, or address fields respectively. Unprefixed
     * keywords are treated as general keywords.
     *
     * @param keywords Tokens parsed from user input.
     */
    public PersonContainsKeywordsPredicate(List<String> keywords) {
        String currentField = GENERAL_FIELD;

        for (String token : keywords) {
            if (isPrefix(token, NAME_PREFIX)) {
                currentField = NAME_FIELD;
                addKeyword(token, getKeywordList(currentField), true);
                continue;
            }

            if (isPrefix(token, PHONE_PREFIX)) {
                currentField = PHONE_FIELD;
                addKeyword(token, getKeywordList(currentField), true);
                continue;
            }

            if (isPrefix(token, ADDRESS_PREFIX)) {
                currentField = ADDRESS_FIELD;
                addKeyword(token, getKeywordList(currentField), true);
                continue;
            }

            if (isPrefix(token, EMAIL_PREFIX)) {
                currentField = EMAIL_FIELD;
                addKeyword(token, getKeywordList(currentField), true);
                continue;
            }

            addKeyword(token, getKeywordList(currentField), false);
        }
    }

    /**
     * Returns {@code true} if the given token starts with the specified prefix.
     *
     * @param token The token to check.
     * @param prefix The prefix to match.
     * @return {@code true} if the token starts with the given prefix.
     */
    private boolean isPrefix(String token, String prefix) {
        return token.startsWith(prefix);
    }

    /**
     * Adds the given token to the specified keyword list.
     *
     * <p>If the token is prefixed, the prefix is removed before adding. Empty values are ignored.
     *
     * @param token The token to add.
     * @param keywordList The list to add the keyword to.
     * @param isPrefixed Whether the token starts with a field prefix.
     */
    private void addKeyword(String token, List<String> keywordList, boolean isPrefixed) {
        String value = isPrefixed ? token.substring(2) : token;
        if (!value.isEmpty()) {
            keywordList.add(value);
        }
    }

    private List<String> getKeywordList(String currentField) {
        switch (currentField) {
        case NAME_FIELD:
            return nameKeywords;
        case PHONE_FIELD:
            return phoneKeywords;
        case ADDRESS_FIELD:
            return addressKeywords;
        case EMAIL_FIELD:
            return emailKeywords;
        default:
            return generalKeywords;
        }
    }

    /**
     * Tests if the given {@code Person} matches the stored keywords.
     *
     * <p>If no field-specific prefixes are used, performs a general search across
     * name, phone, address, and email fields. Otherwise, performs field-specific
     * matching based on prefixes.</p>
     *
     * <p>Keywords within the same field are matched using OR logic, while
     * conditions across different fields are combined using AND logic.</p>
     *
     * @param person The {@code Person} to test.
     * @return {@code true} if the person matches the keywords, {@code false} otherwise.
     */
    @Override
    public boolean test(Person person) {
        if (generalKeywords.isEmpty()
            && nameKeywords.isEmpty()
            && phoneKeywords.isEmpty()
            && addressKeywords.isEmpty()
            && emailKeywords.isEmpty()) {
            return false;
        }

        if (!generalKeywords.isEmpty()
                && nameKeywords.isEmpty()
                && phoneKeywords.isEmpty()
                && addressKeywords.isEmpty()
                && emailKeywords.isEmpty()) {
            return generalKeywords.stream().anyMatch(keyword ->
                    StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword)
                            || StringUtil.containsWordIgnoreCase(person.getPhone().value, keyword)
                            || StringUtil.containsWordIgnoreCase(person.getAddress().value, keyword)
                            || StringUtil.containsWordIgnoreCase(person.getEmail().value, keyword));
        }

        return matches(person.getName().fullName, nameKeywords)
                && matches(person.getPhone().value, phoneKeywords)
                && matches(person.getAddress().value, addressKeywords)
                && matches(person.getEmail().value, emailKeywords);
    }

    /**
     * Returns {@code true} if the given {@code value} matches at least one of the
     * specified {@code keywords}.
     *
     * <p>If the keyword list is empty, this method returns {@code true} (i.e. no
     * restriction is applied for that field). Otherwise, the method checks if any
     * keyword is contained as a word within the value, ignoring case.</p>
     *
     * @param value The string value to be tested against.
     * @param keywords The list of keywords to match.
     * @return {@code true} if the value matches any keyword, or if the keyword list is empty.
     */
    private boolean matches(String value, List<String> keywords) {
        return keywords.isEmpty()
                || keywords.stream().anyMatch(keyword ->
                StringUtil.containsWordIgnoreCase(value, keyword));
    }

    /**
     * Returns {@code true} if this predicate is equal to the given object.
     *
     * <p>Two {@code PersonContainsKeywordsPredicate} objects are considered equal
     * if they contain the same sets of keywords for all fields (general, name,
     * phone, address, and email).</p>
     *
     * @param other The object to compare with.
     * @return {@code true} if the given object represents an equivalent predicate.
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
        return nameKeywords.equals(otherPredicate.nameKeywords)
            && phoneKeywords.equals(otherPredicate.phoneKeywords)
            && addressKeywords.equals(otherPredicate.addressKeywords)
            && generalKeywords.equals(otherPredicate.generalKeywords)
            && emailKeywords.equals(otherPredicate.emailKeywords);
    }

    /**
     * Returns a string representation of this predicate for debugging purposes.
     *
     * <p>The returned string includes all keyword lists (general, name, phone,
     * address, and email) stored in this predicate.</p>
     *
     * @return A string describing the current state of this predicate.
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("generalKeywords", generalKeywords)
            .add("nameKeywords", nameKeywords)
            .add("phoneKeywords", phoneKeywords)
            .add("addressKeywords", addressKeywords)
            .add("emailKeywords", emailKeywords)
            .toString();
    }
}
