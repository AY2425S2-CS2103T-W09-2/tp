package seedu.guestnote.model.guest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.guestnote.model.request.Request;
import seedu.guestnote.model.request.UniqueRequestList;

public class AnyFieldContainsKeywordsPredicateTest {

    /**
     * Helper method to create a Guest object.
     * It supplies dummy values for phone, email, room number, and status,
     * while allowing you to specify the name and email.
     * The 'requests' parameter is a vararg of Strings representing each request.
     */
    private Guest createGuest(String name, String email, String... requests) {
        UniqueRequestList uniqueRequests = new UniqueRequestList();
        for (String req : requests) {
            // Assuming Request has a constructor that accepts a String.
            uniqueRequests.add(new Request(req));
        }
        // Use dummy values for phone, room number, and status.
        return new Guest(new Name(name), new Phone("12345678"), new Email(email),
                new RoomNumber("10-11"), Status.CHECKED_IN, uniqueRequests);
    }

    @Test
    public void test_anyFieldContainsKeywords_returnsTrueWhenNameMatches() {
        // Create predicates for name and email.
        Predicate<Guest> namePredicate =
                new FieldContainsKeywordsPredicate<>(
                        guest -> guest.getName().toString(), Collections.singletonList("Alice"));
        Predicate<Guest> emailPredicate =
                new FieldContainsKeywordsPredicate<>(Guest::getEmail, Collections.singletonList("alice@example.com"));
        AnyFieldContainsKeywordsPredicate compositePredicate =
                new AnyFieldContainsKeywordsPredicate(Arrays.asList(namePredicate, emailPredicate));

        // Create a guest whose name contains "Alice", and the email is the predicate.
        Guest guest = createGuest("Alice Wonderland", "alice@example.com", "Need wifi");
        assertTrue(compositePredicate.test(guest));
    }

    @Test
    public void test_anyFieldContainsKeywords_returnsTrueWhenEmailMatches() {
        // Create predicates for name and email.
        Predicate<Guest> namePredicate =
                new FieldContainsKeywordsPredicate<>(
                        guest -> guest.getName().toString(), Collections.singletonList("Alice"));
        Predicate<Guest> emailPredicate =
                new FieldContainsKeywordsPredicate<>(Guest::getEmail, Collections.singletonList("alice@example.com"));
        AnyFieldContainsKeywordsPredicate compositePredicate =
                new AnyFieldContainsKeywordsPredicate(Arrays.asList(namePredicate, emailPredicate));

        // Create a guest that does not match the name predicate but does have matching email.
        // Since our helper always uses dummy@example.com, we manually construct this Guest.
        UniqueRequestList uniqueRequests = new UniqueRequestList();
        Guest guestWithMatchingEmail = new Guest(new Name("Bob"), new Phone("92345678"),
                new Email("alice@example.com"), new RoomNumber("10-01"), Status.CHECKED_IN, uniqueRequests);
        assertTrue(compositePredicate.test(guestWithMatchingEmail));
    }

    @Test
    public void test_anyFieldContainsKeywords_returnsFalseWhenNoFieldsMatch() {
        // Create predicates that search for non-existent values.
        Predicate<Guest> namePredicate =
                new FieldContainsKeywordsPredicate<>(
                        guest -> guest.getName().toString(), Collections.singletonList("Charlie"));
        Predicate<Guest> emailPredicate =
                new FieldContainsKeywordsPredicate<>(Guest::getEmail, Collections.singletonList("charlie@example.com"));
        AnyFieldContainsKeywordsPredicate compositePredicate =
                new AnyFieldContainsKeywordsPredicate(Arrays.asList(namePredicate, emailPredicate));

        // Create a guest that does not match either predicate.
        Guest guest = createGuest("Alice Wonderland", "dummy@example.com", "Need wifi");
        assertFalse(compositePredicate.test(guest));
    }
}
