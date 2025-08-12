import java.util.ArrayList;
import java.util.Iterator;
import Node.Node;
import Excesao.*;

/**
 * Implementação de uma Árvore Binária
 *
 * Uma árvore binária é uma estrutura hierárquica onde:
 * - Cada nó tem no máximo 2 filhos (esquerdo e direito)
 * - Existe uma raiz (primeiro nó)
 * - Permite navegação e operações em estrutura hierárquica
 *
 * Implementa as interfaces BTreeInterface e GenericTInterface
 */
public class BinaryT implements BTreeInterface {
    private Node root; // Nó raiz da árvore
    private int size; // Número total de nós na árvore

    /**
     * Construtor - cria árvore binária com elemento raiz
     * 
     * @param rootObject elemento que será a raiz
     */
    public BinaryT(Object rootObject) {
        this.root = new Node(rootObject);
        this.size = 1;
    }

    // ========== MÉTODOS DA INTERFACE GenericTInterface ==========

    /**
     * Verifica se a árvore está vazia
     * 
     * @return boolean true se vazia, false caso contrário
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Retorna o número total de nós na árvore
     * 
     * @return int tamanho da árvore
     */
    public int size() {
        return this.size;
    }

    /**
     * Calcula a altura de um nó (distância até a folha mais distante)
     * 
     * @param node nó para calcular altura
     * @return int altura do nó (0 para folhas)
     * @throws EEmptyTree    se árvore vazia
     * @throws ENodeNotFound se nó não pertence à árvore
     */
    public int height(Node node) throws EEmptyTree, ENodeNotFound {
        if (isEmpty()) {
            throw new EEmptyTree("Árvore vazia");
        }
        if (node.getParent() == null && !isRoot(node)) {
            throw new ENodeNotFound("Nó não encontrado na árvore");
        }

        // Caso base: folha tem altura 0
        if (isExternal(node)) {
            return 0;
        }

        // Calcula altura recursivamente: 1 + máxima altura dos filhos
        int maxHeight = 0;
        if (hasLeft(node)) {
            maxHeight = Math.max(maxHeight, height(node.getLeftChild()));
        }
        if (hasRight(node)) {
            maxHeight = Math.max(maxHeight, height(node.getRightChild()));
        }
        return 1 + maxHeight;
    }

    /**
     * Retorna iterator com todos os elementos da árvore em ordem
     * 
     * @return Iterator<Object> iterador dos elementos
     * @throws EEmptyTree se árvore vazia
     */
    public Iterator<Object> elements() throws EEmptyTree {
        ArrayList<Object> array = new ArrayList<>();
        if (!isEmpty()) {
            inOrderElement(this.root, array); // Percurso em ordem
        }
        return array.iterator();
    }

    /**
     * Retorna iterator com todos os nós da árvore em ordem
     * 
     * @return Iterator<Node> iterador dos nós
     * @throws EEmptyTree se árvore vazia
     */
    public Iterator<Node> nodes() throws EEmptyTree {
        ArrayList<Node> array = new ArrayList<>();
        if (!isEmpty()) {
            inOrderNode(this.root, array); // Percurso em ordem dos nós
        }
        return array.iterator();
    }

    /**
     * Retorna o nó raiz da árvore
     * 
     * @return Node a raiz
     * @throws EEmptyTree se árvore vazia
     */
    public Node root() throws EEmptyTree {
        if (isEmpty()) {
            throw new EEmptyTree("Árvore vazia");
        }
        return this.root;
    }

    /**
     * Retorna o nó pai de um dado nó
     * 
     * @param node nó filho
     * @return Node o nó pai
     * @throws ENodeNotFound    se nó não pertence à árvore
     * @throws EInvalidPosition se o nó for a raiz (raiz não tem pai)
     */
    public Node parent(Node node) throws ENodeNotFound, EInvalidPosition {
        if (node.getParent() == null && !isRoot(node)) {
            throw new ENodeNotFound("Nó não encontrado na árvore");
        }
        if (isRoot(node)) {
            throw new EInvalidPosition("Raiz não tem nó pai");
        }
        return node.getParent();
    }

