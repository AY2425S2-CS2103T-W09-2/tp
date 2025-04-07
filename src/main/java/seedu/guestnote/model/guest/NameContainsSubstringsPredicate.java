package seedu.guestnote.model.guest;

import java.util.List;
import java.util.function.Predicate;

import seedu.guestnote.commons.util.StringUtil;
import seedu.guestnote.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Guest}'s {@code Name} matches any of the keywords given.
 */
public class NameContainsSubstringsPredicate implements Predicate<Guest> {
    private final List<String> keywords;

    public NameContainsSubstringsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Guest guest) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsSubstringIgnoreCase(guest.getName().fullName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NameContainsSubstringsPredicate)) {
            return false;
        }

        NameContainsSubstringsPredicate otherNameContainsSubstringsPredicate = (NameContainsSubstringsPredicate) other;
        return keywords.equals(otherNameContainsSubstringsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
