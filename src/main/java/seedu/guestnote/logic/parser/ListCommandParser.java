package seedu.guestnote.logic.parser;

import java.util.Arrays;

import seedu.guestnote.logic.commands.ListCommand;
import seedu.guestnote.logic.parser.exceptions.ParseException;
import seedu.guestnote.model.guest.NameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new ListCommand object.
 */
public class ListCommandParser implements Parser<ListCommand> {

    @Override
    public ListCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            return new ListCommand(); // Default list command
        }
        if (trimmedArgs.equalsIgnoreCase("rq/")) {
            // List all guests with requests
            return new ListCommand(true);
        } else {
            // Convert input into a FindCommand-like predicate
            return new ListCommand(new NameContainsKeywordsPredicate(Arrays.asList(trimmedArgs.split("\\s+"))));
        }
    }
}
