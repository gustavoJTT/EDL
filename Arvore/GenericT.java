import java.util.ArrayList;
import java.util.Iterator;
import Node.Node;
import Excesao.*;

public class GenericT implements TreeInterface {
    private Node root;
    private int size;

    public GenericT(Object rootObject) {
        this.root = new Node(rootObject);
        this.size = 1;
    }

    // Genericos
    public int size() {
        return this.size;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int height(Node node) throws EEmptyTree, ENodeNotFound {
        if (isEmpty()) {
            throw new EEmptyTree("Árvore vazia");
        }
        if (node.getParent() == null && !isRoot(node)) {
            throw new ENodeNotFound("Nó não encontrado na árvore");
        }

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

    public Node parent(Node node) throws ENodeNotFound, EInvalidPosition {
        if (node.getParent() == null && !isRoot(node)) {
            throw new ENodeNotFound("Nó não encontrado na árvore");
        }
        if (isRoot(node)) {
            throw new EInvalidPosition("Raiz não tem nó pai");
        }

        return node.getParent();
    }

    public Iterator<Node> children(Node node) throws EEmptyTree, EInvalidPosition {
        if (isEmpty()) {
            throw new EEmptyTree("Árvore vazia");
        }
        if (isExternal(node)) {
            throw new EInvalidPosition("Nó folha não tem filhos");
        }

        return node.getChild().iterator();
    }

    // Consulta
    public boolean isInternal(Node node) throws ENodeNotFound {
        if (node.getParent() == null && !isRoot(node)) {
            throw new ENodeNotFound("Nó não encontrado na árvore");
        }

        return node.getChild().size() > 0;
    }

    public boolean isExternal(Node node) throws ENodeNotFound {
        if (node.getParent() == null && !isRoot(node)) {
            throw new ENodeNotFound("Nó não encontrado na árvore");
        }

        return node.getChild().size() == 0;
    }

    public boolean isRoot(Node node) throws EEmptyTree {
        if (isEmpty()) {
            throw new EEmptyTree("Árvore vazia");
        }

        return node == this.root;
    }

    public int depth(Node node) throws EEmptyTree, ENodeNotFound {
        if (isEmpty()) {
            throw new EEmptyTree("Árvore vazia");
        }
        if (node.getParent() == null && !isRoot(node)) {
            throw new ENodeNotFound("Nó não encontrado na árvore");
        }

        if (isRoot(node)) {
            return 0;
        }
        return 1 + depth(node.getParent());
    }

    // Atualizacao
    public Object replace(Node node, Object newObject) throws ENodeNotFound {
        if (isEmpty()) {
            throw new EEmptyTree("Árvore vazia");
        }
        if (node.getParent() == null && !isRoot(node)) {
            throw new ENodeNotFound("Nó não encontrado na árvore");
        }

        Object oldElement = node.getElement();
        node.setElement(newObject);
        return oldElement;
    }

    public Node addRoot(Object object) throws ENoEmptyTree {
        if (!isEmpty()) {
            throw new ENoEmptyTree("A árvore já tem raiz");
        }

        this.size = 1;
        this.root = new Node(object);
        return this.root;
    }

    // Adicionais
    public void addChild(Node node, Object newObject) throws EEmptyTree, ENodeNotFound {
        if (isEmpty()) {
            throw new EEmptyTree("Árvore vazia");
        }
        if (node.getParent() == null && !isRoot(node)) {
            throw new ENodeNotFound("Nó não encontrado na árvore");
        }

        Node newChild = new Node(newObject);
        node.addChild(newChild);
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

        Node parent = node.getParent();
        if (isExternal(node)) {
            parent.removeChild(node);
        } else {
            throw new EInvalidPosition("Um nó pai não pode ser removido");
        }
        this.size -= 1;
        return node.getElement();
    }

    public void swapElements(Node nodeOne, Node nodeTwo) throws EEmptyTree, ENodeNotFound {
        if (isEmpty()) {
            throw new EEmptyTree("Árvore vazia");
        }
        if (nodeOne.getParent() == null && !isRoot(nodeOne) || nodeTwo.getParent() == null && !isRoot(nodeTwo)) {
            throw new ENodeNotFound("Um dos nós não foi encontrado na árvore");
        }

        Object temp = nodeOne.getElement();
        nodeOne.setElement(nodeTwo.getElement());
        nodeTwo.setElement(temp);
    }

    // auxiliares
    private void preOrder(Node node, ArrayList<Node> array) throws EEmptyTree {
        array.add(node);
        for (Node child : node.getChild()) {
            preOrder(child, array);
        }
    }

    private void postOrder(Node node, ArrayList<Object> array) throws EEmptyTree {
        for (Node child : node.getChild()) {
            postOrder(child, array);
        }
        array.add(node.getElement());
    }

    // Método para imprimir a árvore em formato visual
    public void printTree() {
        if (isEmpty()) {
            System.out.println("Árvore vazia!");
            return;
        }
        System.out.println("\nEstrutura da Árvore:");
        printNode(root, "", true);
        System.out.println();
    } // Método auxiliar para imprimir os nós com indentação

    private void printNode(Node node, String prefix, boolean isLast) {
        System.out.println(prefix + (isLast ? "└── " : "├── ") + node.getElement());

        java.util.List<Node> children = node.getChild();
        for (int i = 0; i < children.size(); i++) {
            printNode(children.get(i), prefix + (isLast ? "    " : "│   "), i == children.size() - 1);
        }
    }
}
