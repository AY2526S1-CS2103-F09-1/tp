package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MENTOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Mentor;
import seedu.address.model.person.Person;
import seedu.address.model.person.Student;

/**
 * Matches a mentor and student.
 */
public class UnmatchCommand extends Command {
    public static final String COMMAND_WORD = "unmatch";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Unmatches a mentor to a student\n"
            + "Parameters: "
            + PREFIX_MENTOR + "MENTOR INDEX "
            + PREFIX_STUDENT + "STUDENT INDEX\n"
            + "Example : unmatch m/1 s/2";


    private final Index mentor;
    private final Index student;

    /**
     * @param mentor index of the tutor
     * @param student index of the student
     */
    public UnmatchCommand(Index mentor, Index student) {
        requireAllNonNull(mentor, student);

        this.mentor = mentor;
        this.student = student;
    }
    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (mentor.getZeroBased() >= lastShownList.size() || student.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person mentorToMatch = lastShownList.get(mentor.getZeroBased());
        Person studentToMatch = lastShownList.get(student.getZeroBased());

        if (mentorToMatch instanceof Mentor mentorObj && studentToMatch instanceof Student studentObj) {
            if (studentObj.getMentor() == null || !studentObj.getMentor().equals(mentorObj)) {
                throw new CommandException(Messages.MESSAGE_UNMATCH_BETWEEN_UNMATCHED_PERSONS);
            } else {
                studentObj.removeMentor();
                model.setPerson(studentToMatch, studentObj);
                model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            }
        } else {
            throw new CommandException(Messages.MESSAGE_INVALID_ROLES_UNMATCHED);
        }

        return new CommandResult("Mentor: " + mentorToMatch.getName() + "\nand \nStudent: "
                + studentToMatch.getName() + "\nunmatched");
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UnmatchCommand)) {
            return false;
        }

        UnmatchCommand e = (UnmatchCommand) other;
        return mentor.equals(e.mentor)
                && student.equals(e.student);
    }
}
