package seedu.guestnote.model.guest;

import static java.util.Objects.requireNonNull;
import static seedu.guestnote.commons.util.AppUtil.checkArgument;

/**
 * Represents a Guest's phone number in the guestnote book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPhone(String)}
 */
public class Phone {


    public static final String MESSAGE_CONSTRAINTS =
            "Phone numbers should contain 4 to 20 digits";
    public static final String VALIDATION_REGEX = "^\\+?[\\d ]+$";
    public final String value;

    /**
     * Constructs a {@code Phone}.
     *
     * @param phone A valid phone number.
     */
    public Phone(String phone) {
        requireNonNull(phone);
        if (phone.isEmpty()) {
            this.value = null; // Or set to a default value if desired
        } else {
            checkArgument(isValidPhone(phone), MESSAGE_CONSTRAINTS);
            this.value = phone;
        }
    }

    /**
     * Returns true if a given string is a valid phone number.
     */
    public static boolean isValidPhone(String test) {
        if (!test.matches(VALIDATION_REGEX)) {
            return false;
        }

        // Count only the digits
        int digitCount = test.replaceAll("\\D", "").length();
        return digitCount >= 4 && digitCount <= 20;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        Phone otherPhone = (Phone) other;
        if (this.value == null || otherPhone.value == null) {
            return this.value == otherPhone.value;
        }
        return this.value.equals(otherPhone.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
