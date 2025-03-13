package seedu.guestnote.model.guest;

import static java.util.Objects.requireNonNull;
import static seedu.guestnote.commons.util.AppUtil.checkArgument;

/**
 * Represents a Guest's ID in GuestNote.
 * Guarantees: immutable; is valid as declared in {@link #isValidGuestId(String)}
 */
public class GuestId {

    public static final String MESSAGE_CONSTRAINTS =
            "Guest ID should be a 4-character alphabetical string and is case-insensitive.";

    private static final String VALIDATION_REGEX = "^[a-zA-Z]{4}$";

    public final String value;

    /**
     * Constructs a {@code GuestId}.
     *
     * @param guestId A valid guest ID.
     */
    public GuestId(String guestId) {
        requireNonNull(guestId);
        checkArgument(isValidGuestId(guestId), MESSAGE_CONSTRAINTS);
        this.value = guestId.toUpperCase();
    }

    /**
     * Returns true if a given string is a valid guest ID.
     */
    public static boolean isValidGuestId(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof GuestId)) {
            return false;
        }

        GuestId otherGuestId = (GuestId) other;
        return value.equals(otherGuestId.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
