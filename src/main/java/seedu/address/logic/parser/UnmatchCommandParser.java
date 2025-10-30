package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MENTOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.UnmatchCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code MatchCommand} object
 */
public class UnmatchCommandParser implements Parser<UnmatchCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code RemarkCommand}
     * and returns a {@code MatchCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UnmatchCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_MENTOR, PREFIX_STUDENT);

        if (!arePrefixesPresent(argMultimap, PREFIX_MENTOR, PREFIX_STUDENT)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnmatchCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_MENTOR, PREFIX_STUDENT);
        Index mentor = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_MENTOR).get());
        Index student = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_STUDENT).get());
        return new UnmatchCommand(mentor, student);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
