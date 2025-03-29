package seedu.guestnote.testutil;

import static seedu.guestnote.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.guestnote.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.guestnote.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.guestnote.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.guestnote.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.guestnote.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.guestnote.logic.commands.CommandTestUtil.VALID_REQUEST_FRIEND;
import static seedu.guestnote.logic.commands.CommandTestUtil.VALID_REQUEST_HUSBAND;
import static seedu.guestnote.logic.commands.CommandTestUtil.VALID_ROOMNUMBER_AMY;
import static seedu.guestnote.logic.commands.CommandTestUtil.VALID_ROOMNUMBER_BOB;
import static seedu.guestnote.logic.commands.CommandTestUtil.VALID_STATUS_AMY;
import static seedu.guestnote.logic.commands.CommandTestUtil.VALID_STATUS_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.guestnote.model.GuestBook;
import seedu.guestnote.model.guest.Email;
import seedu.guestnote.model.guest.Guest;
import seedu.guestnote.model.guest.Name;
import seedu.guestnote.model.guest.RoomNumber;
import seedu.guestnote.model.guest.Status;
import seedu.guestnote.model.request.UniqueRequestList;


/**
 * A utility class containing a list of {@code Guest} objects to be used in tests.
 */
public class TypicalGuests {

    public static final Guest ALICE = new GuestBuilder()
            .withName("Alice Pauline")
            .withEmail("alice@example.com")
            .withPhone("94351253")
            .withRoomNumber("12-33")
            .withRequests("friend")
            .withStatus(Status.BOOKING).build();
    public static final Guest BENSON = new GuestBuilder()
            .withName("Benson Meier")
            .withEmail("johnd@example.com")
            .withPhone("98765432")
            .withRoomNumber("23-32")
            .withRequests("owesMoney", "friend")
            .withStatus(Status.BOOKING).build();
    public static final Guest CARL = new GuestBuilder()
            .withName("Carl Kurz")
            .withEmail("heinz@example.com")
            .withPhone("95352563")
            .withRoomNumber("01-57")
            .withStatus(Status.BOOKING)
            .build();
    public static final Guest DANIEL = new GuestBuilder()
            .withName("Daniel Meier")
            .withEmail("cornelia@example.com")
            .withPhone("87652533")
            .withRoomNumber("04-22")
            .withRequests("friend")
            .withStatus(Status.BOOKING)
            .build();
    public static final Guest ELLE = new GuestBuilder()
            .withName("Elle Meyer")
            .withEmail("werner@example.com")
            .withPhone("9482224")
            .withRoomNumber("02-23")
            .withStatus(Status.BOOKING)
            .build();
    public static final Guest FIONA = new GuestBuilder()
            .withName("Fiona Kunz")
            .withEmail("lydia@example.com")
            .withPhone("9482427")
            .withRoomNumber("11-33")
            .withStatus(Status.BOOKING)
            .build();
    public static final Guest GEORGE = new GuestBuilder()
            .withName("George Best")
            .withEmail("anna@example.com")
            .withPhone("9482442")
            .withRoomNumber("03-33")
            .withStatus(Status.BOOKING)
            .build();

    // Manually added
    public static final Guest HOON = new GuestBuilder()
            .withName("Hoon Meier")
            .withEmail("stefan@example.com")
            .withPhone("8482424")
            .withRoomNumber("01-08")
            .build();
    public static final Guest IDA = new GuestBuilder()
            .withName("Ida Mueller")
            .withEmail("hans@example.com")
            .withPhone("8482131")
            .withRoomNumber("01-09")
            .build();

    public static final Guest JAY = new GuestBuilder(
            new Guest(new Name("Jay Chua"),
                null,
                new Email("jayc@example.com"),
                new RoomNumber("05-33"),
                Status.BOOKING, new UniqueRequestList())
                )
            .build();

    // Manually added - Guest's details found in {@code CommandTestUtil}
    public static final Guest AMY = new GuestBuilder()
            .withName(VALID_NAME_AMY)
            .withEmail(VALID_EMAIL_AMY)
            .withPhone(VALID_PHONE_AMY)
            .withRoomNumber(VALID_ROOMNUMBER_AMY)
            .withStatus(VALID_STATUS_AMY)
            .withRequests(VALID_REQUEST_FRIEND)
            .build();
    public static final Guest BOB = new GuestBuilder()
            .withName(VALID_NAME_BOB)
            .withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB)
            .withRoomNumber(VALID_ROOMNUMBER_BOB)
            .withStatus(VALID_STATUS_BOB)
            .withRequests(VALID_REQUEST_HUSBAND, VALID_REQUEST_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalGuests() {} // prevents instantiation

    /**
     * Returns an {@code GuestBook} with all the typical guests.
     */
    public static GuestBook getTypicalAddressBook() {
        GuestBook ab = new GuestBook();
        for (Guest guest : getTypicalGuests()) {
            ab.addGuest(guest);
        }
        return ab;
    }

    public static List<Guest> getTypicalGuests() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE, JAY));
    }
}
