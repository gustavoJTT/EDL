package Pilha.PilhaJ;

public class StackArray implements Stack
{
    private int position;
    private int capacity;
    private int growth;
    private Object stack[];

    public StackArray(int capacity, int growth)
    {
        this.capacity = capacity;
        position = -1; //inicia vazia
        this.growth = growth;

        if(growth <= 0)
        {
            growth = 0;
        }
        stack = new Object[capacity];
    }

    public int size()
    {
        return position + 1; 
    }

    public boolean isEmpty()
    {
        return position == -1;
    }

    public Object top() throws EEmptyStack
    {
        if(isEmpty())
        {
            throw new EEmptyStack("Pilha vazia");
        }
        return stack[position];
    }

    public void pushObject(Object o)
    {
        if(position >= capacity - 1)
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
            
            for(int i = 0; i < stack.length; i++)
            {
                newStack[i] = stack[i];
            }
            stack = newStack;
        }
        stack[++position] = o;
    }

    public Object popObject() throws EEmptyStack
    {
        if(isEmpty())
        {
            throw new EEmptyStack("Pilha vazia");
        }
        Object newStack = stack[position--];
        return newStack;
    }

    public void printStack() throws EEmptyStack
    {
        if(isEmpty())
        {
            throw new EEmptyStack("Pilha vazia");
        }
        for(int i = stack.length - 1; i >= 0; i--)
        {
            System.out.println(stack[i]);
        }
    }

    public void empty()
    {
        position = -1;
    }
}