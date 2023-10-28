import schema.Schema;
import tools.IQueue;
import tools.LinkedListQueue;
import tools.SwingPlot;
import unionFind.AUFPercolation;
import unionFind.QFPercolation;

public class Main {

    public static void main(String[] args) {

        int schemaSize = 10;
        int schemaQuantity = 10_000_000;

        // Swing Plot
        IQueue<Double> percolations = new LinkedListQueue<>();
        IQueue<Double> vacancyProbability = new LinkedListQueue<>();

        // Processing
        Schema s;
        AUFPercolation uF = new QFPercolation();
        int percolationTracker;
        long schemaAnalysedTracker = 0L;

        // Time
        long startTime = System.currentTimeMillis();
        double elapsedTime;

        for (double p = 0.000; p <= 1.000; p += 0.001) {
            percolationTracker = 0;
            for (int i = 0; i < schemaQuantity / 1000; i++) {
                ++schemaAnalysedTracker;
                s = new Schema(p, schemaSize);
                uF = new QFPercolation();
                uF.processSchema(s, false);
                if (uF.percolates()) percolationTracker++;
            }
            elapsedTime = (System.currentTimeMillis() - startTime) / 1000.;
            System.out.printf("\rSchema to process : %,d / %,d     time : %.3f seconds",
                    ++schemaAnalysedTracker, schemaQuantity, elapsedTime);
            // Swing plot
            percolations.enqueue((double) percolationTracker / schemaQuantity * 100);
            vacancyProbability.enqueue(p * 100);
        }

        // Swing plot
        new SwingPlot(uF.getClass().getSimpleName(), 5, vacancyProbability, percolations);

    }
}