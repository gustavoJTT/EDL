import Node.Node;

import Excesao.ENodeNotFound;
import Excesao.EEmptyTree;
import Excesao.ENoChild;

public interface BTreeInterface extends GenericTInterface {
    public boolean hasLeft(Node node);
    public Node leftChild(Node node) throws EEmptyTree, ENodeNotFound, ENoChild;

    public boolean hasRight(Node node);
    public Node rightChild(Node node) throws EEmptyTree, ENodeNotFound, ENoChild;
}
