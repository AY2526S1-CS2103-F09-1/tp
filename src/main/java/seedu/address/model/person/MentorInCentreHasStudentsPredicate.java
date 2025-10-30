package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.Model;


/**
 * A predicate that checks whether a {@link Mentor} has been assigned to at least one {@link Student},
 * or whether a {@link Student} has been assigned a {@link Mentor}.
 * Specifically:
 *   For a {@code Mentor}, the predicate returns {@code true} if the mentor has <em>no</em> assigned students.
 *   For a {@code Student}, the predicate returns {@code true} if the student has <em>no</em> assigned mentor.
 *   For any other type of {@code Person}, the predicate always returns {@code true}.
 */
public class MentorInCentreHasStudentsPredicate extends CentreContainsKeywordsPredicate implements Predicate<Person> {

    /**
     * A list of {@link Mentor}s who currently have at least one assigned {@link Student}.
     */
    private final List<Mentor> assignedMentorsList;

    /**
     * The list of all {@link Mentor}s currently in the model.
     */
    private final List<Mentor> rawMentorList;

    /**
     * The list of all {@link Student}s currently in the model.
     */
    private final List<Student> rawStudentList;

    /**
     * Constructs a {@code MentorInCentreHasStudentsPredicate} using the specified list of {@link Person} objects.
     * This constructor collects all mentors who are currently assigned to students in the provided list.
     * The list of mentors is filtered based on whether they are associated with at least one student.
     *
     * @param model the {@link Model} objects to check for mentor–student relationships
     * @param keywords the keywords used to filter persons by their associated centre
     * @throws NullPointerException if {@code personList} is {@code null}
     */
    public MentorInCentreHasStudentsPredicate(Model model, String keywords) {
        super(keywords);
        requireNonNull(model);
        List<Mentor> rawMentorList = model.getFilteredPersonList().stream()
                .filter(person -> person instanceof Mentor)
                .map(mentorPerson -> (Mentor) mentorPerson)
                .toList();

        this.rawMentorList = rawMentorList;

        List<Student> rawStudentList = model.getFilteredPersonList().stream()
                .filter(person -> person instanceof Student)
                .map(studentPerson -> (Student) studentPerson)
                .toList();

        this.rawStudentList = rawStudentList;

        this.assignedMentorsList = rawStudentList.stream()
                .filter(student -> student.hasMentor())
                .map(assignedStudent -> assignedStudent.getMentor()).toList();
    }

    @Override
    public boolean test(Person person) {
        if (!super.test(person)) {
            return false;
        }

        if (person instanceof Mentor mentorPerson) {
            if (assignedMentorsList.isEmpty()) {
                return rawMentorList.contains(mentorPerson);
            }

            return !assignedMentorsList.contains(person);
        }

        if (person instanceof Student castStudent) {
            if (assignedMentorsList.isEmpty()) {
                return rawStudentList.contains(castStudent);
            }

            Mentor assignedMentor = castStudent.getMentor();
            return assignedMentor == null;
        }

        return true;
    }
}
