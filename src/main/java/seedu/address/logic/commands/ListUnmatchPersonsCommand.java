package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.MentorInCentreHasStudentsPredicate;

/**
 * Lists all mentors and students that have not been matched to a student and mentor respectively.
 */
public class ListUnmatchPersonsCommand extends Command {
    public static final String COMMAND_WORD = "listunmatched";

    public static final String MESSAGE_USAGE = "Finds all unmatched Students and Mentors whose centres contain any of"
            + " the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " Bedok Centre";

    private final String keywords;

    public ListUnmatchPersonsCommand(String keywords) {
        this.keywords = keywords;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(new MentorInCentreHasStudentsPredicate(model, keywords));
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }
}
