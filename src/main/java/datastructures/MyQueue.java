package datastructures;

import java.util.Iterator;

public class MyQueue<T> implements Iterable<T> {

    private Node<T> head;
    private Node<T> tail;
    private int size;

    public void add(T data) {
        Node<T> node = new Node<>(data);
        if (tail == null) {
            head = tail = node;
        } else {
            tail.next = node;
            tail = node;
        }
        size++;
    }

    public T poll() {
        if (head == null) return null;
        T data = head.data;
        head = head.next;
        if (head == null) tail = null;
        size--;
        return data;
    }

    public T peek() {
        return head != null ? head.data : null;
    }

    public int size() { return size; }
    public boolean isEmpty() { return size == 0; }

    public T get(int index) {
        if (index < 0 || index >= size) return null;
        Node<T> curr = head;
        for (int i = 0; i < index; i++) {
            curr = curr.next;
        }
        return curr.data;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            Node<T> curr = head;
            public boolean hasNext() { return curr != null; }
            public T next() {
                T data = curr.data;
                curr = curr.next;
                return data;
            }
        };
    }
} 