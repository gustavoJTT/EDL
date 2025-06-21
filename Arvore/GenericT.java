import Node.Node;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import Excesao.EEmptyTree;
import Excesao.EInvalidPosition;
import Excesao.ENodeNotFound;

public class GenericT implements TreeInterface {
    private Node root;
    private int size;

    // vazia
    public GenericT() {
        this.root = null;
        this.size = 0;
    }

    // com o passado
    public GenericT(Object rootObject) {
        this.root = new Node(rootObject);
        this.size = 1;
    }

    // Genericos
    public boolean isEmpty() {
        return this.size == 0;
    }

    public int size() {
        return this.size;
    }

    public int depth(Node node) throws EEmptyTree {
        if (isRoot(node)) {
            return 0;
        }

        return 1 + depth(node.getParent());
    }

    public int height(Node node) throws EEmptyTree {
        if (isExternal(node)) {
            return 0;
        }

        int maxHeight = 0;
        for (Node child : node.getChild()) {
            maxHeight = Math.max(maxHeight, height(child));
        }
        return maxHeight + 1;
    }

    public Iterator<Object> elements() {
        ArrayList<Object> array = new ArrayList<>();
        if (!isEmpty()) {
            postOrder(this.root, array);
        }
        return array.iterator();
    }

    public Iterator<Node> nodes() {
        ArrayList<Node> array = new ArrayList<>();
        if (!isEmpty()) {
            preOrder(this.root, array);
        }
        return array.iterator();
    }

    // Acesso
    public Node root() throws EEmptyTree {
        if (isEmpty()) {
            throw new EEmptyTree("Árvore vazia");
        }
        return this.root;
    }

    public Node parent(Node node) {
        if (isRoot(node)) {
            return null;
        }

        return node.getParent();
    }

    public Iterator<Node> children(Node node) {
        return node.getChild().iterator();
    }

    // Consulta
    public boolean isInternal(Node node) {
        return node.getChild().size() > 0;
    }

    public boolean isExternal(Node node) {
        return node.getChild().size() == 0;
    }

    public boolean isRoot(Node node) {
        return node == this.root;
    }

    // Atualizacao
    public Object replce(Node node, Object newObject) throws ENodeNotFound {
        if (findNode(this.root, node) == null) {
            throw new ENodeNotFound("Nó não encontrado na árvore");
        }

        Object oldElement = node.getElement();
        node.setElement(newObject);
        return oldElement;
    }

    // Adicionais
    public void addChild(Node node, Object newObject) throws ENodeNotFound {
        if (findNode(this.root, node) == null) {
            throw new ENodeNotFound("Nó não encontrado na árvore");
        }

        Node newNode = new Node(newObject);
        node.addChild(newNode);
        this.size++;
    }

    public Object remove(Node node) throws EEmptyTree, EInvalidPosition, ENodeNotFound {
        if (isEmpty()) {
            throw new EEmptyTree("Árvore vazia");
        }

        if (isRoot(node)) {
            if (this.size == 1) {
                Object element = this.root.getElement();
                this.root = null;
                this.size = 0;
                return element;
            } else {
                throw new EInvalidPosition("Não é possível remover a raiz com filhos");
            }
        }

        // Get parent and remove the node
        Node parent = node.getParent();
        if (parent == null)
            throw new ENodeNotFound("Nó não encontrado na árvore");

        // Remove node from parent's children
        parent.removeChild(node);

        // Decrease this.size by the number of nodes in the subtree
        this.size -= countNodes(node);

        return node.getElement();
    }

    public void swapElements(Node nodeOne, Node nodeTwo) throws ENodeNotFound {
        if (findNode(this.root, nodeOne) == null || findNode(this.root, nodeTwo) == null) {
            throw new ENodeNotFound("Um dos nós não foi encontrado na árvore");
        }

        Object temp = nodeOne.getElement();
        nodeOne.setElement(nodeTwo.getElement());
        nodeTwo.setElement(temp);
    }

    // auxiliares
    private int countNodes(Node node) {
        if (node == null)
            return 0;

        int count = 1; // Count this node
        for (Node child : node.getChild()) {
            count += countNodes(child);
        }

        return count;
    }

    private void preOrder(Node node, ArrayList<Node> array) {
        array.add(node);

        for (Node child : node.getChild()) {
            preOrder(child, array);
        }
    }

    private void postOrder(Node node, ArrayList<Object> array) {
        for (Node child : node.getChild()) {
            postOrder(child, array);
        }

        array.add(node.getElement());
    }

    private Node findNode(Node startNode, Node target) {
        if (startNode == null) {
            return null;
        }
        if (startNode == target) {
            return startNode;
        }

        for (Node child : startNode.getChild()) {
            Node result = findNode(child, target);
            if (result != null) {
                return result;
            }
        }

        return null;
    }
}
