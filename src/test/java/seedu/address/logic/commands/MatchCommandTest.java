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
 * {@code MatchCommand}.
 */
public class MatchCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexValidRoles_success() {
        Mentor mentor = new PersonBuilder().buildMentor();
        Student student = new PersonBuilder().withName("Alice Student").buildStudent();
        model.addPerson(mentor);
        model.addPerson(student);
        Index mentorIndex = Index.fromOneBased(model.getFilteredPersonList().size() - 1);
        Index studentIndex = Index.fromOneBased(model.getFilteredPersonList().size());

        MatchCommand matchCommand = new MatchCommand(mentorIndex, studentIndex);
        String expectedMessage = "Mentor: " + mentor.getName() + "\nmatched to\nStudent: " + student.getName();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Student expectedStudent = student;
        expectedStudent.setMentor(mentor);
        expectedModel.setPerson(student, expectedStudent);

        assertCommandSuccess(matchCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        MatchCommand matchCommand = new MatchCommand(outOfBoundIndex, INDEX_FIRST_PERSON);

        assertCommandFailure(matchCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidRolesMatch_throwsCommandException() {
        Student student1 = new PersonBuilder().buildStudent();
        Student student2 = new PersonBuilder().withName("Bob Student").buildStudent();
        model.addPerson(student1);
        model.addPerson(student2);
        Index firstIndex = Index.fromOneBased(model.getFilteredPersonList().size() - 1);
        Index secondIndex = Index.fromOneBased(model.getFilteredPersonList().size());
        MatchCommand matchCommand = new MatchCommand(firstIndex, secondIndex);

        assertCommandFailure(matchCommand, model, Messages.MESSAGE_INVALID_ROLES_MATCHED);
    }

    @Test
    public void execute_matchingSameCentre_success() {
        String testCentre = "Test Centre A";
        Mentor mentor = new PersonBuilder()
                .withName("Bob Mentor")
                .withCentre(testCentre)
                .buildMentor();
        Student student = new PersonBuilder()
                .withName("Alice Student")
                .withCentre(testCentre)
                .buildStudent();
        model.addPerson(mentor);
        model.addPerson(student);
        Index mentorIndex = Index.fromOneBased(model.getFilteredPersonList().size() - 1);
        Index studentIndex = Index.fromOneBased(model.getFilteredPersonList().size());
        MatchCommand matchCommand = new MatchCommand(mentorIndex, studentIndex);
        String expectedMessage = "Mentor: " + mentor.getName() + "\nmatched to\nStudent: " + student.getName();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Student expectedStudent = student;
        expectedStudent.setMentor(mentor);
        expectedModel.setPerson(student, expectedStudent);

        assertCommandSuccess(matchCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_matchingDifferentCentre_throwsCommandException() {
        Mentor mentor = new PersonBuilder()
                .withName("Bob Mentor")
                .withCentre("Centre A")
                .buildMentor();
        Student student = new PersonBuilder()
                .withName("Alice Student")
                .withCentre("Centre B")
                .buildStudent();
        model.addPerson(mentor);
        model.addPerson(student);
        Index mentorIndex = Index.fromOneBased(model.getFilteredPersonList().size() - 1);
        Index studentIndex = Index.fromOneBased(model.getFilteredPersonList().size());
        MatchCommand matchCommand = new MatchCommand(mentorIndex, studentIndex);

        assertCommandFailure(matchCommand, model, Messages.MESSAGE_MATCH_BETWEEN_DIFFERENT_CENTRES);
    }

    @Test
    public void equals() {
        MatchCommand matchFirstCommand = new MatchCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);
        MatchCommand matchSecondCommand = new MatchCommand(INDEX_SECOND_PERSON, INDEX_FIRST_PERSON);

        // same object -> returns true
        assertTrue(matchFirstCommand.equals(matchFirstCommand));

        // same values -> returns true
        MatchCommand matchFirstCommandCopy = new MatchCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);
        assertTrue(matchFirstCommand.equals(matchFirstCommandCopy));

        // different types -> returns false
        assertFalse(matchFirstCommand.equals(1));

        // null -> returns false
        assertFalse(matchFirstCommand.equals(null));

        // different mentor/student order -> returns false
        assertFalse(matchFirstCommand.equals(matchSecondCommand));
    }
}
