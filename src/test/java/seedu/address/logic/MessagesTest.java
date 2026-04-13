package seedu.address.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class MessagesTest {

    @Test
    public void format_withoutMeeting_doesNotIncludeMeetingDetails() {
        Person person = new PersonBuilder()
                .withName("Amy Bee")
                .withPhone("85355255")
                .withEmail("amy@gmail.com")
                .withAddress("123, Jurong West Ave 6, #08-111")
                .withDetails("No details")
                .withTags()
                .withoutMeeting()
                .build();

        String formatted = Messages.format(person);
        assertEquals("Amy Bee; Phone: 85355255; Email: amy@gmail.com; Address: 123, Jurong West Ave 6, #08-111"
                + "; Details: No details; Tags: No tags", formatted);
        assertFalse(formatted.contains("; Meeting: "));
    }

    @Test
    public void format_withMeeting_includesMeetingDetails() {
        Person person = new PersonBuilder()
                .withName("Amy Bee")
                .withPhone("85355255")
                .withEmail("amy@gmail.com")
                .withAddress("123, Jurong West Ave 6, #08-111")
                .withDetails("No details")
                .withTags()
                .withMeeting("23/03/2030", "14:30")
                .build();

        String formatted = Messages.format(person);
        assertEquals("Amy Bee; Phone: 85355255; Email: amy@gmail.com; Address: 123, Jurong West Ave 6, #08-111"
                + "; Details: No details; Tags: No tags; Meeting: 23 Mar 2030 2:30 pm", formatted);
        assertTrue(formatted.contains("; Meeting: 23 Mar 2030 2:30 pm"));
    }
}
