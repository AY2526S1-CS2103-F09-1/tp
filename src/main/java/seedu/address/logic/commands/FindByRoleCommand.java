package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.RoleContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose role matches the specified keyword.
 * Keyword matching is case-insensitive.
 */
public class FindByRoleCommand extends Command {

    public static final String COMMAND_WORD = "findbyrole";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons with the specified role "
            + "(case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: r/ROLE (must be Person, Mentor, or Student)\n"
            + "Example: " + COMMAND_WORD + " mentor";

    private final RoleContainsKeywordsPredicate predicate;

    public FindByRoleCommand(RoleContainsKeywordsPredicate predicate) {
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
        if (!(other instanceof FindByRoleCommand)) {
            return false;
        }

        FindByRoleCommand otherFindCommand = (FindByRoleCommand) other;
        return predicate.equals(otherFindCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
