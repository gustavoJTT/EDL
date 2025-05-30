package Node;

public class Node {
    private Object element;
    private Node next, previous;

    public Node(Object o) {
        element = o;
    }

    public Object getElement() {
        return element;
    }

    public void setElement(Object o) {
        element = o;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node n) {
        next = n;
    }

    public Node getPrevious() {
        return previous;
    }

    public void setPrevious(Node n) {
        previous = n;
    }
}