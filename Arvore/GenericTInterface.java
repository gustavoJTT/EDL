import java.util.Iterator;
import Node.Node;
import Excesao.*;

public interface GenericTInterface {
    //genericos
    public boolean isEmpty();
    public int size();
    public int height(Node node) throws EEmptyTree, ENodeNotFound;
    public Iterator<Object> elements() throws EEmptyTree;
    public Iterator<Node> nodes() throws EEmptyTree;

    //acesso
    public Node root() throws EEmptyTree;
    public Node parent(Node node) throws ENodeNotFound, EInvalidPosition;
    public Iterator<Node> children(Node node) throws EEmptyTree, EInvalidPosition;

    //consulta
    public boolean isInternal(Node node);
    public boolean isExternal(Node node);
    public boolean isRoot(Node node) throws EEmptyTree;
    public int depth(Node node) throws EEmptyTree, ENodeNotFound;

    //atualizacao
    public Object replace(Node node, Object newObject) throws EEmptyTree, ENodeNotFound;

    //adicionais
    public void addChild(Node node, Object newObject) throws EEmptyTree, ENodeNotFound;
    public Object remove(Node node) throws EEmptyTree, EInvalidPosition, ENodeNotFound;
    public void swapElements(Node nodeOne, Node nodeTwo) throws EEmptyTree, ENodeNotFound;
}
