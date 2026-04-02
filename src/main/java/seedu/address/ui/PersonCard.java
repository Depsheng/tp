package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private ImageView photo;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label details;
    @FXML
    private Label meeting;
    @FXML
    private FlowPane tags;

    /**
     * Creates a {@code PersonCard} with the given {@code Person} and index to display.
     * The meeting label is shown only when the person has a meeting.
     *
     * @param person Person to render.
     * @param displayedIndex One-based index shown on the card.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        address.setText(person.getAddress().value);
        email.setText(person.getEmail().value.isEmpty() ? "No email" : person.getEmail().value);
        details.setText(person.getDetails().value);
        if (person.hasMeeting()) {
            String meetingText = "Meeting: " + person.getMeeting().orElseThrow().getFormattedDateTime();
            meeting.setText(meetingText);
            meeting.setManaged(true);
            meeting.setVisible(true);
        } else {
            meeting.setManaged(false);
            meeting.setVisible(false);
        }
        if (person.getIsFavourite()) {
            photo.setImage(new Image(getClass().getResourceAsStream("/images/star.png")));
            photo.setManaged(true);
            photo.setVisible(true);
        } else {
            photo.setImage(new Image(getClass().getResourceAsStream("/images/unstar.png")));
            photo.setManaged(true);
            photo.setVisible(true);
        }

        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName.name())));
    }
}
