package seedu.address.model.person;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Abstract predicate that checks whether a specific field of an object
 * contains any of the given keywords.
 *
 * <p>Subclasses are required to implement {@link #getFieldValue(Object)}
 * to specify which field of the object should be evaluated.
 *
 * @param <T> The type of object to be tested.
 */
public abstract class ObjectContainsKeywordsPredicate<T> implements Predicate<T> {
    protected final List<String> keywords;

    /**
     * Constructs an {@code ObjectContainsKeywordsPredicate} with the specified keywords.
     *
     * <p>The keywords are used to perform case-insensitive substring matching
     * against the target field value.
     *
     * @param keywords A list of keywords to match against the object's field.
     */
    public ObjectContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    /**
     * Returns the string value of the field from the given object to be tested.
     *
     * <p>This method must be implemented by subclasses to define which field
     * of the object is used for keyword matching.
     *
     * @param object The object whose field value is to be retrieved.
     * @return The string value of the relevant field.
     */
    protected abstract String getFieldValue(T object);

    /**
     * Evaluates whether the specified object contains any of the keywords
     * in its target field.
     *
     * <p>The comparison is case-insensitive and checks if any keyword is a
     * substring of the field value.
     *
     * <p>If the field value is {@code null}, this method returns {@code false}.
     *
     * @param object The object to test.
     * @return {@code true} if at least one keyword matches the field value,
     *         {@code false} otherwise.
     */
    @Override
    public boolean test(T object) {
        String fieldValue = getFieldValue(object);
        if (fieldValue == null) {
            return false;
        }

        String lowerCaseFieldValue = fieldValue.toLowerCase();

        return keywords.stream()
                .filter(keyword -> keyword != null)
                .map(String::toLowerCase)
                .anyMatch(lowerCaseFieldValue::contains);
    }

    /**
     * Compares this predicate with another object for equality.
     *
     * <p>Two predicates are considered equal if they are of the same class
     * and contain the same list of keywords.
     *
     * @param other The object to compare with.
     * @return {@code true} if the given object is equal to this predicate,
     *         {@code false} otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (other == null || other.getClass() != this.getClass()) {
            return false;
        }

        ObjectContainsKeywordsPredicate<?> otherPredicate =
                (ObjectContainsKeywordsPredicate<?>) other;
        return Objects.equals(keywords, otherPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}