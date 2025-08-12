import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Iterator;

/**
 * Implementação de uma Skip List - estrutura de dados probabilística
 *
 * CONCEITOS FUNDAMENTAIS:
 * - Estrutura em níveis: cada elemento pode aparecer em múltiplos níveis
 * - Decisão probabilística: coin flip determina se elemento "sobe" de nível
 * - Navegação eficiente: busca começa no topo e desce conforme necessário
 * - Complexidade média O(log n) para busca, inserção e remoção
 *
 * VANTAGENS:
 * - Mais simples que árvores balanceadas
 * - Permite busca paralela
 * - Inserção/remoção sem rotações complexas
 */
public class skipList implements DictInterface {

    /**
     * Nó da Skip List com ponteiros para 4 direções
     * Permite navegação horizontal (prev/next) e vertical (up/down)
     */
    public class QuadNode {
        Item entry; // Dados armazenados (chave + valor)
        QuadNode prev; // Ponteiro para nó anterior no mesmo nível
        QuadNode next; // Ponteiro para próximo nó no mesmo nível
        QuadNode down; // Ponteiro para mesmo elemento no nível inferior
        QuadNode up; // Ponteiro para mesmo elemento no nível superior

        public QuadNode(Item item) {
            this.entry = item;
        }

        public QuadNode(int key, Object value) {
            Item novo = new Item(key, value);
            this(novo);
        }

        @Override
        public String toString() {
            return Integer.toString((Integer) entry.key());
        }
    }

    private QuadNode topHead; // Cabeça do nível mais alto
    private QuadNode topTail; // Cauda do nível mais alto
    private int levels; // Número atual de níveis
    private int size; // Número de elementos
    private final Random rand = new Random(); // Para decisões probabilísticas

    /**
     * Construtor da Skip List
     * Cria nível base com sentinelas (MIN_VALUE e MAX_VALUE)
     */
    public skipList() {
        // Sentinelas: elementos especiais que marcam início e fim
        QuadNode baseHead = new QuadNode(Integer.MIN_VALUE, null);
        QuadNode baseTail = new QuadNode(Integer.MAX_VALUE, null);

        baseHead.next = baseTail;
        baseTail.prev = baseHead;

        this.topHead = baseHead;
        this.topTail = baseTail;
        this.levels = 1;
        this.size = 0;
    }

    /**
     * Busca um elemento pela chave
     *
     * @param valor chave a ser buscada
     * @return Item encontrado ou null se não existir
     *
     *         ALGORITMO:
     *         1. Encontra posição usando findElementNode
     *         2. Verifica se nó atual ou próximo contém a chave
     *         3. Retorna item ou null
     */
    public Item findElement(int valor) {
        QuadNode node = this.findElementNode(valor);

        // Verifica se encontrou exatamente
        if (node.entry != null && (Integer) node.entry.key() == valor) {
            return node.entry;
        }

        // Verifica próximo nó (caso a busca pare antes)
        if (node.next != null && node.next.entry != null &&
                (Integer) node.next.entry.key() == valor) {
            return node.next.entry;
        }

        return null;
    }

