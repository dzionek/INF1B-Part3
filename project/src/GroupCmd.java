import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * Group command, which groups and displays books by their title or author.
 */
public class GroupCmd extends LibraryCommand {
    /** Message displayed before printing groups. Followed by {@link DataType} value. */
    private static final String GROUPED_MESSAGE = "Grouped data by ";
    /** Message displayed if a library is empty. */
    private static final String EMPTY_LIBRARY_MESSAGE = "The library has no book entries.";
    /** String denoting titles starting with a digit, when grouping by title. */
    private static final String DIGITS_GROUP = "[0-9]";
    /** String printed before a group, followed by group's letter or title. */
    private static final String GROUP_HEADER = "## ";

    private DataType mode;

    public GroupCmd(String argumentInput) {
        super(CommandType.GROUP, argumentInput);
    }

    @Override
    protected boolean parseArguments(String argumentInput) {
        Objects.requireNonNull(argumentInput, "Given input argument must not be null.");

        for (DataType legalMode : DataType.values()) {
            String legalModeStr = legalMode.name();
            if (argumentInput.equals(legalModeStr)) {
                mode = legalMode;
                return true;
            }
        }
        return false;
    }

    @Override
    public void execute(LibraryData data) {
        Objects.requireNonNull(data, "Library data must not be null.");
        List<BookEntry> books = LibraryUtils.getNonNullBookData(data);
        if (books.isEmpty()) {
            System.out.println(EMPTY_LIBRARY_MESSAGE);
        } else {
            Objects.requireNonNull(mode, "Mode must not be null.");
            System.out.println(GROUPED_MESSAGE + mode.name());
            switch (mode) {
                case TITLE:
                    groupByTitle(books);
                    break;
                case AUTHOR:
                    groupByAuthor(books);
                    break;
                default:
                    throw new IllegalArgumentException("The given mode is invalid.");
            }
        }
    }

    private void groupByTitle(List<BookEntry> books) {
        List<String> listOfTitles = getListOfTitles(books);
        HashMap<String, ArrayList<String>> dictionaryOfTitles = groupByFirstLetter(listOfTitles);
        printGrouped(dictionaryOfTitles);
    }

    private List<String> getListOfTitles(List<BookEntry> books) {
        ArrayList<String> listOfTitles = new ArrayList<>();
        for (BookEntry book : books) {
            listOfTitles.add(book.getTitle());
        }
        return listOfTitles;
    }

    private HashMap<String, ArrayList<String>> groupByFirstLetter(List<String> values) {
        HashMap<String, ArrayList<String>> dictionary = new HashMap<>();
        for (String value : values) {
            char firstLetter = value.charAt(0);
            String key;
            if (Character.isDigit(firstLetter)) {
                key = DIGITS_GROUP;
            } else {
                key = Character.toString(Character.toUpperCase(firstLetter));
            }
            packToDictionary(key, value, dictionary);
        }
        return dictionary;
    }

    private void packToDictionary(String key, String value, HashMap<String, ArrayList<String>> dictionary) {
        if (dictionary.containsKey(key)) {
            dictionary.get(key).add(value);
        } else {
            ArrayList<String> values = new ArrayList<>();
            values.add(value);
            dictionary.put(key, values);
        }
    }

    private void printGrouped(HashMap<String, ArrayList<String>> dictionary) {
        String[] keys = dictionary.keySet().toArray(new String[dictionary.size()]);
        Arrays.sort(keys);
        for (String key : keys) {
            System.out.println(GROUP_HEADER + key);
            for (String value : dictionary.get(key)) {
                System.out.println(value);
            }
        }
    }

    private void groupByAuthor(List<BookEntry> books) {
        HashMap<String, ArrayList<String>> authorsTitles = getAuthorsTitles(books);
        printGrouped(authorsTitles);
    }

    private HashMap<String, ArrayList<String>> getAuthorsTitles(List<BookEntry> books) {
        HashMap<String, ArrayList<String>> authorsTitles = new HashMap<>();
        for (BookEntry book : books) {
            String title = book.getTitle();
            for (String author : book.getAuthors()) {
                packToDictionary(author, title, authorsTitles);
            }
        }
        return authorsTitles;
    }
}