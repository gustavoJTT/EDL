package Pilha.PilhaEncadeada;

public class Node
{
    public Object element;
    public Node next;

    public Node(Object o)
    {
        element = o;
    }

    public Object getElement()
    {
        return element;
    }

    public void setElement(Object newElement)
    {
        element = newElement;
    }

    public Node getNext()
    {
        return next;
    }

    public void setNext(Node n)
    {
        next = n;
    }
}