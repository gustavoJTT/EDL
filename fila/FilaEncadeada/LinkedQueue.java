package Fila.FilaEncadeada;
import Excesao.EEmptyQueue;
import Node.Node;

public class LinkedQueue implements LinkedQueueInterface
{
    private Node head, tail;
    private int size;

    public LinkedQueue(int size)
    {
        this.size = size;
        this.head = null;
        this.tail = null;
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

    //first
    public Object first() throws EEmptyQueue
    {
        if(isEmpty())
        {
            throw new EEmptyQueue("Pilha vazia");
        }
        return head.getElement();
    }

    //align & misAlign
    public void enqueue(Object newObject)
    {
        Node newNode = new Node(newObject);

        if(isEmpty())
        {
            head = newNode;
        }
        else
        {
            tail.setNext(newNode);
        }
        tail = newNode;
        size++;
    }

    public Object dequeue() throws EEmptyQueue
    {
        if(isEmpty())
        {
            throw new EEmptyQueue("Fila vazia");
        }
        Object element = head.getElement();
        head = head.getNext();
        size--;
        return element;
    }
}