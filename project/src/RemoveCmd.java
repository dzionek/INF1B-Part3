import java.awt.print.Book;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class RemoveCmd extends LibraryCommand {

    private static final String AUTHOR_ARG = "AUTHOR ";
    private static final String TITLE_ARG = "TITLE ";

    private String mode;
    private String modeParameter;

    public RemoveCmd(String argumentInput) {
        super(CommandType.REMOVE, argumentInput);
    }

    @Override
    protected boolean parseArguments(String argumentInput) {
        Objects.requireNonNull(argumentInput, "Given input argument must not be null.");

        if (argumentInput.startsWith(AUTHOR_ARG)) {
            mode = AUTHOR_ARG;
        } else if (argumentInput.startsWith(TITLE_ARG)) {
            mode = TITLE_ARG;
        } else {
            return false;
        }

        modeParameter = argumentInput.substring(mode.length());
        return !modeParameter.isBlank();
    }

    @Override
    public void execute(LibraryData data) {
        Objects.requireNonNull(data, "Library data must not be null.");
        List<BookEntry> books = data.getNonNullBookData();
        Iterator<BookEntry> booksIterator = books.iterator();

        if (mode.equals(TITLE_ARG)) {
            removeTitle(booksIterator);
        } else {
            removeAuthor(booksIterator);
        }
    }

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

    private void printRemoveTitle(boolean isRemoved) {
        if (isRemoved) {
            System.out.println(modeParameter + ": removed successfully.");
        } else {
            System.out.println(modeParameter + ": not found.");
        }
    }

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

    private void printRemoveAuthor(int numberOfRemoved) {
        System.out.println(numberOfRemoved + " books removed for author: " + modeParameter);
    }
}
