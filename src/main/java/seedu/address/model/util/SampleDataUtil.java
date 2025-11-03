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

        Mentor marcusTan = new Mentor(new Name("Marcus Tan"), new Phone("93456789"),
                new Email("marcus.tan@example.com"),
                new Address("Blk 88 Hougang Avenue 10 #07-12"), new Remark("Prefers online sessions."),
                getTagSet("Tuesday", "Thursday"), new Centre("Tampines Secondary School"));

        Student sitiAminah = new Student(new Name("Siti Aminah"), new Phone("82345678"),
                new Email("siti.aminah@example.com"),
                new Address("Blk 210 Woodlands Street 13 #15-06"), new Remark(""),
                getTagSet(), new Centre("Woodlands Primary School"));

        Mentor kevinWong = new Mentor(new Name("Kevin Wong"), new Phone("97123456"),
                new Email("kevinwong@example.com"),
                new Address("Blk 456 Pasir Ris Drive 6 #10-88"), new Remark(""),
                getTagSet("Monday", "Friday"), new Centre("Pasir Ris Secondary School"));

        Student amandaLau = new Student(new Name("Amanda Lau"), new Phone("81234567"),
                new Email("amanda.lau@example.com"),
                new Address("Blk 73 Serangoon Avenue 3 #08-32"), new Remark(""),
                getTagSet("Saturday"), new Centre("Paya Lebar Primary School"));

        Mentor rajKumar = new Mentor(new Name("Raj Kumar"), new Phone("90876543"),
                new Email("raj.kumar@example.com"),
                new Address("Blk 18 Boon Lay Way #04-15"), new Remark("Available after 6pm."),
                getTagSet(), new Centre("Jurong West Secondary School"));

        Student cherylOng = new Student(new Name("Cheryl Ong"), new Phone("98887777"),
                new Email("cheryl.ong@example.com"),
                new Address("Blk 623 Bukit Batok Central #11-20"), new Remark(""),
                getTagSet("Thursday"), new Centre("Bukit Batok Secondary School"));

        Mentor aaronLee = new Mentor(new Name("Aaron Lee"), new Phone("85556666"),
                new Email("aaron.lee@example.com"),
                new Address("Blk 320 Sembawang Drive #06-09"), new Remark(""),
                getTagSet("Monday"), new Centre("Sembawang Primary School"));
        return new Person[] {
            rachelGoh, davidNg, priyaDevi, marcusTan, sitiAminah, kevinWong, amandaLau, rajKumar, cherylOng, aaronLee
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
