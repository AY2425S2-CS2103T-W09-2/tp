package seedu.guestnote.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.guestnote.storage.JsonAdaptedGuest.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.guestnote.testutil.Assert.assertThrows;
import static seedu.guestnote.testutil.TypicalGuests.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.guestnote.commons.exceptions.IllegalValueException;
import seedu.guestnote.model.guest.Email;
import seedu.guestnote.model.guest.Name;
import seedu.guestnote.model.guest.Phone;
import seedu.guestnote.model.guest.RoomNumber;

public class JsonAdaptedGuestTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_ROOMNUMBER = " ";
    private static final String INVALID_REQUEST = "#friend";
    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().map(Phone::toString).orElse("Not Added");
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ROOMNUMBER = BENSON.getRoomNumber().toString();
    private static final String VALID_STATUS = BENSON.getStatus().toString();
    private static final List<JsonAdaptedRequest> VALID_REQUESTS = BENSON.getRequests().stream()
            .map(JsonAdaptedRequest::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validGuestDetails_returnsGuest() throws Exception {
        JsonAdaptedGuest guest = new JsonAdaptedGuest(BENSON);
        assertEquals(BENSON, guest.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedGuest guest = new JsonAdaptedGuest(
                INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ROOMNUMBER, VALID_STATUS, VALID_REQUESTS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, guest::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedGuest guest = new JsonAdaptedGuest(
                null, VALID_PHONE, VALID_EMAIL, VALID_ROOMNUMBER, VALID_STATUS, VALID_REQUESTS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, guest::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedGuest guest =
                new JsonAdaptedGuest(
                        VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_ROOMNUMBER, VALID_STATUS, VALID_REQUESTS);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, guest::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedGuest guest =
                new JsonAdaptedGuest(
                        VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_ROOMNUMBER, VALID_STATUS, VALID_REQUESTS);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, guest::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedGuest guest = new JsonAdaptedGuest(
                VALID_NAME, VALID_PHONE, null, VALID_ROOMNUMBER, VALID_STATUS, VALID_REQUESTS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, guest::toModelType);
    }

    @Test
    public void toModelType_invalidRoomNumber_throwsIllegalValueException() {
        JsonAdaptedGuest guest = new JsonAdaptedGuest(
                        VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_ROOMNUMBER, VALID_STATUS, VALID_REQUESTS);
        String expectedMessage = RoomNumber.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, guest::toModelType);
    }

    @Test
    public void toModelType_nullRoomNumber_throwsIllegalValueException() {
        JsonAdaptedGuest guest = new JsonAdaptedGuest(
                VALID_NAME, VALID_PHONE, VALID_EMAIL, null, VALID_STATUS, VALID_REQUESTS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, RoomNumber.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, guest::toModelType);
    }

    @Test
    public void toModelType_invalidRequests_throwsIllegalValueException() {
        List<JsonAdaptedRequest> invalidRequests = new ArrayList<>(VALID_REQUESTS);
        invalidRequests.add(new JsonAdaptedRequest(INVALID_REQUEST));
        JsonAdaptedGuest guest = new JsonAdaptedGuest(
                VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ROOMNUMBER, VALID_STATUS, invalidRequests);
        assertThrows(IllegalValueException.class, guest::toModelType);
    }

}
