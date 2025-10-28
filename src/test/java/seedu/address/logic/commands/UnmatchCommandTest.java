package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Mentor;
import seedu.address.model.person.Student;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code UnmatchCommand}.
 */
public class UnmatchCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexMatchedPair_success() {
        Mentor mentor = new PersonBuilder().buildMentor();
        Student student = new PersonBuilder().withName("Alice Student").buildStudent();
        student.setMentor(mentor); // Match them first
        model.addPerson(mentor);
        model.addPerson(student);
        Index mentorIndex = Index.fromOneBased(model.getFilteredPersonList().size() - 1);
        Index studentIndex = Index.fromOneBased(model.getFilteredPersonList().size());
        UnmatchCommand unmatchCommand = new UnmatchCommand(mentorIndex, studentIndex);
        String expectedMessage = "Mentor: " + mentor.getName() + "\n and \nStudent: "
                + student.getName() + "\nunmatched";
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Student expectedStudent = student;
        expectedModel.setPerson(student, expectedStudent);

        assertCommandSuccess(unmatchCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        UnmatchCommand unmatchCommand = new UnmatchCommand(outOfBoundIndex, INDEX_FIRST_PERSON);

        assertCommandFailure(unmatchCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidRolesUnmatch_throwsCommandException() {
        Student student1 = new PersonBuilder().buildStudent();
        Student student2 = new PersonBuilder().withName("Bob Student").buildStudent();
        model.addPerson(student1);
        model.addPerson(student2);
        Index firstIndex = Index.fromOneBased(model.getFilteredPersonList().size() - 1);
        Index secondIndex = Index.fromOneBased(model.getFilteredPersonList().size());
        UnmatchCommand unmatchCommand = new UnmatchCommand(firstIndex, secondIndex);

        assertCommandFailure(unmatchCommand, model, Messages.MESSAGE_INVALID_ROLES_UNMATCHED);
    }

    @Test
    public void execute_unmatchUnmatchedPair_throwsCommandException() {
        Mentor mentor = new PersonBuilder().buildMentor();
        Student student = new PersonBuilder().withName("Alice Student").buildStudent();
        // Not matching them first
        model.addPerson(mentor);
        model.addPerson(student);
        Index mentorIndex = Index.fromOneBased(model.getFilteredPersonList().size() - 1);
        Index studentIndex = Index.fromOneBased(model.getFilteredPersonList().size());
        UnmatchCommand unmatchCommand = new UnmatchCommand(mentorIndex, studentIndex);

        assertCommandFailure(unmatchCommand, model, Messages.MESSAGE_UNMATCH_BETWEEN_UNMATCHED_PERSONS);
    }

    @Test
    public void equals() {
        UnmatchCommand unmatchFirstCommand = new UnmatchCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);
        UnmatchCommand unmatchSecondCommand = new UnmatchCommand(INDEX_SECOND_PERSON, INDEX_FIRST_PERSON);

        // same object -> returns true
        assertTrue(unmatchFirstCommand.equals(unmatchFirstCommand));

        // same values -> returns true
        UnmatchCommand unmatchFirstCommandCopy = new UnmatchCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);
        assertTrue(unmatchFirstCommand.equals(unmatchFirstCommandCopy));

        // different types -> returns false
        assertFalse(unmatchFirstCommand.equals(1));

        // null -> returns false
        assertFalse(unmatchFirstCommand.equals(null));

        // different mentor/student order -> returns false
        assertFalse(unmatchFirstCommand.equals(unmatchSecondCommand));
    }
}
