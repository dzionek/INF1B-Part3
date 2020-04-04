import java.util.List;
import java.util.Objects;

public class SearchCmd extends LibraryCommand {

    private static final String NOTHING_FOUND_MESSAGE = "No hits found for search term: ";
    private String searchValue;

    public SearchCmd(String argumentInput) {
        super(CommandType.SEARCH, argumentInput);
    }

    @Override
    protected boolean parseArguments(String argumentInput) {
        Objects.requireNonNull(argumentInput, "Given input argument must not be null.");
        if (argumentInput.isBlank() || argumentInput.contains(" ")) {
            return false;
        } else {
            searchValue = argumentInput;
            return true;
        }
    }

    @Override
    public void execute(LibraryData data) {
        Objects.requireNonNull(data, "Library data must not be null.");
        List<BookEntry> books = data.getNonNullBookData();
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

    private static boolean containsIgnoreCase(String str, String subString) {
        return str.toLowerCase().contains(subString.toLowerCase());
    }
}
