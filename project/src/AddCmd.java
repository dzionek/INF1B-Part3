import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * Add command used to add books from a file to a library.
 */
public class AddCmd extends LibraryCommand {

    /** Extension of an expected file. */
    private static final String EXTENSION = ".csv";

    /** File path of an instance. */
    private final Path filePath;

    /**
     * Create an add method.
     * @param argumentInput argument input is expected to be a path of a file.
     * @throws IllegalArgumentException if given arguments are invalid.
     * @throws NullPointerException if given arguments are null.
     * @see LibraryCommand#LibraryCommand for errors handling.
     */
    public AddCmd(String argumentInput) {
        super(CommandType.ADD, argumentInput);
        filePath = Paths.get(argumentInput);
    }

    /**
     * Check if an argument is a file with extension {@value EXTENSION}.
     * @param argumentInput argument input for add command - path of a file.
     * @return {@code true} if the argument has correct extension, otherwise {@code false}.
     */
    @Override
    protected boolean parseArguments(String argumentInput) {
        Objects.requireNonNull(argumentInput, "Given input argument must not be null.");
        return argumentInput.endsWith(EXTENSION);
    }

    /**
     * Execute an add command.
     * @param data book data to be considered for command execution.
     * @throws NullPointerException if a given argument, or file path of an instance is null.
     */
    @Override
    public void execute(LibraryData data) {
        Objects.requireNonNull(data, "Library data must not be null.");
        Objects.requireNonNull(filePath, "File path must not be null. Check your AddCmd instance.");
        data.loadData(filePath);
    }
}
