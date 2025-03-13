package seedu.guestnote.model.guest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.guestnote.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.guestnote.testutil.Assert.assertThrows;
import static seedu.guestnote.testutil.TypicalPersons.ALICE;
import static seedu.guestnote.testutil.TypicalPersons.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.guestnote.model.guest.exceptions.DuplicateGuestException;
import seedu.guestnote.model.guest.exceptions.PersonNotFoundException;
import seedu.guestnote.testutil.PersonBuilder;

public class UniqueGuestListTest {

    private final UniqueGuestList uniqueGuestList = new UniqueGuestList();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueGuestList.contains(null));
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(uniqueGuestList.contains(ALICE));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniqueGuestList.add(ALICE);
        assertTrue(uniqueGuestList.contains(ALICE));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        uniqueGuestList.add(ALICE);
        Guest editedAlice = new PersonBuilder(ALICE).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniqueGuestList.contains(editedAlice));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueGuestList.add(null));
    }

    @Test
    public void add_duplicatePerson_throwsDuplicatePersonException() {
        uniqueGuestList.add(ALICE);
        assertThrows(DuplicateGuestException.class, () -> uniqueGuestList.add(ALICE));
    }

    @Test
    public void setPerson_nullTargetGuest_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueGuestList.setGuest(null, ALICE));
    }

    @Test
    public void setPerson_nullEditedGuest_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueGuestList.setGuest(ALICE, null));
    }

    @Test
    public void setPerson_targetPersonNotInList_throwsGuestNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> uniqueGuestList.setGuest(ALICE, ALICE));
    }

    @Test
    public void setPerson_editedPersonIsSameGuest_success() {
        uniqueGuestList.add(ALICE);
        uniqueGuestList.setGuest(ALICE, ALICE);
        UniqueGuestList expectedUniqueGuestList = new UniqueGuestList();
        expectedUniqueGuestList.add(ALICE);
        assertEquals(expectedUniqueGuestList, uniqueGuestList);
    }

    @Test
    public void setPerson_editedGuestHasSameIdentity_success() {
        uniqueGuestList.add(ALICE);
        Guest editedAlice = new PersonBuilder(ALICE).withTags(VALID_TAG_HUSBAND)
                .build();
        uniqueGuestList.setGuest(ALICE, editedAlice);
        UniqueGuestList expectedUniqueGuestList = new UniqueGuestList();
        expectedUniqueGuestList.add(editedAlice);
        assertEquals(expectedUniqueGuestList, uniqueGuestList);
    }

    @Test
    public void setPerson_editedGuestHasDifferentIdentity_success() {
        uniqueGuestList.add(ALICE);
        uniqueGuestList.setGuest(ALICE, BOB);
        UniqueGuestList expectedUniqueGuestList = new UniqueGuestList();
        expectedUniqueGuestList.add(BOB);
        assertEquals(expectedUniqueGuestList, uniqueGuestList);
    }

    @Test
    public void setPerson_editedPersonHasNonUniqueIdentity_throwsDuplicateGuestException() {
        uniqueGuestList.add(ALICE);
        uniqueGuestList.add(BOB);
        assertThrows(DuplicateGuestException.class, () -> uniqueGuestList.setGuest(ALICE, BOB));
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueGuestList.remove(null));
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> uniqueGuestList.remove(ALICE));
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        uniqueGuestList.add(ALICE);
        uniqueGuestList.remove(ALICE);
        UniqueGuestList expectedUniqueGuestList = new UniqueGuestList();
        assertEquals(expectedUniqueGuestList, uniqueGuestList);
    }

    @Test
    public void setGuests_nullUniqueGuestList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueGuestList.setGuests((UniqueGuestList) null));
    }

    @Test
    public void setGuests_uniquePersonList_replacesOwnListWithProvidedUniqueGuestList() {
        uniqueGuestList.add(ALICE);
        UniqueGuestList expectedUniqueGuestList = new UniqueGuestList();
        expectedUniqueGuestList.add(BOB);
        uniqueGuestList.setGuests(expectedUniqueGuestList);
        assertEquals(expectedUniqueGuestList, uniqueGuestList);
    }

    @Test
    public void setGuests_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueGuestList.setGuests((List<Guest>) null));
    }

    @Test
    public void setGuests_list_replacesOwnListWithProvidedList() {
        uniqueGuestList.add(ALICE);
        List<Guest> guestList = Collections.singletonList(BOB);
        uniqueGuestList.setGuests(guestList);
        UniqueGuestList expectedUniqueGuestList = new UniqueGuestList();
        expectedUniqueGuestList.add(BOB);
        assertEquals(expectedUniqueGuestList, uniqueGuestList);
    }

    @Test
    public void setPersons_listWithDuplicateGuests_throwsDuplicateGuestException() {
        List<Guest> listWithDuplicateGuests = Arrays.asList(ALICE, ALICE);
        assertThrows(DuplicateGuestException.class, () -> uniqueGuestList.setGuests(listWithDuplicateGuests));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueGuestList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniqueGuestList.asUnmodifiableObservableList().toString(), uniqueGuestList.toString());
    }
}
