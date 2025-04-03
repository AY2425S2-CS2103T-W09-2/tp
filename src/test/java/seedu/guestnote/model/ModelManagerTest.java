package seedu.guestnote.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.guestnote.model.Model.PREDICATE_SHOW_ALL_GUESTS;
import static seedu.guestnote.testutil.Assert.assertThrows;
import static seedu.guestnote.testutil.TypicalGuests.ALICE;
import static seedu.guestnote.testutil.TypicalGuests.BENSON;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.guestnote.commons.core.GuiSettings;
import seedu.guestnote.model.guest.NameContainsKeywordsPredicate;
import seedu.guestnote.testutil.GuestNoteBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new GuestNote(), new GuestNote(modelManager.getGuestNote()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setGuestNoteFilePath(Paths.get("guestnote/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setGuestNoteFilePath(Paths.get("new/guestnote/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setGuestNoteFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuestNoteFilePath(null));
    }

    @Test
    public void setGuestNoteFilePath_validPath_setsGuestNoteFilePath() {
        Path path = Paths.get("guestnote/book/file/path");
        modelManager.setGuestNoteFilePath(path);
        assertEquals(path, modelManager.getGuestNoteFilePath());
    }

    @Test
    public void hasGuest_nullGuest_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasGuest(null));
    }

    @Test
    public void hasGuest_guestNotInGuestNote_returnsFalse() {
        assertFalse(modelManager.hasGuest(ALICE));
    }

    @Test
    public void hasGuest_guestInGuestNote_returnsTrue() {
        modelManager.addGuest(ALICE);
        assertTrue(modelManager.hasGuest(ALICE));
    }

    @Test
    public void getFilteredGuestList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredGuestList().remove(0));
    }

    @Test
    public void equals() {
        GuestNote guestNote = new GuestNoteBuilder().withGuest(ALICE).withGuest(BENSON).build();
        GuestNote differentGuestNote = new GuestNote();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(guestNote, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(guestNote, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different guestNote -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentGuestNote, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredGuestList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(guestNote, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredGuestList(PREDICATE_SHOW_ALL_GUESTS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setGuestNoteFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(guestNote, differentUserPrefs)));
    }
}
