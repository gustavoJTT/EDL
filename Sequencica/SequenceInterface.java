package Sequencica;
import Excesao.EEmptySequence;
import Node.Node;

public interface SequenceInterface
{
    // metodos genericos
    public int size();
    public boolean isEmpty();

    // metodos de vetor
    public Object elemAtRank(int rank) throws EEmptySequence;
    public Object replaceAtRank(int rank, Object object) throws EEmptySequence;
    public void insertAtRank(int rank, Object object) throws EEmptySequence;
    public Object removeAtRank(int rank) throws EEmptySequence;

    // metodos de lista
    public Node first() throws EEmptySequence;
    public Node last() throws EEmptySequence;
    public Node before(Node node) throws EEmptySequence;
    public Node after(Node node) throws EEmptySequence;
    public void replaceElement(Node node, Object object) throws EEmptySequence;
    public void swapElements(Node node, Node q) throws EEmptySequence;
    public void insertBefore(Node node, Object object) throws EEmptySequence;
    public void insertAfter(Node node, Object object) throws EEmptySequence;
    public void insertFirst(Object object);
    public void insertLast(Object object);
    public void remove(Node node) throws EEmptySequence;

    // metodos ponte
    public Node atRank(int rank) throws EEmptySequence;
    public int rankOf(Node node) throws EEmptySequence;

    // extra
    public void print();
}