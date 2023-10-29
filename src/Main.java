import schema.Schema;
import tools.IQueue;
import tools.LinkedListQueue;
import tools.SwingPlot;
import unionFind.AUFPercolation;
import unionFind.QFPercolation;
import unionFind.QUPercolation;
import unionFind.WUPercolation;

public class Main {

    public static void main(String[] args) {
        int schemaSize = 100;
        int schemaQuantity = 100; // For each vacancyP from 0.000 to 1 (1000 times)
        testSchemas(schemaQuantity, schemaSize, new QFPercolation(), "Quick Find");
        testSchemas(schemaQuantity, schemaSize, new QUPercolation(), "Quick Union");
        testSchemas(schemaQuantity, schemaSize, new WUPercolation(), "Weight Union");
    }


    private static void testSchemas(int schemaQuantity, int schemaSize, AUFPercolation algo, String algoTitle) {
        // Swing Plot
        IQueue<Double> percolations = new LinkedListQueue<>();
        IQueue<Double> vacancyProbability = new LinkedListQueue<>();
        // Processing
        Schema s;
        int percolationTracker;
        long schemaAnalysedTracker = 0L;
        double elapsedTime = 0;
        long startTime = System.currentTimeMillis();
        for (double p = 0.000; p <= 1.000; p += 0.001) {
            percolationTracker = 0;
            for (int i = 0; i < schemaQuantity; i++) {
                ++schemaAnalysedTracker;
                s = new Schema(p, schemaSize);
                algo.processSchema(s, true); // true because we will not reuse the schema
                if (algo.percolates()) percolationTracker++;
                elapsedTime = (System.currentTimeMillis() - startTime) / 1000.;
                System.out.printf("\rSchema to process : %,d / %,d     time : %.3f seconds",
                        schemaAnalysedTracker, schemaQuantity * 1000, elapsedTime);
            }
            // Swing plot
            percolations.enqueue((double) percolationTracker / schemaQuantity * 100);
            vacancyProbability.enqueue(p * 100);
        }
        // Swing plot
        String title = String.format("%s : %s schemas of size %,d in %.3f seconds",
                algoTitle, schemaQuantity * 1000, schemaSize, elapsedTime);
        new SwingPlot(title, 5, vacancyProbability, percolations);
    }




}