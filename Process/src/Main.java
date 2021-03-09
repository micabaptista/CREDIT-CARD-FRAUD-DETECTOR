/**
 * @author Afonso Rosa , 54395
 * @author Michael Baptista, 54478
 */git push -u origin main
public class Main {
    /**
     * Main
     *
     * @param args args[0] = "../metrics.txt", args[1] =  "../dataset.csv", args[2] = "../Agregador/Main"
     */
    public static void main(String[] args) {

        ProcessManager processManager = new ProcessManager(args[0], args[1], args[2]);
        processManager.executeAggregators();
        processManager.print();
        processManager.close();
    }
}
