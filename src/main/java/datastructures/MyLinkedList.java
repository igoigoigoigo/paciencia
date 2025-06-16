package datastructures;

import java.util.Iterator;

public class MyLinkedList<T> implements Iterable<T> {


    private Node<T> head;
    private Node<T> tail;
    private int size;

    public void addLast(T data) {
        Node<T> node = new Node<>(data);
        if (tail == null) {
            head = tail = node;
        } else {
            tail.next = node;
            tail = node;
        }
        size++;
    }

    public T peekLast() {
        return tail != null ? tail.data : null;
    }

    public T pollLast() {
        if (head == null) return null;
        if (head == tail) {
            T data = head.data;
            head = tail = null;
            size--;
            return data;
        }
        Node<T> curr = head;
        while (curr.next != tail) {
            curr = curr.next;
        }
        T data = tail.data;
        tail = curr;
        tail.next = null;
        size--;
        return data;
    }

    public int size() { return size; }

    public boolean isEmpty() { return size == 0; }

    public Node<T> getHead() { return head; }

    // Iterator para iteração reversa
    public Iterator<T> reverseIterator() {
        java.util.Stack<T> stack = new java.util.Stack<>();
        Node<T> curr = head;
        while (curr != null) {
            stack.push(curr.data);
            curr = curr.next;
        }
        return stack.iterator();
    }

    // Iterador normal
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