import Node.Node;
import java.util.Iterator;

public class simpleTree implements treeInterface {
    private Node root;
    private int size;

    public simpleTree(Object root) {
        this.root = new Node(root);
        this.size = 1;
    }
}