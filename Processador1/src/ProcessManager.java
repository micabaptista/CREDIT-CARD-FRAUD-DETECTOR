import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Class that allows you to manage a process
 */
public class ProcessManager {
    private final String processPath;
    private final MetricsReader metricsReader;
    private final CSVReader csvReader;
    private ProcessWrapper process;
    private List<List<String>> allValues;
    private int currentMetricNumber;

    private final Consumer<String> executeAggregator = line -> {
        createAggregator(line);
        process.kill();
    };

    /**
     * Constructor Process Manager
     *
     * @param metricsPath File path metrics
     * @param csvPath     File path CSV
     * @param processPath File path process
     */
    public ProcessManager(String metricsPath, String csvPath, String processPath) {
        this.metricsReader = new MetricsReader(metricsPath);
        this.csvReader = new CSVReader(csvPath);
        this.allValues = new ArrayList<>();
        this.processPath = processPath;
        this.currentMetricNumber = -1;
    }

    /**
     * Create all aggregators for all of the metrics
     */
    public void executeAggregators() {
        metricsReader
                .getStream()
                .forEach(executeAggregator);
    }

    /**
     * ‘
     * Create one aggregator
     */
    private void createAggregator(String l) {
        process = new ProcessWrapper(processPath);
        writeMetric(l);
        csvReader.getStream()
                .map(line -> line.replace(",", " "))
                .forEach(this::writeTransaction);
    }

    /**
     * Write a new transaction into a current process
     */
    private void writeTransaction(String line) {
        process.writeLine(line);
        insertValue(process.readLine(), currentMetricNumber);
    }

    /**
     * Write a new metric into a current process
     */
    private void writeMetric(String line) {
        process.writeLine(line);
        currentMetricNumber++;
        allValues.add(new ArrayList<>());
    }

    /**
     * Insert a value into list of all values.
     *
     * @param value   new value
     * @param metrics current number of the metric
     */
    private void insertValue(String value, int metrics) {
        allValues.get(metrics).add(value);
    }

    /**
     * Print all values in columns
     */
    public void print() {

        for (int i = 0; i < (allValues.get(0).size()); i++) {
            for (int j = 0; j < (allValues.size()); j++) {
                if (isNotLastMetric(j)) {
                    System.out.print(allValues.get(j).get(i) + ",");
                } else {
                    System.out.print(allValues.get(j).get(i));
                }
            }
            System.out.print("\n");
        }
    }

    /**
     * Check if its not the last metric to be printed.
     *
     * @param i number metric
     * @return true if its not the last metric, false otherwise
     */
    private boolean isNotLastMetric(int i) {
        return i != allValues.size() - 1;
    }

    /**
     * close process manager
     */
    public void close() {
        process.kill();
    }

}
