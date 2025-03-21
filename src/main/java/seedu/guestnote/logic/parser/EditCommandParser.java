package seedu.guestnote.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.guestnote.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.guestnote.logic.parser.CliSyntax.PREFIX_ADD_REQ;
import static seedu.guestnote.logic.parser.CliSyntax.PREFIX_DELETE_REQ;
import static seedu.guestnote.logic.parser.CliSyntax.PREFIX_DELETE_REQ_INDEX;
import static seedu.guestnote.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.guestnote.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.guestnote.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.guestnote.logic.parser.CliSyntax.PREFIX_ROOMNUMBER;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import seedu.guestnote.commons.core.index.Index;
import seedu.guestnote.logic.commands.EditCommand;
import seedu.guestnote.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.guestnote.logic.parser.exceptions.ParseException;
import seedu.guestnote.model.request.Request;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
                args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ROOMNUMBER,
                PREFIX_ADD_REQ, PREFIX_DELETE_REQ, PREFIX_DELETE_REQ_INDEX
        );

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(
                PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ROOMNUMBER
        );

        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editPersonDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            editPersonDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            editPersonDescriptor.setEmail(ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()));
        }
        if (argMultimap.getValue(PREFIX_ROOMNUMBER).isPresent()) {
            editPersonDescriptor.setRoomNumber(ParserUtil.parseRoomNumber(
                    argMultimap.getValue(PREFIX_ROOMNUMBER).get())
            );
        }
        if (argMultimap.getValue(PREFIX_ADD_REQUEST).isPresent()) {
            parseRequestsForEdit(argMultimap.getAllValues(PREFIX_ADD_REQUEST))
                    .ifPresent(editPersonDescriptor::setRequestsToAdd);
        }
        if (argMultimap.getValue(PREFIX_DELETE_REQUEST).isPresent()) {
            parseRequestsForEdit(argMultimap.getAllValues(PREFIX_DELETE_REQUEST))
                    .ifPresent(editPersonDescriptor::setRequestsToDelete);
        }
        if (argMultimap.getValue(PREFIX_DELETE_REQ_INDEX).isPresent()) {
            editPersonDescriptor.setRequestIndexesToDelete(ParserUtil.parseIndexes(
                    argMultimap.getAllValues(PREFIX_DELETE_REQ_INDEX))
            );
        }

        if (!editPersonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editPersonDescriptor);
    }

    /**
     * Parses {@code Collection<String> requests} into a {@code Set<Request>} if {@code requests} is non-empty.
     * If {@code requests} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Request>} containing zero requests.
     */
    private Optional<List<Request>> parseTagsForEdit(Collection<String> requests) throws ParseException {
        assert requests != null;

        if (requests.isEmpty()) {
            return Optional.empty();
        }
        List<Request> requestList = ParserUtil.parseTags(requests);
        return Optional.of(requestList);
    }

}
