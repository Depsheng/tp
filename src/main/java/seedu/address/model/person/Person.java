package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Details details;
    private final Set<Tag> tags = new HashSet<>();
    private final boolean isFavourite;
    private final Meeting meeting;

    /**
     * Every field must be present and not null. Meeting is optional.
     */
    public Person(Name name, Phone phone, Email email, Address address,
                  Details details, Set<Tag> tags, boolean isFavourite,
                  Meeting meeting) {
        requireAllNonNull(name, phone, email, address, details, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.details = details;
        this.tags.addAll(tags);
        this.isFavourite = isFavourite;
        this.meeting = meeting;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public Details getDetails() {
        return details;
    }

    public boolean getIsFavourite() {
        return isFavourite;
    }

    /**
     * Returns the scheduled meeting, if one has been assigned.
     */
    public Optional<Meeting> getMeeting() {
        return Optional.ofNullable(meeting);
    }

    /**
     * Returns true if a meeting has been assigned.
     */
    public boolean hasMeeting() {
        return meeting != null;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }



    /**
     * Returns true if both persons have the same phone number.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }
        return otherPerson != null && otherPerson.getPhone().equals(getPhone());
    }

    /**
     * Returns true if both persons have the same identity and data fields, including
     * favourite status and any scheduled meeting.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && address.equals(otherPerson.address)
                && details.equals(otherPerson.details)
                && tags.equals(otherPerson.tags)
                && isFavourite == otherPerson.isFavourite
                && Objects.equals(meeting, otherPerson.meeting);
    }

    /**
     * Returns a hash code that includes the person's identity, details, favourite status and meeting.
     */
    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, details, tags, isFavourite, meeting);
    }

    /**
     * Returns a string representation of this person for debugging.
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("details", details)
                .add("tags", tags)
                .add("isFavourite", isFavourite)
                .add("meeting", meeting)
                .toString();
    }

}
