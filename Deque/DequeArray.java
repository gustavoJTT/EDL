package Deque;

import Exceptions.EEmptyDeque;

public class DequeArray implements DequeInterface
{
    private int size, growth, firstElement, lastElement;
    private Object array[];

    public DequeArray(int size, int growth)
    {
        this.firstElement = 0;
        this.lastElement = 0;
        this.size = size;
        this.growth = growth;

        if (growth <= 0)
        {
            this.growth = 0;
        }
        array = new Object[size];
    }

    // isEmpty & size
    public boolean isEmpty()
    {
        return firstElement == lastElement;
    }

    public int size()
    {
        return (lastElement - firstElement + size) % size;
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
        return array[(lastElement - 1 + size) % size];
    }

    // enqueue
    public void enqueueBeginning(Object newElement)
    {
        if (size() == size - 1)
        {
            int newSize;
            if (growth == 0)
            {
                newSize = size * 2;
            }
            else
            {
                newSize = size + growth;
            }
            Object newArray[] = new Object[newSize];
            int newFirst = firstElement;
            newArray[0] = newElement;

            for (int i = 1; i < size(); i++)
            {
                newArray[i] = array[newFirst];
                newFirst = (newFirst + 1) % size;
            }
            firstElement = 0;
            lastElement = size();
            size = newSize;
            array = newArray;
        }
        firstElement = (firstElement - 1 + size) % size;
        array[firstElement] = newElement;
    }

    public void enqueueEnd(Object newElement)
    {
        if (size() == size - 1)
        {
            int newSize;
            if (growth == 0)
            {
                newSize = size * 2;
            }
            else
            {
                newSize = size + growth;
            }
            Object newArray[] = new Object[newSize];
            int newFirst = firstElement;

            for (int i = 0; i < size(); i++)
            {
                newArray[i] = array[newFirst];
                newFirst = (newFirst + 1) % size;
            }
            firstElement = 0;
            lastElement = size();
            size = newSize;
            array = newArray;
        }
        array[lastElement] = newElement;
        lastElement = (lastElement + 1) % size;
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
        firstElement = (firstElement + 1) % size;
        return removedElement;
    }

    public Object dequeueEnd() throws EEmptyDeque
    {
        if (isEmpty())
        {
            throw new EEmptyDeque("Deque vazio");
        }
        lastElement = (lastElement - 1 + size) % size;
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
            current = (current + 1) % size;

            if (current != lastElement)
            {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }
}