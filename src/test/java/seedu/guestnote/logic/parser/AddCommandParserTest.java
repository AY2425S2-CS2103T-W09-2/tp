package seedu.guestnote.logic.parser;

import static seedu.guestnote.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.guestnote.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.guestnote.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.guestnote.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.guestnote.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.guestnote.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.guestnote.logic.commands.CommandTestUtil.INVALID_REQUEST_DESC;
import static seedu.guestnote.logic.commands.CommandTestUtil.INVALID_ROOMNUMBER_DESC;
import static seedu.guestnote.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.guestnote.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.guestnote.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.guestnote.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.guestnote.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.guestnote.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.guestnote.logic.commands.CommandTestUtil.REQUEST_DESC_EXTRAPILLOW;
import static seedu.guestnote.logic.commands.CommandTestUtil.REQUEST_DESC_SEAVIEW;
import static seedu.guestnote.logic.commands.CommandTestUtil.ROOMNUMBER_DESC_AMY;
import static seedu.guestnote.logic.commands.CommandTestUtil.ROOMNUMBER_DESC_BOB;
import static seedu.guestnote.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.guestnote.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.guestnote.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.guestnote.logic.commands.CommandTestUtil.VALID_REQUEST_EXTRAPILLOW;
import static seedu.guestnote.logic.commands.CommandTestUtil.VALID_REQUEST_SEAVIEW;
import static seedu.guestnote.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.guestnote.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.guestnote.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.guestnote.logic.parser.CliSyntax.PREFIX_ROOMNUMBER;
import static seedu.guestnote.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.guestnote.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.guestnote.testutil.TypicalGuests.AMY;
import static seedu.guestnote.testutil.TypicalGuests.BOB;

import org.junit.jupiter.api.Test;

import seedu.guestnote.logic.Messages;
import seedu.guestnote.logic.commands.AddCommand;
import seedu.guestnote.model.guest.Email;
import seedu.guestnote.model.guest.Guest;
import seedu.guestnote.model.guest.Name;
import seedu.guestnote.model.guest.Phone;
import seedu.guestnote.model.guest.RoomNumber;
import seedu.guestnote.model.request.Request;
import seedu.guestnote.testutil.GuestBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Guest expectedGuest = new GuestBuilder(BOB).withRequests(VALID_REQUEST_SEAVIEW).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ROOMNUMBER_DESC_BOB + REQUEST_DESC_SEAVIEW, new AddCommand(expectedGuest));

        // multiple requests - all accepted
        Guest expectedGuestMultipleRequests = new GuestBuilder(BOB)
                .withRequests(VALID_REQUEST_SEAVIEW, VALID_REQUEST_EXTRAPILLOW)
                .build();
        assertParseSuccess(parser,
                NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + ROOMNUMBER_DESC_BOB + REQUEST_DESC_SEAVIEW + REQUEST_DESC_EXTRAPILLOW,
                new AddCommand(expectedGuestMultipleRequests));
    }

    @Test
    public void parse_repeatedNonRequestValue_failure() {
        String validExpectedGuestString = NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ROOMNUMBER_DESC_BOB + REQUEST_DESC_SEAVIEW;

        // multiple names
        assertParseFailure(parser, NAME_DESC_AMY + validExpectedGuestString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple phones
        assertParseFailure(parser, PHONE_DESC_AMY + validExpectedGuestString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // multiple emails
        assertParseFailure(parser, EMAIL_DESC_AMY + validExpectedGuestString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedGuestString + PHONE_DESC_AMY + EMAIL_DESC_AMY + NAME_DESC_AMY
                        + ROOMNUMBER_DESC_AMY + validExpectedGuestString,
                Messages.getErrorMessageForDuplicatePrefixes(
                        PREFIX_NAME, PREFIX_ROOMNUMBER, PREFIX_EMAIL, PREFIX_PHONE));

        // invalid value followed by valid value

        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + validExpectedGuestString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, INVALID_EMAIL_DESC + validExpectedGuestString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, INVALID_PHONE_DESC + validExpectedGuestString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid room number
        assertParseFailure(parser, INVALID_ROOMNUMBER_DESC + validExpectedGuestString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ROOMNUMBER));

        // valid value followed by invalid value

        // invalid name
        assertParseFailure(parser, validExpectedGuestString + INVALID_NAME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, validExpectedGuestString + INVALID_EMAIL_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, validExpectedGuestString + INVALID_PHONE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));
    }

    @Test
    public void parse_duplicateRequests_failure() {
        String userInput = NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ROOMNUMBER_DESC_BOB + REQUEST_DESC_SEAVIEW + REQUEST_DESC_SEAVIEW;

        assertParseFailure(parser, userInput, "Operation would result in duplicate requests");
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero requests
        Guest expectedGuest = new GuestBuilder(AMY).withRequests().build();
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY
                        + EMAIL_DESC_AMY + ROOMNUMBER_DESC_AMY,
                new AddCommand(expectedGuest));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB,
                expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_PHONE_BOB + EMAIL_DESC_BOB,
                expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + VALID_EMAIL_BOB,
                expectedMessage);

        // missing guestnote prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ROOMNUMBER_DESC_BOB
                + REQUEST_DESC_EXTRAPILLOW + REQUEST_DESC_SEAVIEW, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC
                + EMAIL_DESC_BOB + ROOMNUMBER_DESC_BOB
                + REQUEST_DESC_EXTRAPILLOW + REQUEST_DESC_SEAVIEW, Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB
                + INVALID_EMAIL_DESC + ROOMNUMBER_DESC_BOB
                + REQUEST_DESC_EXTRAPILLOW + REQUEST_DESC_SEAVIEW, Email.MESSAGE_CONSTRAINTS);

        // invalid room number
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + INVALID_ROOMNUMBER_DESC
                + REQUEST_DESC_EXTRAPILLOW + REQUEST_DESC_SEAVIEW, RoomNumber.MESSAGE_CONSTRAINTS);

        // invalid request
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ROOMNUMBER_DESC_BOB
                + INVALID_REQUEST_DESC + VALID_REQUEST_SEAVIEW, Request.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB
                        + EMAIL_DESC_BOB + ROOMNUMBER_DESC_BOB,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + ROOMNUMBER_DESC_BOB + REQUEST_DESC_EXTRAPILLOW + REQUEST_DESC_SEAVIEW,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
