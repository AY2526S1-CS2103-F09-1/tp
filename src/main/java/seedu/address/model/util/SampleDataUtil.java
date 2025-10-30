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
        Mentor alice = new Mentor(new Name("Alice Pauline"), new Phone("94351253"), new Email("alice@example.com"),
                new Address("123, Jurong West Ave 6, #08-111"), new Remark("She likes aardvarks."),
                getTagSet("friends"), new Centre("Nan Hua High School"));

        Student benson = new Student(new Name("Benson Meier"), new Phone("98765432"), new Email("bensonM@example.com"),
                new Address("311, Clementi Ave 2, #02-25"), new Remark("He can't take beer!"),
                getTagSet("colleagues", "friends"), new Centre("Nan Hua High School"));
        benson.setMentor(alice);

        Mentor carl = new Mentor(new Name("Carl Kurz"), new Phone("95352563"), new Email("heinz@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), EMPTY_REMARK,
                getTagSet("neighbours"), new Centre("New Town Primary School"));

        return new Person[] {
            alice,
            benson,
            carl
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
