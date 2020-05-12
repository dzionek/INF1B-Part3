import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Static utility class with helper functions needed for commands execution.
 */
public final class Utils {

    /** Not to be used. */
    private Utils() {
        throw new UnsupportedOperationException("This constructor should never be used.");
    }

    /**
     * Get list of books in library if this list is not null and all entries are not null.
     * Otherwise, throw a corresponding exception.
     *
     * @param data a given library of books.
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

    /**
     * Add key -> value pair into a given map. Values of a given key are stored
     * in a HashSet, if a given key already exists, its value is added to the set of values.
     * @param key key to be added to a map.
     * @param value value to be added to a map.
     * @param map map we add key -> value pair into.
     */
    public static <K, V> void packToMap(K key, V value, Map<K, Set<V>> map) {
        if (map.containsKey(key)) {
            map.get(key).add(value);
        } else {
            Set<V> values = new HashSet<>();
            values.add(value);
            map.put(key, values);
        }
    }

    /**
     * Get string representation of an array without square brackets.
     * @param array array of any non-primitive type, e.g. new Integer[] {1, 2, 3, 4, 5}.
     * @return array string without square brackets, e.g. "1, 2, 3, 4, 5".
     */
    public static <T> String arrayWithoutBrackets(T[] array) {
        return Arrays.toString(array).replace("[", "").replace("]", "");
    }

    /**
     * Check whether a given string contains a given substring (case-insensitive).
     * @param str we check if a substring is contained in it.
     * @param subString we check if it is contained in a string.
     * @return {@code true} if str contains substring, otherwise {@code false}.
     */
    public static boolean containsIgnoreCase(String str, String subString) {
        return str.toLowerCase().contains(subString.toLowerCase());
    }
}
