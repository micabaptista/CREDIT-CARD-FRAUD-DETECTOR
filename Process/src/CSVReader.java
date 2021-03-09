import java.io.IOException;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.nio.file.Files;

/**
 * Class that allows you to read CSV files
 */
public class CSVReader {
    private String path;

    /**
     * Constructor CSV Reader
     *
     * @param path File path
     */
    public CSVReader(String path) {
        this.path = path;
    }

    /**
     * Get a stream with all values of the file.csv
     *
     * @return a stream with all values of the file.csv
     */
    public Stream<String> getStream() {
        try {
            return Files.lines(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Stream.empty();
    }
}
