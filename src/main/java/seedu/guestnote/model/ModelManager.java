package seedu.guestnote.model;

import static java.util.Objects.requireNonNull;
import static seedu.guestnote.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.guestnote.commons.core.GuiSettings;
import seedu.guestnote.commons.core.LogsCenter;
import seedu.guestnote.model.guest.Guest;
import seedu.guestnote.model.guest.GuestId;

/**
 * Represents the in-memory model of the guestnote book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final GuestBook guestBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Guest> filteredGuests;

    /**
     * Initializes a ModelManager with the given guestBook and userPrefs.
     */
    public ModelManager(ReadOnlyGuestBook addressBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with guestnote book: " + addressBook + " and user prefs " + userPrefs);

        this.guestBook = new GuestBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredGuests = new FilteredList<>(this.guestBook.getGuestList());
    }

    public ModelManager() {
        this(new GuestBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== GuestBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyGuestBook addressBook) {
        this.guestBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyGuestBook getAddressBook() {
        return guestBook;
    }

    @Override
    public boolean hasPerson(Guest guest) {
        requireNonNull(guest);
        return guestBook.hasGuest(guest);
    }

    @Override
    public void deletePerson(Guest target) {
        guestBook.removeGuest(target);
    }

    @Override
    public void addPerson(Guest guest) {
        guestBook.addGuest(guest);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Guest target, Guest editedGuest) {
        requireAllNonNull(target, editedGuest);

        guestBook.setGuest(target, editedGuest);
    }

    @Override
    public GuestId generateUniqueGuestId() {
        return guestBook.generateUniqueGuestId();
    }

    //=========== Filtered Guest List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Guest} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Guest> getFilteredPersonList() {
        return filteredGuests;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Guest> predicate) {
        requireNonNull(predicate);
        filteredGuests.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return guestBook.equals(otherModelManager.guestBook)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredGuests.equals(otherModelManager.filteredGuests);
    }

}
