package Lista.ListaEncadeada;
import Excesao.EEmptyList;
import Excesao.EInvalidNode;
import Lista.ListInterface;
import Node.Node;

public class LinkedList implements ListInterface
{
    private Node head, tail;
    private int size;

    public LinkedList()
    {
        head = null;
        tail = null;
        size = 0;
    }

    //isEmpty & size
    public int size()
    {
        return size;
    }

    public boolean isEmpty()
    {
        return size == 0;
    }

    //is*
    public boolean isFirst(Node node) throws EEmptyList
    {
        if (isEmpty())
        {
            throw new EEmptyList("Lista vazia");
        }
        return head == node;
    }

    public boolean isLast(Node node) throws EEmptyList
    {
        if (isEmpty())
        {
            throw new EEmptyList("Lista vazia");
        }
        return tail == node;
    }

    //first & last
    public Node first() throws EEmptyList
    {
        if (isEmpty())
        {
            throw new EEmptyList("Lista vazia");
        }
        return head;
    }

    public Node last() throws EEmptyList
    {
        if (isEmpty())
        {
            throw new EEmptyList("Lista vazia");
        }
        return tail;
    }

    //before & after
    public Node before(Node node) throws EEmptyList, EInvalidNode
    {
        if (isEmpty())
        {
            throw new EEmptyList("Lista vazia");
        }
        if (node == head)
        {
            throw new EInvalidNode("Não existe nó anterior ao primeiro");
        }

        Node current = head;
        while (current != null && current.getNext() != node)
        {
            current = current.getNext();
        }

        if (current == null)
        {
            throw new EInvalidNode("Nó inválido");
        }

        return current;
    }

    public Node after(Node node) throws EEmptyList, EInvalidNode
    {
        if (isEmpty())
        {
            throw new EEmptyList("Lista vazia");
        }

        if (node == tail)
        {
            throw new EInvalidNode("Nó é o último da lista");
        }

        Node next = node.getNext();
        if (next == null)
        {
            throw new EInvalidNode("Nó inválido");
        }

        return next;
    }

    //replace
    public void replaceElement(Node node, Object object) throws EEmptyList, EInvalidNode
    {
        if (isEmpty())
        {
            throw new EEmptyList("Lista vazia");
        }

        Node current = head;
        boolean found = false;

        while (current != null)
        {
            if (current == node)
            {
                found = true;
                break;
            }
            current = current.getNext();
        }

        if (!found)
        {
            throw new EInvalidNode("Nó inválido");
        }

        node.setElement(object);
    }

    //swap
    public void swapElement(Node firstNode, Node secondNode) throws EEmptyList, EInvalidNode
    {
        if (isEmpty())
        {
            throw new EEmptyList("Lista vazia");
        }

        // Verificar se os nós pertencem à lista
        Node current = head;
        boolean foundFirst = false;
        boolean foundSecond = false;

        while (current != null)
        {
            if (current == firstNode)
            {
                foundFirst = true;
            }
            if (current == secondNode)
            {
                foundSecond = true;
            }
            if (foundFirst && foundSecond)
            {
                break;
            }
            current = current.getNext();
        }

        if (!foundFirst || !foundSecond)
        {
            throw new EInvalidNode("Um dos nós é inválido");
        }

        // Trocar os elementos
        Object temp = firstNode.getElement();
        firstNode.setElement(secondNode.getElement());
        secondNode.setElement(temp);
    }

    //Insert*
    public void insertBefore(Node node, Object object) throws EEmptyList, EInvalidNode
    {
        if (isEmpty())
        {
            throw new EEmptyList("Lista vazia");
        }

        if (node == head)
        {
            insertFirst(object);
            return;
        }

        Node beforeNode = before(node);
        Node newNode = new Node(object);
        newNode.setNext(node);
        beforeNode.setNext(newNode);
        size++;
    }

    public void insertAfter(Node node, Object object) throws EEmptyList, EInvalidNode
    {
        if (isEmpty())
        {
            throw new EEmptyList("Lista vazia");
        }

        Node current = head;
        boolean found = false;

        while (current != null)
        {
            if (current == node)
            {
                found = true;
                break;
            }
            current = current.getNext();
        }

        if (!found)
        {
            throw new EInvalidNode("Nó inválido");
        }

        Node newNode = new Node(object);
        newNode.setNext(node.getNext());
        node.setNext(newNode);

        if (node == tail)
        {
            tail = newNode;
        }

        size++;
    }

    public void insertFirst(Object object)
    {
        Node newNode = new Node(object);

        if (isEmpty())
        {
            head = newNode;
            tail = newNode;
        }
        else
        {
            newNode.setNext(head);
            head = newNode;
        }

        size++;
    }

    public void insertLast(Object object)
    {
        Node newNode = new Node(object);

        if (isEmpty())
        {
            head = newNode;
            tail = newNode;
        }
        else
        {
            tail.setNext(newNode);
            tail = newNode;
        }

        size++;
    }

    //remove
    public void remove(Node node) throws EEmptyList, EInvalidNode
    {
        if (isEmpty())
        {
            throw new EEmptyList("Lista vazia");
        }

        if (node == head)
        {
            head = head.getNext();
            if (head == null)
            {
                tail = null;
            }
            size--;
            return;
        }

        Node beforeNode = before(node);
        beforeNode.setNext(node.getNext());

        if (node == tail)
        {
            tail = beforeNode;
        }

        size--;
    }
}
