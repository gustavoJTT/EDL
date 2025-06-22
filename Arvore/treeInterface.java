import Node.Node;

import Excesao.EEmptyTree;
import Excesao.EInvalidPosition;
import Excesao.ENodeNotFound;

import java.util.Iterator;

public interface TreeInterface {
    //genericos
    public boolean isEmpty();
    public int size();
    public int height(Node node) throws EEmptyTree, ENodeNotFound;
    public Iterator<Object> elements();
    public Iterator<Node> nodes();

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
