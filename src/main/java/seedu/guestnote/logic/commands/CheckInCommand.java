package seedu.guestnote.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.guestnote.commons.core.index.Index;
import seedu.guestnote.commons.util.ToStringBuilder;
import seedu.guestnote.logic.Messages;
import seedu.guestnote.logic.commands.exceptions.CommandException;
import seedu.guestnote.model.Model;
import seedu.guestnote.model.guest.Guest;
import seedu.guestnote.model.guest.Phone;
import seedu.guestnote.model.guest.Status;
import seedu.guestnote.model.request.UniqueRequestList;

/**
 * Checks in a guest identified using it's displayed index from the guestnote book.
 */
public class CheckInCommand extends Command {
    public static final String COMMAND_WORD = "check-in";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Checks in the guest identified by the index number used in the displayed guest list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";
    public static final String MESSAGE_SUCCESS = "Checked in: %1$s";
    public static final String MESSAGE_ALREADY_CHECKED_IN = "Guest is already checked in.";


    private final Index targetIndex;

    /**
     * Constructor to set Index for guest to be checked-out.
     */
    public CheckInCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Guest> lastShownList = model.getFilteredGuestList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_GUEST_DISPLAYED_INDEX);
        }

        Guest guestToCheckIn = lastShownList.get(targetIndex.getZeroBased());

        if (guestToCheckIn.getStatus() == Status.CHECKED_IN) {
            throw new CommandException(MESSAGE_ALREADY_CHECKED_IN);
        }

        UniqueRequestList updatedRequests = new UniqueRequestList();
        updatedRequests.setRequests(guestToCheckIn.getRequests());

        Guest checkedInGuest = new Guest(
                guestToCheckIn.getName(),
                guestToCheckIn.getPhone().orElse(new Phone("")),
                guestToCheckIn.getEmail(),
                guestToCheckIn.getRoomNumber(),
                Status.CHECKED_IN,
                updatedRequests
        );

        model.setGuest(guestToCheckIn, checkedInGuest);
        model.updateFilteredGuestList(Model.PREDICATE_SHOW_ALL_GUESTS);

        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(checkedInGuest)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CheckInCommand)) {
            return false;
        }

        CheckInCommand otherCheckInCommand = (CheckInCommand) other;
        return targetIndex.equals(otherCheckInCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
