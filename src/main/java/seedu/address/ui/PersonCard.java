package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Mentor;
import seedu.address.model.person.Person;
import seedu.address.model.person.Student;

/**
 * A UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    private static final String STUDENT_AVATAR_PATH = "/images/student_avatar.png";
    private static final String MENTOR_AVATAR_PATH = "/images/mentor_avatar.png";
    private static final String DEFAULT_AVATAR_PATH = "/images/default_profile.png";


    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private FlowPane role;
    @FXML
    private FlowPane tags;
    @FXML
    private Label centre;
    @FXML
    private Label remark;
    @FXML
    private Label mentor;
    @FXML
    private ImageView profileImage;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        address.setText(person.getAddress().value);
        email.setText(person.getEmail().value);
        Label roleLabel = new Label(person.getRole());
        roleLabel.getStyleClass().add("role-label");
        role.getChildren().add(roleLabel);
        if (person instanceof Student) {
            centre.setText("Centre: " + ((Student) person).getCentre().toString());
            setAvatar(STUDENT_AVATAR_PATH);
        } else if (person instanceof Mentor) {
            centre.setText("Centre: " + ((Mentor) person).getCentre().toString());
            setAvatar(MENTOR_AVATAR_PATH);
        } else {
            centre.setManaged(false);
            centre.setVisible(false);
            setAvatar(DEFAULT_AVATAR_PATH);
        }
        String mentorToMatch = "";
        if (person instanceof Student student) {
            mentorToMatch = "Mentor: ";
            if (student.getMentor() != null) {
                mentorToMatch += student.getMentor().getName().fullName;
            }
        }
        mentor.setText(mentorToMatch);
        remark.setText("Remark: " + person.getRemark().value);

        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    /**
     * Loads avatar image into the profile image view.
     */
    private void setAvatar(String path) {
        try {
            Image avatar = new Image(getClass().getResourceAsStream(path),
                    128, 128, true, true);
            profileImage.setImage(avatar);
            profileImage.setFitWidth(128);
            profileImage.setFitHeight(128);
            profileImage.setPreserveRatio(true);
            profileImage.setSmooth(true);
        } catch (Exception e) {
            profileImage.setImage(new Image(getClass().getResourceAsStream(DEFAULT_AVATAR_PATH),
                    128, 128, true, true));
        }
    }
}
