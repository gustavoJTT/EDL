package Pilha.PilhaEncadeada;
import Node.Node;
import Pilha.EEmptyStack;

public class LinkedStack implements LinkedStackInterface
{
    private int size;
    private Node head;

    public LinkedStack(int size)
    {
        this.head = null;
        this.size = size;
    }

    //isEmpty & size
    public boolean isEmpty()
    {
        return head == null;
    }

    public int size()
    {
        return size;
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
        size++;
    }

    public Object pop() throws EEmptyStack
    {
        if(isEmpty())
        {
            throw new EEmptyStack("Pilha vazia");
        }
        Object o = head.getElement();
        head = head.getNext();
        size--;
        return o;
    }
}