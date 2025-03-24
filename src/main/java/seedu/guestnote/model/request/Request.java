package seedu.guestnote.model.request;

import static java.util.Objects.requireNonNull;
import static seedu.guestnote.commons.util.AppUtil.checkArgument;

/**
 * Represents a Request in the guestnote book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidRequestName(String)}
 */
public class Request {

    public static final String MESSAGE_CONSTRAINTS = "Requests names should be alphanumeric";
    public static final String VALIDATION_REGEX = "^[\\p{Alnum}][\\p{Alnum} ]*$";

    public final String requestName;

    /**
     * Constructs a {@code Request}.
     *
     * @param requestName A valid request name.
     */
    public Request(String requestName) {
        requireNonNull(requestName);
        checkArgument(isValidRequestName(requestName), MESSAGE_CONSTRAINTS);
        this.requestName = requestName;
    }

    /**
     * Returns true if a given string is a valid request name.
     */
    public static boolean isValidRequestName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if both requests have the same name.
     * This defines a weaker notion of equality between two requests.
     */
    public boolean isSameRequest(Request otherRequest) {
        if (otherRequest == this) {
            return true;
        }

        return otherRequest != null
                && otherRequest.requestName.toLowerCase().equals(requestName.toLowerCase());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Request)) {
            return false;
        }

        Request otherRequest = (Request) other;
        return requestName.equals(otherRequest.requestName);
    }

    @Override
    public int hashCode() {
        return requestName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + requestName + ']';
    }

}
