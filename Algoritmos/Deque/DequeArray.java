package Deque;

import Excesao.EEmptyDeque;

public class DequeArray implements DequeInterface {
    private int size, growth, firstElement, lastElement;
    private Object array[];

    public DequeArray(int size, int growth) {
        this.firstElement = 0;
        this.lastElement = 0;
        this.size = size;
        this.growth = growth;

        if (this.growth <= 0) {
            this.growth = 0;
        }
        array = new Object[this.size];
    }

    // isEmpty & this.size
    public boolean isEmpty() {
        return this.firstElement == this.lastElement;
    }

    public int size() {
        return (this.lastElement - this.firstElement + this.size) % this.size;
    }

    // first & last
    public Object first() throws EEmptyDeque {
        if (isEmpty()) {
            throw new EEmptyDeque("Deque vazio");
        }
        return array[this.firstElement];
    }

    public Object last() throws EEmptyDeque {
        if (isEmpty()) {
            throw new EEmptyDeque("Deque vazio");
        }
        return array[(this.lastElement - 1 + this.size) % this.size];
    }

    // enqueue
    public void enqueueBeginning(Object newElement) {
        if (size() == this.size - 1) {
            int newSize;
            if (this.growth == 0) {
                newSize = this.size * 2;
            } else {
                newSize = this.size + this.growth;
            }
            Object newArray[] = new Object[newSize];
            int newFirst = this.firstElement;
            newArray[0] = newElement;

            for (int i = 1; i < size(); i++) {
                newArray[i] = array[newFirst];
                newFirst = (newFirst + 1) % this.size;
            }
            this.firstElement = 0;
            this.lastElement = size();
            this.size = newSize;
            array = newArray;
        }
        this.firstElement = (this.firstElement - 1 + this.size) % this.size;
        array[this.firstElement] = newElement;
    }

    public void enqueueEnd(Object newElement) {
        if (size() == this.size - 1) {
            int newSize;
            if (this.growth == 0) {
                newSize = this.size * 2;
            } else {
                newSize = this.size + this.growth;
            }
            Object newArray[] = new Object[newSize];
            int newFirst = this.firstElement;

            for (int i = 0; i < size(); i++) {
                newArray[i] = array[newFirst];
                newFirst = (newFirst + 1) % this.size;
            }
            this.firstElement = 0;
            this.lastElement = size();
            this.size = newSize;
            array = newArray;
        }
        array[this.lastElement] = newElement;
        this.lastElement = (this.lastElement + 1) % this.size;
    }

    // dequeue
    public Object dequeueBeginnig() throws EEmptyDeque {
        if (isEmpty()) {
            throw new EEmptyDeque("Deque vazio");
        }
        Object removedElement = array[this.firstElement];
        array[this.firstElement] = null;
        this.firstElement = (this.firstElement + 1) % this.size;
        return removedElement;
    }

    public Object dequeueEnd() throws EEmptyDeque {
        if (isEmpty()) {
            throw new EEmptyDeque("Deque vazio");
        }
        this.lastElement = (this.lastElement - 1 + this.size) % this.size;
        Object removedElement = array[this.lastElement];
        array[this.lastElement] = null;
        return removedElement;
    }

    // print
    public void printDeque() {
        if (isEmpty()) {
            System.out.println("Deque vazio");
            return;
        }

        System.out.print("Deque: [");
        int current = this.firstElement;
        while (current != this.lastElement) {
            System.out.print(array[current]);
            current = (current + 1) % this.size;

            if (current != this.lastElement) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }
}
