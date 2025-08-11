import Node.Node;
import Excesao.*;

public class BinaryST {
    private Node root;
    private int size;

    public BinaryST() {
        this.size = 0;
        this.root = null;
    }

    // Busca recursiva a partir de um nó
    private Node treeSearch(Node n, int o) {
        if (n == null)
            return null;
        int chave = key(n);
        if (o == chave) {
            return n;
        } else if (o < chave) {
            if (n.getLeftChild() != null) {
                return treeSearch(n.getLeftChild(), o);
            }
        } else {
            if (n.getRightChild() != null) {
                return treeSearch(n.getRightChild(), o);
            }
        }
        return n;
    }

    // Insere um novo elemento na árvore
    public Node insert(int o) {
        if (isEmpty()) {
            this.size = 1;
            this.root = new Node(String.valueOf(o));
            return this.root;
        }
        Node position = this.treeSearch(this.root, o);
        int chave = key(position);
        if (chave == o) {
            throw new EInvalidPosition("Esse elemento já foi inserido");
        }
        Node novo = new Node(String.valueOf(o));
        novo.setParent(position);
        if (o < chave) {
            position.setLeftChild(novo);
        } else {
            position.setRightChild(novo);
        }
        this.size++;
        return novo;
    }

    // Remove um elemento da árvore
    public Object remove(int o) {
        if (isEmpty()) {
            throw new EInvalidPosition("A árvore está vazia");
        }
        Node position = treeSearch(root, o);
        if (position == null || key(position) != o) {
            throw new EInvalidPosition("Elemento não achado");
        }
        Node left = position.getLeftChild();
        Node right = position.getRightChild();
        if (left != null && right != null) {
            Node successor = right;
            while (successor.getLeftChild() != null) {
                successor = successor.getLeftChild();
            }
            Object elem = remove(key(successor));
            position.setElement(successor.getElement());
            return elem;
        }
        Node child;
        if (left != null) {
            child = left;
        } else if (right != null) {
            child = right;
        } else {
            child = null;
        }
        if (position == this.root) {
            this.root = child;
            if (child != null) {
                child.setParent(null);
            }
        } else {
            Node parent = position.getParent();
            if (position == parent.getLeftChild()) {
                parent.setLeftChild(child);
            } else {
                parent.setRightChild(child);
            }
            if (child != null) {
                child.setParent(parent);
            }
        }
        size--;
        return o;
    }

    // Busca um elemento a partir da raiz
    public Node search(int o) {
        if (isEmpty()) {
            throw new EInvalidPosition("Árvore vazia");
        }
        Node n = treeSearch(root, o);
        if (n == null || key(n) != o) {
            throw new EInvalidPosition("Elemento não achado");
        }
        return n;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public Node getRoot() {
        return root;
    }

    public int getSize() {
        return size;
    }

    // Retorna a chave (int) de um nó
    private int key(Node n) {
        if (n == null)
            throw new EInvalidPosition("Nó inválido");
        return Integer.parseInt((String) n.getElement());
    }
}
