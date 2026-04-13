package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.MarkAsFavouriteCommand;

public class MarkAsFavouriteParserTest {

    private final MarkAsFavouriteCommandParser parser = new MarkAsFavouriteCommandParser();

    @Test
    public void parse_validArgument() {
        assertParseSuccess(parser, "1",
                new MarkAsFavouriteCommand(Index.fromOneBased(1)));
    }

    @Test
    public void parse_invalidArgument_throwsParseException() {
        assertParseFailure(parser, "abc",
                MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void parse_nullArgument_throwsParseException() {
        assertParseFailure(parser, "",
                MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void parse_nonPositiveInteger_throwsParseException() {
        assertParseFailure(parser, "-1", MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void parse_nonInteger_throwsParseException() {
        assertParseFailure(parser, "1.5", MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }
}
