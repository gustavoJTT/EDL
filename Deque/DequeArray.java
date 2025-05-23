package Deque;

public class DequeArray implements DequeInterface
{
    private int capacity, growth, firstElement, lastElement;
    private Object array[];

    public DequeArray(int capacity, int growth)
    {
        this.firstElement = 0;
        this.lastElement = 0;
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
        return firstElement == lastElement;
    }

    public int size()
    {
        return (lastElement - firstElement + capacity) % capacity;
    }

    // first & last
    public Object first() throws EEmptyDeque
    {
        if (isEmpty())
        {
            throw new EEmptyDeque("Deque vazio");
        }
        return array[firstElement];
    }

    public Object last() throws EEmptyDeque
    {
        if (isEmpty())
        {
            throw new EEmptyDeque("Deque vazio");
        }
        return array[(lastElement - 1 + capacity) % capacity];
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
            int newFirst = firstElement;
            newArray[0] = newElement;

            for (int i = 1; i < size(); i++)
            {
                newArray[i] = array[newFirst];
                newFirst = (newFirst + 1) % capacity;
            }
            firstElement = 0;
            lastElement = size();
            capacity = newCapacity;
            array = newArray;
        }
        firstElement = (firstElement - 1 + capacity) % capacity;
        array[firstElement] = newElement;
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
            int newFirst = firstElement;

            for (int i = 0; i < size(); i++)
            {
                newArray[i] = array[newFirst];
                newFirst = (newFirst + 1) % capacity;
            }
            firstElement = 0;
            lastElement = size();
            capacity = newCapacity;
            array = newArray;
        }
        array[lastElement] = newElement;
        lastElement = (lastElement + 1) % capacity;
    }

    //dequeue
    public Object dequeueBeginnig() throws EEmptyDeque
    {
        if (isEmpty())
        {
            throw new EEmptyDeque("Deque vazio");
        }
        Object removedElement = array[firstElement];
        array[firstElement] = null;
        firstElement = (firstElement + 1) % capacity;
        return removedElement;
    }

    public Object dequeueEnd() throws EEmptyDeque
    {
        if (isEmpty())
        {
            throw new EEmptyDeque("Deque vazio");
        }
        lastElement = (lastElement - 1 + capacity) % capacity;
        Object removedElement = array[lastElement];
        array[lastElement] = null;
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
        int current = firstElement;
        while (current != lastElement)
        {
            System.out.print(array[current]);
            current = (current + 1) % capacity;

            if (current != lastElement)
            {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }
}