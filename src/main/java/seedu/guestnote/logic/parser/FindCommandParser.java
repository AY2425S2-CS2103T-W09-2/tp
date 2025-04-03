package seedu.guestnote.logic.parser;

import static seedu.guestnote.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import seedu.guestnote.logic.commands.FindCommand;
import seedu.guestnote.logic.parser.exceptions.ParseException;
import seedu.guestnote.model.guest.AnyFieldContainsKeywordsPredicate;
import seedu.guestnote.model.guest.FieldContainsKeywordsPredicate;
import seedu.guestnote.model.guest.Guest;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        String[] keywords = trimmedArgs.split("\\s+");

        List<Predicate<Guest>> fieldPredicates = new ArrayList<>();
        fieldPredicates.add(new FieldContainsKeywordsPredicate<>(Guest::getName, Arrays.asList(keywords)));
        fieldPredicates.add(new FieldContainsKeywordsPredicate<>(Guest::getPhone, Arrays.asList(keywords)));
        fieldPredicates.add(new FieldContainsKeywordsPredicate<>(Guest::getEmail, Arrays.asList(keywords)));
        fieldPredicates.add(new FieldContainsKeywordsPredicate<>(Guest::getStatus, Arrays.asList(keywords)));
        fieldPredicates.add(new FieldContainsKeywordsPredicate<>(Guest::getRoomNumber, Arrays.asList(keywords)));
        fieldPredicates.add(new FieldContainsKeywordsPredicate<>(Guest::getRequestsArray, Arrays.asList(keywords)));
        //add more field predicates here
        Predicate<Guest> compositePredicate = new AnyFieldContainsKeywordsPredicate(fieldPredicates);
        return new FindCommand(compositePredicate);
    }

}
