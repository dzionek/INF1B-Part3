import java.util.Arrays;
import java.util.Objects;

/**
 * Immutable class encapsulating data for a single book entry.
 */
public class BookEntry {

    /** Minimum rating of a book. */
    private static final int MIN_RATING = 0;
    /** Maximum rating of a book. */
    private static final int MAX_RATING = 5;
    /** Minimum number of pages in a book. */
    private static final int MIN_NUM_PAGES = 0;
    /** Hash code multiplier for objects. */
    private static final int HASH_CODE_MUL = 31;

    /** Title of a book. */
    private final String title;
    /** Names of authors of a book.*/
    private final String[] authors;
    /** Rating of a book. */
    private final float rating;
    /** ISBN of a book.*/
    private final String ISBN;
    /** Number of pages of a book. */
    private final int pages;

    /**
     * Constructor of a BookEntry instance. Checks if all parameters are valid.
     * @param title title of a book.
     * @param authors names of authors of a book.
     * @param rating rating of a book.
     * @param ISBN ISBN of a book.
     * @param pages number of pages of a book.
     * @throws NullPointerException in {@link BookEntry#validateParameters}.
     * @throws IllegalArgumentException in {@link BookEntry#validateParameters}.
     */
    public BookEntry(String title, String[] authors, float rating, String ISBN, int pages) {
        validateParameters(title, authors, rating, ISBN, pages);
        this.title = title;
        this.authors = authors;
        this.rating = rating;
        this.ISBN = ISBN;
        this.pages = pages;
    }

    /**
     * Checks if all parameters given to the constructor are valid.
     * @see BookEntry#BookEntry for the description of parameters.
     * @throws NullPointerException if any object parameter is null.
     * @throws IllegalArgumentException if rating is not between {@value MIN_RATING} and {@value MAX_RATING},
     *                                  or number of pages is less than {@value MIN_NUM_PAGES}.
     */
    private static void validateParameters(String title, String[] authors, float rating, String ISBN, int pages) {
        Objects.requireNonNull(title, "Title must most not be null.");
        Objects.requireNonNull(authors, "Authors array must not be null.");
        Objects.requireNonNull(ISBN, "ISBN must not be null.");

        requireNonNullAuthorsEntries(authors);
        requireRatingWithinBoundaries(rating);
        requireNonNegativePages(pages);
    }

    /**
     * Checks if all entries in authors array are not null.
     * @param authors array of authors.
     * @throws NullPointerException if any of the entries are null.
     */
    private static void requireNonNullAuthorsEntries(String[] authors) {
        for (String author : authors) {
            Objects.requireNonNull(author, "Authors array entries must not be null.");
        }
    }

    /**
     * Checks if rating is greater that {@value MIN_RATING} and less than {@value MAX_RATING}.
     * @param rating a rating of a book.
     * @throws IllegalArgumentException if the rating is not within boundaries.
     */
    private static void requireRatingWithinBoundaries(float rating) {
        if (rating < MIN_RATING || rating > MAX_RATING) {
            throw new IllegalArgumentException("Rating must be between 0 and 5");
        }
    }

    /**
     * Checks if the number of pages is not negative.
     * @param pages number of pages of a book.
     * @throws IllegalArgumentException if the number is negative.
     */
    private static void requireNonNegativePages(int pages) {
        if (pages < MIN_NUM_PAGES) {
            throw new IllegalArgumentException("Number of pages must not be negative.");
        }
    }

    /**
     * Gets title of an instance.
     * @return {@link BookEntry#title}.
     */
    public String getTitle() { return title; }

    /**
     * Gets authors array of an instance.
     * @return {@link BookEntry#authors}.
     */
    public String[] getAuthors() {
        return authors;
    }

    /**
     * Gets rating of an instance.
     * @return {@link BookEntry#rating}.
     */
    public float getRating() {
        return rating;
    }

    /**
     * Gets ISBN of an instance.
     * @return {@link BookEntry#ISBN}.
     */
    public String getISBN() {
        return ISBN;
    }

    /**
     * Gets pages of an instance.
     * @return {@link BookEntry#pages}.
     */
    public int getPages() {
        return pages;
    }

    /**
     * Gets string representation of an instance.
     * @return string representation of an instance.
     */
    @Override
    public String toString() {
        String authorsPrintable = Utils.arrayWithoutBrackets(authors);
        String ratingTwoDecimalPlaces = String.format("%.2f", rating);

        StringBuilder sb = new StringBuilder();
        sb.append(title).append("\n");
        sb.append("by ").append(authorsPrintable).append("\n");
        sb.append("Rating: ").append(ratingTwoDecimalPlaces).append("\n");
        sb.append("ISBN: ").append(ISBN).append("\n");
        sb.append(pages).append(" pages\n");

        return sb.toString();
    }

    /**
     * Checks if two objects are equal.
     * @param that the second object.
     * @return {@code true} if they are, otherwise {@code false}.
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) return true;
        if (that == null || getClass() != that.getClass()) return false;
        BookEntry bookEntry = (BookEntry) that;
        return Float.compare(bookEntry.rating, rating) == 0 &&
                pages == bookEntry.pages &&
                title.equals(bookEntry.title) &&
                Arrays.equals(authors, bookEntry.authors) &&
                ISBN.equals(bookEntry.ISBN);
    }

    /**
     * Generates the hash code of an instance.
     * @return hash code of an instance.
     */
    @Override
    public int hashCode() {
        int hashCode = Objects.hash(title, rating, ISBN, pages);
        hashCode = HASH_CODE_MUL * hashCode + Arrays.hashCode(authors);
        return hashCode;
    }
}
