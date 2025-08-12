import Excesao.*;

/**
 * Implementação de Fila de Prioridade usando Heap baseado em Árvore Binária
 * Diferentemente do HeapArray, aqui cada elemento é um nó na árvore
 * Mantém a propriedade do min-heap: pai ≤ filhos
 */
public class HeapBT implements FPInterface {

    /**
     * Classe interna para representar um nó da árvore
     * Cada nó contém:
     * - data: o Item (chave + valor)
     * - left/right: ponteiros para filhos
     * - parent: ponteiro para o pai (facilita navegação)
     */
    private class HeapNode {
        Item data;
        HeapNode left;
        HeapNode right;
        HeapNode parent;

        public HeapNode(Item data) {
            this.data = data;
            this.left = null;
            this.right = null;
            this.parent = null;
        }
    }

    private HeapNode root; // Raiz da árvore (elemento com menor chave)
    private int size; // Número de elementos na árvore

    /**
     * Construtor: inicializa heap vazio
     */
    public HeapBT() {
        this.root = null;
        this.size = 0;
    }

    /**
     * Retorna o número de elementos na árvore
     *
     * @return quantidade de nós
     */
    @Override
    public int size() {
        return this.size;
    }

    /**
     * Verifica se a árvore está vazia
     *
     * @return true se não há nós, false caso contrário
     */
    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * Retorna o elemento com menor chave sem removê-lo
     *
     * @return Item na raiz (menor chave)
     * @throws EFilaPrioridade se o heap estiver vazio
     */
    @Override
    public Item min() throws EFilaPrioridade {
        if (isEmpty()) {
            throw new EFilaPrioridade("A Fila de Prioridade está vazia");
        }
        return this.root.data;
    }

    /**
     * Insere um novo elemento no heap
     *
     * @param key   prioridade do elemento
     * @param value dados a serem armazenados
     *
     *              PROCESSO:
     *              1. Se vazio, cria raiz
     *              2. Senão, encontra próxima posição disponível (heap completo)
     *              3. Insere como filho e chama upHeap para restaurar propriedade
     */
    @Override
    public void insert(Object key, Object value) {
        Item newItem = new Item(key, value);
        HeapNode newNode = new HeapNode(newItem);

        if (isEmpty()) {
            this.root = newNode;
        } else {
            // Encontra a posição para inserção (próxima posição disponível)
            HeapNode parent = findInsertionParent();

            // Insere como filho esquerdo ou direito
            if (parent.left == null) {
                parent.left = newNode;
            } else {
                parent.right = newNode;
            }
            newNode.parent = parent;

            // Restaura a propriedade do heap (bubble up)
            upHeap(newNode);
        }

        this.size++;
    }

    /**
     * Remove e retorna o elemento com menor chave
     *
     * @return Item que estava na raiz
     * @throws EFilaPrioridade se o heap estiver vazio
     *
     *                         PROCESSO:
     *                         1. Salva dados da raiz
     *                         2. Substitui raiz pelo último nó
     *                         3. Remove o último nó
     *                         4. Chama downHeap para restaurar propriedade
     */
    @Override
    public Item removeMin() throws EFilaPrioridade {
        if (isEmpty()) {
            throw new EFilaPrioridade("A Fila de Prioridade está vazia");
        }

        Item minItem = this.root.data;

        if (this.size == 1) {
            this.root = null;
        } else {
            // Encontra o último nó (mais à direita no último nível)
            HeapNode lastNode = findLastNode();

            // Substitui a raiz pelo último nó
            this.root.data = lastNode.data;

            // Remove o último nó
            if (lastNode.parent.right == lastNode) {
                lastNode.parent.right = null;
            } else {
                lastNode.parent.left = null;
            }

            // Restaura a propriedade do heap (bubble down)
            downHeap(this.root);
        }

        this.size--;
        return minItem;
    }

    /**
     * Encontra o pai onde deve ser inserido o próximo nó
     *
     * @return nó que será pai do próximo elemento inserido
     *
     *         ALGORITMO INTELIGENTE:
     *         - Usa representação binária da posição para navegar
     *         - Posição (size+1) em binário indica o caminho na árvore
     *         - 0 = esquerda, 1 = direita
     *         - Ignora primeiro bit (sempre 1) e último (indica filho)
     */
    private HeapNode findInsertionParent() {
        if (this.size == 0)
            return null;

        // Usa representação binária da posição para encontrar o caminho
        int position = this.size + 1; // próxima posição (1-indexada)
        String binaryPath = Integer.toBinaryString(position);

        HeapNode current = this.root;

        // Ignora o primeiro bit (que é sempre 1) e segue o caminho
        for (int i = 1; i < binaryPath.length() - 1; i++) {
            if (binaryPath.charAt(i) == '0') {
                current = current.left;
            } else {
                current = current.right;
            }
        }

        return current;
    }

