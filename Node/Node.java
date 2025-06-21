package Node;

import java.util.List;
import java.util.ArrayList;

public class Node {
    private Object element;
    private Node next, previous;

    private List<Node> children;
    private Node parent, leftChild, rightChild;

    public Node(Object object) {
        this.element = object;
        this.children = new ArrayList<>();
    }

    public Object getElement() {
        return this.element;
    }

    public void setElement(Object object) {
        this.element = object;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node node) {
        next = node;
    }

    public Node getPrevious() {
        return previous;
    }

    public void setPrevious(Node node) {
        previous = node;
    }

    // arvore
    public Node getParent() {
        return parent;
    }

    public void setParent(Node node) {
        parent = node;
    }

    public List<Node> getChild() {
        return children;
    }

    public void addChild(Node child) {
        // child.setParent(this.parent);
        children.add(child);
    }

    public void removeChild(Node child) {
        children.remove(child);
    }

    public Node getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(Node node) {
        if (node == null) {
            removeChild(leftChild);
        } else {
            addChild(node);
        }
        leftChild = node;
    }

    public Node getrightChild() {
        return rightChild;
    }

    public void setRightChild(Node node) {
        if (node == null) {
            removeChild(rightChild);
        } else {
            addChild(node);
        }
        rightChild = node;
    }
}
