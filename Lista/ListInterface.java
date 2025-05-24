package Lista;
import Node.Node;

public interface ListInterface
{
    public int size();
    public boolean isEmpty();
    public boolean isFirst(Node node) throws EEmptyList;
    public boolean isLast(Node node) throws EEmptyList;
    public Node first() throws EEmptyList;
    public Node last() throws EEmptyList;
    public Node before(Node position) throws EEmptyList, EInvalidNode;
    public Node after(Node position) throws EEmptyList, EInvalidNode;
    public void replaceElement(Node node, Object object) throws EEmptyList, EInvalidNode;
    public void swapElement(Node firstNode, Node secondNode) throws EEmptyList, EInvalidNode;
    public void insertBefore(Node node, Object object) throws EEmptyList, EInvalidNode;
    public void insertAfter(Node node, Object object) throws EEmptyList, EInvalidNode;
    public void insertFirst(Object object);
    public void insertLast(Object object);
    public void remove(Node node) throws EEmptyList, EInvalidNode;
}