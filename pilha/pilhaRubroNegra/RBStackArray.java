package Pilha.PilhaRubroNegra;

import Pilha.EEmptyStack;

public class RBStackArray implements RBStackInterface
{
    private int capacity;
    private int growth;
    private int redPosition;
    private int blkPosition;
    private Object rbStack[];

    public RBStackArray(int capacity, int growth)
    {
        this.redPosition = -1;
        this.blkPosition = capacity;
        this.capacity = capacity;
        this.growth = growth;
        if(growth <= 0)
        {
            growth = 0;
        }
        rbStack = new Object[capacity];
    }

    //isEmpty & size
    public boolean redIsEmpty()
    {
        return redPosition == -1;
    }

    public boolean blkIsEmpty()
    {
        return blkPosition == capacity;
    }
    
    public int redSize()
    {
        return redPosition + 1;
    }

    public int blkSize()
    {
        return capacity - blkPosition;
    }

    public int sizeStack()
    {
        return redSize() + blkSize();
    }

    //top
    public Object redTop() throws EEmptyStack
    {
        if(redIsEmpty())
        {
            throw new EEmptyStack("Pilha vermelha vazia");
        }
        return rbStack[redPosition];
    }

    public Object blkTop() throws EEmptyStack
    {
        if(blkIsEmpty())
        {
            throw new EEmptyStack("Pilha preta vazia");
        }
        return rbStack[blkPosition];
    }

    //replace elements
    public void verifySpace()
    {
        if(redPosition + 1 == blkPosition)
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
            resizeElements(newCapacity);
        }
        else if(sizeStack() <= capacity / 3 && capacity > 1)
        {
            int newCapacity = capacity / 2;
            resizeElements(newCapacity);
        }
    }

    public void resizeElements(int newCapacity)
    {
        Object newRbStack[] = new Object[newCapacity];

        for(int i = 0; i <= redPosition; i++)
        {
            newRbStack[i] = rbStack[i];
        }

        int newBlkPosition = newCapacity - blkPosition;
        for(int i = 0; i < blkSize(); i++)
        {
            newRbStack[newBlkPosition + i] = rbStack[blkPosition + i];
        }

        capacity = newCapacity;
        blkPosition = newBlkPosition;
        rbStack = newRbStack;
    }

    //push & pop
    public void redPushObject(Object newObject)
    {
        verifySpace();
        rbStack[++redPosition] = newObject;
    }

    public void blkPushObject(Object newObject)
    {
        verifySpace();
        rbStack[--blkPosition] = newObject;
    }

    public Object redPopObject() throws EEmptyStack
    {
        if(redIsEmpty())
        {
            throw new EEmptyStack("Pilha vermelha vazia");
        }
        Object removed = rbStack[redPosition--];
        verifySpace();
        return removed;
    }

    public Object blkPopObject() throws EEmptyStack
    {
        if(blkIsEmpty())
        {
            throw new EEmptyStack("Pilha preta vazia");
        }
        Object removed = rbStack[blkPosition++];
        verifySpace();
        return removed;
    }

    //print
    public void printRbStack()
    {
        System.out.println("-----------------------------------");
        System.out.println("Pilha Array");
        System.out.println(String.format("Tamanho do Array: %d\nTamanho Pilha Vermelha: %d - P: %d\nTamanho Pilha Preta: %d - P: %d", capacity, redSize(), redPosition, blkSize(), blkPosition));

        for (int i = 0; i < capacity; i++)
        {
            if (i <= redPosition)
            {
                System.out.print(String.format(" %s V |", rbStack[i]));
            }
            else if (i >= blkPosition)
            {
                System.out.print(String.format(" %s P |", rbStack[i]));
            }
            else
            {
                System.out.print("     |");
            }
        }
        System.out.println("\n-----------------------------------");
    }
}