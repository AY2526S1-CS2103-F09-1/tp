package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FindByRoleCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.RoleContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindByRoleCommand object
 */
public class FindByRoleCommandParser implements Parser<FindByRoleCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * FindByRoleCommand and returns a FindByRoleCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindByRoleCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            FindByRoleCommand.MESSAGE_USAGE));
        }

        String[] roleKeywords = trimmedArgs.split("\\s+");

        if (roleKeywords.length != 1) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            FindByRoleCommand.MESSAGE_USAGE));
        }

        String role = roleKeywords[0];

        if (!role.equals("Mentor") && !role.equals("Student")) {
            throw new ParseException("Role must be \"Mentor\" or \"Student\".");
        }

        return new FindByRoleCommand(new RoleContainsKeywordsPredicate(Arrays.asList(roleKeywords)));
    }
}
