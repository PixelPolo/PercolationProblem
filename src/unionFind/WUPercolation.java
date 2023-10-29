package unionFind;

import schema.Schema;

public class WUPercolation extends AUFPercolation implements IUnionFind {

    // Main for testing
    public static void main(String[] args) {
        Schema s = new Schema(0.6, 5);
        System.out.println(s);
        QFPercolation qF = new QFPercolation();
        qF.processSchema(s, true);
        System.out.println("\nPercolates : " + qF.percolates());
    }

    /*
     This solution use the Weight Union method.
     */

    // **** FIELDS *****
    private int[] sizes; // keep track of the size of each tree

    // ***** CONSTRUCTOR *****
    public WUPercolation() {
        super();

    }



    // ***** METHODS *****


    @Override
    public void processSchema(Schema schema, boolean singleUseSchema) {
        this.sizes = new int[schema.getSiteQuantity() + 2];
        // Initialize all tree sizes to 1
        for (int i = 0; i < componentsQuantity; i++) sizes[i] = 1;
        super.processSchema(schema, singleUseSchema);
    }

    @Override
    public int find(int p) {
        // Same as Quick Union
        while (p != components[p]) p = components[p];
        return p;
    }

    @Override
    public void union(int p, int q) {
        int firstComponent = find(p);
        int secondComponent = find(q);
        if (firstComponent == secondComponent) return;
        // Smaller tree will always point to bigger tree
        // Then increment the size of the bigger tree
        if (sizes[firstComponent] < sizes[secondComponent]) {
            components[firstComponent] = secondComponent;
            sizes[secondComponent] += sizes[firstComponent];
        } else {
            components[secondComponent] = firstComponent;
            sizes[firstComponent] += sizes[secondComponent];
        }
        componentsQuantity--;
    }

    @Override
    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

}
