package unionFind;

import schema.Schema;

public class QUPercolation extends AUFPercolation implements IUnionFind {

    // Main for testing
    public static void main(String[] args) {
        Schema s = new Schema(0.6, 3);
        System.out.println(s);
        QFPercolation qF = new QFPercolation();
        qF.processSchema(s, true);
        System.out.println("\nPercolates : " + qF.percolates());
    }

    /*
     This solution use the Quick Union method.
     */

    // ***** METHODS *****

    @Override
    public int find(int p) {
        // We are looking for the root : site which component is equal to its index.
        // When searching, we reassign all site's component until finding the root.
        // This makes a tree data structure.
        while (p != components[p]) p = components[p];
        return p;
    }

    @Override
    public void union(int p, int q) {
        int pComponent = find(p);
        int qComponent = find(q);
        if (pComponent == qComponent) return;
        // Assign p component to q component.
        components[pComponent] = qComponent;
        componentsQuantity--;
    }

    @Override
    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

}
