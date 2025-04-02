package seedu.guestnote.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.guestnote.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.guestnote.commons.exceptions.IllegalValueException;
import seedu.guestnote.commons.util.JsonUtil;
import seedu.guestnote.model.GuestNote;
import seedu.guestnote.testutil.TypicalGuests;

public class JsonSerializableGuestNoteTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableGuestNoteTest");
    private static final Path TYPICAL_GUESTS_FILE = TEST_DATA_FOLDER.resolve("typicalGuestsGuestNote.json");
    private static final Path INVALID_GUEST_FILE = TEST_DATA_FOLDER.resolve("invalidGuestGuestNote.json");
    private static final Path DUPLICATE_GUEST_FILE = TEST_DATA_FOLDER.resolve("duplicateGuestGuestNote.json");

    @Test
    public void toModelType_typicalGuestsFile_success() throws Exception {
        JsonSerializableGuestNote dataFromFile = JsonUtil.readJsonFile(TYPICAL_GUESTS_FILE,
                JsonSerializableGuestNote.class).get();
        GuestNote guestNoteFromFile = dataFromFile.toModelType();
        GuestNote typicalGuestsGuestNote = TypicalGuests.getTypicalGuestNote();
        assertEquals(guestNoteFromFile, typicalGuestsGuestNote);
    }

    @Test
    public void toModelType_invalidGuestFile_throwsIllegalValueException() throws Exception {
        JsonSerializableGuestNote dataFromFile = JsonUtil.readJsonFile(INVALID_GUEST_FILE,
                JsonSerializableGuestNote.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateGuests_throwsIllegalValueException() throws Exception {
        JsonSerializableGuestNote dataFromFile = JsonUtil.readJsonFile(DUPLICATE_GUEST_FILE,
                JsonSerializableGuestNote.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableGuestNote.MESSAGE_DUPLICATE_GUEST,
                dataFromFile::toModelType);
    }

}
