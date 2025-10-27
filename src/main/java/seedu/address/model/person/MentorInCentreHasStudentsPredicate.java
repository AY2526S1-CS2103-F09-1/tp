package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.function.Predicate;


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
     * A list of {@link Mentor}s who have at least one assigned {@link Student}.
     */
    private final List<Mentor> assignedMentorsList;

    /**
     * Constructs a {@code MentorInCentreHasStudentsPredicate} using the specified list of {@link Person} objects.
     * This constructor collects all mentors who are currently assigned to students in the provided list.
     * The list of mentors is filtered based on whether they are associated with at least one student.
     *
     * @param personList the list of {@link Person} objects to check for mentor–student relationships
     * @param keywords the keywords used to filter persons by their associated centre
     * @throws NullPointerException if {@code personList} is {@code null}
     */
    public MentorInCentreHasStudentsPredicate(List<Person> personList, String keywords) {
        super(keywords);
        requireNonNull(personList);
        this.assignedMentorsList = personList.stream().filter(person -> person instanceof Student)
                .filter(student -> ((Student) student).hasMentor())
                .map(assignedStudent -> ((Student) assignedStudent).getMentor()).toList();
    }

    @Override
    public boolean test(Person person) {
        if (!super.test(person)) {
            return false;
        }

        if (person instanceof Mentor) {
            return !assignedMentorsList.contains(person);
        }

        if (person instanceof Student) {
            Student castStudent = (Student) person;
            Mentor assignedMentor = castStudent.getMentor();
            return assignedMentor == null;
        }

        return true;
    }
}
