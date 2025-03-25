package seedu.guestnote.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.guestnote.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.guestnote.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.guestnote.testutil.Assert.assertThrows;
import static seedu.guestnote.testutil.TypicalIndexes.INDEX_FIRST_GUEST;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.guestnote.logic.commands.AddCommand;
import seedu.guestnote.logic.commands.CheckInCommand;
import seedu.guestnote.logic.commands.CheckOutCommand;
import seedu.guestnote.logic.commands.ClearCommand;
import seedu.guestnote.logic.commands.DeleteCommand;
import seedu.guestnote.logic.commands.EditCommand;
import seedu.guestnote.logic.commands.EditCommand.EditGuestDescriptor;
import seedu.guestnote.logic.commands.ExitCommand;
import seedu.guestnote.logic.commands.FindCommand;
import seedu.guestnote.logic.commands.HelpCommand;
import seedu.guestnote.logic.commands.ListCommand;
import seedu.guestnote.logic.parser.exceptions.ParseException;
import seedu.guestnote.model.guest.Guest;
import seedu.guestnote.model.guest.NameContainsKeywordsPredicate;
import seedu.guestnote.testutil.EditGuestDescriptorBuilder;
import seedu.guestnote.testutil.GuestBuilder;
import seedu.guestnote.testutil.GuestUtil;

public class GuestBookParserTest {

    private final GuestBookParser parser = new GuestBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Guest guest = new GuestBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(GuestUtil.getAddCommand(guest));
        assertEquals(new AddCommand(guest), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_GUEST.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_GUEST), command);
    }

    @Test
    public void parseCommand_check_in() throws Exception {
        CheckInCommand command = (CheckInCommand) parser.parseCommand(
                CheckInCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new CheckInCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_check_out() throws Exception {
        CheckOutCommand command = (CheckOutCommand) parser.parseCommand(
                CheckOutCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new CheckOutCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Guest guest = new GuestBuilder().build();
        EditGuestDescriptor descriptor = new EditGuestDescriptorBuilder(guest).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_GUEST.getOneBased() + " " + GuestUtil.getEditGuestDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_GUEST, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
                -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
