import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * Add command, which adds books from a file to a library.
 */
public class AddCmd extends LibraryCommand {

    /** Extension of the expected file. */
    private static final String EXTENSION = ".csv";

    /** File path of an instance. */
    private final Path filePath;

    /**
     * Create an add method.
     * @param argumentInput argument input is expected to be a path of the file.
     * @throws IllegalArgumentException if given arguments are invalid.
     * @throws NullPointerException if given arguments are null.
     */
    public AddCmd(String argumentInput) {
        super(CommandType.ADD, argumentInput);
        filePath = Paths.get(argumentInput);
    }

    /**
     * Checks if an argument is a path to a file with extension {@value EXTENSION}.
     * @param argumentInput argument input for add command, path to a file.
     * @return true if argument has correct extension, otherwise false.
     */
    @Override
    protected boolean parseArguments(String argumentInput) {
        if (argumentInput.length() > EXTENSION.length()) {
            String lastFourChars = argumentInput.substring(argumentInput.length() - EXTENSION.length());
            return lastFourChars.equals(EXTENSION);
        } else {
            return false;
        }
    }

    /**
     * Execute the add command.
     * @param data book data to be considered for command execution.
     * @throws NullPointerException if a given argument is null.
     */
    @Override
    public void execute(LibraryData data) {
        Objects.requireNonNull(data, "Library data must not be null.");
        data.loadData(filePath);
    }
}
