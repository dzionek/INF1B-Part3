import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * Remove command used to delete entries from a library
 * if a given {@link BookField} value is common for them.
 */
public class RemoveCmd extends LibraryCommand {

    /** Gap between arguments in a line given by user. */
    private static final String PADDING = " ";
    /** Message displayed after removing books by an author. */
    private static final String REMOVE_AUTHOR_MESSAGE = " books removed for author: ";
    /** Message displayed if there is no book with a given title. */
    private static final String REMOVE_TITLE_NOT_FOUND_MESSAGE = ": not found.";
    /** Message displayed after successfully removing books by a title. */
    private static final String REMOVE_TITLE_SUCCESSFULLY = ": removed successfully.";


    /** One of {@link BookField} values. */
    private BookField mode;
    /** Full information of {@link BookField} value
     *  depending what {@link RemoveCmd#mode} is.
     */
    private String modeParameter;

    /** Create a remove command.
     *
     * @param argumentInput input is expected to be of the form
     *                      "{@link RemoveCmd#mode} {@link RemoveCmd#modeParameter}"
     * @throws IllegalArgumentException if given arguments are invalid.
     * @throws NullPointerException if given arguments are null.
     * @see LibraryCommand#LibraryCommand for errors handling.
     * @see RemoveCmd#parseArguments for {@link RemoveCmd#mode}
     *      and {@link RemoveCmd#modeParameter} initialisation.
     */
    public RemoveCmd(String argumentInput) {
        super(CommandType.REMOVE, argumentInput);
    }

    /**
     * Check if the argument starts with {@link BookField} value,
     * followed by whitespace and non-blank word.
     *
     * Assign {@link RemoveCmd#mode} and {@link RemoveCmd#modeParameter} fields
     * to an instance.
     *
     * @param argumentInput argument input for this command
     * @return {@code true} if the argument is valid, otherwise {@code false}.
     */
    @Override
    protected boolean parseArguments(String argumentInput) {
        Objects.requireNonNull(argumentInput, "Given input argument must not be null.");

        boolean isFirstArgValid = false;
        for (BookField bookField : BookField.values()) {
            String legalMode = bookField.name();
            if (argumentInput.startsWith(legalMode + PADDING)) {
                isFirstArgValid = true;
                mode = bookField;
                break;
            }
        }

        if (!isFirstArgValid) {
            return false;
        } else {
            modeParameter = argumentInput.substring(mode.name().length() + PADDING.length());
            return !modeParameter.isBlank();
        }
    }

    /**
     * Execute the remove command and remove all books with a certain title/author.
     * @param data book data to be considered for command execution.
     * @throws NullPointerException if given parameter is null, list of books is null,
     *                              or any entry in the list of books is null.
     * @throws IllegalArgumentException if instance mode is invalid.
     */
    @Override
    public void execute(LibraryData data) {
        Objects.requireNonNull(data, "Library data must not be null.");
        List<BookEntry> books = Utils.getNonNullBookData(data);
        Iterator<BookEntry> booksIterator = books.iterator();

        switch (mode) {
            case TITLE:
                removeTitle(booksIterator);
                break;
            case AUTHOR:
                removeAuthor(booksIterator);
                break;
            default:
                throw new IllegalArgumentException("The given mode is invalid.");
        }
    }

    /**
     * Remove a book with a given title.
     * If a book was successfully removed or not found, prints a special message.
     *
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
     * Display a message after removing a book by a title.
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
     * After that, print a special message {@link RemoveCmd#printRemoveAuthor}
     *
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
     * Display a message after removing books by an author.
     * @param numberOfRemoved number of books that were removed.
     */
    private void printRemoveAuthor(int numberOfRemoved) {
        System.out.println(numberOfRemoved + REMOVE_AUTHOR_MESSAGE + modeParameter);
    }
}
