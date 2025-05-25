package Lista.ListaDuplamenteEncadeada;
import Lista.EEmptyList;
import Lista.EInvalidNode;
import Lista.ListInterface;
import Node.Node;

public class DLinkedList
{
    private Node head, tail;
    private int size;

    public DLinkedList()
    {
        head = new Node(null);
        tail = new Node(null);
        head.setNext(tail);
        tail.setPrevious(head);
        size = 0;
    }

    //isEmpty & size
    public boolean isEmpty()
    {
        return size == 0;
    }

    public int size()
    {
        return size;
    }

    //is*
}