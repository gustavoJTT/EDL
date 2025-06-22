import Node.Node;

import Excesao.EEmptyTree;
import Excesao.ENoEmptyTree;
import Excesao.EInvalidPosition;
import Excesao.ENodeNotFound;
import Excesao.ENoChild;

import java.util.ArrayList;
import java.util.Iterator;

public class BinaryT implements BTreeInterface {
    private Node root;
    private int size;

    // vazia
    public BinaryT() {
        this.root = null;
        this.size = 0;
    }

    // com o passado
    public BinaryT(Object rootObject) {
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
        int leftHeight = hasLeft(node) ? height(node.getLeftChild()) : -1;
        int rightHeight = hasRight(node) ? height(node.getRightChild()) : -1;
        return 1 + Math.max(leftHeight, rightHeight);
    }

    public Iterator<Object> elements() {
        ArrayList<Object> array = new ArrayList<>();
        if (!isEmpty()) {
            inOrderElement(this.root, array);
        }

        return array.iterator();
    }

    public Iterator<Node> nodes() {
        ArrayList<Node> array = new ArrayList<>();
        if (!isEmpty()) {
            inOrderNode(this.root, array);
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

        ArrayList<Node> children = new ArrayList<>();
        if (hasLeft(node)) {
            children.add(leftChild(node));
        }
        if (hasRight(node)) {
            children.add(rightChild(node));
        }
        return children.iterator();
    }

    // ----------------- Acesso arvore binária
    public Node leftChild(Node node) throws EEmptyTree, ENodeNotFound, ENoChild {
        if (isEmpty()) {
            throw new EEmptyTree("Árvore vazia");
        }
        if (node.getParent() == null && !isRoot(node)) {
            throw new ENodeNotFound("Nó não encontrado na árvore");
        }
        if (!hasLeft(node)) {
            throw new ENoChild("Sem filho esquerdo");
        }

        return node.getLeftChild();
    }

    public Node rightChild(Node node) throws EEmptyTree, ENodeNotFound, ENoChild {
        if (isEmpty()) {
            throw new EEmptyTree("Árvore vazia");
        }
        if (node.getParent() == null && !isRoot(node)) {
            throw new ENodeNotFound("Nó não encontrado na árvore");
        }
        if (!hasRight(node)) {
            throw new ENoChild("Sem filho direito");
        }

        return node.getRightChild();
    }

    // Consulta
    public boolean isInternal(Node node) {
        return hasLeft(node) || hasRight(node);
    }

    public boolean isExternal(Node node) {
        return !hasLeft(node) && !hasRight(node);
    }

    public boolean isRoot(Node node) throws EEmptyTree {
        if (isEmpty()) {
            throw new EEmptyTree("Árvore vazia");
        }

        return node == root();
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

    // ----------------- Consulta arvore binária
    public boolean hasLeft(Node node) {
        return node.getLeftChild() != null;
    }

    public boolean hasRight(Node node) {
        return node.getRightChild() != null;
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
        // Não usado na binária
        return;
    }

    public void addLeft(Node node, Object element) throws EEmptyTree, ENodeNotFound {
        if (isEmpty()) {
            throw new EEmptyTree("Árvore vazia");
        }
        if (node.getParent() == null && !isRoot(node)) {
            throw new ENodeNotFound("Nó não encontrado na árvore");
        }
        if (hasLeft(node)) {
            throw new EInvalidPosition("Já existe filho à esquerda");
        }

        Node newNode = new Node(element);
        newNode.setParent(node);
        node.setLeftChild(newNode);
        this.size++;
    }

    public void addRight(Node node, Object element) throws EEmptyTree, ENodeNotFound {
        if (isEmpty()) {
            throw new EEmptyTree("Árvore vazia");
        }
        if (node.getParent() == null && !isRoot(node)) {
            throw new ENodeNotFound("Nó não encontrado na árvore");
        }
        if (hasRight(node)) {
            throw new EInvalidPosition("Já existe filho à direita");
        }

        Node newNode = new Node(element);
        newNode.setParent(node);
        node.setRightChild(newNode);
        this.size++;
    }

    public Object remove(Node node) throws EEmptyTree, EInvalidPosition, ENodeNotFound {
        //revisar
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
        if (parent == null) {
            throw new ENodeNotFound("Nó não encontrado na árvore");
        }

        if (node == parent.getLeftChild()) {
            parent.setLeftChild(null);
        } else if (node == parent.getRightChild()) {
            parent.setRightChild(null);
        } else {
            throw new ENodeNotFound("Nó não é filho do nó pai indicado");
        }

        int nodeCount = countNodes(node);
        this.size -= nodeCount;
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

    // auxiliares binarios
    private int countNodes(Node node) throws EEmptyTree {
        if (node == null)
            return 0;

        int count = 1;
        if (hasLeft(node)) {
            count += countNodes(node.getLeftChild());
        }
        if (hasRight(node)) {
            count += countNodes(node.getRightChild());
        }
        return count;
    }

    private void inOrderElement(Node node, ArrayList<Object> array) throws EEmptyTree, ENodeNotFound {
        if (node == null) {
            return;
        }

        if (hasLeft(node)) {
            inOrderElement(node.getLeftChild(), array);
        }

        array.add(node.getElement());

        if (hasRight(node)) {
            inOrderElement(node.getRightChild(), array);
        }
    }

    private void inOrderNode(Node node, ArrayList<Node> array) throws EEmptyTree, ENodeNotFound {
        if (node == null) {
            return;
        }

        if (hasLeft(node)) {
            inOrderNode(node.getLeftChild(), array);
        }

        array.add(node);

        if (hasRight(node)) {
            inOrderNode(node.getRightChild(), array);
        }
    }
}
