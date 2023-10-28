package tools;

import java.util.Iterator;

public class LinkedListQueue<Item> implements IQueue<Item> {


    // ***** FIELDS *****
    private Node head;
    private Node tail;
    private int queueSize;


    // ***** CONSTRUCTOR *****
    public LinkedListQueue() {
        head = null;
        tail = null;
        queueSize = 0;
    }


    // ***** ANONYMOUS NODE CLASS *****
    private class Node {
        // Fields
        Item item;
        Node next;
        // Constructor
        public Node(Item item, Node next) {
            this.item = item;
            this.next = next;
        }
    }


    // ***** METHODS *****

    @Override
    public void enqueue(Item i) {
        Node newNode = new Node(i, null);
        if (queueSize == 0) head = newNode;
        else tail.next = newNode;
        tail = newNode;
        queueSize++;
    }

    @Override
    public Item dequeue() {
        if (queueSize == 0) return null;
        Item i = head.item;
        if (queueSize == 1) {
            tail = null;
            head = null;
        } else {
            head = head.next;
        }
        queueSize--;
        return i;
    }

    @Override
    public Item peek() {
        if (queueSize == 0) return null;
        else return head.item;
    }

    @Override
    public boolean isEmpty() {
        return queueSize == 0;
    }

    @Override
    public int size() {
        return queueSize;
    }

    @Override
    public Iterator<Item> iterator() {
        return new Iterator<Item>() {
            Node current = head;
            @Override
            public boolean hasNext() {
                return current != null;
            }
            @Override
            public Item next() {
                Item i = current.item;
                current = current.next;
                return i;
            }
        };
    }

    @Override
    public LinkedListQueue<Item> copy() {
        LinkedListQueue<Item> copy = new LinkedListQueue<>();
        for (Item item : this) copy.enqueue(item);
        return copy;
    }






}
