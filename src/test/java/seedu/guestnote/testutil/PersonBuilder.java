package seedu.guestnote.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.guestnote.model.guest.Email;
import seedu.guestnote.model.guest.Guest;
import seedu.guestnote.model.guest.Name;
import seedu.guestnote.model.guest.Phone;
import seedu.guestnote.model.guest.RoomNumber;
import seedu.guestnote.model.request.Request;
import seedu.guestnote.model.util.SampleDataUtil;

/**
 * A utility class to help with building Guest objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ROOM_NUMBER = "00-00";

    private Name name;
    private Phone phone;
    private Email email;
    private RoomNumber roomNumber;
    private Set<Request> requests;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        roomNumber = new RoomNumber(DEFAULT_ROOM_NUMBER);
        requests = new HashSet<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code guestToCopy}.
     */
    public PersonBuilder(Guest guestToCopy) {
        name = guestToCopy.getName();
        phone = guestToCopy.getPhone();
        email = guestToCopy.getEmail();
        roomNumber = guestToCopy.getRoomNumber();
        requests = new HashSet<>(guestToCopy.getRequests());
    }

    /**
     * Sets the {@code Name} of the {@code Guest} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code requests} into a {@code Set<Request>} and set it to the {@code Guest} that we are building.
     */
    public PersonBuilder withTags(String ... tags) {
        this.requests = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Guest} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Guest} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code RoomNumber} of the {@code Guest} that we are building.
     */
    public PersonBuilder withRoomNumber(String roomNumber) {
        this.roomNumber = new RoomNumber(roomNumber);
        return this;
    }

    public Guest build() {
        return new Guest(name, phone, email, roomNumber, requests);
    }

}
