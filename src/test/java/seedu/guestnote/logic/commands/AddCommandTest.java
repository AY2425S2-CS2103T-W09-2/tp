package seedu.guestnote.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.guestnote.testutil.Assert.assertThrows;
import static seedu.guestnote.testutil.TypicalGuests.ALICE;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.guestnote.commons.core.GuiSettings;
import seedu.guestnote.logic.Messages;
import seedu.guestnote.logic.commands.exceptions.CommandException;
import seedu.guestnote.model.GuestBook;
import seedu.guestnote.model.Model;
import seedu.guestnote.model.ReadOnlyGuestBook;
import seedu.guestnote.model.ReadOnlyUserPrefs;
import seedu.guestnote.model.guest.Guest;
import seedu.guestnote.testutil.GuestBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullGuest_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_guestAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingGuestAdded modelStub = new ModelStubAcceptingGuestAdded();
        Guest validGuest = new GuestBuilder().build();

        CommandResult commandResult = new AddCommand(validGuest).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(validGuest)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validGuest), modelStub.guestsAdded);
    }

    @Test
    public void execute_guestWithNoPhoneAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingGuestAdded modelStub = new ModelStubAcceptingGuestAdded();
        Guest alice = new GuestBuilder()
                .withName("Alice")
                .withEmail("aliceyeo@gmail.com")
                .withRoomNumber("03-20")
                .withRequests("friend")
                .build();

        CommandResult commandResult = new AddCommand(alice).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(alice)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(alice), modelStub.guestsAdded);
    }

    @Test
    public void execute_duplicateGuest_throwsCommandException() {
        Guest validGuest = new GuestBuilder().build();
        AddCommand addCommand = new AddCommand(validGuest);
        ModelStub modelStub = new ModelStubWithGuest(validGuest);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_GUEST, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Guest alice = new GuestBuilder().withName("Alice").build();
        Guest bob = new GuestBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different guest -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    @Test
    public void toStringMethod() {
        AddCommand addCommand = new AddCommand(ALICE);
        String expected = AddCommand.class.getCanonicalName() + "{toAdd=" + ALICE + "}";
        assertEquals(expected, addCommand.toString());
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addGuest(Guest guest) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyGuestBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyGuestBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasGuest(Guest guest) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteGuest(Guest target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuest(Guest target, Guest editedGuest) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Guest> getFilteredGuestList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredGuestList(Predicate<Guest> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single guest.
     */
    private class ModelStubWithGuest extends ModelStub {
        private final Guest guest;

        ModelStubWithGuest(Guest guest) {
            requireNonNull(guest);
            this.guest = guest;
        }

        @Override
        public boolean hasGuest(Guest guest) {
            requireNonNull(guest);
            return this.guest.isSameGuest(guest);
        }
    }

    /**
     * A Model stub that always accept the guest being added.
     */
    private class ModelStubAcceptingGuestAdded extends ModelStub {
        final ArrayList<Guest> guestsAdded = new ArrayList<>();

        @Override
        public boolean hasGuest(Guest guest) {
            requireNonNull(guest);
            return guestsAdded.stream().anyMatch(guest::isSameGuest);
        }

        @Override
        public void addGuest(Guest guest) {
            requireNonNull(guest);
            guestsAdded.add(guest);
        }

        @Override
        public ReadOnlyGuestBook getAddressBook() {
            return new GuestBook();
        }
    }

}
