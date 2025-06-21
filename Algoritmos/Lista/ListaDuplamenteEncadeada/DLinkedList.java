package Lista.ListaDuplamenteEncadeada;

import Excesao.EEmptyList;
import Lista.ListInterface;
import Node.Node;

public class DLinkedList implements ListInterface {
    private Node head, tail;
    private int size;

    public DLinkedList() {
        this.head = new Node(null);
        this.tail = new Node(null);
        this.head.setNext(this.tail);
        this.tail.setPrevious(this.head);
        this.size = 0;
    }

    // isEmpty & this.size
    public boolean isEmpty() {
        return this.size == 0;
    }

    public int size() {
        return this.size;
    }

    // is*
    public boolean isFirst(Node node) throws EEmptyList {
        if (isEmpty()) {
            throw new EEmptyList("Lista vazia");
        }
        return node == this.head;
    }

    public boolean isLast(Node node) throws EEmptyList {
        if (isEmpty()) {
            throw new EEmptyList("Lista vazia");
        }
        return node == this.tail;
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
    public Node before(Node node) throws EEmptyList {
        if (isEmpty()) {
            throw new EEmptyList("Lista vazia");
        }
        return node.getPrevious();
    }

    public Node after(Node node) throws EEmptyList {
        if (isEmpty()) {
            throw new EEmptyList("Lista vazia");
        }
        return node.getNext();
    }

    // reeplace
    public void replaceElement(Node node, Object object) throws EEmptyList {
        if (isEmpty()) {
            throw new EEmptyList("Lista vazia");
        }
        node.setElement(object);
    }

    // swap
    public void swapElement(Node firstNode, Node secondNode) throws EEmptyList {
        if (isEmpty()) {
            throw new EEmptyList("Lista vazia");
        }

        Object temp = firstNode.getElement();

        firstNode.setElement(secondNode.getElement());
        secondNode.setElement(temp);
    }

    // insert*
    public void insertBefore(Node node, Object object) throws EEmptyList {
        if (isEmpty()) {
            throw new EEmptyList("Lista vazia");
        }

        Node newNode = new Node(object);

        newNode.setNext(node);
        newNode.setPrevious(node.getPrevious());
        node.getPrevious().setNext(newNode);
        node.setPrevious(newNode);
        this.size++;
    }

    public void insertAfter(Node node, Object object) throws EEmptyList {
        if (isEmpty()) {
            throw new EEmptyList("Lista vazia");
        }
        Node newNode = new Node(object);

        newNode.setNext(node.getNext());
        newNode.setPrevious(node);
        node.getNext().setPrevious(newNode);
        node.setNext(newNode);
        this.size++;
    }

    public void insertFirst(Object object) throws EEmptyList {
        if (isEmpty()) {
            throw new EEmptyList("Lista vazia");
        }
        Node newNode = new Node(object);

        newNode.setPrevious(this.head);
        newNode.setNext(this.head.getNext());
        this.head.getNext().setPrevious(newNode);
        this.head.setNext(newNode);
        this.size++;
    }

    public void insertLast(Object object) throws EEmptyList {
        if (isEmpty()) {
            throw new EEmptyList("Lista vazia");
        }
        Node newNode = new Node(object);

        newNode.setNext(this.tail);
        newNode.setPrevious(this.tail.getPrevious());
        this.tail.getPrevious().setNext(newNode);
        this.tail.setNext(newNode);
        this.size++;
    }

    // remove
    public void remove(Node node) throws EEmptyList {
        if (isEmpty()) {
            throw new EEmptyList("Lista vaiza");
        }
        node.getPrevious().setNext(node.getNext());
        node.getNext().setPrevious(node.getPrevious());
    }

    // print
    public void print() {
        Node temp = this.head.getNext();
        while (temp.getElement() != null) {
            System.out.println(temp.getElement());
            temp = temp.getNext();
        }
    }
}
