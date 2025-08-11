import java.util.ArrayList;
import java.util.Iterator;
import Node.Node;
import Excesao.*;

public class BinaryT implements BTreeInterface {
    private Node root;
    private int size;

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
        // retorna a altura da arvore mas de maneira diferente da generica
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
        if (hasLeft(node)) {
            // precorre todos os nós a direita do nó de referencia
            maxHeight = Math.max(maxHeight, height(node.getLeftChild()));
        }
        if (hasRight(node)) {
            // percorre todos os nós a esquerda do nó de referencia
            maxHeight = Math.max(maxHeight, height(node.getRightChild()));
        }
        // depois de pegar todos os nós a esquerda e direita pega o que teve mais e adiciona mais 1(nó passado na função)
        return 1 + maxHeight;
    }

    public Iterator<Object> elements() {
        // retorna a lista de elementos dos nós de uma arvore
        ArrayList<Object> array = new ArrayList<>();
        if (!isEmpty()) {
            // enquanto não está vazio chama a
            inOrderElement(this.root, array);
        }

        return array.iterator();
    }

    public Iterator<Node> nodes() {
        // retorna uma coleção de nós de uma arvore
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
        // retorna o ula lista de filhos de um node
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

    // ----------------- Acesso arvore binária
    public Node leftChild(Node node) throws EEmptyTree, ENodeNotFound, ENoChild {
        // retorna o filho esquerdo de um node
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
        // retorna o filho direito de um node
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
        // verifica se tem algum filho, se tiver ele é interno
        return hasLeft(node) || hasRight(node);
    }

    public boolean isExternal(Node node) {
        // verifica se tem algum filho, se n tiver ele é externo
        return !hasLeft(node) && !hasRight(node);
    }

    public boolean isRoot(Node node) throws EEmptyTree {
        // verifica se o node é uma raiz
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

    // ----------------- Consulta arvore binária
    public boolean hasLeft(Node node) {
        // verifica se o node tem filho esquerdo
        return node.getLeftChild() != null;
    }

    public boolean hasRight(Node node) {
        // verifica se o node tem filho esquerdo
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

    public void addLeft(Node node, Object newObject) throws EEmptyTree, ENodeNotFound {
        if (isEmpty()) {
            throw new EEmptyTree("Árvore vazia");
        }
        if (node.getParent() == null && !isRoot(node)) {
            throw new ENodeNotFound("Nó não encontrado na árvore");
        }
        if (hasLeft(node)) {
            throw new EInvalidPosition("Já existe filho à esquerda");
        }

        Node newChild = new Node(newObject);
        newChild.setParent(node);
        node.setLeftChild(newChild);
        this.size++;
    }

    public void addRight(Node node, Object newObject) throws EEmptyTree, ENodeNotFound {
        if (isEmpty()) {
            throw new EEmptyTree("Árvore vazia");
        }
        if (node.getParent() == null && !isRoot(node)) {
            throw new ENodeNotFound("Nó não encontrado na árvore");
        }
        if (hasRight(node)) {
            throw new EInvalidPosition("Já existe filho à direita");
        }

        Node newChild = new Node(newObject);
        newChild.setParent(node);
        node.setRightChild(newChild);
        this.size++;
    }

    public Object remove(Node node) throws EEmptyTree, ENodeNotFound {
        if (isEmpty()) {
            throw new EEmptyTree("Árvore vazia");
        }
        if (node.getParent() == null && !isRoot(node)) {
            throw new ENodeNotFound("Nó não encontrado na árvore");
        }

        Object removedElement = node.getElement();

        // Caso 1: Nó folha (sem filhos)
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
        }
        // Caso 2: Nó com apenas um filho
        else if (hasLeft(node) && !hasRight(node)) {
            Node child = node.getLeftChild();
            replaceNode(node, child);
        } else if (!hasLeft(node) && hasRight(node)) {
            Node child = node.getRightChild();
            replaceNode(node, child);
        }
        // Caso 3: Nó com dois filhos
        else {
            // Encontra o sucessor in-order (menor nó da subárvore direita)
            Node successor = findMinNode(node.getRightChild());

            // Guarda o elemento do sucessor
            Object successorElement = successor.getElement();

            // Remove o sucessor da árvore (será um nó folha ou com no máximo um filho)
            remove(successor);

            // Substitui o elemento do nó a ser removido pelo elemento do sucessor
            node.setElement(successorElement);

            // Não decrementa size aqui porque o remove do sucessor já fez isso
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

    // auxiliares binarios
    // ----------------------Encontra o nó com o menor valor na subárvore
    private Node findMinNode(Node node) {
        Node current = node;
        // Continua indo para a esquerda até encontrar o nó mais à esquerda
        while (hasLeft(current)) {
            current = current.getLeftChild();
        }
        return current;
    }

    // ----------------------Substitui um nó por seu filho na árvore
    private void replaceNode(Node node, Node child) throws EEmptyTree {
        child.setParent(node.getParent());

        if (isRoot(node)) {
            // Se o nó a ser removido é a raiz
            this.root = child;
        } else {
            // Se é filho esquerdo ou direito do pai
            Node parent = node.getParent();
            if (node == parent.getLeftChild()) {
                parent.setLeftChild(child);
            } else {
                parent.setRightChild(child);
            }
        }

        // Limpa as referências do nó removido
        node.setLeftChild(null);
        node.setRightChild(null);
        node.setParent(null);

        this.size--;
    }

    private void inOrderElement(Node node, ArrayList<Object> array) throws EEmptyTree, ENodeNotFound {
        /*
        faz um percurso em ordem (in-order traversal) na árvore binária, começando pelo nó passado como parâmetro. Ele visita:
        - Primeiro o filho esquerdo (recursivamente),
        - Depois o próprio nó,
        - Depois o filho direito (recursivamente).

        Durante esse percurso, ele adiciona o elemento de cada nó visitado em um ArrayList<Object> array
        */
        if (node == null) {
            throw new ENodeNotFound("Nó não encontrado na arvore");
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
        /*
        faz um percurso em ordem (in-order traversal) na árvore binária, começando pelo nó passado como parâmetro. Ele visita:
        - Ele visita recursivamente o filho esquerdo,
        - Depois adiciona o próprio nó ao array,
        - Depois visita recursivamente o filho direito.

        No final, o array contém todos os nós da árvore na ordem in-order (esquerda, raiz, direita).
        */
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

    // Método para imprimir a árvore em formato visual
    public void printTree() {
        if (isEmpty()) {
            System.out.println("Árvore vazia!");
            return;
        }
        System.out.println("\nEstrutura da Árvore:");
        printNode(root, "", true);
        System.out.println();
    }

    // Método auxiliar para imprimir os nós com indentação
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
