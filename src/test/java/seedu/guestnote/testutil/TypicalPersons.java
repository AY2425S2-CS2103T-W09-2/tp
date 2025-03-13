package seedu.guestnote.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.guestnote.model.GuestBook;
import seedu.guestnote.model.guest.Guest;

import static seedu.guestnote.logic.commands.CommandTestUtil.*;

/**
 * A utility class containing a list of {@code Guest} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Guest ALICE = new PersonBuilder()
            .withGuestId("abcd")
            .withName("Alice Pauline")
            .withEmail("alice@example.com")
            .withPhone("94351253")
            .withRoomNumber("12-33")
            .withTags("friends").build();
    public static final Guest BENSON = new PersonBuilder()
            .withGuestId("shdj")
            .withName("Benson Meier")
            .withEmail("johnd@example.com")
            .withPhone("98765432")
            .withRoomNumber("23-32")
            .withTags("owesMoney", "friends").build();
    public static final Guest CARL = new PersonBuilder()
            .withGuestId("fdsf")
            .withName("Carl Kurz")
            .withEmail("heinz@example.com")
            .withPhone("95352563")
            .withRoomNumber("01-57")
            .build();
    public static final Guest DANIEL = new PersonBuilder()
            .withGuestId("lkjh")
            .withName("Daniel Meier")
            .withEmail("cornelia@example.com")
            .withPhone("87652533")
            .withRoomNumber("04-22")
            .withTags("friends")
            .build();
    public static final Guest ELLE = new PersonBuilder()
            .withGuestId("lksd")
            .withName("Elle Meyer")
            .withEmail("werner@example.com")
            .withPhone("9482224")
            .withRoomNumber("02-23")
            .build();
    public static final Guest FIONA = new PersonBuilder()
            .withGuestId("lkdi")
            .withName("Fiona Kunz")
            .withEmail("lydia@example.com")
            .withPhone("9482427")
            .withRoomNumber("11-33")
            .build();
    public static final Guest GEORGE = new PersonBuilder()
            .withGuestId("pord")
            .withName("George Best")
            .withEmail("anna@example.com")
            .withPhone("9482442")
            .withRoomNumber("03-33")
            .build();

    // Manually added
    public static final Guest HOON = new PersonBuilder()
            .withGuestId("dfcv")
            .withName("Hoon Meier")
            .withEmail("stefan@example.com")
            .withPhone("8482424")
            .withRoomNumber("01-08")
            .build();
    public static final Guest IDA = new PersonBuilder()
            .withGuestId("llsk")
            .withName("Ida Mueller")
            .withEmail("hans@example.com")
            .withPhone("8482131")
            .withRoomNumber("01-09")
            .build();

    // Manually added - Guest's details found in {@code CommandTestUtil}
    public static final Guest AMY = new PersonBuilder()
            .withGuestId(VALID_GUESTID_AMY)
            .withName(VALID_NAME_AMY)
            .withEmail(VALID_EMAIL_AMY)
            .withPhone(VALID_PHONE_AMY)
            .withRoomNumber(VALID_ROOMNUMBER_AMY)
            .withTags(VALID_TAG_FRIEND)
            .build();
    public static final Guest BOB = new PersonBuilder()
            .withGuestId(VALID_GUESTID_BOB)
            .withName(VALID_NAME_BOB)
            .withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB)
            .withRoomNumber(VALID_ROOMNUMBER_BOB)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code GuestBook} with all the typical persons.
     */
    public static GuestBook getTypicalAddressBook() {
        GuestBook ab = new GuestBook();
        for (Guest guest : getTypicalPersons()) {
            ab.addGuest(guest);
        }
        return ab;
    }

    public static List<Guest> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
