import Excesao.*;

/**
 * Implementação de Fila de Prioridade usando Heap (Min-Heap) baseado em array
 * Um Min-Heap garante que o elemento com menor chave (maior prioridade)
 * está sempre na raiz (índice 1)
 */
public class HeapArray implements FPInterface {
    private Item heap[]; // Array que armazena os elementos do heap
    private int capacidade; // Capacidade máxima atual do array
    private int ultimo; // Índice do último elemento válido

    /**
     * Construtor: inicializa o heap com capacidade especificada
     * PONTO IMPORTANTE: Usa índice 1-based (índice 0 não é usado)
     * Isso simplifica os cálculos de pai/filho:
     * - Pai de i: i/2
     * - Filho esquerdo de i: 2*i
     * - Filho direito de i: 2*i+1
     */
    public HeapArray(int capacidade) {
        this.capacidade = capacidade + 1; // +1 porque índice 0 não é usado
        this.heap = new Item[this.capacidade];
        this.ultimo = 0; // último elemento válido (heap vazio)
    }

    /**
     * Retorna o número de elementos no heap
     *
     * @return quantidade de elementos válidos
     */
    public int size() {
        return this.ultimo;
    }

    /**
     * Verifica se o heap está vazio
     *
     * @return true se não há elementos, false caso contrário
     */
    public boolean isEmpty() {
        return this.ultimo == 0;
    }

    /**
     * Insere um novo elemento no heap
     *
     * @param key prioridade do elemento (deve implementar Comparable)
     * @param val dados a serem armazenados
     *
     *            PROCESSO:
     *            1. Redimensiona array se necessário (dobra capacidade)
     *            2. Insere novo elemento na última posição
     *            3. Chama upHeap para restaurar propriedade do heap
     */
    public void insert(Object key, Object val) {
        // key = prioridade (usado para comparação)
        // val = dados que você quer guardar

        // Verifica se precisa redimensionar o array
        if (this.ultimo + 1 >= this.capacidade) {
            this.capacidade *= 2; // Dobra a capacidade

            Item temp[] = new Item[capacidade];

            // Copia elementos existentes para novo array
            for (int i = 0; i <= this.ultimo; i++) {
                temp[i] = this.heap[i];
            }
            this.heap = temp;
        }

        // Insere novo elemento na próxima posição disponível
        this.ultimo++;
        Item entry = new Item(key, val);
        this.heap[ultimo] = entry;

        // Restaura propriedade do heap "subindo" o elemento
        this.upHeap(ultimo);
    }

    /**
     * Retorna o elemento com menor chave sem removê-lo
     *
     * @return Item com menor prioridade (na raiz - índice 1)
     * @throws EFilaPrioridade se o heap estiver vazio
     */
    public Item min() throws EFilaPrioridade {
        // O menor valor de um min-heap é sempre o primeiro (raiz)
        if (this.isEmpty()) {
            throw new EFilaPrioridade("A Fila de Prioridade está vazia");
        }

        return this.heap[1];
    }

    /**
     * Remove e retorna o elemento com menor chave (maior prioridade)
     *
     * @return Item que estava na raiz
     * @throws EFilaPrioridade se o heap estiver vazio
     *
     *                         PROCESSO:
     *                         1. Salva o elemento mínimo (raiz)
     *                         2. Move último elemento para a raiz
     *                         3. Remove o último elemento
     *                         4. Chama downHeap para restaurar propriedade do heap
     */
    public Item removeMin() throws EFilaPrioridade {
        if (this.isEmpty()) {
            throw new EFilaPrioridade("A Fila de Prioridade está vazia");
        }

        Item min = this.min(); // Salva o elemento a ser retornado

        // Troca último elemento com a raiz
        this.swap(ultimo, 1);

        // Remove o último elemento (que agora contém o min)
        this.heap[this.ultimo] = null;
        this.ultimo--;

        // Restaura propriedade do heap "descendo" o novo elemento da raiz
        if (!this.isEmpty()) { // Se ainda há elementos
            this.downHeap(1);
        }

        return min;
    }

