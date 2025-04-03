package seedu.guestnote.model.guest;

import java.util.List;
import java.util.function.Predicate;

/**
 * Composite predicate that tests whether any of the given field-specific predicates
 * match a Guest.
 * <p>
 * This class is used to combine multiple predicates (e.g., for name, phone, email, etc.)
 * so that a Guest is considered a match if at least one predicate returns true.
 * </p>
 */
public class AnyFieldContainsKeywordsPredicate implements Predicate<Guest> {
    private final List<Predicate<Guest>> predicates;

    public AnyFieldContainsKeywordsPredicate(List<Predicate<Guest>> predicates) {
        this.predicates = predicates;
    }

    @Override
    public boolean test(Guest guest) {
        // Return true if any predicate matches
        for (Predicate<Guest> predicate : predicates) {
            if (predicate.test(guest)) {
                assert(predicate != null);
                return true;
            }
        }
        return false;
    }
    @Override
    public String toString() { //Only prints out a summary of predicates instead of all predicates
        return getClass().getSimpleName() + "{predicateCount=" + predicates.size() + "}";
    }
}
