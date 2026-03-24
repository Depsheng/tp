package seedu.address.logic.parser;

import static seedu.address.commons.util.DateTimeUtil.MESSAGE_DATE_TIME_PAST;
import static seedu.address.commons.util.DateTimeUtil.MESSAGE_INVALID_DATE_TIME_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.DateTimeUtil;
import seedu.address.logic.commands.MeetingCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Meeting;

/**
 * Parses input arguments and creates a {@code MeetingCommand}.
 */
public class MeetingCommandParser implements Parser<MeetingCommand> {

    /**
     * Parses a meeting command in the form {@code INDEX DATE_TIME}.
     * Supports flexible date/time formats including:
     * - 15 Mar 2025 4pm
     * - 15 Mar 2025 4:30pm
     * - 15 Mar 4pm (assumes current year)
     * - 15/3/2025 16:30
     * - Today 4pm
     * - Tomorrow 9am
     * - Monday 2pm (or Mon 2pm)
     *
     * @param args Raw user input following the {@code meeting} command word.
     * @return A {@code MeetingCommand} with the parsed index and meeting.
     * @throws ParseException if the input does not match the expected meeting command format
     *             or if the date/time is in the past.
     */
    @Override
    public MeetingCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        String[] parts = trimmedArgs.split("\\s+", 2);

        if (parts.length < 2) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MeetingCommand.MESSAGE_USAGE));
        }

        try {
            Index index = ParserUtil.parseIndex(parts[0]);

            // Combine all remaining parts as the date/time string
            String dateTimeStr = String.join(" ", java.util.Arrays.copyOfRange(parts, 1, parts.length));

            DateTimeUtil.DateTimeParseResult result = DateTimeUtil.parseDateTime(dateTimeStr);
            Meeting meeting = new Meeting(result.getDateTime());

            return new MeetingCommand(index, meeting);
        } catch (IllegalArgumentException exception) {
            String message = exception.getMessage();
            if (message.equals(MESSAGE_DATE_TIME_PAST)) {
                throw new ParseException(MESSAGE_DATE_TIME_PAST);
            } else if (message.equals(MESSAGE_INVALID_DATE_TIME_FORMAT)) {
                throw new ParseException(MESSAGE_INVALID_DATE_TIME_FORMAT);
            } else {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, MeetingCommand.MESSAGE_USAGE), exception);
            }
        } catch (Exception exception) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MeetingCommand.MESSAGE_USAGE), exception);
        }
    }
}
