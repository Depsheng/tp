package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's details in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDetails(String)}
 */
public class Details {

    public static final String MESSAGE_CONSTRAINTS = "Details should be between 1 and 512 characters long";

    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs a {@code Details}.
     *
     * @param details A valid details string.
     */
    public Details(String details) {
        requireNonNull(details);
        checkArgument(isValidDetails(details.trim()), MESSAGE_CONSTRAINTS);
        value = details.trim();
    }

    /**
     * Returns true if a given string is a valid details.
     */
    public static boolean isValidDetails(String test) {
        return test != null && test.matches(VALIDATION_REGEX) && test.length() <= 512;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Details)) {
            return false;
        }

        Details otherDetails = (Details) other;
        return value.equals(otherDetails.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
