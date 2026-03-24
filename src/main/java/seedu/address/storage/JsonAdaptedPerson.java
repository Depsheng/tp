package seedu.address.storage;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Details;
import seedu.address.model.person.Email;
import seedu.address.model.person.Meeting;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String name;
    private final String phone;
    private final String email;
    private final String address;
    private String details;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();
    private final boolean isFavourite;
    private final String meeting;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     *
     * @param name Serialized name.
     * @param phone Serialized phone number.
     * @param email Serialized email address.
     * @param address Serialized address.
     * @param details Serialized details field.
     * @param tags Serialized tags.
     * @param isFavourite Serialized favourite flag.
     * @param meeting Serialized meeting date/time in ISO-8601 format, if present.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
            @JsonProperty("email") String email, @JsonProperty("address") String address,
            @JsonProperty("details") String details, @JsonProperty("tags") List<JsonAdaptedTag> tags,
            @JsonProperty("isFavourite") boolean isFavourite,
            @JsonProperty("meeting") String meeting) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.details = details;
        if (tags != null) {
            this.tags.addAll(tags);
        }
        this.isFavourite = isFavourite;
        this.meeting = meeting;
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     *
     * @param source Person to serialize.
     */
    public JsonAdaptedPerson(Person source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        details = source.getDetails().value;
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        isFavourite = source.getIsFavourite();
        meeting = source.getMeeting().map(meeting -> meeting.getDateTime().toString()).orElse(null);
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @return Deserialized {@code Person}.
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            personTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        if (details == null) {
            details = "No details";
        }
        if (!Details.isValidDetails(details.trim())) {
            throw new IllegalValueException(Details.MESSAGE_CONSTRAINTS);
        }
        final Details modelDetails = new Details(details.trim());

        final Set<Tag> modelTags = new HashSet<>(personTags);

        final boolean modelIsFavourite = isFavourite;

        Meeting modelMeeting = null;
        if (meeting != null) {
            try {
                modelMeeting = new Meeting(LocalDateTime.parse(meeting));
            } catch (DateTimeParseException exception) {
                throw new IllegalValueException("Meeting date/time must be stored in ISO-8601 format.");
            }
        }

        return new Person(modelName, modelPhone, modelEmail, modelAddress, modelDetails,
                modelTags, modelIsFavourite, modelMeeting);
    }

}
