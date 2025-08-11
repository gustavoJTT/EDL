import java.util.ArrayList;
import java.util.Iterator;
import Node.Node;
import Excesao.*;

public class GenericT implements GenericTInterface {
    private Node root;
    private int size;

    public GenericT(Object rootObject) {
        this.root = new Node(rootObject);
        this.size = 1;
    }

    // Genericos
    public int size() {
        // retorna o tamanho normal
        return this.size;
    }

    public boolean isEmpty() {
        // usa o tamanho pra ver se tem raiz ou não
        return size() == 0;
    }

    public int height(Node node) throws EEmptyTree, ENodeNotFound {
        // Mede a distância do nó até a folha mais distante abaixo dele (quantos níveis para baixo).
        if (isEmpty()) {
            throw new EEmptyTree("Árvore vazia");
        }
        if (node.getParent() == null && !isRoot(node)) {
            throw new ENodeNotFound("Nó não encontrado na árvore");
        }

        // se o nó for externo(uma folha) ele n tem altura pq ele já está no final
        if (isExternal(node)) {
            return 0;
        }
        // se ele n for externo percorremos os filhos e calculamos sua altura
        int maxHeight = 0;
        for (Node child : node.getChild()) { //igual o for in de python
            maxHeight = Math.max(maxHeight, height(child)); // recursividade vai até node.getChild não achar mais filhos
        }
        return maxHeight + 1;
    }

    public Iterator<Object> elements() throws EEmptyTree {
        // retorna a quantidade de elementos de uma arvore
        if (isEmpty()) {
            throw new EEmptyTree("Árvore vazia");
        }

        ArrayList<Object> array = new ArrayList<>();
        preOrder(this.root, array); // pode usar pre ou postOrder
        return array.iterator();
    }

    public Iterator<Node> nodes() throws EEmptyTree {
        // retorna a quantidade de nós
        if (isEmpty()) {
            throw new EEmptyTree("Árvore vazia");
        }

        ArrayList<Node> array = new ArrayList<>();
        postOrder(this.root, array); // pode usar pre ou postOrder
        return array.iterator();
    }

    // Acesso
    public Node root() throws EEmptyTree {
        // retorna a raiz
        if (isEmpty()) {
            throw new EEmptyTree("Árvore vazia");
        }

        return this.root;
    }

    public Node parent(Node node) throws ENodeNotFound, EInvalidPosition {
        // retorna o parente(pai) de um node
        if (node.getParent() == null && !isRoot(node)) {
            throw new ENodeNotFound("Nó não encontrado na árvore");
        }
        // se for raiz não tem pai
        if (isRoot(node)) {
            throw new EInvalidPosition("Raiz não tem nó pai");
        }

        return node.getParent();
    }

    public Iterator<Node> children(Node node) throws EEmptyTree, EInvalidPosition {
        // retorna os filhos de um node, esse é um iterator pq n tem restrição da quantidade de filhos
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
        // verifica se o nó é interno, se ele tem filhos
        if (node.getParent() == null && !isRoot(node)) {
            throw new ENodeNotFound("Nó não encontrado na árvore");
        }

        return node.getChild().size() > 0;
    }

    public boolean isExternal(Node node) throws ENodeNotFound {
        // verifica se o nó é externo(folha), se ele não tem filhos
        if (node.getParent() == null && !isRoot(node)) {
            throw new ENodeNotFound("Nó não encontrado na árvore");
        }

        return node.getChild().size() == 0;
    }

    public boolean isRoot(Node node) throws EEmptyTree {
        // verifica se é raiz
        if (isEmpty()) {
            throw new EEmptyTree("Árvore vazia");
        }

        return node == this.root;
    }

    public int depth(Node node) throws EEmptyTree, ENodeNotFound {
        // Mede a distância do nó até a raiz (quantos níveis para cima)
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
        // troca o valor de um node
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

    // Adicionais
    public void addChild(Node node, Object newObject) throws EEmptyTree, ENodeNotFound {
        // adiciona mais um filho a um node, não importa esquerda ou direita pq é uma arvore genérica
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
        // nessa implementação remove apenas nós folhas e a raiz sem filhos
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
        // troca a posição de dois nós
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
    private void preOrder(Node node, ArrayList<Object> array) throws EEmptyTree {
        /*
        - percorre os nós da arvore
        - o node é visitado ANTES de seus descendentes
        -Útil para copiar/clonar árvores, criar estruturas hierárquicas, ou quando se precisa processar um nó antes de seus descendentes

        - Retorna uma lista de Node (só os primeiros)
        */

        Object element = node.getElement();
        array.add(element);
        for (Node child : node.getChild()) {
            preOrder(child, array);
        }

    }

    private void postOrder(Node node, ArrayList<Node> array) throws EEmptyTree {
        /*
         - percorre os nós da arvore
         - o node é visitado DEPOIS de seus descendentes
         - Útil para calcular tamanhos, liberar memória, deletar nós, ou quando se precisa processar os filhos antes do pai

         - Retorna uma lista de Object (elementos dos Node)
        */

        for (Node child : node.getChild()) {
            postOrder(child, array);
        }
        array.add(node);
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
