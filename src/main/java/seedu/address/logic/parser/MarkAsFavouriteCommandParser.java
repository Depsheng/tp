package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.MarkAsFavouriteCommand;
import seedu.address.logic.parser.exceptions.ParseException;



/**
 * Parses input arguments and creates a {@code MarkAsFavouriteCommand} object.
 * <p>
 * The expected input is a single index representing the person in the
 * currently displayed list to be marked as favourite.
 */
public class MarkAsFavouriteCommandParser implements Parser<MarkAsFavouriteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * {@code MarkAsFavouriteCommand} and returns a {@code MarkAsFavouriteCommand} object.
     *
     * @param args User input arguments containing the index.
     * @return A {@code MarkAsFavouriteCommand} for execution.
     * @throws ParseException If the input does not contain a valid index.
     */
    public MarkAsFavouriteCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new MarkAsFavouriteCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, pe);
        }
    }
}
