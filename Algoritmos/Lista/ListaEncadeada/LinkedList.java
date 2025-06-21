package Lista.ListaEncadeada;

import Excesao.EEmptyList;
import Excesao.EInvalidNode;
import Lista.ListInterface;
import Node.Node;

public class LinkedList implements ListInterface {
    private Node head, tail;
    private int size;

    public LinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    // isEmpty & this.size
    public int size() {
        return this.size;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    // is*
    public boolean isFirst(Node node) throws EEmptyList {
        if (isEmpty()) {
            throw new EEmptyList("Lista vazia");
        }
        return this.head == node;
    }

    public boolean isLast(Node node) throws EEmptyList {
        if (isEmpty()) {
            throw new EEmptyList("Lista vazia");
        }
        return this.tail == node;
    }

    // first & last
    public Node first() throws EEmptyList {
        if (isEmpty()) {
            throw new EEmptyList("Lista vazia");
        }
        return this.head;
    }

    public Node last() throws EEmptyList {
        if (isEmpty()) {
            throw new EEmptyList("Lista vazia");
        }
        return this.tail;
    }

    // before & after
    public Node before(Node node) throws EEmptyList, EInvalidNode {
        if (isEmpty()) {
            throw new EEmptyList("Lista vazia");
        }
        if (node == this.head) {
            throw new EInvalidNode("Não existe nó anterior ao primeiro");
        }

        Node current = this.head;
        while (current != null && current.getNext() != node) {
            current = current.getNext();
        }

        if (current == null) {
            throw new EInvalidNode("Nó inválido");
        }

        return current;
    }

    public Node after(Node node) throws EEmptyList, EInvalidNode {
        if (isEmpty()) {
            throw new EEmptyList("Lista vazia");
        }

        if (node == this.tail) {
            throw new EInvalidNode("Nó é o último da lista");
        }

        Node next = node.getNext();
        if (next == null) {
            throw new EInvalidNode("Nó inválido");
        }

        return next;
    }

    // replace
    public void replaceElement(Node node, Object object) throws EEmptyList, EInvalidNode {
        if (isEmpty()) {
            throw new EEmptyList("Lista vazia");
        }

        Node current = this.head;
        boolean found = false;

        while (current != null) {
            if (current == node) {
                found = true;
                break;
            }
            current = current.getNext();
        }

        if (!found) {
            throw new EInvalidNode("Nó inválido");
        }

        node.setElement(object);
    }

    // swap
    public void swapElement(Node firstNode, Node secondNode) throws EEmptyList, EInvalidNode {
        if (isEmpty()) {
            throw new EEmptyList("Lista vazia");
        }

        // Verificar se os nós pertencem à lista
        Node current = this.head;
        boolean foundFirst = false;
        boolean foundSecond = false;

        while (current != null) {
            if (current == firstNode) {
                foundFirst = true;
            }
            if (current == secondNode) {
                foundSecond = true;
            }
            if (foundFirst && foundSecond) {
                break;
            }
            current = current.getNext();
        }

        if (!foundFirst || !foundSecond) {
            throw new EInvalidNode("Um dos nós é inválido");
        }

        // Trocar os elementos
        Object temp = firstNode.getElement();
        firstNode.setElement(secondNode.getElement());
        secondNode.setElement(temp);
    }

    // Insert*
    public void insertBefore(Node node, Object object) throws EEmptyList, EInvalidNode {
        if (isEmpty()) {
            throw new EEmptyList("Lista vazia");
        }

        if (node == this.head) {
            insertFirst(object);
            return;
        }

        Node beforeNode = before(node);
        Node newNode = new Node(object);
        newNode.setNext(node);
        beforeNode.setNext(newNode);
        this.size++;
    }

    public void insertAfter(Node node, Object object) throws EEmptyList, EInvalidNode {
        if (isEmpty()) {
            throw new EEmptyList("Lista vazia");
        }

        Node current = this.head;
        boolean found = false;

        while (current != null) {
            if (current == node) {
                found = true;
                break;
            }
            current = current.getNext();
        }

        if (!found) {
            throw new EInvalidNode("Nó inválido");
        }

        Node newNode = new Node(object);
        newNode.setNext(node.getNext());
        node.setNext(newNode);

        if (node == this.tail) {
            this.tail = newNode;
        }

        this.size++;
    }

    public void insertFirst(Object object) {
        Node newNode = new Node(object);

        if (isEmpty()) {
            this.head = newNode;
            this.tail = newNode;
        } else {
            newNode.setNext(this.head);
            this.head = newNode;
        }

        this.size++;
    }

    public void insertLast(Object object) {
        Node newNode = new Node(object);

        if (isEmpty()) {
            this.head = newNode;
            this.tail = newNode;
        } else {
            this.tail.setNext(newNode);
            this.tail = newNode;
        }

        this.size++;
    }

    // remove
    public void remove(Node node) throws EEmptyList, EInvalidNode {
        if (isEmpty()) {
            throw new EEmptyList("Lista vazia");
        }

        if (node == this.head) {
            this.head = this.head.getNext();
            if (this.head == null) {
                this.tail = null;
            }
            this.size--;
            return;
        }

        Node beforeNode = before(node);
        beforeNode.setNext(node.getNext());

        if (node == this.tail) {
            this.tail = beforeNode;
        }

        this.size--;
    }
}
