package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.UnmarkAsFavouriteCommand;
import seedu.address.logic.parser.exceptions.ParseException;



/**
 * Parses input arguments and creates a {@code UnmarkAsFavouriteCommand} object.
 * <p>
 * The input should contain a single index that identifies the person in the
 * currently displayed person list to be unmarked as a favourite.
 */
public class UnmarkAsFavouriteParser implements Parser<UnmarkAsFavouriteCommand> {

    /**
     * Parses the given {@code String} of arguments and returns a {@code UnmarkAsFavouriteCommand}.
     *
     * @param args User input containing the index of the person to be unmarked as favourite.
     * @return A {@code UnmarkAsFavouriteCommand} object for execution.
     * @throws ParseException If the input does not contain a valid non-zero unsigned integer index.
     */
    public UnmarkAsFavouriteCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new UnmarkAsFavouriteCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            UnmarkAsFavouriteCommand.MESSAGE_USAGE), pe);
        }
    }
}