    /**
     * Retorna iterator dos filhos de um nó
     * 
     * @param node nó pai
     * @return Iterator<Node> iterador dos filhos
     * @throws EEmptyTree       se árvore vazia
     * @throws EInvalidPosition se nó é folha (sem filhos)
     */
    public Iterator<Node> children(Node node) throws EEmptyTree, EInvalidPosition {
        if (isEmpty()) {
            throw new EEmptyTree("Árvore vazia");
        }
        if (isExternal(node)) {
            throw new EInvalidPosition("Nó folha não tem filhos");
        }

        ArrayList<Node> children = new ArrayList<>();
        if (hasLeft(node)) {
            children.add(node.getLeftChild());
        }
        if (hasRight(node)) {
            children.add(node.getRightChild());
        }
        return children.iterator();
    }

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
        return node == this.root;
    }

    /**
     * Calcula a profundidade de um nó (distância até a raiz)
     * 
     * @param node nó para calcular profundidade
     * @return int profundidade (0 para raiz)
     * @throws EEmptyTree    se árvore vazia
     * @throws ENodeNotFound se nó não pertence à árvore
     */
    public int depth(Node node) throws EEmptyTree, ENodeNotFound {
        if (isEmpty()) {
            throw new EEmptyTree("Árvore vazia");
        }
        if (node.getParent() == null && !isRoot(node)) {
            throw new ENodeNotFound("Nó não encontrado na árvore");
        }

        // Caso base: raiz tem profundidade 0
        if (isRoot(node)) {
            return 0;
        }
        // Recursão: 1 + profundidade do pai
        return 1 + depth(node.getParent());
    }

    /**
     * Substitui o elemento de um nó por um novo elemento
     * 
     * @param node      nó a ser alterado
     * @param newObject novo elemento
     * @return Object elemento antigo que foi substituído
     * @throws EEmptyTree    se árvore vazia
     * @throws ENodeNotFound se nó não pertence à árvore
     */
    public Object replace(Node node, Object newObject) throws EEmptyTree, ENodeNotFound {
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

    /**
     * Adiciona um novo filho a um nó (respeitando regras de árvore binária)
     * 
     * @param node      nó pai que receberá o novo filho
     * @param newObject elemento do novo nó filho
     * @throws EEmptyTree    se árvore vazia
     * @throws ENodeNotFound se nó pai não pertence à árvore
     */
    public void addChild(Node node, Object newObject) throws EEmptyTree, ENodeNotFound {
        // Não implementado para árvore binária
    }

    public Object remove(Node node) throws EEmptyTree, EInvalidPosition, ENodeNotFound {
        if (isEmpty()) {
            throw new EEmptyTree("Árvore vazia");
        }
        if (node.getParent() == null && !isRoot(node)) {
            throw new ENodeNotFound("Nó não encontrado na árvore");
        }

        Object removedElement = node.getElement();

        if (isExternal(node)) {
            if (isRoot(node)) {
                this.root = null;
                this.size = 0;
            } else {
                Node parent = node.getParent();
                if (node == parent.getLeftChild()) {
                    parent.setLeftChild(null);
                } else {
                    parent.setRightChild(null);
                }
                this.size--;
            }
        } else if (hasLeft(node) && !hasRight(node)) {
            Node child = node.getLeftChild();
            replaceNode(node, child);
        } else if (!hasLeft(node) && hasRight(node)) {
            Node child = node.getRightChild();
            replaceNode(node, child);
        } else {
            Node successor = findMinNode(node.getRightChild());
            Object successorElement = successor.getElement();
            remove(successor);
            node.setElement(successorElement);
            return removedElement;
        }

        return removedElement;
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

    // ========== MÉTODOS DA INTERFACE BTreeInterface ==========

    public boolean hasLeft(Node node) {
        return node.getLeftChild() != null;
    }

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

    public boolean hasRight(Node node) {
        return node.getRightChild() != null;
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

    // ========== MÉTODOS AUXILIARES NECESSÁRIOS ==========

    private Node findMinNode(Node node) {
        Node current = node;
        while (hasLeft(current)) {
            current = current.getLeftChild();
        }
        return current;
    }

    private void replaceNode(Node node, Node child) throws EEmptyTree {
        child.setParent(node.getParent());

        if (isRoot(node)) {
            this.root = child;
        } else {
            Node parent = node.getParent();
            if (node == parent.getLeftChild()) {
                parent.setLeftChild(child);
            } else {
                parent.setRightChild(child);
            }
        }

        node.setLeftChild(null);
        node.setRightChild(null);
        node.setParent(null);
        this.size--;
    }

    private void inOrderElement(Node node, ArrayList<Object> array) {
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

    private void inOrderNode(Node node, ArrayList<Node> array) {
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

    // ========== MÉTODO PRINT ==========

    public void printTree() {
        if (isEmpty()) {
            System.out.println("Árvore vazia!");
            return;
        }
        System.out.println("\nEstrutura da Árvore Binária:");
        printNode(root, "", true);
        System.out.println();
    }

    private void printNode(Node node, String prefix, boolean isLast) {
        if (node == null)
            return;

        System.out.println(prefix + (isLast ? "└── " : "├── ") + node.getElement());

        if (hasLeft(node) || hasRight(node)) {
            if (hasLeft(node)) {
                printNode(node.getLeftChild(), prefix + (isLast ? "    " : "│   "), !hasRight(node));
            } else {
                System.out.println(prefix + (isLast ? "    " : "│   ") + "├── (vazio)");
            }

            if (hasRight(node)) {
                printNode(node.getRightChild(), prefix + (isLast ? "    " : "│   "), true);
            } else {
                System.out.println(prefix + (isLast ? "    " : "│   ") + "└── (vazio)");
            }
        }
    }
}
