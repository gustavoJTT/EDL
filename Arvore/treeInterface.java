import Node.Node;
import java.util.Iterator;

public interface treeInterface {
    //genericos
    public boolean isEmpty();
    public int size();
    public int height(Node node);
    //public int heightSlow();
    public Iterator<Object> elements();
    public Iterator<Node> nodes();

    //acesso
    public Node root();
    public Node parent(Node node);
    public Iterator<Node> children(Node node);

    //consulta
    public boolean isInternal(Node node);
    public boolean isExternal(Node node);
    public boolean isRoot(Node node);
    public int depth(Node node);

    //atualizacao
    public Object replce(Node node, Object newObject);

    //adicionais
    public void addChild(Node node, Object newObject);
    public Object remove(Node node);
    public void swapElements(Node nodeOne, Node nodeTwo);
}