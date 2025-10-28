package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.CentreContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose centre contains any of
 * the argument keywords. Keyword matching is case-insensitive.
 */
public class FindByCentreCommand extends Command {

    public static final String COMMAND_WORD = "findbycentre";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose centres contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " Bedok Centre";

    private final CentreContainsKeywordsPredicate predicate;

    public FindByCentreCommand(CentreContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW,
                        model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindByCentreCommand)) {
            return false;
        }

        FindByCentreCommand otherFindCommand = (FindByCentreCommand) other;
        return predicate.equals(otherFindCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
