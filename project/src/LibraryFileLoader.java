import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** 
 * Class responsible for loading
 * book data from file.
 */
public class LibraryFileLoader {

    /**
     * Positions of data types in each line of a file.
     */
    private static final int TITLE_POSITION   = 0;
    private static final int AUTHORS_POSITION = 1;
    private static final int RATING_POSITION  = 2;
    private static final int ISBN_POSITION    = 3;
    private static final int PAGES_POSITION   = 4;

    /**
     * Contains all lines read from a book data file using
     * the loadFileContent method.
     * 
     * This field can be null if loadFileContent was not called
     * for a valid Path yet.
     * 
     * NOTE: Individual line entries do not include line breaks at the 
     * end of each line.
     */
    private List<String> fileContent;

    /** Create a new loader. No file content has been loaded yet. */
    public LibraryFileLoader() { 
        fileContent = null;
    }

    /**
     * Load all lines from the specified book data file and
     * save them for later parsing with the parseFileContent method.
     * 
     * This method has to be called before the parseFileContent method
     * can be executed successfully.
     * 
     * @param fileName file path with book data
     * @return true if book data could be loaded successfully, false otherwise
     * @throws NullPointerException if the given file name is null
     */
    public boolean loadFileContent(Path fileName) {
        Objects.requireNonNull(fileName, "Given filename must not be null.");
        boolean success = false;

        try {
            fileContent = Files.readAllLines(fileName);
            success = true;
        } catch (IOException | SecurityException e) {
            System.err.println("ERROR: Reading file content failed: " + e);
        }

        return success;
    }

    /**
     * Has file content been loaded already?
     * @return true if file content has been loaded already.
     */
    public boolean contentLoaded() {
        return fileContent != null;
    }

    /**
     * Parse file content loaded previously with the loadFileContent method.
     * 
     * @return books parsed from the previously loaded book data or an empty list
     * if no book data has been loaded yet.
     */
    public List<BookEntry> parseFileContent() {
        ArrayList<BookEntry> bookEntries = new ArrayList<>();

        // We need to ignore the first line, column headers are not actual data.
        boolean firstLine = true;
        if (contentLoaded()) {
            for (String line : fileContent) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }
                bookEntries.add(parseLine(line));
            }
        } else {
            System.err.println("ERROR: No content loaded before parsing.");
        }

        return bookEntries;
    }

    /**
     * Parse a line of {@link LibraryFileLoader#fileContent}.
     * Each line is mapped to a corresponding {@link BookEntry} instance.
     *
     * @param line one line of a file.
     * @return corresponding book entry.
     */
    private BookEntry parseLine(String line) {
        String[] lineByDataTypes = line.split(",");

        String title = lineByDataTypes[TITLE_POSITION];
        String[] authors = lineByDataTypes[AUTHORS_POSITION].split("-");
        float rating = Float.parseFloat(lineByDataTypes[RATING_POSITION]);
        String ISBN = lineByDataTypes[ISBN_POSITION];
        int pages = Integer.parseInt(lineByDataTypes[PAGES_POSITION]);

        return new BookEntry(title, authors, rating, ISBN, pages);
    }
}
