package Fila;

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
        queue = new Object[capacity];
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
            capacity = newCapacity;
            first = 0;
            last = size();
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

    public void reverse()
    {
        Object reverseQueue[] = new Object[capacity];
        int newFirst = first;

        for(int i = size(); i + 1 != last; i--)
        {
            reverseQueue[i] = queue[newFirst];
            newFirst = (newFirst + 1) % capacity;
        }
        queue = reverseQueue;
        first = 0;
        last = size();
    }

    public void print()
    {
        System.out.println("Fila (tamanho total: " + capacity + ")");
        for (int i = 0; i < size(); i++)
        {
            System.out.print("[" + i + "]: ");
            if (queue[i] != null)
            {
                System.out.print(queue[i]);
            }
            else
            {
                System.out.print("null");
            }

            if (i == first)
            {
                System.out.print(" <- início");
            }
            
            if (i == last)
            {
                System.out.print(" <- fim");
            }
            System.out.println();
        }
        System.out.println("Tamanho atual da fila (elementos): " + size());
        System.out.println("Está vazia? " + isEmpty());
        System.out.println("----------------------------------");
    }
}