package seedu.guestnote.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.guestnote.model.guest.Guest;
import seedu.guestnote.model.guest.Phone;

/**
 * An UI component that displays information of a {@code Guest}.
 */
public class GuestCard extends UiPart<Region> {

    private static final String FXML = "GuestListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on GuestBook level 4</a>
     */

    public final Guest guest;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label email;
    @FXML
    private FlowPane roomNumber;
    @FXML
    private FlowPane status;
    @FXML
    private FlowPane requests;


    /**
     * Creates a {@code GuestCode} with the given {@code Guest} and index to display.
     */
    public GuestCard(Guest guest, int displayedIndex) {
        super(FXML);
        this.guest = guest;
        id.setText(displayedIndex + ". ");
        name.setText(guest.getName().fullName);
        phone.setText(guest.getPhone().map(Phone::toString).orElse("Phone: Not Added"));
        email.setText(guest.getEmail().value);
        roomNumber.getChildren().add(new Label(guest.getRoomNumber().roomNumber));
        Label statusLabel = new Label(guest.getStatus().name());
        statusLabel.getStyleClass().add("status-label");

        switch (guest.getStatus()) {

        case BOOKING:
            statusLabel.getStyleClass().add("status-booking");
            break;
        case CHECKED_IN:
            statusLabel.getStyleClass().add("status-checkedin");
            break;
        case CHECKED_OUT:
            statusLabel.getStyleClass().add("status-checkedout");
            break;

        default:
            break;
        }


        status.getChildren().add(statusLabel);

        final int[] counter = {1};
        guest.getRequests()
                .forEach(request -> {
                    Label requestLabel = new Label(counter[0] + ". " + request.requestName);
                    requests.getChildren().add(requestLabel);
                    counter[0]++;
                });
    }
}
