package fila;

public class QueueArray implements QueueInterface
{
    private int first, last, capacity, growth;
    private Object queue[];

    public QueueArray(int capacity, int growth)
    {
        this.first = 0;
        this.last = 0;
        this.capacity = capacity;
        this.growth = growth;
        if(growth <= 0)
        {
            growth = 0;
        }
        Object queue[] = new Object[capacity];
    }

    //isEmpty & size
    public boolean isEmpty()
    {
        return first == last;
    }

    public int size()
    {
        return (capacity - first + last) % capacity;
    }

    //first
    public Object first() throws EEmptyQueue
    {
        if(isEmpty())
        {
            throw new EEmptyQueue("Fila vazia");
        }
        return queue[first];
    }

    //enqueue & dequeue
    public void enqueue(Object newObject)
    {
        if(size() == capacity - 1)
        {
            int newCapacity;
            if(growth == 0)
            {
                newCapacity = capacity * 2;
            }
            else
            {
                newCapacity = capacity + growth;
            }
            Object newQueue[] = new Object[newCapacity];
            int newFirst = first;

            for(int i = 0; i < size(); i++)
            {
                newQueue[i] = queue[newFirst];
                newFirst = (newFirst + 1) % capacity;
            }
            last = size();
            first = 0;
            capacity = newCapacity;
            queue = newQueue;
        }
        queue[last] = newObject;
        last = (last + 1) % capacity;
    }

    public Object dequeue() throws EEmptyQueue
    {
        if(isEmpty())
        {
            throw new EEmptyQueue("Fila vazia");
        }
        Object removed = queue[first];
        first = (first + 1) % capacity;
        return removed;
    }
}