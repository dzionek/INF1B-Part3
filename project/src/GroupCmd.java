import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

/**
 * Group command used to group and display books by {@link BookField} value.
 */
public class GroupCmd extends LibraryCommand {

    /** Message displayed before printing groups. Followed by {@link BookField} value. */
    private static final String GROUPED_MESSAGE = "Grouped data by ";
    /** Message displayed if a library is empty. */
    private static final String EMPTY_LIBRARY_MESSAGE = "The library has no book entries.";
    /** String denoting titles starting with a digit, when grouping by title. */
    private static final String DIGITS_GROUP = "[0-9]";
    /** String printed before a group, followed by a group's name. */
    private static final String GROUP_HEADER = "## ";

    /** Mode of a group command - one of {@link BookField} values. */
    private BookField mode;

    /**
     * Create a group method.
     * @param argumentInput argument input is expected to be {@link BookField} value.
     * @throws IllegalArgumentException if given argument is invalid.
     * @throws NullPointerException if given argument is null.
     * @see LibraryCommand#LibraryCommand for errors handling.
     * @see GroupCmd#parseArguments for {@link GroupCmd#mode} initialisation.
     */
    public GroupCmd(String argumentInput) {
        super(CommandType.GROUP, argumentInput);
    }

    /**
     * Check if a given argument is valid (is one of {@link BookField} value).
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
     * Execute group command, displays books grouped according to {@link GroupCmd#mode}.
     * If there are no books in a library, prints a special message instead.
     *
     * @param data book data to be considered for command execution.
     * @throws NullPointerException if library, list of books, any book, or {@link GroupCmd#mode} is null.
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
     * Group by title and print all groups.
     * @param books not null and not empty list of books which will be grouped.
     */
    private static void groupByTitle(List<BookEntry> books) {
        Set<String> listOfTitles = getListOfTitles(books);
        Map<String, Set<String>> mapOfTitles = groupByFirstLetter(listOfTitles);
        printGrouped(mapOfTitles);
    }

    /**
     * Get set of titles of a given list of books.
     * Each book is mapped to its title and added to the set.
     * @param books not null and not empty list of books.
     * @return set of all titles in a library.
     */
    private static Set<String> getListOfTitles(List<BookEntry> books) {
        Set<String> titles = new HashSet<>();
        for (BookEntry book : books) {
            titles.add(book.getTitle());
        }
        return titles;
    }

    /**
     * Group all entries in a set into a TreeMap where a key is the first letter
     * which maps to all entries starting with that letter, stored in a set.
     *
     * If a digit is a first letter, the key is {@value DIGITS_GROUP}.
     *
     * @param values set of strings.
     * @return Map of ordered keys where first letter of each set member maps to a set
     *         of all entries starting with that letter.
     */
    private static Map<String, Set<String>> groupByFirstLetter(Set<String> values) {
        Map<String, Set<String>> map = new TreeMap<>();
        for (String value : values) {
            char firstLetter = value.charAt(0);
            String key;
            if (Character.isDigit(firstLetter)) {
                key = DIGITS_GROUP;
            } else {
                key = Character.toString(Character.toUpperCase(firstLetter));
            }
            Utils.packToMap(key, value, map);
        }
        return map;
    }

    /**
     * Print {@value GROUP_HEADER} followed by TreeMap key
     * and all elements in a set corresponding to that key.
     *
     * @param treeMap a given not null, and not empty TreeMap to be printed.
     */
    private static void printGrouped(Map<String, Set<String>> treeMap) {
        for (Map.Entry<String, Set<String>> entry : treeMap.entrySet()) {
            System.out.println(GROUP_HEADER + entry.getKey());
            for (String value : entry.getValue()) {
                System.out.println(value);
            }
        }
    }

    /**
     * Group by author, and prints all groups.
     * @param books list of books to be grouped and printed.
     */
    private static void groupByAuthor(List<BookEntry> books) {
        Map<String, Set<String>> authorsTitles = getAuthorsTitles(books);
        printGrouped(authorsTitles);
    }

    /**
     * Get a TreeMap where authors are keys, and values are books they have written,
     * packed into a set.
     *
     * @param books list of books to be transformed into a TreeMap.
     * @return TreeMap specified above, e.g. {author1 -> [book1, book2], author2 -> [book1]}.
     */
    private static Map<String, Set<String>> getAuthorsTitles(List<BookEntry> books) {
        Map<String, Set<String>> authorsTitles = new TreeMap<>();
        for (BookEntry book : books) {
            String title = book.getTitle();
            for (String author : book.getAuthors()) {
                Utils.packToMap(author, title, authorsTitles);
            }
        }
        return authorsTitles;
    }
}
