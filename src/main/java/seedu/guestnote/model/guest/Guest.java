package seedu.guestnote.model.guest;

import static seedu.guestnote.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;
import java.util.Optional;

import javafx.collections.ObservableList;
import seedu.guestnote.commons.util.ToStringBuilder;
import seedu.guestnote.model.request.Request;
import seedu.guestnote.model.request.UniqueRequestList;


/**
 * Represents a Guest in the guestnote book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Guest {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final RoomNumber roomNumber;
    private final Status status;
    private final UniqueRequestList requests = new UniqueRequestList();

    /**
     * Every field must be present and not null.
     */
    public Guest(Name name, Phone phone, Email email, RoomNumber roomNumber, Status status,
                 UniqueRequestList requests) {
        requireAllNonNull(name, email, requests);
        this.name = name;
        this.phone = phone != null ? phone : new Phone(""); // or use null depending on your preference
        this.email = email;
        this.roomNumber = roomNumber;
        this.status = status;
        this.requests.setRequests(requests);
    }

    public Name getName() {
        return name;
    }

    public Optional<Phone> getPhone() {
        return Optional.ofNullable(phone);
    }

    public Email getEmail() {
        return email;
    }

    public RoomNumber getRoomNumber() {
        return roomNumber;
    }

    public Status getStatus() {
        return status;
    }

    /**
     * Returns an immutable request set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public ObservableList<Request> getRequests() {
        return requests.asUnmodifiableObservableList();
    }

    public String[] getRequestsArray() {
        return requests.asUnmodifiableObservableList().stream().map(Request::toString).toArray(String[]::new);
    }

    /**
     * Returns true if both guests have the same name.
     * This defines a weaker notion of equality between two guests.
     */
    public boolean isSameGuest(Guest otherGuest) {
        if (otherGuest == this) {
            return true;
        }

        return otherGuest != null
                && otherGuest.getEmail().equals(getEmail());
    }

    /**
     * Returns true if both guests have the same identity and data fields.
     * This defines a stronger notion of equality between two guests.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Guest)) {
            return false;
        }

        Guest otherGuest = (Guest) other;
        return name.equals(otherGuest.name)
                && phone.equals(otherGuest.phone)
                && email.equals(otherGuest.email)
                && roomNumber.equals(otherGuest.roomNumber)
                && status.equals(otherGuest.status)
                && requests.equals(otherGuest.requests);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, roomNumber, status, requests);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("roomNumber", roomNumber)
                .add("status", status)
                .add("requests", requests)
                .toString();
    }

}
