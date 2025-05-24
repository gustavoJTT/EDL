package Lista.ListaEncadeada;

public class Node
{
    private Object element;
    private Node next;

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
}