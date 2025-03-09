package seedu.guestnote.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.guestnote.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.guestnote.commons.exceptions.IllegalValueException;
import seedu.guestnote.commons.util.JsonUtil;
import seedu.guestnote.model.GuestBook;
import seedu.guestnote.testutil.TypicalPersons;

public class JsonSerializableGuestBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableGuestBookTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve("typicalPersonsGuestBook.json");
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidPersonGuestBook.json");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER.resolve("duplicatePersonGuestBook.json");

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        JsonSerializableGuestBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_PERSONS_FILE,
                JsonSerializableGuestBook.class).get();
        GuestBook guestBookFromFile = dataFromFile.toModelType();
        GuestBook typicalPersonsGuestBook = TypicalPersons.getTypicalAddressBook();
        assertEquals(guestBookFromFile, typicalPersonsGuestBook);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        JsonSerializableGuestBook dataFromFile = JsonUtil.readJsonFile(INVALID_PERSON_FILE,
                JsonSerializableGuestBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        JsonSerializableGuestBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PERSON_FILE,
                JsonSerializableGuestBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableGuestBook.MESSAGE_DUPLICATE_PERSON,
                dataFromFile::toModelType);
    }

}
