package Deque;

public class DequeArray implements DequeInterface
{
    private int first, last, capacity, growth;
    private Object array[];

    public DequeArray(int capacity, int growth)
    {
        this.first = 0;
        this.last = 0;
        this.capacity = capacity;
        this.growth = growth;

        if (growth <= 0)
        {
            this.growth = 0;
        }
        array = new Object[capacity];
    }

    // isEmpty & size
    public boolean isEmpty()
    {
        return first == last;
    }

    public int size()
    {
        return (last - first + capacity) % capacity;
    }

    // fisrt & last
    public Object first() throws EEmptyDeque
    {
        if (isEmpty())
        {
            throw new EEmptyDeque("Deque vazio");
        }
        return array[first];
    }

    public Object last() throws EEmptyDeque
    {
        if (isEmpty())
        {
            throw new EEmptyDeque("Deque vazio");
        }
        return array[(last - 1 + capacity) % capacity];
    }

    // enqueue
    public void enqueueBeginning(Object newElement)
    {
        if (size() == capacity - 1)
        {
            int newCapacity;
            if (growth == 0)
            {
                newCapacity = capacity * 2;
            }
            else
            {
                newCapacity = capacity + growth;
            }
            Object newArray[] = new Object[newCapacity];
            int newFirst = first;
            newArray[0] = newElement;

            for (int i = 1; i < size(); i++)
            {
                newArray[i] = array[newFirst];
                newFirst = (newFirst + 1) % capacity;
            }
            last = size();
            first = 0;
            capacity = newCapacity;
            array = newArray;
        }
        first = (first - 1 + capacity) % capacity;
        array[first] = newElement;
    }

    public void enqueueEnd(Object newElement)
    {
        if (size() == capacity - 1)
        {
            int newCapacity;
            if (growth == 0)
            {
                newCapacity = capacity * 2;
            }
            else
            {
                newCapacity = capacity + growth;
            }
            Object newArray[] = new Object[newCapacity];
            int newFirst = first;

            for (int i = 0; i < size(); i++)
            {
                newArray[i] = array[newFirst];
                newFirst = (newFirst + 1) % capacity;
            }
            capacity = newCapacity;
            first = 0;
            last = size();
            array = newArray;
        }
        array[last] = newElement;
        last = (last + 1) % capacity;
    }

    //dequeue
    public Object dequeueBeginnig() throws EEmptyDeque
    {
        if (isEmpty())
        {
            throw new EEmptyDeque("Deque vazio");
        }
        Object removedElement = array[first];
        array[first] = null;
        first = (first + 1) % capacity;
        return removedElement;
    }

    public Object dequeueEnd() throws EEmptyDeque
    {
        if (isEmpty())
        {
            throw new EEmptyDeque("Deque vazio");
        }
        int pos = (last - 1 + capacity) % capacity;
        Object removedElement = array[pos];
        array[pos] = null;
        last = pos;
        return removedElement;
    }

    //print
    public void printDeque()
    {
        if (isEmpty())
        {
            System.out.println("Deque vazio");
            return;
        }

        System.out.print("Deque: [");
        int current = first;
        while (current != last)
        {
            System.out.print(array[current]);
            current = (current + 1) % capacity;

            if (current != last)
            {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }
}