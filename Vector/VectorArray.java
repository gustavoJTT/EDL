package Vector;

public class VectorArray implements VectorInterface
{
    private int capacity, growth, finalArray;
    private Object array[];

    public VectorArray(int capacity, int growth)
    {
        this.finalArray = 0;
        this.capacity = capacity;
        this.growth = growth;
        if(growth <= 0)
        {
            this.growth = 0;
        }
        array = new Object[capacity];
    }

    //isEmpty & size
    public boolean isEmpty()
    {
        return finalArray == 0;
    }

    public int size()
    {
        return finalArray;
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
    public void insertAtRank(int rank, Object newObject) throws EEmptyVector, ERankOOL
    {
        if(rank < 0 || rank > size())
        {
            throw new ERankOOL("Rank fora dos limites");
        }

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
            Object newArray[] = new Object[newCapacity];

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
            capacity = newCapacity;
            array = newArray;
        }
        for(int i = size(); i > rank; i--)
        {
            array[i] = array[i - 1];
        }
        array[rank] = newObject;
        finalArray++;
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
        finalArray--;

        return removed;
    }
}