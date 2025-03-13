package seedu.guestnote.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.guestnote.model.GuestBook;
import seedu.guestnote.model.ReadOnlyGuestBook;
import seedu.guestnote.model.guest.*;
import seedu.guestnote.model.request.Request;

/**
 * Contains utility methods for populating {@code GuestBook} with sample data.
 */
public class SampleDataUtil {
    public static Guest[] getSamplePersons() {
        return new Guest[] {
            new Guest(new GuestId("abcd"), new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new RoomNumber("12-33"),
                getTagSet("friends")),
            new Guest(new GuestId("sjdk"), new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new RoomNumber("23-32"),
                getTagSet("colleagues", "friends")),
            new Guest(new GuestId("ldkf"), new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new RoomNumber("01-57"),
                getTagSet("neighbours")),
            new Guest(new GuestId("dfds"), new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new RoomNumber("04-22"),
                getTagSet("family")),
            new Guest(new GuestId("lsoc"), new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new RoomNumber("02-23"),
                getTagSet("classmates")),
            new Guest(new GuestId("lsoc"), new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new RoomNumber("11-33"),
                getTagSet("colleagues"))
        };
    }

    public static ReadOnlyGuestBook getSampleAddressBook() {
        GuestBook sampleAb = new GuestBook();
        for (Guest sampleGuest : getSamplePersons()) {
            sampleAb.addGuest(sampleGuest);
        }
        return sampleAb;
    }

    /**
     * Returns a request set containing the list of strings given.
     */
    public static Set<Request> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Request::new)
                .collect(Collectors.toSet());
    }

}
