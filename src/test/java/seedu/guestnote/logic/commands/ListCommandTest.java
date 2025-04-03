package seedu.guestnote.logic.commands;

import static seedu.guestnote.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.guestnote.logic.commands.CommandTestUtil.showGuestAtIndex;
import static seedu.guestnote.testutil.TypicalGuests.getTypicalGuestNote;
import static seedu.guestnote.testutil.TypicalIndexes.INDEX_FIRST_GUEST;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.guestnote.model.Model;
import seedu.guestnote.model.ModelManager;
import seedu.guestnote.model.UserPrefs;
import seedu.guestnote.model.guest.NameContainsKeywordsPredicate;


/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalGuestNote(), new UserPrefs());
        expectedModel = new ModelManager(model.getGuestNote(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showGuestAtIndex(model, INDEX_FIRST_GUEST);
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listWithFilter_showsFiltered() {
        List<String> keywords = List.of("Alice", "Bob");
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(keywords);
        model.updateFilteredGuestList(predicate);
        expectedModel.updateFilteredGuestList(predicate);
        assertCommandSuccess(
                new ListCommand(predicate), model, ListCommand.MESSAGE_SUCCESS + " (filtered)", expectedModel);
    }

    @Test
    public void execute_listWithRequest_showsOnlyGuestsWithRequests() {
        expectedModel.updateFilteredGuestList(guest -> guest.getRequests() != null);
        String expectedMessage = "Listed all guests with requests";
        assertCommandSuccess(
                new ListCommand(true), model, expectedMessage, model);
    }
}