    /**
     * Restaura a propriedade do heap "descendo" um elemento
     *
     * @param raiz índice do elemento que precisa descer
     *
     *             ALGORITMO:
     *             1. Calcula índices dos filhos
     *             2. Se não tem filhos, para (chegou numa folha)
     *             3. Se tem só filho esquerdo, compara e troca se necessário
     *             4. Se tem ambos filhos, encontra o menor e compara com pai
     *             5. Se filho < pai, troca e continua descendo recursivamente
     */
    private void downHeap(int raiz) {
        // Calcula índices dos filhos usando fórmulas do heap binário
        int esquerda = 2 * raiz;
        int direita = 2 * raiz + 1;

        int menor;

        // CASO 1: Não tem filhos - chegou numa folha
        if (esquerda > this.ultimo && direita > this.ultimo) {
            return;
        }

        // CASO 2: Só tem filho esquerdo
        if (direita > this.ultimo) {
            if (this.compare(esquerda, raiz)) { // filho < pai?
                this.swap(raiz, esquerda); // troca
                this.downHeap(esquerda); // continua descendo
            }
            return;
        }

        // CASO 3: Tem ambos os filhos - encontra o menor
        if (this.compare(direita, esquerda)) {
            menor = direita; // direita é menor
        } else {
            menor = esquerda; // esquerda é menor (ou igual)
        }

        // Compara pai com o menor filho
        if (this.compare(menor, raiz)) { // menor filho < pai?
            this.swap(menor, raiz); // troca
            this.downHeap(menor); // continua descendo
        }
    }

    /**
     * Restaura a propriedade do heap "subindo" um elemento
     *
     * @param n índice do elemento que precisa subir
     *
     *          ALGORITMO:
     *          1. Calcula o pai do elemento
     *          2. Se chegou na raiz (pai = 0), para
     *          3. Se filho >= pai, para (heap já está correto)
     *          4. Se filho < pai, troca e continua subindo
     */
    private void upHeap(int n) {
        int pai = n / 2; // Fórmula do heap: pai de i é i/2

        // Para se chegou na raiz ou se já está na ordem correta
        if (pai == 0 || !this.compare(n, pai)) {
            return;
        }

        // Troca filho com pai
        this.swap(n, pai);

        // Continua subindo recursivamente
        this.upHeap(pai);
    }

    /**
     * Compara duas chaves do heap - VERSÃO SIMPLIFICADA PARA INTEIROS
     *
     * @param filho índice do primeiro elemento
     * @param pai   índice do segundo elemento
     * @return true se chave do filho < chave do pai (min-heap)
     *
     *         NOTA: Esta versão assume que as chaves são sempre Integer
     *         Para maior flexibilidade, use a versão com Comparable
     */
    private boolean compare(int filho, int pai) {
        Item itemFilho = this.heap[filho];
        Item itemPai = this.heap[pai];

        // Assume que as chaves são Integer
        int keyFilho = (Integer) itemFilho.key();
        int keyPai = (Integer) itemPai.key();

        return (keyFilho < keyPai); // filho < pai (min-heap)
    }

    /**
     * Troca dois elementos de posição no array
     *
     * @param a índice do primeiro elemento
     * @param b índice do segundo elemento
     */
    private void swap(int a, int b) {
        Item temp = this.heap[a];
        this.heap[a] = this.heap[b];
        this.heap[b] = temp;
    }

    // ========== MÉTODO PRINT ==========

    /**
     * Imprime a estrutura visual do heap em formato de árvore
     * Mostra como os elementos estão organizados hierarquicamente
     */
    public void printTree() {
        if (isEmpty()) {
            System.out.println("Heap vazio!");
            return;
        }

        System.out.println("\n=== HEAP ARRAY (Min-Heap) ===");
        System.out.println("Tamanho: " + size());
        System.out.println("Estrutura da árvore:");

        printNode(1, "", true);

        System.out.println("\nRepresentação do array:");
        System.out.print("Índices: [");
        for (int i = 1; i <= ultimo; i++) {
            System.out.print(i + (i < ultimo ? ", " : ""));
        }
        System.out.println("]");

        System.out.print("Chaves:  [");
        for (int i = 1; i <= ultimo; i++) {
            System.out.print(heap[i].key() + (i < ultimo ? ", " : ""));
        }
        System.out.println("]");
        System.out.println("=========================\n");
    }

    /**
     * Método auxiliar recursivo para impressão hierárquica
     * 
     * @param index  índice do nó atual no array
     * @param prefix prefixo para formatação (indentação)
     * @param isLast indica se é o último filho (para formatação)
     */
    private void printNode(int index, String prefix, boolean isLast) {
        if (index > ultimo) {
            return;
        }

        // Imprime o nó atual
        System.out.println(prefix + (isLast ? "└── " : "├── ") +
                heap[index].key() + " (" + heap[index].value() + ")");

        // Calcula índices dos filhos
        int leftChild = 2 * index;
        int rightChild = 2 * index + 1;

        // Se tem pelo menos um filho
        if (leftChild <= ultimo) {
            // Imprime filho esquerdo (se existe)
            if (leftChild <= ultimo) {
                printNode(leftChild, prefix + (isLast ? "    " : "│   "), rightChild > ultimo);
            }

            // Imprime filho direito (se existe)
            if (rightChild <= ultimo) {
                printNode(rightChild, prefix + (isLast ? "    " : "│   "), true);
            }
        }
    }
}