    /**
     * Encontra o último nó da árvore (mais à direita no último nível)
     *
     * @return o último nó inserido
     *
     *         ALGORITMO:
     *         - Usa representação binária da posição atual (size)
     *         - Navega seguindo o caminho binário
     *         - 0 = esquerda, 1 = direita
     */
    private HeapNode findLastNode() {
        if (this.size == 0)
            return null;

        // Usa representação binária da posição para encontrar o caminho
        String binaryPath = Integer.toBinaryString(this.size);

        HeapNode current = this.root;

        // Ignora o primeiro bit (que é sempre 1) e segue o caminho
        for (int i = 1; i < binaryPath.length(); i++) {
            if (binaryPath.charAt(i) == '0') {
                current = current.left;
            } else {
                current = current.right;
            }
        }

        return current;
    }

    /**
     * Restaura a propriedade do heap subindo o nó (usado após inserção)
     *
     * @param node nó que precisa subir
     *
     *             PROCESSO:
     *             1. Se chegou na raiz, para
     *             2. Se nó < pai, troca dados e continua subindo
     *             3. Se nó >= pai, para (propriedade restaurada)
     */
    private void upHeap(HeapNode node) {
        if (node.parent == null) {
            return; // chegou na raiz
        }

        if (compare(node.data, node.parent.data)) {
            // Troca os dados (não os nós)
            Item temp = node.data;
            node.data = node.parent.data;
            node.parent.data = temp;

            // Continua subindo
            upHeap(node.parent);
        }
    }

    /**
     * Restaura a propriedade do heap descendo o nó (usado após remoção)
     *
     * @param node nó que precisa descer
     *
     *             PROCESSO:
     *             1. Encontra o menor entre: nó, filho esquerdo, filho direito
     *             2. Se o menor não é o nó atual, troca e continua descendo
     *             3. Se o nó já é o menor, para (propriedade restaurada)
     */
    private void downHeap(HeapNode node) {
        HeapNode smallest = node;

        // Verifica filho esquerdo
        if (node.left != null && compare(node.left.data, smallest.data)) {
            smallest = node.left;
        }

        // Verifica filho direito
        if (node.right != null && compare(node.right.data, smallest.data)) {
            smallest = node.right;
        }

        // Se o menor não é o nó atual, troca e continua
        if (smallest != node) {
            Item temp = node.data;
            node.data = smallest.data;
            smallest.data = temp;

            downHeap(smallest);
        }
    }

    /**
     * Compara duas chaves (retorna true se key1 < key2)
     *
     * @param item1 primeiro item para comparação
     * @param item2 segundo item para comparação
     * @return true se chave de item1 < chave de item2
     */
    private boolean compare(Item item1, Item item2) {
        // Verifica se as chaves são comparáveis
        if (!(item1.key() instanceof Comparable) ||
                !(item2.key() instanceof Comparable)) {
            throw new IllegalArgumentException("As chaves devem implementar Comparable");
        }

        @SuppressWarnings("unchecked")
        Comparable<Object> key1 = (Comparable<Object>) item1.key();
        Object key2 = item2.key();

        return key1.compareTo(key2) < 0;
    }

    /**
     * Método auxiliar para visualização - mostra a estrutura da árvore
     * Imprime a árvore de forma hierárquica e visual
     */
    public void printTree() {
        if (isEmpty()) {
            System.out.println("Heap vazio!");
            return;
        }

        System.out.println("\n=== HEAP BINARY TREE (Min-Heap) ===");
        System.out.println("Tamanho: " + size());
        System.out.println("Estrutura da árvore:");
        printTreeRecursive(this.root, "", true);
        System.out.println("====================================\n");
    }

    /**
     * Método recursivo para imprimir árvore
     *
     * @param node   nó atual
     * @param prefix prefixo para formatação
     * @param isLast se é o último filho
     */
    private void printTreeRecursive(HeapNode node, String prefix, boolean isLast) {
        if (node != null) {
            System.out.println(prefix + (isLast ? "└── " : "├── ") +
                    node.data.key() + " (" + node.data.value() + ")");

            if (node.left != null || node.right != null) {
                if (node.left != null) {
                    printTreeRecursive(node.left, prefix + (isLast ? "    " : "│   "), node.right == null);
                }
                if (node.right != null) {
                    printTreeRecursive(node.right, prefix + (isLast ? "    " : "│   "), true);
                }
            }
        }
    }
}
