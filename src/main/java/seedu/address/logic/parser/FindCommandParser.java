package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.SearchPersonForKeyword;

/**
 * Parses input arguments and creates a new FindCommand object.
 */
public class FindCommandParser implements Parser<FindCommand> {

    private static final String PREFIX_REGEX = "(?=[npaed]/)";

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a {@code FindCommand} object for execution.
     *
     * <p>The input may either be:
     * <ul>
     *     <li>General keywords (e.g. "Alice Bob")</li>
     *     <li>Prefixed keywords (e.g. "n/Alice p/12345678")</li>
     * </ul>
     *
     * <p>If the input is empty or invalid, a {@code ParseException} is thrown.
     *
     * @param args Raw user input arguments.
     * @return A {@code FindCommand} object with the parsed search predicates.
     * @throws ParseException If the input is empty or does not conform to the expected format.
     */
    @Override
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        Map<String, List<String>> fieldMap = parseFindArguments(trimmedArgs);
        assert !fieldMap.isEmpty() : "fieldMap should not be empty after parsing";
        return new FindCommand(new SearchPersonForKeyword(fieldMap));
    }

    /**
     * Parses the input string into a mapping of field prefixes to their corresponding keyword lists.
     *
     * <p>The method supports two formats:
     * <ul>
     *     <li>General search (no prefixes): all words are stored under a general key</li>
     *     <li>Prefixed search: keywords are grouped under specific prefixes such as
     *         {@code n/}, {@code p/}, {@code e/}, {@code a/}, {@code d/}</li>
     * </ul>
     *
     * <p>Each prefix may contain multiple comma-separated values.
     *
     * <p>If any unrecognized prefix or malformed segment is encountered, a {@code ParseException} is thrown.
     *
     * @param input The trimmed user input string.
     * @return A map where each key is a prefix (or general key) and the value is a list of keywords.
     * @throws ParseException If the input contains invalid prefixes or format.
     */
    private Map<String, List<String>> parseFindArguments(String input) throws ParseException {
        Map<String, List<String>> fieldMap = new HashMap<>();

        Pattern anyPrefixPattern = Pattern.compile("(^|\\s)[a-zA-Z]/");
        Pattern invalidPrefixPattern = Pattern.compile("(^|\\s)[^npaed\\s]/");

        boolean hasAnyPrefixPattern = anyPrefixPattern.matcher(input).find();
        boolean hasInvalidPrefix = invalidPrefixPattern.matcher(input).find();

        if (hasInvalidPrefix) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        if (hasAnyPrefixPattern && !input.matches("^[npaed]/.*")) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        if (!hasAnyPrefixPattern) {
            addGeneralValues(fieldMap, input);
            return fieldMap;
        }

        parsePrefixedSegments(fieldMap, input);
        return fieldMap;
    }

    /**
     * Splits a prefixed input string by recognized prefix tokens and populates the field map
     * with the corresponding keyword values.
     *
     * <p>The input is expected to be a valid prefixed search string that has already passed
     * prefix validation. It is split using {@code PREFIX_REGEX} and each resulting segment
     * must begin with one of the recognized prefixes: {@code n/}, {@code p/}, {@code e/},
     * {@code a/}, or {@code d/}.
     *
     * <p>For each segment, the prefix is used as the map key and the remainder of the segment
     * is passed to {@link #addTaggedValues} to be parsed and stored.
     *
     * @param fieldMap The map to populate with prefix-to-keyword-list entries.
     * @param input    The validated prefixed input string to parse.
     * @throws ParseException If a segment does not begin with a recognized prefix.
     */
    private void parsePrefixedSegments(Map<String, List<String>> fieldMap, String input)
            throws ParseException {
        String[] parts = input.split(PREFIX_REGEX);

        for (String part : parts) {
            part = part.trim();
            if (part.isEmpty()) {
                continue;
            }
            assert part.matches("[npaed]/.*") : "Invalid prefix encountered: " + part;

            if (part.startsWith("n/")) {
                addTaggedValues(fieldMap, "n/", part.substring(2));
            } else if (part.startsWith("p/")) {
                addTaggedValues(fieldMap, "p/", part.substring(2));
            } else if (part.startsWith("e/")) {
                addTaggedValues(fieldMap, "e/", part.substring(2));
            } else if (part.startsWith("a/")) {
                addTaggedValues(fieldMap, "a/", part.substring(2));
            } else if (part.startsWith("d/")) {
                addTaggedValues(fieldMap, "d/", part.substring(2));
            } else {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }
        }
    }

    /**
     * Adds comma-separated values associated with a specific prefix into the field map.
     *
     * <p>Each value is trimmed and only non-empty values are added. If the prefix key
     * does not exist in the map, it is initialized.
     *
     * <p>Example: "Alice, Bob" → ["Alice", "Bob"]
     *
     * @param fieldMap The map storing prefixes and their associated keyword lists.
     * @param key The prefix key (e.g. {@code "n/"}).
     * @param raw The raw string containing comma-separated values.
     */
    private void addTaggedValues(Map<String, List<String>> fieldMap, String key, String raw) {
        String[] values = raw.split(",");
        fieldMap.putIfAbsent(key, new ArrayList<>());

        for (String value : values) {
            String cleaned = value.trim();
            if (!cleaned.isEmpty()) {
                fieldMap.get(key).add(cleaned);
            }
        }
    }

    /**
     * Adds whitespace-separated general keywords into the field map under a general key.
     *
     * <p>Each value is trimmed and only non-empty values are added. If the general key
     * does not exist in the map, it is initialized.
     *
     * <p>Example: "Alice Bob" → ["Alice", "Bob"]
     *
     * @param fieldMap The map storing prefixes and their associated keyword lists.
     * @param raw The raw string containing whitespace-separated values.
     */
    private void addGeneralValues(Map<String, List<String>> fieldMap, String raw) {
        String[] values = raw.split(",");

        fieldMap.putIfAbsent(SearchPersonForKeyword.GENERAL_KEY, new ArrayList<>());

        for (String value : values) {
            String cleaned = value.trim();

            if (!cleaned.isEmpty()) {
                fieldMap.get(SearchPersonForKeyword.GENERAL_KEY).add(cleaned);
            }
        }
    }
}
