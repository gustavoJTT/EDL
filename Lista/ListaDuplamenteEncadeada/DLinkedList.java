package Lista.ListaDuplamenteEncadeada;
import Exceptions.EEmptyList;
import Lista.ListInterface;
import Node.Node;

public class DLinkedList implements ListInterface
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
    public boolean isFirst(Node node) throws EEmptyList
    {
        if(isEmpty())
        {
            throw new EEmptyList("Lista vazia");
        }
        return node == head;
    }

    public boolean isLast(Node node) throws EEmptyList
    {
        if(isEmpty())
        {
            throw new EEmptyList("Lista vazia");
        }
        return node == tail;
    }

    //first & last
    public Node first() throws EEmptyList
    {
        if(isEmpty())
        {
            throw new EEmptyList("Lista vazia");
        }
        return head;
    }

    public Node last() throws EEmptyList
    {
        if(isEmpty())
        {
            throw new EEmptyList("Lista vazia");
        }
        return tail;
    }

    //before & after
    public Node before(Node node) throws EEmptyList
    {
        if(isEmpty())
        {
            throw new EEmptyList("Lista vazia");
        }
        return node.getPrevious();
    }

    public Node after(Node node) throws EEmptyList
    {
        if(isEmpty())
        {
            throw new EEmptyList("Lista vazia");
        }
        return node.getNext();
    }

    //reeplace
    public void replaceElement(Node node, Object object) throws EEmptyList
    {
        if (isEmpty()) 
        {
            throw new EEmptyList("Lista vazia");
        }
        node.setElement(object);
    }

    //swap
    public void swapElement(Node firstNode, Node secondNode) throws EEmptyList
    {
        if(isEmpty())
        {
            throw new EEmptyList("Lista vazia");
        }

        Object temp = firstNode.getElement();

        firstNode.setElement(secondNode.getElement());
        secondNode.setElement(temp);
    }

    //insert*
    public void insertBefore(Node node, Object object) throws EEmptyList
    {
        if(isEmpty())
        {
            throw new EEmptyList("Lista vazia");
        }

        Node newNode = new Node(object);

        newNode.setNext(node);
        newNode.setPrevious(node.getPrevious());
        node.getPrevious().setNext(newNode);
        node.setPrevious(newNode);
        size++;
    }

    public void insertAfter(Node node, Object object) throws EEmptyList
    {
        if(isEmpty())
        {
            throw new EEmptyList("Lista vazia");
        }
        Node newNode = new Node(object);

        newNode.setNext(node.getNext());
        newNode.setPrevious(node);
        node.getNext().setPrevious(newNode);
        node.setNext(newNode);
        size++;
    }

    public void insertFirst(Object object) throws EEmptyList
    {
        if(isEmpty())
        {
            throw new EEmptyList("Lista vazia");
        }
        Node newNode = new Node(object);

        newNode.setPrevious(head);
        newNode.setNext(head.getNext());
        head.getNext().setPrevious(newNode);
        head.setNext(newNode);
        size++;
    }

    public void insertLast(Object object) throws EEmptyList
    {
        if(isEmpty())
        {
            throw new EEmptyList("Lista vazia");
        }
        Node newNode = new Node(object);

        newNode.setNext(tail);
        newNode.setPrevious(tail.getPrevious());
        tail.getPrevious().setNext(newNode);
        tail.setNext(newNode);
        size++;
    }

    //remove
    public void remove(Node node) throws EEmptyList
    {
        if(isEmpty())
        {
            throw new EEmptyList("Lista vaiza");
        }
        node.getPrevious().setNext(node.getNext());
        node.getNext().setPrevious(node.getPrevious());
    }

    //print
    public void print()
    {
        Node temp = head.getNext(); 
        while (temp.getElement() != null) {
            System.out.println(temp.getElement());
            temp = temp.getNext();
        }
    }
}