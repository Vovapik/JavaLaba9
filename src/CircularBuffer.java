public class CircularBuffer<T> {

    private class Node {
        T value;
        Node next;
        Node(T value) {
            this.value = value;
        }
    }

    private final int capacity;
    private int size = 0;

    private Node head;
    private Node tail;

    public CircularBuffer(int capacity) {
        this.capacity = capacity;

        head = new Node(null);
        Node current = head;
        for (int i = 1; i < capacity; i++) {
            current.next = new Node(null);
            current = current.next;
        }
        current.next = head;
        tail = head;
    }

    public synchronized void put(T value) throws InterruptedException {
        while (size == capacity) {
            wait();
        }

        tail.value = value;
        tail = tail.next;
        size++;

        notifyAll();
    }

    public synchronized T take() throws InterruptedException {
        while (size == 0) {
            wait();
        }

        T value = head.value;
        head.value = null;
        head = head.next;
        size--;

        notifyAll();
        return value;
    }
}
