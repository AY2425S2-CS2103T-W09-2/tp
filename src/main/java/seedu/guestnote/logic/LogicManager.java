package seedu.guestnote.logic;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.guestnote.commons.core.GuiSettings;
import seedu.guestnote.commons.core.LogsCenter;
import seedu.guestnote.logic.commands.Command;
import seedu.guestnote.logic.commands.CommandResult;
import seedu.guestnote.logic.commands.exceptions.CommandException;
import seedu.guestnote.logic.parser.GuestNoteParser;
import seedu.guestnote.logic.parser.exceptions.ParseException;
import seedu.guestnote.model.Model;
import seedu.guestnote.model.ReadOnlyGuestNote;
import seedu.guestnote.model.guest.Guest;
import seedu.guestnote.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_FORMAT = "Could not save data due to the following error: %s";

    public static final String FILE_OPS_PERMISSION_ERROR_FORMAT =
            "Could not save data to file %s due to insufficient permissions to write to the file or the folder.";

    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final GuestNoteParser guestNoteParser;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        guestNoteParser = new GuestNoteParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = guestNoteParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveGuestNote(model.getGuestNote());
        } catch (AccessDeniedException e) {
            throw new CommandException(String.format(FILE_OPS_PERMISSION_ERROR_FORMAT, e.getMessage()), e);
        } catch (IOException ioe) {
            throw new CommandException(String.format(FILE_OPS_ERROR_FORMAT, ioe.getMessage()), ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyGuestNote getGuestNote() {
        return model.getGuestNote();
    }

    @Override
    public ObservableList<Guest> getFilteredGuestList() {
        return model.getFilteredGuestList();
    }

    @Override
    public Path getGuestNoteFilePath() {
        return model.getGuestNoteFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }
}
