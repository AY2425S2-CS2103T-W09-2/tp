package seedu.guestnote.logic.parser;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.guestnote.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.guestnote.logic.parser.CommandParserTestUtil.assertParseFailure;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.guestnote.logic.commands.FindCommand;
import seedu.guestnote.logic.parser.exceptions.ParseException;
import seedu.guestnote.model.guest.AnyFieldContainsKeywordsPredicate;
import seedu.guestnote.model.guest.FieldContainsKeywordsPredicate;
import seedu.guestnote.model.guest.Guest;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() throws ParseException {
        // Build the expected FindCommand with the composite predicate.
        FindCommand expectedFindCommand = new FindCommand(
                new AnyFieldContainsKeywordsPredicate(Arrays.asList(
                        new FieldContainsKeywordsPredicate<>(Guest::getName, Arrays.asList("Alice", "Bob")),
                        new FieldContainsKeywordsPredicate<>(Guest::getPhone, Arrays.asList("Alice", "Bob")),
                        new FieldContainsKeywordsPredicate<>(Guest::getEmail, Arrays.asList("Alice", "Bob")),
                        new FieldContainsKeywordsPredicate<>(Guest::getRoomNumber, Arrays.asList("Alice", "Bob")),
                        new FieldContainsKeywordsPredicate<>(Guest::getStatus, Arrays.asList("Alice", "Bob")),
                        new FieldContainsKeywordsPredicate<>(Guest::getRequestsArray, Arrays.asList("Alice", "Bob"))
                ))
        );

        // Parse the input using the parser (casting the result to FindCommand)
        FindCommand actualFindCommand = (FindCommand) parser.parse("Alice Bob");
        String actualStr = actualFindCommand.toString();

        // Remove the hash code portion by extracting the part starting with the '<'
        int idx = actualStr.indexOf('<');
        String meaningfulActual = idx != -1 ? actualStr.substring(idx) : actualStr;

        // The expected meaningful output. Adjust this if needed.
        String expectedMeaningful = "FindCommand{predicate=AnyFieldContainsKeywordsPredicate{predicateCount=6}}";

        assertTrue(meaningfulActual.contains(expectedMeaningful));

    }

}
