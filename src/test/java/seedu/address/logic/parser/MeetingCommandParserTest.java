package seedu.address.logic.parser;

import static seedu.address.commons.util.DateTimeUtil.MESSAGE_DATE_TIME_PAST;
import static seedu.address.commons.util.DateTimeUtil.MESSAGE_INVALID_DATE_TIME_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.MeetingCommand;
import seedu.address.model.person.Meeting;

public class MeetingCommandParserTest {

    private final MeetingCommandParser parser = new MeetingCommandParser();

    @Test
    public void parse_validArguments() {
        // Test traditional format
        assertParseSuccess(parser, "1 25/03/2030 14:30",
                new MeetingCommand(Index.fromOneBased(1),
                        new Meeting(LocalDate.of(2030, 3, 25).atTime(14, 30))));

        // Test new flexible formats
        assertParseSuccess(parser, "1 25 Mar 2030 4pm",
                new MeetingCommand(Index.fromOneBased(1),
                        new Meeting(LocalDate.of(2030, 3, 25).atTime(16, 0))));

        assertParseSuccess(parser, "1 25 Mar 2030 4:30pm",
                new MeetingCommand(Index.fromOneBased(1),
                        new Meeting(LocalDate.of(2030, 3, 25).atTime(16, 30))));

        assertParseSuccess(parser, "1 25 Mar 2030 4.30pm",
                new MeetingCommand(Index.fromOneBased(1),
                        new Meeting(LocalDate.of(2030, 3, 25).atTime(16, 30))));

        assertParseSuccess(parser, "1 25 Mar 2030 1600",
                new MeetingCommand(Index.fromOneBased(1),
                        new Meeting(LocalDate.of(2030, 3, 25).atTime(16, 0))));

        assertParseSuccess(parser, "1 25 March 2030 4pm",
                new MeetingCommand(Index.fromOneBased(1),
                        new Meeting(LocalDate.of(2030, 3, 25).atTime(16, 0))));

        assertParseSuccess(parser, "1 25-3-2030 4:30pm",
                new MeetingCommand(Index.fromOneBased(1),
                        new Meeting(LocalDate.of(2030, 3, 25).atTime(16, 30))));

        assertParseSuccess(parser, "1 25.3.2030 1630",
                new MeetingCommand(Index.fromOneBased(1),
                        new Meeting(LocalDate.of(2030, 3, 25).atTime(16, 30))));
    }

    @Test
    public void parse_currentYearFormats_success() {
        int currentYear = LocalDate.now().getYear();

        assertParseSuccess(parser, "1 31 Dec 11:59pm",
                new MeetingCommand(Index.fromOneBased(1),
                        new Meeting(LocalDate.of(currentYear, 12, 31).atTime(23, 59))));

        assertParseSuccess(parser, "1 31 December 11:59pm",
                new MeetingCommand(Index.fromOneBased(1),
                        new Meeting(LocalDate.of(currentYear, 12, 31).atTime(23, 59))));
    }

    @Test
    public void parse_relativeDates_success() {
        // Test relative dates
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);

        assertParseSuccess(parser, "1 Today 2359",
                new MeetingCommand(Index.fromOneBased(1), new Meeting(today.atTime(23, 59))));

        assertParseSuccess(parser, "1 Tomorrow 2359",
                new MeetingCommand(Index.fromOneBased(1), new Meeting(tomorrow.atTime(23, 59))));

        // Test relative dates without time (should default to 12:00 AM, but use 2359 for testing)
        assertParseSuccess(parser, "1 Today 2359",
                new MeetingCommand(Index.fromOneBased(1), new Meeting(today.atTime(23, 59))));

        assertParseSuccess(parser, "1 Tomorrow 2359",
                new MeetingCommand(Index.fromOneBased(1), new Meeting(tomorrow.atTime(23, 59))));
    }

    @Test
    public void parse_weekdayFormats_success() {
        // Test simplified weekday formats
        LocalDate targetDate = LocalDate.now();
        int currentDayOfWeek = targetDate.getDayOfWeek().getValue(); // Monday=1, Sunday=7
        int daysToAdd = (1 - currentDayOfWeek + 7) % 7;
        if (daysToAdd == 0) {
            daysToAdd = 7; // If today is Monday, go to next Monday
        }
        targetDate = targetDate.plusDays(daysToAdd);

        assertParseSuccess(parser, "1 Monday 2pm",
                new MeetingCommand(Index.fromOneBased(1), new Meeting(targetDate.atTime(14, 0))));

        assertParseSuccess(parser, "1 Mon 2pm",
                new MeetingCommand(Index.fromOneBased(1), new Meeting(targetDate.atTime(14, 0))));
    }

    @Test
    public void parse_invalidDate_throwsParseException() {
        assertParseFailure(parser, "1 2026-03-23 14:30", MESSAGE_INVALID_DATE_TIME_FORMAT);
        assertParseFailure(parser, "1 32 Mar 2026 4pm", MESSAGE_INVALID_DATE_TIME_FORMAT);
        assertParseFailure(parser, "1 15 Foo 2026 4pm", MESSAGE_INVALID_DATE_TIME_FORMAT);
        assertParseFailure(parser, "1 15 Mar 2026 25:00", MESSAGE_INVALID_DATE_TIME_FORMAT);
        assertParseFailure(parser, "1 invalid date", MESSAGE_INVALID_DATE_TIME_FORMAT);
    }

    @Test
    public void parse_pastDate_throwsParseException() {
        LocalDate pastDate = LocalDate.now().minusDays(1);
        String pastDateStr = pastDate.getDayOfMonth() + " "
                           + pastDate.getMonth().toString().substring(0, 3) + " "
                           + pastDate.getYear() + " 4pm";

        assertParseFailure(parser, "1 " + pastDateStr, MESSAGE_DATE_TIME_PAST);
    }

    @Test
    public void parse_missingParts_throwsParseException() {
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, MeetingCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "1", expectedMessage);
        assertParseFailure(parser, "", expectedMessage);
    }
}