    /**
     * Insere um novo elemento na Skip List
     *
     * @param key   chave do elemento
     * @param value valor a ser armazenado
     * @throws IllegalArgumentException se chave já existir
     *
     *                                  PROCESSO:
     *                                  1. Verifica se chave já existe
     *                                  2. Insere no nível base
     *                                  3. Probabilisticamente decide se "promove"
     *                                  para níveis superiores
     *                                  4. Cria novos níveis se necessário
     */
    public void insertItem(int key, Object value) {
        Item novo = new Item(key, value);
        QuadNode pos = findElementNode((Integer) novo.key());

        // Verifica duplicatas
        if (pos.entry != null && (Integer) pos.entry.key() == key) {
            throw new IllegalArgumentException("Valor " + value + " já existe. Ignorando inserção.");
        }

        if (pos.next != null && pos.next.entry != null &&
                (Integer) pos.next.entry.key() == key) {
            throw new IllegalArgumentException("Valor " + value + " já existe (à direita). Ignorando inserção.");
        }

        // Insere no nível base
        QuadNode newNode = new QuadNode(novo);
        newNode.prev = pos;
        newNode.next = pos.next;

        if (pos.next != null) {
            pos.next.prev = newNode;
        }
        pos.next = newNode;

        // PROMOÇÃO PROBABILÍSTICA: coin flip para cada nível
        QuadNode lowerNode = newNode;
        int currentLevel = 1;

        while (this.rand.nextBoolean()) { // 50% chance de subir
            currentLevel++;

            // Cria novo nível se necessário
            if (currentLevel > this.levels) {
                this.levels++;

                QuadNode newHead = new QuadNode(Integer.MIN_VALUE, null);
                QuadNode newTail = new QuadNode(Integer.MAX_VALUE, null);

                newHead.next = newTail;
                newTail.prev = newHead;

                newHead.down = this.topHead;
                this.topHead.up = newHead;
                newTail.down = this.topTail;
                this.topTail.up = newTail;

                this.topHead = newHead;
                this.topTail = newTail;
            }

            // Encontra posição no nível superior
            while (pos.up == null && pos != null) {
                pos = pos.prev;
            }

            if (pos == null) {
                pos = this.topHead;
            } else {
                pos = pos.up;
            }

            // Cria cópia do elemento no nível superior
            QuadNode elevatedNode = new QuadNode(novo);
            elevatedNode.prev = pos;
            elevatedNode.next = pos.next;

            if (pos.next != null) {
                pos.next.prev = elevatedNode;
            }
            pos.next = elevatedNode;

            // Liga verticalmente
            elevatedNode.down = lowerNode;
            lowerNode.up = elevatedNode;

            lowerNode = elevatedNode;
        }

        this.size++;
    }

    /**
     * Remove um elemento da Skip List
     *
     * @param key chave do elemento a remover
     * @return Item removido
     * @throws IllegalArgumentException se chave não existir
     *
     *                                  PROCESSO:
     *                                  1. Encontra elemento no nível base
     *                                  2. Remove de todos os níveis (subindo
     *                                  verticalmente)
     *                                  3. Remove níveis vazios se necessário
     */
    public Item removeElement(int key) {
        QuadNode node = this.findElementNode(key);

        // Verifica se encontrou o elemento
        if (node == null || node.entry == null || (Integer) node.entry.key() != key) {
            if (node != null && node.next != null && node.next.entry != null &&
                    (Integer) node.next.entry.key() == key) {
                node = node.next;
            } else {
                throw new IllegalArgumentException("Chave " + key + " não encontrada");
            }
        }

        Item removido = node.entry;

        // Remove de todos os níveis (torre vertical)
        while (node != null) {
            QuadNode left = node.prev;
            QuadNode right = node.next;

            // Reconecta vizinhos horizontais
            if (left != null)
                left.next = right;
            if (right != null)
                right.prev = left;

            QuadNode up = node.up;
            // Limpa referências do nó removido
            node.prev = node.next = node.up = node.down = null;
            node = up; // Sobe para próximo nível
        }

        // Remove níveis vazios (só sentinelas)
        while (this.levels > 1 && this.topHead.next == this.topTail) {
            this.topHead = this.topHead.down;
            this.topTail = this.topTail.down;

            if (this.topHead != null)
                this.topHead.up = null;
            if (this.topTail != null)
                this.topTail.up = null;

            this.levels--;
        }

        this.size--;
        return removido;
    }

    /**
     * Retorna o número de elementos na Skip List
     *
     * @return quantidade de elementos únicos
     */
    public int size() {
        return this.size;
    }

