package Pilha;

import Excesao.EEmptyStack;

public class StackArray implements StackInterface
{
    private int size, growth, currentPosition;
    private Object stack[];

    public StackArray(int size, int growth)
    {
        this. currentPosition = -1;
        this.size = size;
        this.growth = growth;
        if(growth <= 0)
        {
            this.growth = 0;
        }
        stack = new Object[size];
    }

    //isEmpty & size
    public boolean isEmpty()
    {
        return currentPosition == -1;
    }

    public int size()
    {
        return currentPosition + 1;
    }

    //top
    public Object top() throws EEmptyStack
    {
        if(isEmpty())
        {
            throw new EEmptyStack("Pilha vazia");
        }

        return stack[currentPosition];
    }

    //push & pop
    public void push(Object o)
    {
        if(currentPosition >= size - 1) //ou if(currentPosition + 1 == size)
        {
            if(growth == 0)
            {
                size *= 2;
            }
            else
            {
                size += growth;
            }
            
            Object newStack[] = new Object[size];
            for(int newPosition = 0; newPosition < size(); newPosition++)
            {   
                newStack[newPosition] = stack[newPosition];
            }
            stack = newStack;
        }
        stack[++currentPosition] = o;
    }

    public Object pop() throws EEmptyStack
    {
        if(isEmpty())
        {
            throw new EEmptyStack("Pilha vazia");
        }
        Object removed = stack[currentPosition--];
        return removed;
    }

    //print & empty
    public void printStack() throws EEmptyStack
    {
        if(isEmpty())
        {
            throw new EEmptyStack("Pilha vazia");
        }
        for(int i = 0; i <= currentPosition; i++)
        {
            System.out.println(String.format("Posição %d -> %s | Capacidade: %d", i, stack[i], size));
        }
    }

    public void empty()
    {
        currentPosition = -1;
    }
}