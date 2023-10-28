package schema;

import tools.IQueue;
import tools.LinkedListQueue;

import java.io.Serializable;
import java.util.Random;

public class Schema implements Serializable {

    // Main for test
    public static void main(String[] args) {
        Schema s = new Schema(0.4, 3);
        System.out.println(s);
    }

    // ***** DATA STRUCTURE *****
    /*
            vacancyP :          site vacancy probability to be a 0 or a 1

            model :             N square matrix (0 is close, 1 is open)
                                    [ 0 0 1 0 0 ]
                                    [ 0 0 1 0 1 ]
                                    [ 1 0 0 0 0 ]
                                    [ 0 0 1 0 0 ]
                                    [-1 -2] two virtual sites

            siteQuantity :         N square = site quantity without virtual sites

            pairsToUnion :      coordinates are converted from 2D to 1D
                                then added in this flattened list
                                    (2, -1, 2, 7, 17, -2)

            Rules of the game :
                A schema percolates if virtual site -1 is connected to -2 through open sites (1)
     */


    // ***** FIELDS *****
    private final double vacancyP;
    private final int[][] model;
    private final int siteQuantity;
    private final IQueue<Integer> pairsToUnion;


    // ***** CONSTRUCTOR *****
    public Schema(double vacancyP, int size) {
        this.vacancyP = vacancyP;
        this.model = new int[size][size];
        this.siteQuantity = size * size;
        this.pairsToUnion = new LinkedListQueue<>();
        fillModel(vacancyP);
        fillPairsToUnion();
    }

    // Fill the matrix square model
    private void fillModel(double vacancyP) {
        Random rdm = new Random();
        for (int i = 0; i < model.length; i++) {
            for (int j = 0; j < model.length; j++) {
                model[i][j] = rdm.nextDouble() <= vacancyP ? 1 : 0;
            }
        }
    }

    // Fill the pairsToUnion list
    public void fillPairsToUnion() {
        int size = model.length;
        for (int i = 0; i < size; i++) {
            // Connect the first row open site to virtual site : index = siteQuantity
            if (model[0][i] == 1) {
                pairsToUnion.enqueue(convert2DIndexTo1DIndex(0, i, size));
                pairsToUnion.enqueue(siteQuantity);
            }
            // Connect the last row open site to virtual site index = siteQuantity + 2
            if (model[size-1][i] == 1) {
                pairsToUnion.enqueue(convert2DIndexTo1DIndex(size-1, i, size));
                pairsToUnion.enqueue(siteQuantity + 1);
            }
            for (int j = 1; j < size; j++) {
                // For each row, if adjacent site are open, they make a pair to union
                if (model[i][j] == 1 && model[i][j-1] == 1) {
                    pairsToUnion.enqueue(convert2DIndexTo1DIndex(i, j, size));
                    pairsToUnion.enqueue(convert2DIndexTo1DIndex(i, j-1, size));
                }
                // For each col, if adjacent site are open, they make a pair to union
                if (model[j][i] == 1 && model[j-1][i] == 1) {
                    pairsToUnion.enqueue(convert2DIndexTo1DIndex(j, i, size));
                    pairsToUnion.enqueue(convert2DIndexTo1DIndex(j-1, i, size));
                }
            }
        }
    }

    // Convert coords to have a flattened list of pairs to union
    private int convert2DIndexTo1DIndex(int i, int j, int colsQuantity) {
        return i * colsQuantity + j;
    }


    // ***** GETTERS *****
    public int[][] getModel() { return model; }
    public int getSiteQuantity() { return siteQuantity; }
    public double getVacancyP() { return vacancyP; }
    public IQueue<Integer> getPairsToUnion() { return pairsToUnion; }
    public IQueue<Integer> getPairsToUnionCopy() { return pairsToUnion.copy(); }


    // ***** METHODS *****

    @Override
    public String toString() {
        StringBuilder sB = new StringBuilder();
        // Print the schema model
        sB.append("\nSchema model with ").append(siteQuantity).append(" sites : \n");
        for (int[] row : model) {
            sB.append("[");
            for (int i = 0; i < model.length; i++)
                sB.append(" ").append(row[i]).append(" ");
            sB.append("]\n");
        }
        // Print pairs to union
        IQueue<Integer> pairsToUnionCopy = pairsToUnion.copy();
        if (pairsToUnion.size() == 0) {
            sB.append("No pairs to union...");
        } else {
            sB.append("\nPairs to union :\n");
            int pairsToUnionSize = pairsToUnionCopy.size();
            for (int i = 0; i < pairsToUnionSize / 2; i++) {
                int a = pairsToUnionCopy.dequeue();
                int b = pairsToUnionCopy.dequeue();
                sB.append("(").append(a).append(",").append(b).append(") ");
            }
        }
        return sB.toString();
    }
}
