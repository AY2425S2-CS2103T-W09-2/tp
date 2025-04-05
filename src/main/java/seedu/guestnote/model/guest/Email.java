package seedu.guestnote.model.guest;

import static java.util.Objects.requireNonNull;
import static seedu.guestnote.commons.util.AppUtil.checkArgument;

/**
 * Represents a Guest's email in the guestnote book.
 * Guarantees: immutable; is valid as declared in {@link #isValidEmail(String)}
 */
public class Email {

    public static final int MAX_EMAIL_LENGTH = 254;
    private static final String SPECIAL_CHARACTERS = "+_.-";
    public static final String MESSAGE_CONSTRAINTS = "Emails should be in the format local-part@domain.top-level-domain"
            + " and must follow these rules:\n"
            + "1. The entire email must not exceed 254 characters.\n"
            + "2. The local-part (before the '@') must:\n"
            + "   - Contain only alphanumeric characters and the special characters: " + SPECIAL_CHARACTERS + "\n"
            + "   - Not start or end with a special character.\n"
            + "3. The domain (after the '@') must:\n"
            + "   - Consist of domain labels separated by periods (e.g., example.com, school.edu.sg).\n"
            + "   - Each label must start and end with an alphanumeric character.\n"
            + "   - Labels may contain hyphens internally, but not at the start or end.\n"
            + "   - The final label (top-level domain) must be at least 1 character long and contain only letters.";

    // alphanumeric and special characters
    private static final String ALPHANUMERIC_NO_UNDERSCORE = "[^\\W_]+";
    private static final String LOCAL_PART_REGEX = "^" + ALPHANUMERIC_NO_UNDERSCORE
            + "([" + SPECIAL_CHARACTERS + "]" + ALPHANUMERIC_NO_UNDERSCORE + ")*";
    private static final String DOMAIN_PART_REGEX = ALPHANUMERIC_NO_UNDERSCORE
            + "(-" + ALPHANUMERIC_NO_UNDERSCORE + ")*";
    private static final String DOMAIN_LAST_PART_REGEX = "[a-zA-Z]{2,}";
    private static final String DOMAIN_REGEX = "(" + DOMAIN_PART_REGEX + "\\.)+" + DOMAIN_LAST_PART_REGEX;
    public static final String VALIDATION_REGEX = LOCAL_PART_REGEX + "@" + DOMAIN_REGEX;


    public final String value;

    /**
     * Constructs an {@code Email}.
     *
     * @param email A valid email guestnote.
     */
    public Email(String email) {
        requireNonNull(email);
        checkArgument(isValidEmail(email), MESSAGE_CONSTRAINTS);
        value = email;
    }

    /**
     * Returns if a given string is a valid email.
     */
    public static boolean isValidEmail(String email) {
        return email.length() <= MAX_EMAIL_LENGTH && email.matches(VALIDATION_REGEX);
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

        // instanceof handles nulls
        if (!(other instanceof Email)) {
            return false;
        }

        Email otherEmail = (Email) other;
        return value.equals(otherEmail.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
