import java.util.List;
import java.util.Objects;

/**
 * List command used to show all entries in a library.
 */
public class ListCmd extends LibraryCommand {

    /** Command argument, which displays books in a shorter form - {@link ListCmd#shortPrint} */
    private static final String SHORT_ARG = "short";
    /** Command argument, which displays books in a longer form - {@link ListCmd#longPrint} */
    private static final String LONG_ARG  = "long";
    /** Message displayed if there are no books in a library. */
    private static final String EMPTY_MESSAGE = "The library has no book entries.";
    /** Message displayed when there are some books (n) in a library, of the form "n"+HEADER. */
    private static final String HEADER = " books in library:";

    /** Instance mode of displaying all books, either {@value SHORT_ARG} or {@value LONG_ARG}. */
    private String mode;

    /**
     * Create a list method.
     * @param argumentInput argument input is expected to be blank,
     *                      or {@value SHORT_ARG}, or {@value LONG_ARG}.
     * @throws IllegalArgumentException if given arguments are invalid.
     * @throws NullPointerException if given arguments are null.
     * @see LibraryCommand#LibraryCommand for errors handling.
     * @see ListCmd#parseArguments for {@link ListCmd#mode} initialisation.
     */
    public ListCmd(String argumentInput) {
        super(CommandType.LIST, argumentInput);
    }

    /**
     * Check if an argument is blank, {@value SHORT_ARG}, or {@value LONG_ARG}.
     *
     * Set an instance field {@link ListCmd#mode} according to that.
     * Blank arguments is considered as {@value SHORT_ARG}.
     *
     * @param argumentInput argument input list command.
     * @return {@code true} if it is as described above, otherwise {@code false}.
     */
    @Override
    protected boolean parseArguments(String argumentInput) {
        Objects.requireNonNull(argumentInput, "Given input argument must not be null.");

        if (argumentInput.equals(SHORT_ARG) || argumentInput.isBlank()) {
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

        List<BookEntry> books = Utils.getNonNullBookData(data);

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

    /**
     * Print header with a number of books in a library.
     * @param books list of all books in a library which is not null.
     */
    private static void headerPrint(List<BookEntry> books) {
        System.out.println(books.size() + HEADER);
    }

    /**
     * Print a book in a shorter form (only titles).
     * @param book a book in a library which is not null.
     */
    private static void shortPrint(BookEntry book) {
        System.out.println(book.getTitle());
    }

    /**
     * Print a book in a longer form - the entire {@link BookEntry#toString}.
     * @param book a book in a library which is not null.
     */
    private static void longPrint(BookEntry book) {
        System.out.println(book);
    }
}
