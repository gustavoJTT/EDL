package Vector;

import Excesao.EEmptyVector;
import Excesao.ERankOOL;

public class VectorArray implements VectorInterface
{
    private int size, growth, lastElement;
    private Object array[];

    public VectorArray(int size, int growth)
    {
        this.lastElement = 0;
        this.size = size;
        this.growth = growth;
        if(growth <= 0)
        {
            this.growth = 0;
        }
        array = new Object[size];
    }

    //isEmpty & size
    public boolean isEmpty()
    {
        return lastElement == 0;
    }

    public int size()
    {
        return lastElement;
    }

    //element
    public Object elemAtRank(int rank) throws EEmptyVector, ERankOOL
    {
        if(isEmpty())
        {
            throw new EEmptyVector("Array vazio");
        }

        if(rank < 0 || rank >= size())
        {
            throw new ERankOOL("Rank fora dos limites");
        }
        return array[rank];
    }

    //replace
    public Object replaceAtRank(int rank, Object newObject) throws EEmptyVector, ERankOOL
    {
        if(isEmpty())
        {
            throw new EEmptyVector("Array vazio");
        }

        if(rank < 0 || rank >= size())
        {
            throw new ERankOOL("Rank fora dos limites");
        }
        Object old = array[rank];
        array[rank] = newObject;
        return old;
    }

    //insert
    public void insertAtRank(int rank, Object newObject) throws ERankOOL
    {
        if(rank < 0 || rank > size())
        {
            throw new ERankOOL("Rank fora dos limites");
        }

        if(size() == size)
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
            Object newArray[] = new Object[newSize];

            for(int i = 0; i < size(); i++)
            {
                if(i < rank)
                {
                    newArray[i] = array[i];
                }
                else
                {
                    newArray[i + 1] = array[i];
                }
            }
            size = newSize;
            array = newArray;
        }
        for(int i = size(); i > rank; i--)
        {
            array[i] = array[i - 1];
        }
        array[rank] = newObject;
        lastElement++;
    }

    //remove
    public Object removeAtRank(int rank) throws EEmptyVector, ERankOOL
    {
        if (isEmpty())
        {
            throw new EEmptyVector("Array vazio");
        }
        if (rank < 0 || rank >= size())
        {
            throw new ERankOOL("Rank fora dos limites");
        }

        Object removed = array[rank];

        // Move todos os elementos após o rank uma posição para trás
        for (int i = rank; i < size() - 1; i++)
        {
            array[i] = array[i + 1];
        }

        array[size() - 1] = null; // Remove a referência do último elemento
        lastElement--;

        return removed;
    }
}