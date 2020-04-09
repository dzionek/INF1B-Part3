import java.util.List;
import java.util.Objects;

/**
 * Class with helper functions regarding library and books.
 */
public class LibraryUtils {
    /**
     * Gets list of books in library if this list is not null and all entries are not null.
     * @param data - a given library.
     * @return list of books of a library
     * @throws NullPointerException if library is empty, list of books is empty, or any book is empty.
     */
    public static List<BookEntry> getNonNullBookData(LibraryData data) {
        Objects.requireNonNull(data, "Library data must not be null.");
        List<BookEntry> books = data.getBookData();

        Objects.requireNonNull(books, "List of books must not be null.");

        for (BookEntry book : books) {
            Objects.requireNonNull(book, "Book in a list must not be null.");
        }

        return books;
    }
}
