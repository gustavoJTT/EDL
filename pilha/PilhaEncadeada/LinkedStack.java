package Pilha.PilhaEncadeada;
import Pilha.EEmptyStack;

public class LinkedStack implements LinkedStackInterface
{
    private int capacity;
    private Node head;

    public LinkedStack(int capacity)
    {
        this.head = null;
        this.capacity = capacity;
    }

    //isEmpty & size
    public boolean isEmpty()
    {
        return head == null;
    }

    public int size()
    {
        return capacity;
    }

    //top
    public Object top() throws EEmptyStack
    {
        if(isEmpty())
        {
            throw new EEmptyStack("Pilha vazia");
        }
        return head.getElement();
    }

    //push pop
    public void push(Object newElement)
    {
        Node newNode = new Node(newElement);
        newNode.setNext(head);  //liga o novo no atual
        head = newNode;
        capacity++;
    }

    public Object pop() throws EEmptyStack
    {
        if(isEmpty())
        {
            throw new EEmptyStack("Pilha vazia");
        }
        Object o = head.getElement();
        head = head.getNext();
        capacity--;
        return o;
    }
}