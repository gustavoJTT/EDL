package Lista;

import Excesao.EEmptyList;
import Excesao.EInvalidNode;
import Node.Node;

public class ListArray implements ListInterface {
    private int capacity, size;
    private Node array[];

    public ListArray(int capacity) {
        size = 0;
        this.capacity = capacity;
        array = new Node[capacity];
    }

    // isEmpty & size
    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return size;
    }

    // is*
    public boolean isFirst(Node node) throws EEmptyList {
        if (isEmpty()) {
            throw new EEmptyList("Lista vazia");
        }
        return array[0] == node;
    }

    public boolean isLast(Node node) throws EEmptyList {
        if (isEmpty()) {
            throw new EEmptyList("Lista vazia");
        }
        return array[size() - 1] == node;
    }

    // first & last
    public Node first() throws EEmptyList {
        if (isEmpty()) {
            throw new EEmptyList("Lista vazia");
        }
        return array[0];
    }

    public Node last() throws EEmptyList {
        if (isEmpty()) {
            throw new EEmptyList("Lista vazia");
        }
        return array[size() - 1];
    }

    // before & after
    public Node before(Node node) throws EEmptyList, EInvalidNode {
        if (isEmpty()) {
            throw new EEmptyList("Lista vazia");
        }
        if (array[0] == node) {
            throw new EInvalidNode("Não existe nó anterior ao primeiro");
        }

        for (int i = 1; i < size(); i++) {
            if (array[i] == node) {
                return array[i - 1];
            }
        }
        throw new EInvalidNode("Nó inválido");
    }

    public Node after(Node node) throws EEmptyList, EInvalidNode {
        if (isEmpty()) {
            throw new EEmptyList("Lista vazia");
        }

        for (int i = 0; i < size() - 1; i++) {
            if (array[i] == node) {
                return array[i + 1];
            }
        }

        if (array[size() - 1] == node) {
            throw new EInvalidNode("Nó é o último da lista");
        }

        throw new EInvalidNode("Nó inválido");
    }

    // replace
    public void replaceElement(Node node, Object object) throws EEmptyList, EInvalidNode {
        if (isEmpty()) {
            throw new EEmptyList("Lista vazia");
        }

        for (int i = 0; i < size(); i++) {
            if (array[i] == node) {
                node.setElement(object);
                return;
            }
        }
        throw new EInvalidNode("Nó inválido");
    }

    // swap
    public void swapElement(Node firstNode, Node secondNode) throws EEmptyList, EInvalidNode {
        if (isEmpty()) {
            throw new EEmptyList("Lista vazia");
        }

        Node foundFirstNode = null;
        Node foundSecondNode = null;

        for (int i = 0; i < size(); i++) {
            if (array[i] == firstNode) {
                foundFirstNode = array[i];
            }
            if (array[i] == secondNode) {
                foundSecondNode = array[i];
            }
        }

        if (foundFirstNode == null || foundSecondNode == null) {
            throw new EInvalidNode("Um dos nós é inválido");
        }

        Object temp = foundFirstNode.getElement();
        foundFirstNode.setElement(foundSecondNode.getElement());
        foundSecondNode.setElement(temp);
    }

    // insert*
    public void insertBefore(Node node, Object object) throws EEmptyList, EInvalidNode {
        if (isEmpty()) {
            throw new EEmptyList("Lista vazia");
        }

        if (size() == capacity) {
            expandCapacity();
        }

        int position = -1;
        for (int i = 0; i < size(); i++) {
            if (array[i] == node) {
                position = i;
                break;
            }
        }

        if (position == -1) {
            throw new EInvalidNode("Nó inválido");
        }

        for (int i = size; i > position; i--) {
            array[i] = array[i - 1];
        }

        array[position] = new Node(object);
        size++;
    }

    public void insertAfter(Node node, Object object) throws EEmptyList, EInvalidNode {
        if (isEmpty()) {
            throw new EEmptyList("Lista vazia");
        }

        if (size() == capacity) {
            expandCapacity();
        }

        int position = -1;
        for (int i = 0; i < size(); i++) {
            if (array[i] == node) {
                position = i;
                break;
            }
        }

        if (position == -1) {
            throw new EInvalidNode("Nó inválido");
        }

        for (int i = size; i > position + 1; i--) {
            array[i] = array[i - 1];
        }

        array[position + 1] = new Node(object);
        size++;
    }

    public void insertFirst(Object object) {
        if (size() == capacity) {
            expandCapacity();
        }

        for (int i = size; i > 0; i--) {
            array[i] = array[i - 1];
        }

        array[0] = new Node(object);
        size++;
    }

    public void insertLast(Object object) {
        if (size() == capacity) {
            expandCapacity();
        }

        array[size] = new Node(object);
        size++;
    }

    // remove
    public void remove(Node node) throws EEmptyList, EInvalidNode {
        if (isEmpty()) {
            throw new EEmptyList("Lista vazia");
        }

        int position = -1;
        for (int i = 0; i < size(); i++) {
            if (array[i] == node) {
                position = i;
                break;
            }
        }

        if (position == -1) {
            throw new EInvalidNode("Nó inválido");
        }

        for (int i = position; i < size - 1; i++) {
            array[i] = array[i + 1];
        }

        array[size - 1] = null;
        size--;
    }

    // duble
    private void expandCapacity() {
        capacity = capacity * 2;
        Node[] newArray = new Node[capacity];

        for (int i = 0; i < size(); i++) {
            newArray[i] = array[i];
        }

        array = newArray;
    }
}
