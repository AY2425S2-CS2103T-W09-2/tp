package seedu.guestnote.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.guestnote.testutil.Assert.assertThrows;
import static seedu.guestnote.testutil.TypicalPersons.ALICE;

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
import seedu.guestnote.model.guest.GuestId;
import seedu.guestnote.testutil.PersonBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Guest validGuest = new PersonBuilder().build();

        CommandResult commandResult = new AddCommand(validGuest).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(validGuest)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validGuest), modelStub.personsAdded);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Guest validGuest = new PersonBuilder().build();
        AddCommand addCommand = new AddCommand(validGuest);
        ModelStub modelStub = new ModelStubWithPerson(validGuest);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_PERSON, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Guest alice = new PersonBuilder().withName("Alice").build();
        Guest bob = new PersonBuilder().withName("Bob").build();
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
        public void addPerson(Guest guest) {
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
        public boolean hasPerson(Guest guest) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Guest target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Guest target, Guest editedGuest) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuestId generateUniqueGuestId() {
            return new GuestId("GUEST-0001"); // Mock a unique guest ID for testing
        }

        @Override
        public ObservableList<Guest> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Guest> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single guest.
     */
    private class ModelStubWithPerson extends ModelStub {
        private final Guest guest;

        ModelStubWithPerson(Guest guest) {
            requireNonNull(guest);
            this.guest = guest;
        }

        @Override
        public boolean hasPerson(Guest guest) {
            requireNonNull(guest);
            return this.guest.isSameGuest(guest);
        }
    }

    /**
     * A Model stub that always accept the guest being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Guest> personsAdded = new ArrayList<>();

        @Override
        public boolean hasPerson(Guest guest) {
            requireNonNull(guest);
            return personsAdded.stream().anyMatch(guest::isSameGuest);
        }

        @Override
        public void addPerson(Guest guest) {
            requireNonNull(guest);
            personsAdded.add(guest);
        }

        @Override
        public ReadOnlyGuestBook getAddressBook() {
            return new GuestBook();
        }
    }

}
