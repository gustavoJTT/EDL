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
        if (this.growth <= 0) {
            this.growth = 0;
        }
        this.queue = new Object[this.capacity];
    }

    // isEmpty & this.capacity
    public boolean isEmpty() {
        return this.firstElement == this.lastElement;
    }

    public int size() {
        return (this.capacity - this.firstElement + this.lastElement) % this.capacity;
    }

    // first
    public Object first() throws EEmptyQueue {
        if (isEmpty()) {
            throw new EEmptyQueue("Fila vazia");
        }
        return this.queue[this.firstElement];
    }

    // enqueue & dequeue
    public void enqueue(Object newObject) {
        if (size() == this.capacity - 1) {
            int newSize;
            if (this.growth == 0) {
                newSize = this.capacity * 2;
            } else {
                newSize = this.capacity + this.growth;
            }
            Object newQueue[] = new Object[newSize];
            int newFirst = this.firstElement;

            for (int i = 0; i < size(); i++) {
                newQueue[i] = this.queue[newFirst];
                newFirst = (newFirst + 1) % this.capacity;
            }
            this.firstElement = 0;
            this.lastElement = size();
            this.capacity = newSize;
            this.queue = newQueue;
        }
        this.queue[this.lastElement] = newObject;
        this.lastElement = (this.lastElement + 1) % this.capacity;
    }

    public Object dequeue() throws EEmptyQueue {
        if (isEmpty()) {
            throw new EEmptyQueue("Fila vazia");
        }
        Object removed = this.queue[this.firstElement];
        this.firstElement = (this.firstElement + 1) % this.capacity;
        return removed;
    }

    public void reverse() {
        Object reverseQueue[] = new Object[this.capacity];

        for (int i = size(); i + 1 != this.lastElement; i--) {
            reverseQueue[i] = this.queue[(this.lastElement - 1 - i + this.capacity) % this.capacity];
        }
        this.firstElement = 0;
        this.lastElement = size();
        this.queue = reverseQueue;
    }

    public void print() {
        System.out.println("Fila (tamanho total: " + this.capacity + ")");
        int current = this.firstElement;
        for (int i = 0; i < size(); i++) {
            System.out.print("[" + i + "]: " + this.queue[current]);
            if (i == 0) {
                System.out.print(" <- início");
            }
            if (i == size() - 1) {
                System.out.print(" <- fim");
            }
            System.out.println();
            current = (current + 1) % this.capacity;
        }
        System.out.println("Tamanho atual: " + size());
        System.out.println("Está vazia? " + isEmpty());
    }
}
