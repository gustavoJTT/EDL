package Fila;

import Excesao.EEmptyQueue;

public class QueueRoundArray implements QueueInterface {
    private int capacity, growth, firstElement, lastElement;
    private Object queue[];

    public QueueRoundArray(int capacity, int growth) {
        this.firstElement = 0;
        this.lastElement = 0;
        this.capacity = capacity;
        this.growth = growth;
        if (growth <= 0) {
            this.growth = 0;
        }
        queue = new Object[capacity];
    }

    // isEmpty & capacity
    public boolean isEmpty() {
        return firstElement == lastElement;
    }

    public int size() {
        return (capacity - firstElement + lastElement) % capacity;
    }

    // first
    public Object first() throws EEmptyQueue {
        if (isEmpty()) {
            throw new EEmptyQueue("Fila vazia");
        }
        return queue[firstElement];
    }

    // enqueue & dequeue
    public void enqueue(Object newObject) {
        if (size() == capacity - 1) {
            int newSize;
            if (growth == 0) {
                newSize = capacity * 2;
            } else {
                newSize = capacity + growth;
            }
            Object newQueue[] = new Object[newSize];
            int newFirst = firstElement;

            for (int i = 0; i < size(); i++) {
                newQueue[i] = queue[newFirst];
                newFirst = (newFirst + 1) % capacity;
            }
            firstElement = 0;
            lastElement = size();
            capacity = newSize;
            queue = newQueue;
        }
        queue[lastElement] = newObject;
        lastElement = (lastElement + 1) % capacity;
    }

    public Object dequeue() throws EEmptyQueue {
        if (isEmpty()) {
            throw new EEmptyQueue("Fila vazia");
        }
        Object removed = queue[firstElement];
        firstElement = (firstElement + 1) % capacity;
        return removed;
    }

    public void reverse() {
        Object reverseQueue[] = new Object[capacity];

        for (int i = size(); i + 1 != lastElement; i--) {
            reverseQueue[i] = queue[(lastElement - 1 - i + capacity) % capacity];
        }
        firstElement = 0;
        lastElement = size();
        queue = reverseQueue;
    }

    public void print() {
        System.out.println("Fila (tamanho total: " + capacity + ")");
        int current = firstElement;
        for (int i = 0; i < size(); i++) {
            System.out.print("[" + i + "]: " + queue[current]);
            if (i == 0) {
                System.out.print(" <- início");
            }
            if (i == size() - 1) {
                System.out.print(" <- fim");
            }
            System.out.println();
            current = (current + 1) % capacity;
        }
        System.out.println("Tamanho atual: " + size());
        System.out.println("Está vazia? " + isEmpty());
    }
}