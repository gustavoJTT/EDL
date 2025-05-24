package Lista.ListaEncadeada;
import Lista.EEmptyList;
import Lista.EInvalidNode;
import Lista.ListInterface;
import Node.Node;

public class LinkedList implements ListInterface {
    private Node head;
    private Node tail;
    private int size;

    public LinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean isFirst(Node node) throws EEmptyList {
        if (isEmpty()) {
            throw new EEmptyList("Lista vazia");
        }
        return head == node;
    }

    @Override
    public boolean isLast(Node node) throws EEmptyList {
        if (isEmpty()) {
            throw new EEmptyList("Lista vazia");
        }
        return tail == node;
    }

    @Override
    public Node first() throws EEmptyList {
        if (isEmpty()) {
            throw new EEmptyList("Lista vazia");
        }
        return head;
    }

    @Override
    public Node last() throws EEmptyList {
        if (isEmpty()) {
            throw new EEmptyList("Lista vazia");
        }
        return tail;
    }

    @Override
    public Node before(Node node) throws EEmptyList, EInvalidNode {
        if (isEmpty()) {
            throw new EEmptyList("Lista vazia");
        }
        if (node == head) {
            throw new EInvalidNode("Não existe nó anterior ao primeiro");
        }

        Node current = head;
        while (current != null && current.getNext() != node) {
            current = current.getNext();
        }

        if (current == null) {
            throw new EInvalidNode("Nó inválido");
        }

        return current;
    }

    @Override
    public Node after(Node node) throws EEmptyList, EInvalidNode {
        if (isEmpty()) {
            throw new EEmptyList("Lista vazia");
        }

        if (node == tail) {
            throw new EInvalidNode("Nó é o último da lista");
        }

        Node next = node.getNext();
        if (next == null) {
            throw new EInvalidNode("Nó inválido");
        }

        return next;
    }

    @Override
    public void replaceElement(Node node, Object object) throws EEmptyList, EInvalidNode {
        if (isEmpty()) {
            throw new EEmptyList("Lista vazia");
        }

        Node current = head;
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

    @Override
    public void swapElement(Node firstNode, Node secondNode) throws EEmptyList, EInvalidNode {
        if (isEmpty()) {
            throw new EEmptyList("Lista vazia");
        }

        // Verificar se os nós pertencem à lista
        Node current = head;
        boolean foundFirst = false;
        boolean foundSecond = false;

        while (current != null) {
            if (current == firstNode)
                foundFirst = true;
            if (current == secondNode)
                foundSecond = true;
            if (foundFirst && foundSecond)
                break;
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

    @Override
    public void insertBefore(Node node, Object object) throws EEmptyList, EInvalidNode {
        if (isEmpty()) {
            throw new EEmptyList("Lista vazia");
        }

        if (node == head) {
            insertFirst(object);
            return;
        }

        try {
            Node beforeNode = before(node);
            Node newNode = new Node(object);
            newNode.setNext(node);
            beforeNode.setNext(newNode);
            size++;
        } catch (EInvalidNode e) {
            throw new EInvalidNode("Nó inválido");
        }
    }

    @Override
    public void insertAfter(Node node, Object object) throws EEmptyList, EInvalidNode {
        if (isEmpty()) {
            throw new EEmptyList("Lista vazia");
        }

        Node current = head;
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

        if (node == tail) {
            tail = newNode;
        }

        size++;
    }

    @Override
    public void insertFirst(Object object) {
        Node newNode = new Node(object);

        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.setNext(head);
            head = newNode;
        }

        size++;
    }

    @Override
    public void insertLast(Object object) {
        Node newNode = new Node(object);

        if (isEmpty()) {
            head = newNode;
        } else {
            tail.setNext(newNode);
        }

        tail = newNode;
        size++;
    }

    @Override
    public void remove(Node node) throws EEmptyList, EInvalidNode {
        if (isEmpty()) {
            throw new EEmptyList("Lista vazia");
        }

        if (node == head) {
            head = head.getNext();
            if (head == null) {
                tail = null;
            }
            size--;
            return;
        }

        try {
            Node beforeNode = before(node);
            beforeNode.setNext(node.getNext());

            if (node == tail) {
                tail = beforeNode;
            }

            size--;
        } catch (EInvalidNode e) {
            throw new EInvalidNode("Nó inválido");
        }
    }
}
