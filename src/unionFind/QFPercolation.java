package unionFind;


import schema.Schema;

public class QFPercolation extends AUFPercolation implements IUnionFind {

    // Main for testing
    public static void main(String[] args) {
        Schema s = new Schema(0.6, 3);
        System.out.println(s);
        QFPercolation qF = new QFPercolation();
        qF.processSchema(s, true);
        System.out.println("\nPercolates : " + qF.percolates());
    }

    /*
     This solution use the Quick Find method.
     */

    // ***** METHODS *****

    @Override
    public int find(int p) {
        return components[p];
    }

    @Override
    public void union(int p, int q) {
        int pComponent = find(p);
        int qComponent = find(q);
        if (pComponent == qComponent) return;
        for (int i = 0; i < components.length; i++) {
            // For each site which has component p, replace it by component q
            if (components[i] == pComponent) components[i] = qComponent;
        }
        componentsQuantity--;
    }

    @Override
    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

}
