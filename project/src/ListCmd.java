import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * List command, which shows all entries in a library.
 */
public class ListCmd extends LibraryCommand {

    /** Command argument, which display books in a shorter form. {@link ListCmd#shortPrint} */
    private static final String SHORT_ARG = "short";
    /** Command argument, which display books in a longer form. {@link ListCmd#longPrint} */
    private static final String LONG_ARG  = "long";
    /** Message displayed when there are no books in a library. */
    private static final String EMPTY_MESSAGE = "The library has no book entries.";
    /** Message displayed when there are some books (n) in a library, of the form "n"+HEADER. */
    private static final String HEADER = " books in library:";

    /** instance mode of displaying all books, either {@value SHORT_ARG} or {@value LONG_ARG}. */
    private String mode;

    /**
     * Create a list method.
     * @param argumentInput argument input is expected to be blank,
     *                      or {@value SHORT_ARG}, or {@value LONG_ARG}.
     * @throws IllegalArgumentException if given arguments are invalid.
     * @throws NullPointerException if given arguments are null.
     */
    public ListCmd(String argumentInput) {
        super(CommandType.LIST, argumentInput);
    }

    /**
     * Checks if an argument is blank, {@value SHORT_ARG}, or {@value LONG_ARG}.
     * @param argumentInput argument input list command.
     * @return true if it is as described above, otherwise false.
     */
    @Override
    protected boolean parseArguments(String argumentInput) {
        if (argumentInput.equals(SHORT_ARG) || argumentInput.equals("")) {
            mode = SHORT_ARG;
            return true;
        } else if (argumentInput.equals(LONG_ARG)){
            mode = LONG_ARG;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Display all books in a library.
     * @param data book data to be considered for command execution.
     * @throws NullPointerException if a given argument is null, instance mode is null,
     *                              a list of books is null, or a book in a list is null.
     */
    @Override
    public void execute(LibraryData data) {
        Objects.requireNonNull(data, "Library data must not be null.");
        Objects.requireNonNull(mode, "Mode must not be null.");

        List<BookEntry> books = data.getBookData();

        requireNonNullBooks(books);

        if (books.isEmpty()) {
            System.out.println(EMPTY_MESSAGE);
        } else {
            headerPrint(books);
            for (BookEntry book : books) {
               if (mode.equals(SHORT_ARG)) {
                   shortPrint(book);
               } else {
                   longPrint(book);
               }
            }
        }
    }

    private void requireNonNullBooks(List<BookEntry> books) {
        Objects.requireNonNull(books, "List of books must not be null.");

        for (BookEntry book : books) {
            Objects.requireNonNull(book, "Book in a list must not be null.");
        }
    }

    /**
     * Prints header with a number of books in a library.
     * @param books list of all books in a library which is not null.
     */
    private void headerPrint(List<BookEntry> books) {
        System.out.println(books.size() + HEADER);
    }

    /**
     * Print a book in a shorter form (only titles).
     * @param book a book in a library which is not null.
     */
    private void shortPrint(BookEntry book) {
        System.out.println(book.getTitle());
    }

    /**
     * Print a book in a longer form. {@link BookEntry#toString}
     * @param book a book in a library which is not null.
     */
    private void longPrint(BookEntry book) {
        System.out.println(book);
    }
}
