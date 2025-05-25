package Fila;

import Exceptions.EEmptyQueue;

//rever o reverse quando for fazer atividades novamente

public class QueueArray implements QueueInterface
{
    private int  size, growth, firstElement, lastElement;
    private Object queue[];

    public QueueArray(int size, int growth)
    {
        this.firstElement = 0;
        this.lastElement = 0;
        this.size = size;
        this.growth = growth;
        if(growth <= 0)
        {
            growth = 0;
        }
        queue = new Object[size];
    }

    //isEmpty & size
    public boolean isEmpty()
    {
        return firstElement == lastElement;
    }

    public int size()
    {
        return (size - firstElement + lastElement) % size;
    }

    //first
    public Object first() throws EEmptyQueue
    {
        if(isEmpty())
        {
            throw new EEmptyQueue("Fila vazia");
        }
        return queue[firstElement];
    }

    //enqueue & dequeue
    public void enqueue(Object newObject)
    {
        if(size() == size - 1)
        {
            int newSize;
            if(growth == 0)
            {
                newSize = size * 2;
            }
            else
            {
                newSize = size + growth;
            }
            Object newQueue[] = new Object[newSize];
            int newFirst = firstElement;

            for(int i = 0; i < size(); i++)
            {
                newQueue[i] = queue[newFirst];
                newFirst = (newFirst + 1) % size;
            }
            firstElement = 0;
            lastElement = size();
            size = newSize;
            queue = newQueue;
        }
        queue[lastElement] = newObject;
        lastElement = (lastElement + 1) % size;
    }

    public Object dequeue() throws EEmptyQueue
    {
        if(isEmpty())
        {
            throw new EEmptyQueue("Fila vazia");
        }
        Object removed = queue[firstElement];
        firstElement = (firstElement + 1) % size;
        return removed;
    }

    public void reverse()
    {
        Object reverseQueue[] = new Object[size];

        for(int i = size(); i + 1 != lastElement; i--)
        {
            reverseQueue[i] = queue[(lastElement - 1 - i + size) % size];
        }
        firstElement = 0;
        lastElement = size();
        queue = reverseQueue;
    }

    public void print()
    {
        System.out.println("Fila (tamanho total: " + size + ")");
        int current = firstElement;
        for (int i = 0; i < size(); i++)
        {
            System.out.print("[" + i + "]: " + queue[current]);
            if (i == 0)
            {
                System.out.print(" <- início");
            }
            if (i == size() - 1)
            {
                System.out.print(" <- fim");
            }
            System.out.println();
            current = (current + 1) % size;
        }
        System.out.println("Tamanho atual: " + size());
        System.out.println("Está vazia? " + isEmpty());
    }
}