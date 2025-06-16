package datastructures;

import java.util.Iterator;

public class MyStack<T> implements Iterable<T> {

    private Node<T> top;
    private int size;

    public void push(T data) {
        top = new Node<>(data, top);
        size++;
    }

    public T pop() {
        if (top == null) return null;
        T data = top.data;
        top = top.next;
        size--;
        return data;
    }

    public T peek() {
        return top != null ? top.data : null;
    }

    public int size() { return size; }
    public boolean isEmpty() { return size == 0; }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            Node<T> curr = top;
            public boolean hasNext() { return curr != null; }
            public T next() {
                T data = curr.data;
                curr = curr.next;
                return data;
            }
        };
    }
} 