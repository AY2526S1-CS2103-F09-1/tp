package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
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

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static final Remark EMPTY_REMARK = new Remark("");
    public static Person[] getSamplePersons() {
        Mentor rachelGoh = new Mentor(new Name("Rachel Goh"), new Phone("91234567"),
                new Email("rachel.goh@example.com"),
                new Address("Blk 145 Toa Payoh Lorong 2 #05-18"), new Remark(""),
                getTagSet(), new Centre("Ang Mo Kio Secondary School"));

        Mentor davidNg = new Mentor(new Name("David Ng"), new Phone("96543210"),
                new Email("davidng@example.com"),
                new Address("Blk 32 Marine Parade Road #12-44"), new Remark(""),
                getTagSet("weekends"), new Centre("Bedok Primary School"));

        Student priyaDevi = new Student(new Name("Priya Devi"), new Phone("87654321"),
                new Email("priya.devi@example.com"),
                new Address("Blk 501 Bishan Street 11 #03-25"), new Remark(""),
                getTagSet("Wednesday"), new Centre("Yishun Secondary School"));
        return new Person[] {
            rachelGoh, davidNg, priyaDevi
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
