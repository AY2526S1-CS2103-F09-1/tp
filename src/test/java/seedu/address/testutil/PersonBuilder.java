package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.Centre;
import seedu.address.model.person.Email;
import seedu.address.model.person.Mentor;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.person.Student;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_REMARK = "";
    public static final String DEFAULT_ROLE = "Person";
    public static final String STUDENT_ROLE = "Student";
    public static final String MENTOR_ROLE = "Mentor";
    public static final String DEFAULT_CENTRE = "CENTRE UNASSIGNED";
    public static final Mentor DEFAULT_ASSIGNED_MENTOR = new Mentor(new Name("Default Mentor"),
            new Phone(DEFAULT_PHONE), new Email(DEFAULT_EMAIL), new Address(DEFAULT_ADDRESS),
            new Remark(DEFAULT_REMARK), new HashSet<>(), new Centre(DEFAULT_CENTRE));


    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Remark remark;
    private Set<Tag> tags;
    private String role;
    private Mentor mentor;
    private Centre centre;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        remark = new Remark(DEFAULT_REMARK);
        tags = new HashSet<>();
        role = DEFAULT_ROLE;
        mentor = DEFAULT_ASSIGNED_MENTOR;
        centre = new Centre(DEFAULT_CENTRE);
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        address = personToCopy.getAddress();
        remark = personToCopy.getRemark();
        role = personToCopy.getRole();
        tags = new HashSet<>(personToCopy.getTags());

        if (personToCopy instanceof Student s) {
            role = STUDENT_ROLE;
            centre = s.getCentre();
            mentor = s.getMentor();
        } else if (personToCopy instanceof Mentor m) {
            role = MENTOR_ROLE;
            centre = m.getCentre();
            mentor = null;
        } else {
            role = DEFAULT_ROLE;
            centre = null;
            mentor = null;
        }
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy} as a Student.
     */
    public PersonBuilder(Student personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        address = personToCopy.getAddress();
        remark = personToCopy.getRemark();
        tags = new HashSet<>(personToCopy.getTags());
        role = personToCopy.getRole();
        mentor = personToCopy.getMentor();
        centre = personToCopy.getCentre();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy} as a Mentor.
     */
    public PersonBuilder(Mentor personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        address = personToCopy.getAddress();
        remark = personToCopy.getRemark();
        tags = new HashSet<>(personToCopy.getTags());
        role = personToCopy.getRole();
        centre = personToCopy.getCentre();
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Remark} of the {@code Person} that we are building.
     */
    public PersonBuilder withRemark(String remark) {
        this.remark = new Remark(remark);
        return this;
    }

    /**
     * Sets the {@code Remark} of the {@code Person} that we are building.
     */
    public PersonBuilder withAssignedMentor(Mentor mentor) {
        this.mentor = mentor;
        return this;
    }

    /**
     * Sets the {@code Centre} of the {@code Person} that we are building.
     */
    public PersonBuilder withCentre(String centre) {
        this.centre = new Centre(centre);
        return this;
    }

    /**
     * Returns the {@code Person} object that we are building.
     */
    public Person build() {
        if (STUDENT_ROLE.equals(role)) {
            Centre c = (centre != null) ? centre : new Centre(DEFAULT_CENTRE);
            Student createdStudent = new Student(name, phone, email, address, remark, tags, c);
            if (mentor != null && mentor.getCentre() != null && mentor.getCentre().equals(c)) {
                createdStudent.setMentor(mentor);
            }
            return createdStudent;
        } else if (MENTOR_ROLE.equals(role)) {
            Centre c = (centre != null) ? centre : new Centre(DEFAULT_CENTRE);
            return new Mentor(name, phone, email, address, remark, tags, c);
        } else {
            // PERSON: must not have centre/mentor
            return new Person(name, phone, email, address, remark, tags);
        }
    }

    /**
     * Returns the {@code Student} object that we are building.
     */
    public Student buildStudent() {
        Student createdStudent = new Student(name, phone, email, address, remark, tags, centre);
        createdStudent.setMentor(mentor);
        return createdStudent;
    }

    /**
     * Returns the {@code Mentor} object that we are building.
     */
    public Mentor buildMentor() {
        return new Mentor(name, phone, email, address, remark, tags, centre);
    }

}
