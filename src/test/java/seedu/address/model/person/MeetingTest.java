package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class MeetingTest {

    @Test
    public void constructor_validMeeting_success() {
        LocalDateTime futureDateTime = LocalDateTime.now().plusDays(1);
        Meeting meeting = new Meeting(futureDateTime);
        assertEquals(futureDateTime, meeting.getDateTime());
    }

    @Test
    public void constructor_pastDateTime_throwsIllegalArgumentException() {
        LocalDateTime pastDateTime = LocalDateTime.now().minusDays(1);
        assertThrows(IllegalArgumentException.class, () -> new Meeting(pastDateTime));
    }

    @Test
    public void constructor_nullDateTime_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Meeting(null));
    }

    @Test
    public void isValidMeetingDateTime_validDateTime_returnsTrue() {
        LocalDateTime futureDateTime = LocalDateTime.now().plusDays(1);
        assertTrue(Meeting.isValidMeetingDateTime(futureDateTime));
    }

    @Test
    public void isValidMeetingDateTime_pastDateTime_returnsFalse() {
        LocalDateTime pastDateTime = LocalDateTime.now().minusDays(1);
        assertFalse(Meeting.isValidMeetingDateTime(pastDateTime));
    }

    @Test
    public void isValidMeetingDateTime_null_returnsFalse() {
        assertFalse(Meeting.isValidMeetingDateTime(null));
    }

    @Test
    public void getFormattedDateTime_returnsCorrectFormat() {
        LocalDateTime dateTime = LocalDateTime.of(2030, 3, 15, 16, 30);
        Meeting meeting = new Meeting(dateTime);
        assertEquals("15 Mar 2030 4:30 pm", meeting.getFormattedDateTime());
    }

    @Test
    public void getCommandFormat_returnsCorrectFormat() {
        LocalDateTime dateTime = LocalDateTime.of(2030, 3, 15, 16, 30);
        Meeting meeting = new Meeting(dateTime);
        assertEquals("15/3/2030 16:30", meeting.getCommandFormat());
    }

    @Test
    public void toString_returnsFormattedDateTime() {
        LocalDateTime dateTime = LocalDateTime.of(2030, 3, 15, 16, 30);
        Meeting meeting = new Meeting(dateTime);
        assertEquals(meeting.getFormattedDateTime(), meeting.toString());
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        LocalDateTime dateTime = LocalDateTime.now().plusDays(1);
        Meeting meeting = new Meeting(dateTime);
        assertTrue(meeting.equals(meeting));
    }

    @Test
    public void equals_sameDateTime_returnsTrue() {
        LocalDateTime dateTime = LocalDateTime.now().plusDays(1);
        Meeting meeting1 = new Meeting(dateTime);
        Meeting meeting2 = new Meeting(dateTime);
        assertTrue(meeting1.equals(meeting2));
    }

    @Test
    public void equals_differentDateTime_returnsFalse() {
        LocalDateTime dateTime1 = LocalDateTime.now().plusDays(1);
        LocalDateTime dateTime2 = LocalDateTime.now().plusDays(2);
        Meeting meeting1 = new Meeting(dateTime1);
        Meeting meeting2 = new Meeting(dateTime2);
        assertFalse(meeting1.equals(meeting2));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        LocalDateTime dateTime = LocalDateTime.now().plusDays(1);
        Meeting meeting = new Meeting(dateTime);
        assertFalse(meeting.equals(dateTime));
    }

    @Test
    public void hashCode_sameDateTime_returnsSameHashCode() {
        LocalDateTime dateTime = LocalDateTime.now().plusDays(1);
        Meeting meeting1 = new Meeting(dateTime);
        Meeting meeting2 = new Meeting(dateTime);
        assertEquals(meeting1.hashCode(), meeting2.hashCode());
    }

    @Test
    public void hashCode_differentDateTime_returnsDifferentHashCode() {
        LocalDateTime dateTime1 = LocalDateTime.now().plusDays(1);
        LocalDateTime dateTime2 = LocalDateTime.now().plusDays(2);
        Meeting meeting1 = new Meeting(dateTime1);
        Meeting meeting2 = new Meeting(dateTime2);
        assertFalse(meeting1.hashCode() == meeting2.hashCode());
    }
}
