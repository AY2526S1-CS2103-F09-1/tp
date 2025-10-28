package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ListUnmatchPersonsCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ListUnmatchPersonsCommand object.
 */
public class ListUnmatchPersonsCommandParser implements Parser<ListUnmatchPersonsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * ListUnmatchPersonsCommand and returns a ListUnmatchPersonsCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public ListUnmatchPersonsCommand parse(String string) throws ParseException {
        String stripped = string.strip();
        if (stripped.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            ListUnmatchPersonsCommand.MESSAGE_USAGE));
        }

        return new ListUnmatchPersonsCommand(stripped);
    }
}
