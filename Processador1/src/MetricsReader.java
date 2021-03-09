import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * Class that allows you to read metrics files (.txt with commands haskell)
 */
public class MetricsReader{
    private String path;

    /**
     * Constructor Metrics Reader
     *
     * @param path File path
     */
    public MetricsReader(String path) {
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
