package seedu.guestnote.logic.commands;

import static seedu.guestnote.model.Model.PREDICATE_SHOW_ALL_GUESTS;

import seedu.guestnote.model.Model;
import seedu.guestnote.model.guest.NameContainsKeywordsPredicate;

/**
 * Lists all guests in the guest book.
 * This command supports listing all guests as well as filtering the list based on a search query.
 * It utilizes the model's filtered guest list to display the guests.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String MESSAGE_SUCCESS = "Listed all guests";
    private final boolean listWithRequests;
    private final NameContainsKeywordsPredicate predicate;
    /**
     * Constructs a ListCommand that lists all guests.
     */
    public ListCommand() {
        this.predicate = null;
        this.listWithRequests = false;
    }
    /**
     * Constructs a ListCommand that lists guests based on the provided search query.
     * @param predicate The predicate used to filter the guest list.
     */
    public ListCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
        this.listWithRequests = false;
    }

    /**
     * Constructs a ListCommand that lists all guests with requests.
     * @param listWithRequests
     */
    public ListCommand(boolean listWithRequests) {
        this.predicate = null;
        this.listWithRequests = listWithRequests;
    }
    /**
     * Executes the list command and returns the result.
     * If a search query is provided, the command filters the guest list by matching the guest's name or ID.
     * Otherwise, it displays all guests.
     *
     * @param model The {@code Model} containing the guest book data.
     * @return A {@link CommandResult} containing the success message for testing purpose.
     */
    @Override
    public CommandResult execute(Model model) {
        // Update the filtered list to show all guests.
        if (listWithRequests) {
            model.updateFilteredGuestList(guest -> !guest.getRequests().isEmpty());
            return new CommandResult("Listed all guests with requests");
        }
        if (predicate == null) {
            // Default behavior: list all guests
            model.updateFilteredGuestList(PREDICATE_SHOW_ALL_GUESTS);
            return new CommandResult(MESSAGE_SUCCESS);
        } else {
            // Search behavior: filter list based on the predicate
            model.updateFilteredGuestList(predicate);
            return new CommandResult(MESSAGE_SUCCESS + " (filtered)");
        }

    }
}
