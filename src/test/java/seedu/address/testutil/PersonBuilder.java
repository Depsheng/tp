package seedu.address.testutil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.Details;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_DETAILS = "No details";
    public static final boolean DEFAULT_ISFAVOURITE = false;

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Details details;
    private Set<Tag> tags;
    private boolean isFavourite;
    private LocalDate meetingDate;
    private LocalTime meetingTime;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        details = new Details(DEFAULT_DETAILS);
        tags = new HashSet<>();
        isFavourite = DEFAULT_ISFAVOURITE;
        meetingDate = null;
        meetingTime = null;
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        address = personToCopy.getAddress();
        details = personToCopy.getDetails();
        tags = new HashSet<>(personToCopy.getTags());
        isFavourite = personToCopy.getIsFavourite();
        meetingDate = personToCopy.getMeetingDate().orElse(null);
        meetingTime = personToCopy.getMeetingTime().orElse(null);
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Details} of the {@code Person} that we are building.
     */
    public PersonBuilder withDetails(String details) {
        this.details = new Details(details);
        return this;
    }

    /**
     * Sets the favourite status of the {@code Person} that we are building.
     */
    public PersonBuilder withIsFavourite(boolean isFavourite) {
        this.isFavourite = isFavourite;
        return this;
    }

    /**
     * Sets the meeting date and time of the {@code Person} that we are building.
     */
    public PersonBuilder withMeeting(String date, String time) {
        this.meetingDate = LocalDate.parse(date, java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        this.meetingTime = LocalTime.parse(time, java.time.format.DateTimeFormatter.ofPattern("HH:mm"));
        return this;
    }

    /**
     * Removes any assigned meeting date and time from the {@code Person} that we are building.
     */
    public PersonBuilder withoutMeeting() {
        this.meetingDate = null;
        this.meetingTime = null;
        return this;
    }

    /**
     * Builds and returns a {@code Person} with the configured fields.
     */
    public Person build() {
        return new Person(name, phone, email, address, details, tags, isFavourite, meetingDate, meetingTime);
    }

}
