package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's centre in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidCentre(String)}.
 */
public class Centre {

    public static final String MESSAGE_CONSTRAINTS =
            "Centres should only contain alphanumeric characters and spaces, and it should not be blank.";

    /*
     * The first character of the centre must not be a whitespace,
     * otherwise ' ' (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    /** Default text when no centre is allocated. */
    public static final String DEFAULT_VALUE = "CENTRE UNASSIGNED";

    /** Default instance used when a person has no assigned centre. */
    public static final Centre DEFAULT_CENTRE = new Centre(DEFAULT_VALUE);

    public final String value;

    /**
     * Constructs a {@code Centre}.
     *
     * @param centre A valid centre name.
     */
    public Centre(String centre) {
        requireNonNull(centre);
        checkArgument(isValidCentre(centre), MESSAGE_CONSTRAINTS);
        value = centre;
    }

    /**
     * Returns true if a given string is a valid centre name.
     */
    public static boolean isValidCentre(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Centre)) {
            return false;
        }

        Centre otherCentre = (Centre) other;
        return value.equals(otherCentre.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
