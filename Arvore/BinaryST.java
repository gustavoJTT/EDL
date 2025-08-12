import Node.Node;
import Excesao.*;

/**
 * Implementação de uma Árvore Binária de Busca (Binary Search Tree)
 *
 * Uma BST é uma árvore binária onde:
 * - Cada nó tem no máximo 2 filhos (esquerdo e direito)
 * - Valores menores ficam à esquerda
 * - Valores maiores ficam à direita
 * - Permite busca, inserção e remoção em O(log n) no caso médio
 */
public class BinaryST {
    private Node root; // Nó raiz da árvore
    private int size; // Número total de elementos na árvore

    /**
     * Construtor - cria uma BST vazia
     */
    public BinaryST() {
        this.size = 0;
        this.root = null;
    }

    // ========== OPERAÇÕES BÁSICAS BST ==========

    /**
     * Insere um novo elemento na BST mantendo a propriedade de ordenação
     *
     * @param o valor inteiro a ser inserido
     * @return Node o nó criado e inserido
     * @throws EInvalidPosition se o elemento já existe na árvore
     */
    public Node insert(int o) {
        // Caso especial: árvore vazia - cria a raiz
        if (isEmpty()) {
            this.size = 1;
            this.root = new Node(String.valueOf(o));
            return this.root;
        }

        // Busca a posição onde inserir (retorna o pai do novo nó)
        Node position = this.treeSearch(this.root, o);
        int chave = key(position);

        // Verifica se o elemento já existe
        if (chave == o) {
            throw new EInvalidPosition("Esse elemento já foi inserido");
        }

        // Cria o novo nó e estabelece relação pai-filho
        Node novo = new Node(String.valueOf(o));
        novo.setParent(position);

        // Decide se vai para esquerda ou direita baseado na comparação
        if (o < chave) {
            position.setLeftChild(novo); // Menor: vai para esquerda
        } else {
            position.setRightChild(novo); // Maior: vai para direita
        }

        this.size++;
        return novo;
    }

    /**
     * Remove um elemento da BST mantendo a propriedade de ordenação
     *
     * @param o valor inteiro a ser removido
     * @return Object o elemento removido
     * @throws EInvalidPosition se árvore vazia ou elemento não encontrado
     */
    public Object remove(int o) {
        if (isEmpty()) {
            throw new EInvalidPosition("A árvore está vazia");
        }

        // Busca o nó a ser removido
        Node position = treeSearch(root, o);
        if (position == null || key(position) != o) {
            throw new EInvalidPosition("Elemento não achado");
        }

        Node left = position.getLeftChild();
        Node right = position.getRightChild();

        // CASO 1: Nó com dois filhos (mais complexo)
        // Substitui pelo sucessor (menor elemento da subárvore direita)
        if (left != null && right != null) {
            Node successor = right;
            // Encontra o sucessor: vai para direita e depois sempre para esquerda
            while (successor.getLeftChild() != null) {
                successor = successor.getLeftChild();
            }
            // Remove o sucessor recursivamente e substitui o valor
            Object elem = remove(key(successor));
            position.setElement(successor.getElement());
            return elem;
        }

        // CASO 2: Nó com um filho ou nenhum filho
        Node child;
        if (left != null) {
            child = left; // Tem só filho esquerdo
        } else if (right != null) {
            child = right; // Tem só filho direito
        } else {
            child = null; // Não tem filhos (folha)
        }

        // Reconecta as referências, "pulando" o nó removido
        if (position == this.root) {
            // Se estamos removendo a raiz
            this.root = child;
            if (child != null) {
                child.setParent(null);
            }
        } else {
            // Se é um nó interno
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

    /**
     * Busca um elemento na BST
     *
     * @param o valor inteiro a ser buscado
     * @return Node o nó encontrado
     * @throws EInvalidPosition se árvore vazia ou elemento não encontrado
     */
    public Node search(int o) {
        if (isEmpty()) {
            throw new EInvalidPosition("Árvore vazia");
        }

        // Utiliza busca interna e verifica se encontrou exatamente o elemento
        Node n = treeSearch(root, o);
        if (n == null || key(n) != o) {
            throw new EInvalidPosition("Elemento não achado");
        }
        return n;
    }

    /**
     * Verifica se a árvore está vazia
     *
     * @return boolean true se vazia, false caso contrário
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Retorna a raiz da árvore
     *
     * @return Node a raiz da árvore
     */
    public Node getRoot() {
        return root;
    }

    /**
     * Retorna o número de elementos na árvore
     *
     * @return int tamanho da árvore
     */
    public int getSize() {
        return size;
    }

    // ========== MÉTODOS AUXILIARES NECESSÁRIOS ==========

    /**
     * Busca recursiva na BST - método auxiliar interno
     * Retorna o nó com a chave procurada OU o nó pai onde deveria estar
     *
     * @param n nó atual da recursão
     * @param o valor procurado
     * @return Node encontrado ou pai onde deveria inserir
     */
    private Node treeSearch(Node n, int o) {
        if (n == null)
            return null;

        int chave = key(n);
        if (o == chave) {
            // Encontrou exatamente o que procurava
            return n;
        } else if (o < chave) {
            // Valor procurado é menor - vai para esquerda
            if (n.getLeftChild() != null) {
                return treeSearch(n.getLeftChild(), o);
            }
        } else {
            // Valor procurado é maior - vai para direita
            if (n.getRightChild() != null) {
                return treeSearch(n.getRightChild(), o);
            }
        }
        // Se chegou aqui, não encontrou e retorna onde deveria inserir
        return n;
    }

    /**
     * Extrai a chave (valor inteiro) de um nó
     *
     * @param n nó do qual extrair a chave
     * @return int valor da chave
     * @throws EInvalidPosition se nó for nulo
     */
    private int key(Node n) {
        if (n == null)
            throw new EInvalidPosition("Nó inválido");
        return Integer.parseInt((String) n.getElement());
    }

    // ========== MÉTODO PRINT ==========

    /**
     * Imprime a estrutura visual da BST
     * Mostra a hierarquia da árvore de forma organizada
     */
    public void printTree() {
        if (isEmpty()) {
            System.out.println("BST vazia!");
            return;
        }
        System.out.println("\nEstrutura da BST (Binary Search Tree):");
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
        if (node == null)
            return;

        // Imprime o nó atual com formatação adequada
        System.out.println(prefix + (isLast ? "└── " : "├── ") + node.getElement());

        boolean hasLeft = node.getLeftChild() != null;
        boolean hasRight = node.getRightChild() != null;

        // Se tem filhos, imprime eles recursivamente
        if (hasLeft || hasRight) {
            if (hasLeft) {
                // Imprime filho esquerdo
                printNode(node.getLeftChild(), prefix + (isLast ? "    " : "│   "), !hasRight);
            } else {
                // Indica posição vazia
                System.out.println(prefix + (isLast ? "    " : "│   ") + "├── (vazio)");
            }

            if (hasRight) {
                // Imprime filho direito
                printNode(node.getRightChild(), prefix + (isLast ? "    " : "│   "), true);
            } else {
                // Indica posição vazia
                System.out.println(prefix + (isLast ? "    " : "│   ") + "└── (vazio)");
            }
        }
    }
}
