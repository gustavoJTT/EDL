package Pilha.pilhaRubroNegra;

import Excesao.EEmptyStack;

public class RBStackArray implements RBStackInterface
{
    private int size, growth, redPosition, blkPosition;
    private Object rbStack[];

    public RBStackArray(int size, int growth)
    {
        this.redPosition = -1;
        this.blkPosition = size;
        this.size = size;
        this.growth = growth;
        if(growth <= 0)
        {
            this.growth = 0;
        }
        rbStack = new Object[size];
    }

    //isEmpty & size
    public boolean redIsEmpty()
    {
        return redPosition == -1;
    }

    public boolean blkIsEmpty()
    {
        return blkPosition == size;
    }
    
    public int redSize()
    {
        return redPosition + 1;
    }

    public int blkSize()
    {
        return size - blkPosition;
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
            int newSize;
            if(growth == 0)
            {
                newSize = size * 2;
            }
            else
            {
                newSize = size + growth;
            }
            resizeElements(newSize);
        }
        else if(sizeStack() <= size / 3 && size > 1)
        {
            int newSize = size / 2;
            resizeElements(newSize);
        }
    }

    public void resizeElements(int newSize)
    {
        Object newRbStack[] = new Object[newSize];

        for(int i = 0; i <= redPosition; i++)
        {
            newRbStack[i] = rbStack[i];
        }

        int newBlkPosition = newSize - blkSize();
        for(int i = 0; i < blkSize(); i++)
        {
            newRbStack[newBlkPosition + i] = rbStack[blkPosition + i];
        }

        size = newSize;
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
        System.out.println(String.format("Tamanho do Array: %d\nTamanho Pilha Vermelha: %d - P: %d\nTamanho Pilha Preta: %d - P: %d", size, redSize(), redPosition, blkSize(), blkPosition));

        for (int i = 0; i < size; i++)
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