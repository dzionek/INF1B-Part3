import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * Group command, which groups and displays books by their title or author.
 */
public class GroupCmd extends LibraryCommand {

    /** Message displayed before printing groups. Followed by {@link BookField} value. */
    private static final String GROUPED_MESSAGE = "Grouped data by ";
    /** Message displayed if a library is empty. */
    private static final String EMPTY_LIBRARY_MESSAGE = "The library has no book entries.";
    /** String denoting titles starting with a digit, when grouping by title. */
    private static final String DIGITS_GROUP = "[0-9]";
    /** String printed before a group, followed by group's name. */
    private static final String GROUP_HEADER = "## ";

    /** Mode of a group command - one of {@link BookField} values. */
    private BookField mode;

    /** Creates group command. */
    public GroupCmd(String argumentInput) {
        super(CommandType.GROUP, argumentInput);
    }

    /**
     * Checks if a given argument is valid (is one of {@link BookField} value).
     * @param argumentInput argument input for this command.
     * @return {@code true} if the argument is valid, otherwise {@code false}.
     * @throws NullPointerException if the given argument is null.
     */
    @Override
    protected boolean parseArguments(String argumentInput) {
        Objects.requireNonNull(argumentInput, "Given input argument must not be null.");

        for (BookField legalMode : BookField.values()) {
            String legalModeStr = legalMode.name();
            if (argumentInput.equals(legalModeStr)) {
                mode = legalMode;
                return true;
            }
        }
        return false;
    }

    /**
     * Executes group command, displays books grouped according to {@link GroupCmd#mode}.
     * If there are no books in a library, prints a special message instead.
     * @param data book data to be considered for command execution.
     * @throws NullPointerException if library, list of books, any book, or mode is null.
     * @throws IllegalArgumentException if an instance's mode is invalid.
     */
    @Override
    public void execute(LibraryData data) {
        Objects.requireNonNull(data, "Library data must not be null.");
        List<BookEntry> books = Utils.getNonNullBookData(data);
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

    /**
     * Groups by title and print all groups.
     * @param books not null and not empty list of books which will be grouped.
     */
    private static void groupByTitle(List<BookEntry> books) {
        List<String> listOfTitles = getListOfTitles(books);
        HashMap<String, ArrayList<String>> dictionaryOfTitles = groupByFirstLetter(listOfTitles);
        printGrouped(dictionaryOfTitles);
    }

    /**
     * Get list of titles of a given list of books.
     * Each book entry is mapped to its title.
     * @param books not null and not empty list of books.
     * @return list of titles in a library.
     */
    private static List<String> getListOfTitles(List<BookEntry> books) {
        ArrayList<String> listOfTitles = new ArrayList<>();
        for (BookEntry book : books) {
            listOfTitles.add(book.getTitle());
        }
        return listOfTitles;
    }

    private static HashMap<String, ArrayList<String>> groupByFirstLetter(List<String> values) {
        HashMap<String, ArrayList<String>> dictionary = new HashMap<>();
        for (String value : values) {
            char firstLetter = value.charAt(0);
            String key;
            if (Character.isDigit(firstLetter)) {
                key = DIGITS_GROUP;
            } else {
                key = Character.toString(Character.toUpperCase(firstLetter));
            }
            Utils.packToDictionary(key, value, dictionary);
        }
        return dictionary;
    }

    /**
     * Prints all elements grouped in a HashMap. Keys are sorted in lexicographical order.
     * @param dictionary a given not null, and not empty HashMap to be printed.
     */
    private static void printGrouped(HashMap<String, ArrayList<String>> dictionary) {
        String[] keys = dictionary.keySet().toArray(new String[dictionary.size()]);
        Arrays.sort(keys);
        for (String key : keys) {
            System.out.println(GROUP_HEADER + key);
            for (String value : dictionary.get(key)) {
                System.out.println(value);
            }
        }
    }

    /**
     * Groups by author, and prints all groups.
     * @param books list of books to be grouped and printed.
     */
    private static void groupByAuthor(List<BookEntry> books) {
        HashMap<String, ArrayList<String>> authorsTitles = getAuthorsTitles(books);
        printGrouped(authorsTitles);
    }

    /**
     * Gets a HashMap where authors are keys, and values are books they have written,
     * packed into an ArrayList.
     * @param books list of books to be transformed into a HashMap.
     * @return Hashmap specified above, e.g. {author1 -> [book1, book2], author2 -> [book1]}.
     */
    private static HashMap<String, ArrayList<String>> getAuthorsTitles(List<BookEntry> books) {
        HashMap<String, ArrayList<String>> authorsTitles = new HashMap<>();
        for (BookEntry book : books) {
            String title = book.getTitle();
            for (String author : book.getAuthors()) {
                Utils.packToDictionary(author, title, authorsTitles);
            }
        }
        return authorsTitles;
    }
}