package tools;

public interface IQueue<Item> extends Iterable<Item> {
    void enqueue(Item i);
    Item dequeue();
    Item peek();
    boolean isEmpty();
    int size();
    IQueue<Item> copy();
}



