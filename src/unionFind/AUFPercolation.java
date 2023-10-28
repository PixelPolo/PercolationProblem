package unionFind;

import schema.Schema;
import tools.IQueue;

public abstract class AUFPercolation implements IUnionFind {

    // ***** DATA STRUCTURE *****
    /*
            components :    [1, 2, 3, 4, ..., amount of sites, n+1, n+2]
                            Represent all sites (indexes) with a component (value) + 2 virtual sites
     */


    // ***** FIELDS *****
    protected int componentsQuantity;
    protected int[] components;


    // ***** CONSTRUCTOR *****
    public AUFPercolation() {
        this.componentsQuantity = 0;
        this.components = new int[0];
    }


    // ***** GETTERS *****
    public int count() { return componentsQuantity; }


    // ***** METHODS *****

    public void processSchema(Schema schema, boolean singleUseSchema) {
        // All sites have their own component + 2 virtual sites with their own.
        componentsQuantity = schema.getSiteQuantity() + 2;
        components = new int[componentsQuantity];
        for (int i = 0; i < componentsQuantity; i++) components[i] = i;
        // Then union sites
        IQueue<Integer> pairs = singleUseSchema ? schema.getPairsToUnion() : schema.getPairsToUnionCopy();
        int queueSize = pairs.size();
        for (int i = 0; i < queueSize / 2; i++) {
            union(pairs.dequeue(), pairs.dequeue());
        }
    }

    public boolean percolates() {
        return find(components.length - 1) == find(components.length - 2);
    }


}
