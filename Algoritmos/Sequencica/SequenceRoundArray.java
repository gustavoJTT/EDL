package Sequencica;
import Node.Node;
import Excesao.EEmptySequence;
import Excesao.ERankOOL;

public class SequenceRoundArray implements SequenceInterface
{
    private int firstElement, lastElement, capacity;
    private Node array[];


    public SequenceRoundArray(int capacity)
    {
        firstElement = 0;
        lastElement = 0;
        this.capacity = capacity;
        array = new Node[capacity];
    }

    //isEmpty & size
    public boolean isEmpty()
    {
        return firstElement == lastElement;
    }

    public int size()
    {
        return (capacity - firstElement + lastElement) % capacity;
    }

    //*atRank
    public Object elemAtRank(int rank) throws EEmptySequence, ERankOOL
    {
        if(isEmpty())
        {
            throw new EEmptySequence("Sequência vazia");
        }
        if(rank < 0 || rank >= size())
        {
            throw new ERankOOL("Ranque fora dos limites");
        }
        int rankPosition = (firstElement + rank) % capacity;

        return array[rankPosition].getElement();
    }

    public Object replaceAtRank(int rank, Object object) throws EEmptySequence, ERankOOL
    {
        if(isEmpty())
        {
            throw new EEmptySequence("Sequência vazia");
        }
        if(rank < 0 || rank >= size())
        {
            throw new ERankOOL("Ranque fora dos limites");
        }
        int rankPosition = (firstElement + rank) % capacity;
        
        Object temp = array[rankPosition].getElement();
        array[rankPosition].setElement(object);
        return temp;
    }

    public void insertAtRank(int rank, Object object) throws EEmptySequence, ERankOOL
    {
        if(isEmpty())
        {
            throw new EEmptySequence("Sequência vazia");
        }
        if(rank < 0 || rank >= size())
        {
            throw new ERankOOL("Ranque fora dos limites");
        }

        if(rank == 0)
        {
            firstElement = (firstElement - 1 + capacity) % capacity;
            array[firstElement].setElement(object);
        }
        else
        {
            for (int i = lastElement; i != (firstElement + rank) % capacity; i = (i - 1 + capacity) % capacity)
            {
                array[i] = array[(i - 1 + capacity) % capacity];
            }

            array[(firstElement + rank) % capacity].setElement(object);
            lastElement = (lastElement + 1) % capacity;
        }
    }

    public Object removeAtRank(int rank) throws EEmptySequence, ERankOOL
    {
        if (isEmpty())
        {
            throw new EEmptySequence("Sequência vazia");
        }
        if (rank < 0 || rank >= size())
        {
            throw new ERankOOL("Ranque fora dos limites");
        }

        Object element = array[(firstElement + rank) % capacity];

        if (rank == 0)
        {
            firstElement = (firstElement + 1) % capacity;
        }
        else
        {
            for (int i = lastElement; i != (firstElement + rank) % capacity; i = (i - 1 + capacity) % capacity)
            {
                array[i] = array[(i + 1) % capacity];
            }
            lastElement = (lastElement - 1 + capacity) % capacity;
        }
        return element;
    }

    //first & last
    public Node first() throws EEmptySequence
    {
        if (isEmpty())
        {
            throw new EEmptySequence("A sequencia está vazia");
        }

        return array[firstElement];
    }

    public Node last() throws EEmptySequence
    {
        if (isEmpty())
        {
            throw new EEmptySequence("A sequencia está vazia");
        }

        return array[(lastElement - 1 + capacity) % capacity];
    }

    //before & after
    public Node before(Node node) throws EEmptySequence
    {
        if (isEmpty())
        {
            throw new EEmptySequence("A sequencia está vazia");
        }
        
        for (int i = 0; i < size(); i++)
        {
            int pos = (firstElement + i) % capacity;
            if (array[pos] == node)
            {
                if (i == 0)
                    return null; // Não há nó anterior ao primeiro
                return array[(firstElement + i - 1) % capacity];
            }
        }
        return null;
    }

