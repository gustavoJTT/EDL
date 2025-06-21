package Lista;

import Excesao.EEmptyList;
import Excesao.EInvalidNode;
import Node.Node;

public class ListArray implements ListInterface {
    private int capacity, size;
    private Node array[];

    public ListArray(int capacity) {
        this.size = 0;
        this.capacity = capacity;
        this.array = new Node[this.capacity];
    }

    // isEmpty & this.size
    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return this.size;
    }

    // is*
    public boolean isFirst(Node node) throws EEmptyList {
        if (isEmpty()) {
            throw new EEmptyList("Lista vazia");
        }
        return this.array[0] == node;
    }

    public boolean isLast(Node node) throws EEmptyList {
        if (isEmpty()) {
            throw new EEmptyList("Lista vazia");
        }
        return this.array[size() - 1] == node;
    }

    // first & last
    public Node first() throws EEmptyList {
        if (isEmpty()) {
            throw new EEmptyList("Lista vazia");
        }
        return this.array[0];
    }

    public Node last() throws EEmptyList {
        if (isEmpty()) {
            throw new EEmptyList("Lista vazia");
        }
        return this.array[size() - 1];
    }

    // before & after
    public Node before(Node node) throws EEmptyList, EInvalidNode {
        if (isEmpty()) {
            throw new EEmptyList("Lista vazia");
        }
        if (this.array[0] == node) {
            throw new EInvalidNode("Não existe nó anterior ao primeiro");
        }

        for (int i = 1; i < size(); i++) {
            if (this.array[i] == node) {
                return this.array[i - 1];
            }
        }
        throw new EInvalidNode("Nó inválido");
    }

    public Node after(Node node) throws EEmptyList, EInvalidNode {
        if (isEmpty()) {
            throw new EEmptyList("Lista vazia");
        }

        for (int i = 0; i < size() - 1; i++) {
            if (this.array[i] == node) {
                return this.array[i + 1];
            }
        }

        if (this.array[size() - 1] == node) {
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
            if (this.array[i] == node) {
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
            if (this.array[i] == firstNode) {
                foundFirstNode = this.array[i];
            }
            if (this.array[i] == secondNode) {
                foundSecondNode = this.array[i];
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

        if (size() == this.capacity) {
            expandCapacity();
        }

        int position = -1;
        for (int i = 0; i < size(); i++) {
            if (this.array[i] == node) {
                position = i;
                break;
            }
        }

        if (position == -1) {
            throw new EInvalidNode("Nó inválido");
        }

        for (int i = this.size; i > position; i--) {
            this.array[i] = this.array[i - 1];
        }

        this.array[position] = new Node(object);
        this.size++;
    }

    public void insertAfter(Node node, Object object) throws EEmptyList, EInvalidNode {
        if (isEmpty()) {
            throw new EEmptyList("Lista vazia");
        }

        if (size() == this.capacity) {
            expandCapacity();
        }

        int position = -1;
        for (int i = 0; i < size(); i++) {
            if (this.array[i] == node) {
                position = i;
                break;
            }
        }

        if (position == -1) {
            throw new EInvalidNode("Nó inválido");
        }

        for (int i = this.size; i > position + 1; i--) {
            this.array[i] = this.array[i - 1];
        }

        this.array[position + 1] = new Node(object);
        this.size++;
    }

    public void insertFirst(Object object) {
        if (size() == this.capacity) {
            expandCapacity();
        }

        for (int i = this.size; i > 0; i--) {
            this.array[i] = this.array[i - 1];
        }

        this.array[0] = new Node(object);
        this.size++;
    }

    public void insertLast(Object object) {
        if (size() == this.capacity) {
            expandCapacity();
        }

        this.array[this.size] = new Node(object);
        this.size++;
    }

    // remove
    public void remove(Node node) throws EEmptyList, EInvalidNode {
        if (isEmpty()) {
            throw new EEmptyList("Lista vazia");
        }

        int position = -1;
        for (int i = 0; i < size(); i++) {
            if (this.array[i] == node) {
                position = i;
                break;
            }
        }

        if (position == -1) {
            throw new EInvalidNode("Nó inválido");
        }

        for (int i = position; i < this.size - 1; i++) {
            this.array[i] = this.array[i + 1];
        }

        this.array[this.size - 1] = null;
        this.size--;
    }

    // duble
    private void expandCapacity() {
        this.capacity = this.capacity * 2;
        Node[] newArray = new Node[this.capacity];

        for (int i = 0; i < size(); i++) {
            newArray[i] = this.array[i];
        }

        this.array = newArray;
    }
}
