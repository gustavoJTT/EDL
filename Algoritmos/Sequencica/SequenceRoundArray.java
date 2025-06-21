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
        this.firstElement = 0;
        this.lastElement = 0;
        this.capacity = capacity;
        this.array = new Node[this.capacity];
    }

    //isEmpty & size
    public boolean isEmpty()
    {
        return this.firstElement == this.lastElement;
    }

    public int size()
    {
        return (this.capacity - this.firstElement + this.lastElement) % this.capacity;
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
        int rankPosition = (this.firstElement + rank) % this.capacity;

        return this.array[rankPosition].getElement();
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
        int rankPosition = (this.firstElement + rank) % this.capacity;

        Object temp = this.array[rankPosition].getElement();
        this.array[rankPosition].setElement(object);
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
            this.firstElement = (this.firstElement - 1 + this.capacity) % this.capacity;
            this.array[this.firstElement].setElement(object);
        }
        else
        {
            for (int i = this.lastElement; i != (this.firstElement + rank) % this.capacity; i = (i - 1 + this.capacity) % this.capacity)
            {
                this.array[i] = this.array[(i - 1 + this.capacity) % this.capacity];
            }

            this.array[(this.firstElement + rank) % this.capacity].setElement(object);
            this.lastElement = (this.lastElement + 1) % this.capacity;
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

        Object element = this.array[(this.firstElement + rank) % this.capacity];

        if (rank == 0)
        {
            this.firstElement = (this.firstElement + 1) % this.capacity;
        }
        else
        {
            for (int i = this.lastElement; i != (this.firstElement + rank) % this.capacity; i = (i - 1 + this.capacity) % this.capacity)
            {
                this.array[i] = this.array[(i + 1) % this.capacity];
            }
            this.lastElement = (this.lastElement - 1 + this.capacity) % this.capacity;
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

        return this.array[this.firstElement];
    }

    public Node last() throws EEmptySequence
    {
        if (isEmpty())
        {
            throw new EEmptySequence("A sequencia está vazia");
        }

        return this.array[(this.lastElement - 1 + this.capacity) % this.capacity];
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
            int pos = (this.firstElement + i) % this.capacity;
            if (this.array[pos] == node)
            {
                if (i == 0)
                    return null; // Não há nó anterior ao primeiro
                return this.array[(this.firstElement + i - 1) % this.capacity];
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
            int pos = (this.firstElement + i) % this.capacity;
            if (this.array[pos] == node)
            {
                if (i == size() - 1)
                    return null; // Não há nó após o último
                return this.array[(this.firstElement + i + 1) % this.capacity];
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
            int pos = (this.firstElement + i) % this.capacity;
            if (this.array[pos] == node)
            {
                this.array[pos].setElement(object);
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
            int pos = (this.firstElement + i) % this.capacity;
            if (this.array[pos] == node)
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
            int pos = (this.firstElement + i) % this.capacity;
            if (this.array[pos] == node)
            {
                insertAtRank(i + 1, object);
                return;
            }
        }
    }

    public void insertFirst(Object object)
    {
        if (size() == this.capacity - 1)
        {
            // Aumentar capacidade ou tratar erro
            return;
        }

        this.firstElement = (this.firstElement - 1 + this.capacity) % this.capacity;
        this.array[this.firstElement] = new Node(object);
    }

    public void insertLast(Object object)
    {
        if (size() == this.capacity - 1)
        {
            // Aumentar capacidade ou tratar erro
            return;
        }

        this.array[this.lastElement] = new Node(object);
        this.lastElement = (this.lastElement + 1) % this.capacity;
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
            int pos = (this.firstElement + i) % this.capacity;
            if (this.array[pos] == node)
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

        return this.array[(this.firstElement + rank) % this.capacity];
    }

    public int rankOf(Node node) throws EEmptySequence
    {
        if (isEmpty())
        {
            throw new EEmptySequence("A sequencia está vazia");
        }

        for (int i = 0; i < size(); i++)
        {
            int pos = (this.firstElement + i) % this.capacity;
            if (this.array[pos] == node)
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
            int pos = (this.firstElement + i) % this.capacity;
            System.out.print(this.array[pos].getElement() + " ");
        }
        System.out.println("]");
    }
}
