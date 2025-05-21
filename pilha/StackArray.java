package Pilha;

public class StackArray implements StackInterface
{
    private int capacity, growth, position;
    private Object stack[];

    public StackArray(int capacity, int growth)
    {
        this. position = -1;
        this.capacity = capacity;
        this.growth = growth;
        if(growth <= 0)
        {
            growth = 0;
        }
        stack = new Object[capacity];
    }

    //isEmpty & size
    public boolean isEmpty()
    {
        return position == -1;
    }

    public int size()
    {
        return position + 1;
    }

    //top
    public Object top() throws EEmptyStack
    {
        if(isEmpty())
        {
            throw new EEmptyStack("Pilha vazia");
        }

        return stack[position];
    }

    //push & pop
    public void push(Object o)
    {
        if(position >= capacity - 1) //ou if(position + 1 == capacity)
        {
            if(growth == 0)
            {
                capacity *= 2;
            }
            else
            {
                capacity += growth;
            }
            
            Object newStack[] = new Object[capacity];
            for(int newPosition = 0; newPosition < size(); newPosition++)
            {
                newStack[newPosition] = stack[newPosition];
            }
            stack = newStack;
        }
        stack[++position] = o;
    }

    public Object pop() throws EEmptyStack
    {
        if(isEmpty())
        {
            throw new EEmptyStack("Pilha vazia");
        }
        Object removed = stack[position--];
        return removed;
    }

    //print & empty
    public void printStack() throws EEmptyStack
    {
        if(isEmpty())
        {
            throw new EEmptyStack("Pilha vazia");
        }
        for(int i = 0; i <= position; i++)
        {
            System.out.println(String.format("Posição %d -> %s | Capacidade: %d", i, stack[i], capacity));
        }
    }

    public void empty()
    {
        position = -1;
    }
}