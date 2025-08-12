import java.util.ArrayList;
import java.util.Iterator;
import Node.Node;
import Excesao.*;

/**
 * Implementação de uma Árvore Genérica (N-ária)
 *
 * Uma árvore genérica permite que cada nó tenha qualquer número de filhos
 * - Estrutura mais flexível que árvore binária
 * - Cada nó pode ter 0, 1, 2, 3... N filhos
 * - Útil para representar hierarquias complexas (sistemas de arquivos,
 * organizações, etc.)
 *
 * Implementa a interface GenericTInterface
 */
public class GenericT implements GenericTInterface {
    private Node root; // Nó raiz da árvore
    private int size; // Número total de nós na árvore

    /**
     * Construtor - cria árvore genérica com elemento raiz
     * 
     * @param rootObject elemento que será a raiz
     */
    public GenericT(Object rootObject) {
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
     * Em árvores genéricas, verifica TODOS os filhos para encontrar o maior caminho
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

        // Verifica TODOS os filhos (não só esquerdo/direito como em binária)
        int maxHeight = 0;
        for (Node child : node.getChild()) {
            maxHeight = Math.max(maxHeight, height(child));
        }
        return maxHeight + 1;
    }

    /**
     * Retorna iterator com todos os elementos da árvore
     * Utiliza percurso pré-ordem (visita raiz antes dos filhos)
     * 
     * @return Iterator<Object> iterador dos elementos
     * @throws EEmptyTree se árvore vazia
     */
    public Iterator<Object> elements() throws EEmptyTree {
        if (isEmpty()) {
            throw new EEmptyTree("Árvore vazia");
        }

        ArrayList<Object> array = new ArrayList<>();
        preOrder(this.root, array); // Percurso pré-ordem
        return array.iterator();
    }

    /**
     * Retorna iterator com todos os nós da árvore
     * Utiliza percurso pós-ordem (visita filhos antes da raiz)
     * 
     * @return Iterator<Node> iterador dos nós
     * @throws EEmptyTree se árvore vazia
     */
    public Iterator<Node> nodes() throws EEmptyTree {
        if (isEmpty()) {
            throw new EEmptyTree("Árvore vazia");
        }

        ArrayList<Node> array = new ArrayList<>();
        postOrder(this.root, array); // Percurso pós-ordem
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

    public boolean isInternal(Node node) {
        if (node.getParent() == null && !isRoot(node)) {
            throw new ENodeNotFound("Nó não encontrado na árvore");
        }
        return node.getChild().size() > 0;
    }

    public boolean isExternal(Node node) {
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
     * Adiciona um novo filho a um nó existente
     * 
     * @param node      nó pai que receberá o novo filho
     * @param newObject elemento do novo nó filho
     * @throws EEmptyTree    se árvore vazia
     * @throws ENodeNotFound se nó pai não pertence à árvore
     */
    public void addChild(Node node, Object newObject) throws EEmptyTree, ENodeNotFound {
        if (isEmpty()) {
            throw new EEmptyTree("Árvore vazia");
        }
        if (node.getParent() == null && !isRoot(node)) {
            throw new ENodeNotFound("Nó não encontrado na árvore");
        }

        Node newChild = new Node(newObject);
        node.addChild(newChild); // Adiciona à lista de filhos
        this.size++; // Incrementa tamanho da árvore
    }

    /**
     * Remove um nó da árvore (apenas nós folha ou raiz sem filhos)
     * 
     * @param node nó a ser removido
     * @return Object elemento do nó removido
     * @throws EEmptyTree       se árvore vazia
     * @throws EInvalidPosition se tentar remover nó com filhos ou raiz com filhos
     * @throws ENodeNotFound    se nó não pertence à árvore
     */
    public Object remove(Node node) throws EEmptyTree, EInvalidPosition, ENodeNotFound {
        if (isEmpty()) {
            throw new EEmptyTree("Árvore vazia");
        }
        if (isRoot(node)) {
            if (this.size == 1) {
                // Pode remover raiz se for o único nó
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
            // Só remove se for folha (nó externo)
            parent.removeChild(node);
        } else {
            throw new EInvalidPosition("Um nó pai não pode ser removido");
        }
        this.size -= 1;
        return node.getElement();
    }

    /**
     * Troca os elementos de dois nós
     * 
     * @param nodeOne primeiro nó
     * @param nodeTwo segundo nó
     * @throws EEmptyTree    se árvore vazia
     * @throws ENodeNotFound se algum nó não pertence à árvore
     */
    public void swapElements(Node nodeOne, Node nodeTwo) throws EEmptyTree, ENodeNotFound {
        if (isEmpty()) {
            throw new EEmptyTree("Árvore vazia");
        }
        if (nodeOne.getParent() == null && !isRoot(nodeOne) || nodeTwo.getParent() == null && !isRoot(nodeTwo)) {
            throw new ENodeNotFound("Um dos nós não foi encontrado na árvore");
        }

        // Troca simples usando variável temporária
        Object temp = nodeOne.getElement();
        nodeOne.setElement(nodeTwo.getElement());
        nodeTwo.setElement(temp);
    }

    // ========== MÉTODOS AUXILIARES NECESSÁRIOS ==========

    /**
     * Percurso pré-ordem: visita raiz, depois filhos
     * Ordem: Nó atual -> Filho1 -> Filho2 -> ... -> FilhoN
     */
    private void preOrder(Node node, ArrayList<Object> array) {
        Object element = node.getElement();
        array.add(element); // Visita o nó atual primeiro
        // Depois visita todos os filhos recursivamente
        for (Node child : node.getChild()) {
            preOrder(child, array);
        }
    }

    /**
     * Percurso pós-ordem: visita filhos, depois raiz
     * Ordem: Filho1 -> Filho2 -> ... -> FilhoN -> Nó atual
     */
    private void postOrder(Node node, ArrayList<Node> array) {
        // Primeiro visita todos os filhos recursivamente
        for (Node child : node.getChild()) {
            postOrder(child, array);
        }
        array.add(node); // Depois visita o nó atual
    }

    // ========== MÉTODO PRINT ==========

    /**
     * Imprime a estrutura visual da árvore genérica
     * Mostra todos os filhos de cada nó de forma hierárquica
     */
    public void printTree() {
        if (isEmpty()) {
            System.out.println("Árvore vazia!");
            return;
        }
        System.out.println("\nEstrutura da Árvore Genérica:");
        printNode(root, "", true);
        System.out.println();
    }

    /**
     * Método auxiliar recursivo para impressão hierárquica
     * 
     * @param node   nó atual a ser impresso
     * @param prefix prefixo para formatação (indentação)
     * @param isLast indica se é o último filho (para formatação)
     */
    private void printNode(Node node, String prefix, boolean isLast) {
        System.out.println(prefix + (isLast ? "└── " : "├── ") + node.getElement());

        // Imprime todos os filhos (árvore genérica pode ter N filhos)
        java.util.List<Node> children = node.getChild();
        for (int i = 0; i < children.size(); i++) {
            printNode(children.get(i), prefix + (isLast ? "    " : "│   "), i == children.size() - 1);
        }
    }
}
