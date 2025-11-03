package seedu.address.model.person;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Centre} matches any of the keywords given.
 */
public class CentreContainsKeywordsPredicate implements Predicate<Person> {
    private static final String UNASSIGNED = "centre unassigned";
    private final String keywords;

    public CentreContainsKeywordsPredicate(String keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        if (person instanceof Student student) {
            if (student.getCentre().value.toLowerCase().equals(UNASSIGNED)) {
                return false;
            }
            return student.getCentre().value.toLowerCase().contains(keywords.toLowerCase());
        } else if (person instanceof Mentor mentor) {
            if (mentor.getCentre().value.toLowerCase().equals(UNASSIGNED)) {
                return false;
            }
            return mentor.getCentre().value.toLowerCase().contains(keywords.toLowerCase());
        } else {
            return false;
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CentreContainsKeywordsPredicate)) {
            return false;
        }

        CentreContainsKeywordsPredicate otherCentreContainsKeywordsPredicate = (CentreContainsKeywordsPredicate) other;
        return keywords.equals(otherCentreContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