    /**
     * Verifica se a Skip List está vazia
     *
     * @return true se não há elementos
     */
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * Retorna um iterador para todas as chaves na Skip List em ordem
     *
     * @return Iterator<Integer> das chaves ordenadas
     */
    public Iterator<Integer> keys() {
        List<Integer> listaChaves = new ArrayList<>();

        // Vai para o nível base (mais baixo)
        QuadNode atual = this.topHead;
        while (atual.down != null) {
            atual = atual.down;
        }

        // Percorre linearmente ignorando sentinelas
        atual = atual.next; // Pula MIN_VALUE
        while (atual != null && (Integer) atual.entry.key() != Integer.MAX_VALUE) {
            listaChaves.add((Integer) atual.entry.key());
            atual = atual.next;
        }

        return listaChaves.iterator();
    }

    /**
     * Retorna um iterador para todos os elementos na Skip List em ordem
     *
     * @return Iterator<Item> dos elementos ordenados por chave
     */
    public Iterator<Item> elements() {
        List<Item> listaItens = new ArrayList<>();

        // Vai para o nível base (mais baixo)
        QuadNode atual = this.topHead;
        while (atual.down != null) {
            atual = atual.down;
        }

        // Percorre linearmente ignorando sentinelas
        atual = atual.next; // Pula MIN_VALUE
        while (atual != null && (Integer) atual.entry.key() != Integer.MAX_VALUE) {
            listaItens.add(atual.entry);
            atual = atual.next;
        }

        return listaItens.iterator();
    }

    // ========== MÉTODOS AUXILIARES NECESSÁRIOS ==========

    /**
     * Encontra a posição onde um elemento deveria estar
     *
     * @param valor chave a procurar
     * @return QuadNode que precede a posição (ou contém o elemento)
     *
     *         ALGORITMO DE NAVEGAÇÃO:
     *         1. Começa no topo-esquerda
     *         2. Move-se horizontalmente enquanto possível
     *         3. Desce um nível e repete
     *         4. Para quando chega no nível base
     */
    private QuadNode findElementNode(int valor) {
        QuadNode atual = this.topHead;

        while (true) {
            // Move-se horizontalmente até não poder mais
            while (atual.next != null &&
                    atual.next.entry != null &&
                    (Integer) atual.next.entry.key() <= valor &&
                    (Integer) atual.next.entry.key() != Integer.MAX_VALUE) {
                atual = atual.next;
            }

            // Se pode descer, desce; senão encontrou posição
            if (atual.down != null) {
                atual = atual.down;
            } else {
                return atual; // Chegou no nível base
            }
        }
    }

    // ========== MÉTODO PRINT ==========

    /**
     * Imprime a estrutura visual da Skip List
     * Mostra todos os níveis e conexões entre nós
     */
    public void printSkipList() {
        System.out.println("\n=== SKIP LIST ===");
        System.out.println("Níveis: " + levels);
        System.out.println("Elementos: " + size);
        System.out.println("Estrutura:");

        // Imprime cada nível de cima para baixo
        QuadNode levelHead = topHead;
        for (int level = levels; level >= 1; level--) {
            System.out.printf("Nível %2d: ", level);

            QuadNode atual = levelHead;
            while (atual != null) {
                if (atual.entry.key().equals(Integer.MIN_VALUE)) {
                    System.out.print("[-∞]");
                } else if (atual.entry.key().equals(Integer.MAX_VALUE)) {
                    System.out.print("[+∞]");
                } else {
                    System.out.print("[" + atual.entry.key() + "]");
                }

                if (atual.next != null) {
                    System.out.print(" ↔ ");
                }
                atual = atual.next;
            }
            System.out.println();

            // Desce para próximo nível
            if (levelHead.down != null) {
                levelHead = levelHead.down;
            }
        }

        System.out.println("\nElementos em ordem (nível base):");
        Iterator<Item> elementos = elements();
        while (elementos.hasNext()) {
            Item item = elementos.next();
            System.out.println("- " + item.key() + " → " + item.value());
        }
        System.out.println("================\n");
    }
}