    public Node after(Node node) throws EEmptySequence
    {
        if (isEmpty())
        {
            throw new EEmptySequence("A sequencia está vazia");
        }
        
        for (int i = 0; i < size(); i++)
        {
            int pos = (firstElement + i) % capacity;
            if (array[pos] == node)
            {
                if (i == size() - 1)
                    return null; // Não há nó após o último
                return array[(firstElement + i + 1) % capacity];
            }
        }
        return null;
    }

    //replaceElement & swapElements
    public void replaceElement(Node node, Object object) throws EEmptySequence
    {
        if (isEmpty())
        {
            throw new EEmptySequence("A sequencia está vazia");
        }
        
        for (int i = 0; i < size(); i++)
        {
            int pos = (firstElement + i) % capacity;
            if (array[pos] == node)
            {
                array[pos].setElement(object);
                return;
            }
        }
    }

    public void swapElements(Node node, Node q) throws EEmptySequence
    {
        if (isEmpty())
        {
            throw new EEmptySequence("A sequencia está vazia");
        }
        
        Object temp = node.getElement();
        node.setElement(q.getElement());
        q.setElement(temp);
    }

    //insertBefore, insertAfter, insertFirst, insertLast
    public void insertBefore(Node node, Object object) throws EEmptySequence
    {
        if (isEmpty())
        {
            throw new EEmptySequence("A sequencia está vazia");
        }
        
        for (int i = 0; i < size(); i++)
        {
            int pos = (firstElement + i) % capacity;
            if (array[pos] == node)
            {
                insertAtRank(i, object);
                return;
            }
        }
    }

    public void insertAfter(Node node, Object object) throws EEmptySequence
    {
        if (isEmpty())
        {
            throw new EEmptySequence("A sequencia está vazia");
        }
        
        for (int i = 0; i < size(); i++)
        {
            int pos = (firstElement + i) % capacity;
            if (array[pos] == node)
            {
                insertAtRank(i + 1, object);
                return;
            }
        }
    }

    public void insertFirst(Object object)
    {
        if (size() == capacity - 1)
        {
            // Aumentar capacidade ou tratar erro
            return;
        }
        
        firstElement = (firstElement - 1 + capacity) % capacity;
        array[firstElement] = new Node(object);
    }

    public void insertLast(Object object)
    {
        if (size() == capacity - 1)
        {
            // Aumentar capacidade ou tratar erro
            return;
        }
        
        array[lastElement] = new Node(object);
        lastElement = (lastElement + 1) % capacity;
    }

    //remove
    public void remove(Node node) throws EEmptySequence
    {
        if (isEmpty())
        {
            throw new EEmptySequence("A sequencia está vazia");
        }
        
        for (int i = 0; i < size(); i++)
        {
            int pos = (firstElement + i) % capacity;
            if (array[pos] == node)
            {
                removeAtRank(i);
                return;
            }
        }
    }

    //métodos ponte
    public Node atRank(int rank) throws EEmptySequence
    {
        if (isEmpty())
        {
            throw new EEmptySequence("A sequencia está vazia");
        }
        if (rank < 0 || rank >= size())
        {
            throw new ERankOOL("Ranque fora dos limites");
        }
        
        return array[(firstElement + rank) % capacity];
    }

    public int rankOf(Node node) throws EEmptySequence
    {
        if (isEmpty())
        {
            throw new EEmptySequence("A sequencia está vazia");
        }
        
        for (int i = 0; i < size(); i++)
        {
            int pos = (firstElement + i) % capacity;
            if (array[pos] == node)
            {
                return i;
            }
        }
        
        throw new EEmptySequence("Nó não encontrado na sequência");
    }

    //print
    public void print()
    {
        if (isEmpty())
        {
            System.out.println("Sequência vazia");
            return;
        }
        
        System.out.print("Sequência: [ ");
        for (int i = 0; i < size(); i++)
        {
            int pos = (firstElement + i) % capacity;
            System.out.print(array[pos].getElement() + " ");
        }
        System.out.println("]");
    }
}