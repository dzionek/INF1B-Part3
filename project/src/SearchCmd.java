import java.util.List;
import java.util.Objects;

/**
 * Search command used to search for books with a title containing a given phrase.
 */
public class SearchCmd extends LibraryCommand {

    /** Message displayed when nothing was found. */
    private static final String NOTHING_FOUND_MESSAGE = "No hits found for search term: ";

    /** The phrase we search for. */
    private final String searchValue;

    /** Generate search command. */
    public SearchCmd(String argumentInput) {
        super(CommandType.SEARCH, argumentInput);
        searchValue = argumentInput;
    }

    /**
     * Check whether the given argument is not blank and has only one word.
     * @param argumentInput argument input for this command
     * @return {@code true} if the argument is valid, otherwise {@code false}.
     * @throws NullPointerException if the given argument is null.
     */
    @Override
    protected boolean parseArguments(String argumentInput) {
        Objects.requireNonNull(argumentInput, "Given input argument must not be null.");
        return !argumentInput.isBlank() && !argumentInput.contains(" ");
    }

    /**
     * Execute the command and displays either all books which were found,
     * or a message that nothing was found
     * @param data book data to be considered for command execution.
     * @throws NullPointerException if library data is null, or list of books of the library is null,
     *                              or if any book in this list is null.
     */
    @Override
    public void execute(LibraryData data) {
        Objects.requireNonNull(data, "Library data must not be null.");
        List<BookEntry> books = Utils.getNonNullBookData(data);
        boolean nothingPrinted = true;

        for (BookEntry book : books) {
            String title = book.getTitle();
            if (containsIgnoreCase(title, searchValue)) {
                nothingPrinted = false;
                System.out.println(title);
            }
        }

        if (nothingPrinted) {
            System.out.println(NOTHING_FOUND_MESSAGE + searchValue);
        }
    }

    /**
     * Check whether a given string contains a given substring (case-insensitive).
     * @param str a string we check whether a substring is contained in.
     * @param subString a substring we check whether is contained in a string.
     * @return {@code true} if str contains substring, otherwise {@code false}.
     */
    private static boolean containsIgnoreCase(String str, String subString) {
        return str.toLowerCase().contains(subString.toLowerCase());
    }
}
