package seedu.guestnote.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.guestnote.commons.core.GuiSettings;
import seedu.guestnote.model.guest.Guest;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Guest> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' guestnote book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' guestnote book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces guestnote book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyGuestBook addressBook);

    /** Returns the GuestBook */
    ReadOnlyGuestBook getAddressBook();

    /**
     * Returns true if a guest with the same identity as {@code guest} exists in the guestnote book.
     */
    boolean hasPerson(Guest guest);

    /**
     * Deletes the given guest.
     * The guest must exist in the guestnote book.
     */
    void deletePerson(Guest target);

    /**
     * Adds the given guest.
     * {@code guest} must not already exist in the guestnote book.
     */
    void addPerson(Guest guest);

    /**
     * Replaces the given guest {@code target} with {@code editedGuest}.
     * {@code target} must exist in the guestnote book.
     * The guest identity of {@code editedGuest} must not be the same as another existing guest in the book.
     */
    void setPerson(Guest target, Guest editedGuest);

    /**
     * Returns true if there exists another guest in the guest list (excluding {@code guestToExclude})
     * that has the same phone number or email as {@code guestToCheck}.
     *
     * <p>This is used to enforce uniqueness constraints on phone and email fields
     * during operations like editing a guest, ensuring no duplicate phone or email values exist.</p>
     */
    boolean hasGuestWithDuplicatePhoneOrEmailExcluding(Guest guestToCheck, Guest guestToExclude);


    /** Returns an unmodifiable view of the filtered guest list */
    ObservableList<Guest> getFilteredPersonList();

    /**
     * Updates the filter of the filtered guest list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Guest> predicate);
}
