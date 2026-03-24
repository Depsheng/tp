package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class PersonCardTest {

    @BeforeAll
    public static void setUpJavaFxToolkit() {
        try {
            Platform.startup(() -> { });
        } catch (IllegalStateException ignored) {
            // JavaFX toolkit already initialized by another test.
        }
    }

    @Test
    public void constructor_withoutMeeting_hidesMeetingLabel() throws Exception {
        Person person = new PersonBuilder()
                .withName("Amy Bee")
                .withPhone("85355255")
                .withEmail("amy@gmail.com")
                .withAddress("123, Jurong West Ave 6, #08-111")
                .withDetails("No details")
                .withTags("Seller", "Buyer")
                .withoutMeeting()
                .build();

        runOnFxThreadAndWait(() -> {
            PersonCard card = new PersonCard(person, 1);
            Parent root = (Parent) card.getRoot();
            new Scene(root);

            Label meetingLabel = (Label) root.lookup("#meeting");
            assertNotNull(meetingLabel);
            assertFalse(meetingLabel.isManaged());
            assertFalse(meetingLabel.isVisible());

            FlowPane tags = (FlowPane) root.lookup("#tags");
            assertNotNull(tags);
            assertEquals(2, tags.getChildren().size());
            assertEquals("BUYER", ((Label) tags.getChildren().get(0)).getText());
            assertEquals("SELLER", ((Label) tags.getChildren().get(1)).getText());
        });
    }

    @Test
    public void constructor_withMeeting_showsFormattedMeetingLabel() throws Exception {
        Person person = new PersonBuilder()
                .withName("Amy Bee")
                .withPhone("85355255")
                .withEmail("amy@gmail.com")
                .withAddress("123, Jurong West Ave 6, #08-111")
                .withDetails("No details")
                .withTags()
                .withMeeting("23/03/2026", "14:30")
                .build();

        runOnFxThreadAndWait(() -> {
            PersonCard card = new PersonCard(person, 1);
            Parent root = (Parent) card.getRoot();
            new Scene(root);

            Label meetingLabel = (Label) root.lookup("#meeting");
            assertNotNull(meetingLabel);
            assertTrue(meetingLabel.isManaged());
            assertTrue(meetingLabel.isVisible());
            assertEquals("Meeting: 23/03/2026 14:30", meetingLabel.getText());
        });
    }

    private static void runOnFxThreadAndWait(Runnable action) throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<Throwable> throwable = new AtomicReference<>();

        Platform.runLater(() -> {
            try {
                action.run();
            } catch (Throwable t) {
                throwable.set(t);
            } finally {
                latch.countDown();
            }
        });

        boolean completed = latch.await(10, TimeUnit.SECONDS);
        assertTrue(completed, "Timed out waiting for JavaFX action to complete.");
        if (throwable.get() != null) {
            if (throwable.get() instanceof Exception exception) {
                throw exception;
            }
            throw new RuntimeException(throwable.get());
        }
    }
}
