package Fila.FilaJ;

import Pilha.PilhaJ.EEmptyStack;

public class QueueArray implements Queue
{
    private int first;
    private int last;
    private int capacity;
    private int growth;
    private Object queue[];

    public QueueArray(int capacity, int growth)
    {
        this.capacity = capacity;
        first = 0;
        last = 0;
        if(growth <= 0)
        {
            growth = 0;
        }
        queue = new Object[capacity];
    }

    //size
    public int size()
    {
        return (capacity - last + first) % capacity;
    }

    //isEmpty
    public boolean isEmpty()
    {
        return first == last;
    }

    //first && last
    public Object first() throws EEmptyQueue
    {
        if(isEmpty())
        {
            throw new EEmptyQueue("Fila vazia");
        }
        return queue[first];
    }

    public Object last() throws EEmptyQueue
    {
        if(isEmpty())
        {
            throw new EEmptyQueue("Fila vazia");
        }
        return queue[last];
    }

    //enqueue
    public void enqueue(Object o)
    {
        if(size() == capacity - 1)
        {
            if(growth == 0)
            {
                capacity *= 2;
            }
            else
            {
                capacity += growth;
            }
            Object newQueue[] = new Object[capacity];
            int newFirst = first;

            for(int i = 0; i < size() ;i++)
            {
                newQueue[i] = queue[newFirst];
                newFirst = (newFirst + 1) % capacity;
            }
            last = size();
            first = 0;
            queue = newQueue;
        }
        queue[last] = o;
        last = (last + 1) % capacity;
    }
}