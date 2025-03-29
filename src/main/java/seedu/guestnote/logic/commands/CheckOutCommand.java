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
 * Checks out a guest identified using it's displayed index from the guestnote book.
 */
public class CheckOutCommand extends Command {
    public static final String COMMAND_WORD = "check-out";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Checks out the guest identified by the index number used in the displayed guest list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";
    public static final String MESSAGE_SUCCESS = "Checked out: %1$s";
    public static final String MESSAGE_NOT_CHECKED_IN = "Guest has not checked in.";
    public static final String MESSAGE_ALREADY_CHECKED_OUT = "Guest has already checked out.";

    private final Index targetIndex;

    /**
     * Constructor to set Index for guest to be checked-out.
     */
    public CheckOutCommand(Index targetIndex) {
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

        Guest guestToCheckOut = lastShownList.get(targetIndex.getZeroBased());

        if (guestToCheckOut.getStatus() == Status.BOOKING) {
            throw new CommandException(MESSAGE_NOT_CHECKED_IN);
        }

        if (guestToCheckOut.getStatus() == Status.CHECKED_OUT) {
            throw new CommandException(MESSAGE_ALREADY_CHECKED_OUT);
        }

        UniqueRequestList updatedRequests = new UniqueRequestList();
        updatedRequests.setRequests(guestToCheckOut.getRequests());

        Guest checkedOutGuest = new Guest(
                guestToCheckOut.getName(),
                guestToCheckOut.getPhone().orElse(new Phone("")),
                guestToCheckOut.getEmail(),
                guestToCheckOut.getRoomNumber(),
                Status.CHECKED_OUT,
                updatedRequests
        );

        model.setGuest(guestToCheckOut, checkedOutGuest);
        model.updateFilteredGuestList(Model.PREDICATE_SHOW_ALL_GUESTS);

        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(checkedOutGuest)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CheckOutCommand)) {
            return false;
        }

        CheckOutCommand otherCheckOutCommand = (CheckOutCommand) other;
        return targetIndex.equals(otherCheckOutCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
