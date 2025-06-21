package Fila.FilaEncadeada;

import Excesao.EEmptyQueue;
import Node.Node;

public class LinkedQueue implements LinkedQueueInterface {
    private Node head, tail;
    private int size;

    public LinkedQueue(int size) {
        this.size = 0;
        this.head = null;
        this.tail = null;
    }

    // isEmpty & this.size
    public boolean isEmpty() {
        return this.head == null;
    }

    public int size() {
        return this.size;
    }

    // first
    public Object first() throws EEmptyQueue {
        if (isEmpty()) {
            throw new EEmptyQueue("Fila vazia");
        }
        return this.head.getElement();
    }

    // align & misAlign
    public void enqueue(Object newObject) {
        Node newNode = new Node(newObject);

        if (isEmpty()) {
            this.head = newNode;
        } else {
            this.tail.setNext(newNode);
        }
        this.tail = newNode;
        this.size++;
    }

    public Object dequeue() throws EEmptyQueue {
        if (isEmpty()) {
            throw new EEmptyQueue("Fila vazia");
        }
        Object element = this.head.getElement();
        this.head = this.head.getNext();
        this.size--;

        if (isEmpty()) {
            this.tail = null;
        }
        return element;
    }
}
