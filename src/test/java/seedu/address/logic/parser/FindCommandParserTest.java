package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.PersonContainsKeywordsPredicate;

public class FindCommandParserTest {

    private final FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "   ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_untaggedArgs_returnsFindCommand() {
        FindCommand expectedFindCommand = new FindCommand(
                new PersonContainsKeywordsPredicate(
                        Map.of(PersonContainsKeywordsPredicate.GENERAL_KEY,
                                Arrays.asList("Alice", "Bob"))));

        assertParseSuccess(parser, "Alice Bob", expectedFindCommand);
    }

    @Test
    public void parse_untaggedArgsWithExtraWhitespace_returnsFindCommand() {
        FindCommand expectedFindCommand = new FindCommand(
                new PersonContainsKeywordsPredicate(
                        Map.of(PersonContainsKeywordsPredicate.GENERAL_KEY,
                                Arrays.asList("Alice", "Bob", "Charlie"))));

        assertParseSuccess(parser, " \n Alice \t Bob   Charlie \n ", expectedFindCommand);
    }

    @Test
    public void parse_namePrefix_returnsFindCommand() {
        FindCommand expectedFindCommand = new FindCommand(
                new PersonContainsKeywordsPredicate(
                        Map.of("n/", Arrays.asList("john doe", "betty boo"))));

        assertParseSuccess(parser, "n/john doe, betty boo", expectedFindCommand);
    }

    @Test
    public void parse_phonePrefix_returnsFindCommand() {
        FindCommand expectedFindCommand = new FindCommand(
                new PersonContainsKeywordsPredicate(
                        Map.of("p/", Arrays.asList("12345678", "87654321"))));

        assertParseSuccess(parser, "p/12345678, 87654321", expectedFindCommand);
    }

    @Test
    public void parse_emailPrefix_returnsFindCommand() {
        FindCommand expectedFindCommand = new FindCommand(
                new PersonContainsKeywordsPredicate(
                        Map.of("e/", Arrays.asList("alice@example.com", "bob@example.com"))));

        assertParseSuccess(parser, "e/alice@example.com, bob@example.com", expectedFindCommand);
    }

    @Test
    public void parse_addressPrefix_returnsFindCommand() {
        FindCommand expectedFindCommand = new FindCommand(
                new PersonContainsKeywordsPredicate(
                        Map.of("a/", Arrays.asList("123 Main Street", "Jurong West"))));

        assertParseSuccess(parser, "a/123 Main Street, Jurong West", expectedFindCommand);
    }

    @Test
    public void parse_detailsPrefix_returnsFindCommand() {
        FindCommand expectedFindCommand = new FindCommand(
                new PersonContainsKeywordsPredicate(
                        Map.of("d/", Arrays.asList("good friend", "teammate"))));

        assertParseSuccess(parser, "d/good friend, teammate", expectedFindCommand);
    }

    @Test
    public void parse_multipleTaggedPrefixes_returnsFindCommand() {
        FindCommand expectedFindCommand = new FindCommand(
                new PersonContainsKeywordsPredicate(
                        Map.of(
                                "n/", List.of("john doe", "betty boo"),
                                "p/", List.of("12345678"),
                                "e/", List.of("john@example.com"),
                                "a/", List.of("123 Main Street"),
                                "d/", List.of("good friend")
                        )));

        assertParseSuccess(parser,
                "n/john doe, betty boo p/12345678 e/john@example.com a/123 Main Street d/good friend",
                expectedFindCommand);
    }

    @Test
    public void parse_mixedGeneralAndTaggedArgs_throwsParseException() {
        assertParseFailure(parser, "hello world p/1234",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_taggedArgsWithEmptyCommaEntries_skipsEmptyValues() {
        FindCommand expectedFindCommand = new FindCommand(
                new PersonContainsKeywordsPredicate(
                        Map.of("n/", Arrays.asList("Alice", "Bob"))));

        assertParseSuccess(parser, "n/Alice, , Bob,   ", expectedFindCommand);
    }

    @Test
    public void parse_multipleGeneralSegmentsAroundPrefix_throwsParseException() {
        assertParseFailure(parser, "hello world p/1234 bye",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }
}