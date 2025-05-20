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
            
        }
    }
}