package Pilha.PilhaEncadeada;

import Excesao.EEmptyStack;
import Node.Node;

public class LinkedStack implements LinkedStackInterface {
    private int size;
    private Node head;

    public LinkedStack(int size) {
        this.head = null;
        this.size = 0;
    }

    // isEmpty & this.size
    public boolean isEmpty() {
        return this.head == null;
    }

    public int size() {
        return this.size;
    }

    // top
    public Object top() throws EEmptyStack {
        if (isEmpty()) {
            throw new EEmptyStack("Pilha vazia");
        }
        return this.head.getElement();
    }

    // push pop
    public void push(Object newElement) {
        Node newNode = new Node(newElement);
        newNode.setNext(this.head); // liga o novo no atual
        this.head = newNode;
        this.size++;
    }

    public Object pop() throws EEmptyStack {
        if (isEmpty()) {
            throw new EEmptyStack("Pilha vazia");
        }
        Object o = this.head.getElement();
        this.head = this.head.getNext();
        this.size--;
        return o;
    }
}
