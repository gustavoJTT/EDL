package Fila.FilaEncadeada;
import Fila.EEmptyQueue;

public class LinkedQueue implements LinkedQueueInterface
{
    private int capacity;
    private Node head, tail;

    public LinkedQueue(int capacity)
    {
        this.capacity = capacity;
        this.head = null;
        this.tail = null;
    }

    //isEmpty & size
    public boolean isEmpty()
    {
        return capacity == 0;
    }

    public int size()
    {
        return capacity;
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
        capacity++;
    }

    public Object dequeue() throws EEmptyQueue
    {
        if(isEmpty())
        {
            throw new EEmptyQueue("Fila vazia");
        }
        Object element = head.getElement();
        head = head.getNext();
        capacity--;
        return element;
    }
}