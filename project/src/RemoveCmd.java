import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * Remove command, which deletes entries from a library with a certain title or author.
 */
public class RemoveCmd extends LibraryCommand {

    /** Gap between two arguments. */
    private static final String PADDING = " ";
    /** Message displayed after removing books by an author. */
    private static final String REMOVE_AUTHOR_MESSAGE = " books removed for author: ";
    /** Message displayed if there is no book with a given title. */
    private static final String REMOVE_TITLE_NOT_FOUND_MESSAGE = ": not found.";
    /** Message displayed after successfully removing books by a title. */
    private static final String REMOVE_TITLE_SUCCESSFULLY = ": removed successfully.";

    /** There are two modes of an instance, either {@link BookField#AUTHOR} or {@link BookField#TITLE}*/
    private String mode;
    /** Full title or author name depending which {@link RemoveCmd#mode} we have.*/
    private String modeParameter;

    /**
     * @param argumentInput input is expected to be of the form
     *                      "{@link RemoveCmd#mode} {@link RemoveCmd#modeParameter}"
     * @throws IllegalArgumentException if given arguments are invalid.
     * @throws NullPointerException if given arguments are null.
     */
    public RemoveCmd(String argumentInput) {
        super(CommandType.REMOVE, argumentInput);
    }

    /**
     * Checks if the argument starts with {@link BookField#AUTHOR} or {@link BookField#TITLE}
     * followed by one whitespace and non-blank author/title.
     * @param argumentInput argument input for this command
     * @return {@code true} if the argument is valid, otherwise {@code false}.
     */
    @Override
    protected boolean parseArguments(String argumentInput) {
        Objects.requireNonNull(argumentInput, "Given input argument must not be null.");

        if (argumentInput.startsWith(BookField.AUTHOR.name() + PADDING)) {
            mode = BookField.AUTHOR.name();
        } else if (argumentInput.startsWith(BookField.TITLE.name() + PADDING)) {
            mode = BookField.TITLE.name();
        } else {
            return false;
        }

        modeParameter = argumentInput.substring(mode.length() + PADDING.length());
        return !modeParameter.isBlank();
    }

    /**
     * Executes the remove command and remove all books with a certain title/author.
     * @param data book data to be considered for command execution.
     */
    @Override
    public void execute(LibraryData data) {
        Objects.requireNonNull(data, "Library data must not be null.");
        List<BookEntry> books = Utils.getNonNullBookData(data);
        Iterator<BookEntry> booksIterator = books.iterator();

        if (mode.equals(BookField.TITLE.name())) {
            removeTitle(booksIterator);
        } else {
            removeAuthor(booksIterator);
        }
    }

    /**
     * Remove a book with a given title, if possible.
     * @param booksIterator iterator of a list of all books.
     */
    private void removeTitle(Iterator<BookEntry> booksIterator) {
        boolean isRemoved = false;
        while (booksIterator.hasNext() && !isRemoved) {
            BookEntry book = booksIterator.next();
            String bookTitle = book.getTitle();
            if (bookTitle.equals(modeParameter)) {
                booksIterator.remove();
                isRemoved = true;
            }
        }

        printRemoveTitle(isRemoved);
    }

    /**
     * Displays a message after removing a book by a title.
     * @param isRemoved {@code true} if a book was removed,
     *                  {@code false} if no book was removed.
     */
    private void printRemoveTitle(boolean isRemoved) {
        if (isRemoved) {
            System.out.println(modeParameter + REMOVE_TITLE_SUCCESSFULLY);
        } else {
            System.out.println(modeParameter + REMOVE_TITLE_NOT_FOUND_MESSAGE);
        }
    }

    /**
     * Remove a book with a given title, if possible.
     * @param booksIterator iterator of a list of all books.
     */
    private void removeAuthor(Iterator<BookEntry> booksIterator) {
        int numberOfRemoved = 0;
        while (booksIterator.hasNext()) {
            BookEntry book = booksIterator.next();
            String[] authors = book.getAuthors();
            for (String author : authors) {
                if (author.equals(modeParameter)) {
                    booksIterator.remove();
                    numberOfRemoved++;
                    break;
                }
            }
        }
        printRemoveAuthor(numberOfRemoved);
    }

    /**
     * Displays a message after removing books by an author.
     * @param numberOfRemoved number of books that were removed.
     */
    private void printRemoveAuthor(int numberOfRemoved) {
        System.out.println(numberOfRemoved + REMOVE_AUTHOR_MESSAGE + modeParameter);
    }
}
