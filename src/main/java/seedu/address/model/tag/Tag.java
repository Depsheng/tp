package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Tag in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidTagName(String)}
 */
public class Tag {

    public static final String MESSAGE_CONSTRAINTS = "Invalid tag. Valid tags are: Renter, Landlord, Buyer, Seller.";
    public static final String VALIDATION_REGEX = "\\p{Alnum}+";

    public final ClientTag tagName;

    /**
     * Constructs a {@code Tag}.
     *
     * @param tagName A valid tag name.
     */
    public Tag(String tagName) throws IllegalArgumentException {
        requireNonNull(tagName);
        try {
            this.tagName = ClientTag.valueOf(tagName.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Tag must be of Renter, Landlord, buyer or seller");
        }

    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidTagName(String test) {
        try {
            ClientTag.valueOf(test.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Tag)) {
            return false;
        }

        Tag otherTag = (Tag) other;
        return tagName.equals(otherTag.tagName);
    }

    @Override
    public int hashCode() {
        return tagName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + tagName.name() + ']';
    }

}
