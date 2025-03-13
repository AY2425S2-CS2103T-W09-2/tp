package seedu.guestnote.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import seedu.guestnote.commons.util.ToStringBuilder;
import seedu.guestnote.model.guest.Guest;
import seedu.guestnote.model.guest.GuestId;
import seedu.guestnote.model.guest.UniqueGuestList;

/**
 * Wraps all data at the guestnote-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class GuestBook implements ReadOnlyGuestBook {

    private final UniqueGuestList guests = new UniqueGuestList();

    public GuestBook() {}

    /**
     * Creates an GuestBook using the Persons in the {@code toBeCopied}
     */
    public GuestBook(ReadOnlyGuestBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the guest list with {@code guests}.
     * {@code guests} must not contain duplicate guests.
     */
    public void setGuests(List<Guest> guests) {
        this.guests.setGuests(guests);
    }

    /**
     * Resets the existing data of this {@code GuestBook} with {@code newData}.
     */
    public void resetData(ReadOnlyGuestBook newData) {
        requireNonNull(newData);

        setGuests(newData.getGuestList());
    }

    //// guest-level operations

    /**
     * Returns true if a guest with the same identity as {@code guest} exists in the guestnote book.
     */
    public boolean hasGuest(Guest guest) {
        requireNonNull(guest);
        return guests.contains(guest);
    }

    /**
     * Adds a guest to the guestnote book.
     * The guest must not already exist in the guestnote book.
     */
    public void addGuest(Guest p) {
        guests.add(p);
    }

    /**
     * Replaces the given guest {@code target} in the list with {@code editedGuest}.
     * {@code target} must exist in the guestnote book.
     * The guest identity of {@code editedGuest} must not be the same as another existing guest in the book.
     */
    public void setGuest(Guest target, Guest editedGuest) {
        requireNonNull(editedGuest);

        guests.setGuest(target, editedGuest);
    }

    /**
     * Removes {@code key} from this {@code GuestBook}.
     * {@code key} must exist in the guestnote book.
     */
    public void removeGuest(Guest key) {
        guests.remove(key);
    }

    //// util methods
    /**
     * Generates a unique Guest ID.
     */
    public GuestId generateUniqueGuestId() {
        Random random = new Random();
        String guestId;
        do {
            guestId = random.ints(4, 0, 26)
                    .mapToObj(i -> String.valueOf((char) ('A' + i)))
                    .collect(Collectors.joining());
        } while (containsGuestId(guestId)); // Ensure uniqueness
        return new GuestId(guestId);
    }

    /**
     * Checks if the GuestBook already contains a guest with the given Guest ID.
     */
    public boolean containsGuestId(String guestId) {
        return guests.asUnmodifiableObservableList().stream()
                .anyMatch(guest -> guest.getGuestId().value.equalsIgnoreCase(guestId));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("guests", guests)
                .toString();
    }

    @Override
    public ObservableList<Guest> getGuestList() {
        return guests.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof GuestBook)) {
            return false;
        }

        GuestBook otherGuestBook = (GuestBook) other;
        return guests.equals(otherGuestBook.guests);
    }

    @Override
    public int hashCode() {
        return guests.hashCode();
    }
}
