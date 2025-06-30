import Node.Node;
import Excesao.*;

public class BinaryST extends BinaryT {
    public BinaryST(Object object) {
        super(object);
    }

    // Busca um elemento na árvore binária de pesquisa
    public Node search(Object element, Node node) throws EEmptyTree, ENodeNotFound {
        if (isEmpty()) {
            throw new EEmptyTree("Árvore vazia");
        }

        int comparison = compare(node.getElement(), element);

        if (comparison < 0) {
            if (hasRight(node)) {
                return search(element, rightChild(node));
            }
        } else if (comparison == 0) {
            return node;
        } else {
            if (hasLeft(node)) {
                return search(element, leftChild(node));
            }
        }

        return node;
    }

    // Busca um elemento a partir da raiz
    public Node search(Object element) throws EEmptyTree {
        if (isEmpty()) {
            throw new EEmptyTree("Árvore vazia");
        }
        return search(element, root());
    }

    // Insere um novo elemento na árvore
    public Node insert(Object element) throws EInvalidPosition {
        if (isEmpty()) {
            return addRoot(element);
        }

        Node parentNode = search(element, root());
        if (compare(element, parentNode.getElement()) == 0) {
            throw new EInvalidPosition("Esse elemento já existe!");
        }

        if (compare(parentNode.getElement(), element) < 0) {
            addRight(parentNode, element);
            return parentNode.getRightChild();
        } else {
            addLeft(parentNode, element);
            return parentNode.getLeftChild();
        }
    }

    // Remove um nó da árvore
    public Object removeBST(Node node) throws EEmptyTree, ENodeNotFound, EInvalidPosition {
        if (isEmpty()) {
            throw new EEmptyTree("A árvore está vazia!");
        }

        Object element = node.getElement();

        // Caso 1: Nó com dois filhos
        if (hasLeft(node) && hasRight(node)) {
            Node successor = findMinPrivate(rightChild(node));
            replace(node, successor.getElement());
            removeBST(successor);
            return element;
        }
        // Caso 2 e 3: Usa o método remove da classe pai que já trata esses casos
        else {
            return remove(node);
        }
    }

    // Encontra o menor elemento (método privado auxiliar)
    private Node findMinPrivate(Node node) {
        if (isExternal(node)) {
            return node;
        } else {
            if (hasLeft(node)) {
                return findMinPrivate(leftChild(node));
            } else {
                return node;
            }
        }
    }

    // Método para imprimir a árvore em ordem (elementos ordenados)
    public void printInOrder() throws EEmptyTree {
        if (isEmpty()) {
            System.out.println("Árvore vazia!");
            return;
        }
        System.out.print("Elementos em ordem: ");
        inOrderPrint(root());
        System.out.println();
    }

    private void inOrderPrint(Node node) {
        if (node != null) {
            if (hasLeft(node)) {
                inOrderPrint(leftChild(node));
            }
            System.out.print(node.getElement() + " ");
            if (hasRight(node)) {
                inOrderPrint(rightChild(node));
            }
        }
    }

    // Encontra o maior elemento (mais à direita)
    public Node findMax() throws EEmptyTree {
        if (isEmpty()) {
            throw new EEmptyTree("Árvore vazia");
        }
        return findMax(root());
    }

    private Node findMax(Node node) {
        if (isExternal(node)) {
            return node;
        } else {
            if (hasRight(node)) {
                return findMax(rightChild(node));
            } else {
                return node;
            }
        }
    }

    // Encontra o menor elemento (mais à esquerda)
    public Node findMin() throws EEmptyTree {
        if (isEmpty()) {
            throw new EEmptyTree("Árvore vazia");
        }
        return findMinPrivate(root());
    }

    // Auxiliar para comparação
    private int compare(Object objectOne, Object objectTwo) {
        int one = convertInt(objectOne);
        int two = convertInt(objectTwo);

        return Integer.compare(one, two);
    }

    private int convertInt(Object object) {
        if (object instanceof Integer) {
            return (Integer) object;
        }
        if (object instanceof Float) {
            return Math.round((Float) object);
        }
        if (object instanceof String) {
            return Integer.parseInt((String) object);
        }
        if (object instanceof Boolean) {
            return ((Boolean) object) ? 1 : 0;
        }

        return 0;
    }
}
