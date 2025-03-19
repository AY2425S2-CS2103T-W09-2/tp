package seedu.guestnote.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.guestnote.logic.commands.CommandTestUtil.VALID_REQUEST_HUSBAND;
import static seedu.guestnote.testutil.Assert.assertThrows;
import static seedu.guestnote.testutil.TypicalPersons.ALICE;
import static seedu.guestnote.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.guestnote.model.guest.Guest;
import seedu.guestnote.model.guest.exceptions.DuplicateGuestException;
import seedu.guestnote.testutil.PersonBuilder;

public class GuestBookTest {

    private final GuestBook guestBook = new GuestBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), guestBook.getGuestList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> guestBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        GuestBook newData = getTypicalAddressBook();
        guestBook.resetData(newData);
        assertEquals(newData, guestBook);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two guests with the same identity fields
        Guest editedAlice = new PersonBuilder(ALICE).withRequests(VALID_REQUEST_HUSBAND)
                .build();
        List<Guest> newGuests = Arrays.asList(ALICE, editedAlice);
        GuestBookStub newData = new GuestBookStub(newGuests);

        assertThrows(DuplicateGuestException.class, () -> guestBook.resetData(newData));
    }

    @Test
    public void hasPerson_nullGuest_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> guestBook.hasGuest(null));
    }

    @Test
    public void hasPerson_guestNotInAddressBook_returnsFalse() {
        assertFalse(guestBook.hasGuest(ALICE));
    }

    @Test
    public void hasPerson_guestInAddressBook_returnsTrue() {
        guestBook.addGuest(ALICE);
        assertTrue(guestBook.hasGuest(ALICE));
    }

    @Test
    public void hasPerson_guestWithSameIdentityFieldsInAddressBook_returnsTrue() {
        guestBook.addGuest(ALICE);
        Guest editedAlice = new PersonBuilder(ALICE).withRequests(VALID_REQUEST_HUSBAND)
                .build();
        assertTrue(guestBook.hasGuest(editedAlice));
    }

    @Test
    public void getGuestList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> guestBook.getGuestList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = GuestBook.class.getCanonicalName() + "{guests=" + guestBook.getGuestList() + "}";
        assertEquals(expected, guestBook.toString());
    }

    /**
     * A stub ReadOnlyGuestBook whose guests list can violate interface constraints.
     */
    private static class GuestBookStub implements ReadOnlyGuestBook {
        private final ObservableList<Guest> guests = FXCollections.observableArrayList();

        GuestBookStub(Collection<Guest> guests) {
            this.guests.setAll(guests);
        }

        @Override
        public ObservableList<Guest> getGuestList() {
            return guests;
        }
    }

}
