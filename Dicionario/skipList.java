import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Iterator;

/**
 * Implementação de uma Skip List - uma estrutura de dados probabilística
 * que permite operações de busca, inserção e remoção em tempo médio O(log n).
 *
 * A Skip List funciona como uma lista ligada multi-nível, onde elementos
 * podem aparecer em múltiplos níveis com probabilidade decrescente.
 * Isso permite "pular" grandes porções da lista durante a busca.
 */
public class skipList implements DictInterface {

    /**
     * Nó da Skip List com ponteiros para 4 direções:
     * - prev/next: navegação horizontal (mesmo nível)
     * - up/down: navegação vertical (entre níveis)
     */
    public class QuadNode {
        Item entry; // Item armazenado no nó
        QuadNode prev; // Ponteiro para o nó anterior no mesmo nível
        QuadNode next; // Ponteiro para o próximo nó no mesmo nível
        QuadNode down; // Ponteiro para o nó correspondente no nível inferior
        QuadNode up; // Ponteiro para o nó correspondente no nível superior

        /**
         * Construtor que recebe um Item
         *
         * @param item Item a ser armazenado
         */
        public QuadNode(Item item) {
            this.entry = item;
        }

        /**
         * Construtor que recebe chave e valor separadamente
         *
         * @param key   Chave do elemento
         * @param value Valor do elemento
         */
        public QuadNode(int key, Object value) {
            Item novo = new Item(key, value);
            this(novo);
        }

        @Override
        public String toString() {
            return Integer.toString((Integer) entry.key());
        }
    }

    // Ponteiros para o início e fim do nível mais alto
    private QuadNode topHead;
    private QuadNode topTail;

    // Número de níveis na Skip List
    private int levels;

    // Número de elementos armazenados
    private int size;

    // Gerador de números aleatórios para decisões probabilísticas
    private final Random rand = new Random();

    /**
     * Construtor da Skip List
     * Inicializa com um nível base contendo apenas os sentinelas (head e tail)
     */
    public skipList() {
        // Cria nós sentinela com valores extremos para facilitar a navegação
        QuadNode baseHead = new QuadNode(Integer.MIN_VALUE, null);
        QuadNode baseTail = new QuadNode(Integer.MAX_VALUE, null);

        // Liga os sentinelas horizontalmente
        baseHead.next = baseTail;
        baseTail.prev = baseHead;

        // Inicializa os ponteiros do topo
        this.topHead = baseHead;
        this.topTail = baseTail;
        this.levels = 1;
        this.size = 0;
    }

    /**
     * Retorna o número de elementos na Skip List
     *
     * @return Tamanho atual
     */
    public int size() {
        return this.size;
    }

    /**
     * Verifica se a Skip List está vazia
     *
     * @return true se vazia, false caso contrário
     */
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * Retorna o número de níveis na Skip List
     *
     * @return Número de níveis
     */
    public int getLevels() {
        return this.levels;
    }

    /**
     * Busca um elemento pela chave
     *
     * @param valor Chave a ser buscada
     * @return Item encontrado ou null se não existir
     */
    public Item findElement(int valor) {
        QuadNode node = this.findElementNode(valor);

        // Verifica se encontrou exatamente o elemento buscado
        if (node.entry != null && (Integer) node.entry.key() == valor) {
            return node.entry;
        }

        // Verifica se o próximo nó tem a chave buscada
        if (node.next != null && node.next.entry != null &&
                (Integer) node.next.entry.key() == valor) {
            return node.next.entry;
        }

        return null;
    }

    /**
     * Encontra o nó onde um elemento está ou deveria estar
     * Implementa o algoritmo de busca da Skip List:
     * 1. Começa no nível mais alto
     * 2. Move horizontalmente enquanto puder
     * 3. Desce um nível e repete
     * 4. Para quando chega ao nível base
     *
     * @param valor Chave a ser buscada
     * @return Nó onde o elemento está ou onde deveria ser inserido
     */
    public QuadNode findElementNode(int valor) {
        QuadNode atual = this.topHead;

        while (true) {
            // Move horizontalmente enquanto o próximo nó tem chave menor ou igual
            while (atual.next != null &&
                    atual.next.entry != null &&
                    (Integer) atual.next.entry.key() <= valor &&
                    (Integer) atual.next.entry.key() != Integer.MAX_VALUE) {
                atual = atual.next;
            }

            // Se há um nível inferior, desce
            if (atual.down != null) {
                atual = atual.down;
            } else {
                // Chegou ao nível base, retorna a posição
                return atual;
            }
        }
    }

    /**
     * Insere um novo elemento na Skip List
     * Algoritmo:
     * 1. Busca a posição de inserção no nível base
     * 2. Insere o elemento
     * 3. "Lança moedas" para decidir em quantos níveis superiores inserir
     * 4. Cria novos níveis se necessário
     *
     * @param key   Chave do elemento
     * @param value Valor do elemento
     */
    public void insertItem(int key, Object value) {
        Item novo = new Item(key, value);

        // Encontra a posição onde inserir no nível base
        QuadNode pos = findElementNode((Integer) novo.key());

        // Verifica se a chave já existe
        if (pos.entry != null && (Integer) pos.entry.key() == key) {
            throw new IllegalArgumentException("Valor " + value + " já existe. Ignorando inserção.");
        }

        if (pos.next != null && pos.next.entry != null &&
                (Integer) pos.next.entry.key() == key) {
            throw new IllegalArgumentException("Valor " + value + " já existe (à direita). Ignorando inserção.");
        }

        // Cria e insere o novo nó no nível base
        QuadNode newNode = new QuadNode(novo);
        newNode.prev = pos;
        newNode.next = pos.next;

        // Atualiza os ponteiros dos nós adjacentes
        if (pos.next != null) {
            pos.next.prev = newNode;
        }
        pos.next = newNode;

        // Referência para o nó do nível inferior (para criar a torre)
        QuadNode lowerNode = newNode;
        int currentLevel = 1;

        // "Lança moedas" para decidir a altura da torre
        // Cada cara (true) adiciona um nível com probabilidade 50%
        while (this.rand.nextBoolean()) {
            currentLevel++;

            // Se precisa criar um novo nível no topo
            if (currentLevel > this.levels) {
                this.levels++;

                // Cria novos sentinelas para o novo nível
                QuadNode newHead = new QuadNode(Integer.MIN_VALUE, null);
                QuadNode newTail = new QuadNode(Integer.MAX_VALUE, null);

                // Liga horizontalmente
                newHead.next = newTail;
                newTail.prev = newHead;

                // Liga verticalmente com o nível anterior
                newHead.down = this.topHead;
                this.topHead.up = newHead;
                newTail.down = this.topTail;
                this.topTail.up = newTail;

                // Atualiza os ponteiros do topo
                this.topHead = newHead;
                this.topTail = newTail;
            }

            // Encontra a posição no nível superior onde inserir
            // Sobe na estrutura até encontrar um nó que tenha ponteiro "up"
            while (pos.up == null && pos != null) {
                pos = pos.prev;
            }

            // Se não encontrou, vai para o head do nível superior
            if (pos == null) {
                pos = this.topHead;
            } else {
                pos = pos.up;
            }

            // Cria e insere o nó no nível superior
            QuadNode elevatedNode = new QuadNode(novo);
            elevatedNode.prev = pos;
            elevatedNode.next = pos.next;

            // Atualiza ponteiros horizontais
            if (pos.next != null) {
                pos.next.prev = elevatedNode;
            }
            pos.next = elevatedNode;

            // Liga verticalmente com o nó do nível inferior
            elevatedNode.down = lowerNode;
            lowerNode.up = elevatedNode;

            // Atualiza referência para continuar construindo a torre
            lowerNode = elevatedNode;
        }

        this.size++;
    }

    /**
     * Remove um elemento da Skip List
     * Algoritmo:
     * 1. Busca o elemento
     * 2. Remove de todos os níveis onde aparece
     * 3. Remove níveis vazios do topo
     *
     * @param key Chave do elemento a ser removido
     * @return Item removido
     */
    public Item removeElement(int key) {
        QuadNode node = this.findElementNode(key);

        // Verifica se o elemento existe
        if (node == null || node.entry == null || (Integer) node.entry.key() != key) {
            if (node != null && node.next != null && node.next.entry != null &&
                    (Integer) node.next.entry.key() == key) {
                node = node.next;
            } else {
                throw new IllegalArgumentException("Chave " + key + " não encontrada");
            }
        }

        Item removido = node.entry;

        // Remove o elemento de todos os níveis onde aparece
        while (node != null) {
            QuadNode left = node.prev;
            QuadNode right = node.next;

            // Atualiza ponteiros horizontais
            if (left != null)
                left.next = right;
            if (right != null)
                right.prev = left;

            // Sobe para o próximo nível
            QuadNode up = node.up;

            // Limpa as referências do nó removido
            node.prev = node.next = node.up = node.down = null;
            node = up;
        }

        // Remove níveis vazios do topo (que só têm sentinelas)
        while (this.levels > 1 && this.topHead.next == this.topTail) {
            this.topHead = this.topHead.down;
            this.topTail = this.topTail.down;

            // Limpa ponteiros "up" dos novos nós do topo
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
     * Verifica se uma chave existe na Skip List
     *
     * @param key Chave a ser verificada
     * @return true se existe, false caso contrário
     */
    public boolean contains(int key) {
        QuadNode node = this.findElementNode(key);

        if (node != null && node.entry != null && (Integer) node.entry.key() == key) {
            return true;
        }

        if (node != null && node.next != null && node.next.entry != null &&
                (Integer) node.next.entry.key() == key) {
            return true;
        }

        return false;
    }

    /**
     * Método para visualizar a estrutura da Skip List
     * Mostra todos os níveis com seus elementos
     */
    public void printLevels() {
        System.out.println("=== ESTRUTURA DA SKIP LIST ===");
        System.out.println("Níveis: " + levels + " | Elementos: " + size);

        QuadNode head = topHead;
        int lvl = levels;

        while (head != null) {
            System.out.print("Nível " + lvl + ": ");
            QuadNode cur = head;

            while (cur != null) {
                if ((Integer) cur.entry.key() == Integer.MIN_VALUE) {
                    System.out.print("HEAD");
                } else if ((Integer) cur.entry.key() == Integer.MAX_VALUE) {
                    System.out.print("TAIL");
                } else {
                    System.out.print(cur.entry.key());
                }

                if (cur.next != null) {
                    System.out.print(" -> ");
                }

                cur = cur.next;
                if (cur != null && cur == head)
                    break; // Evita loop infinito
            }
            System.out.println();
            head = head.down;
            lvl--;
        }
        System.out.println("===============================");
    }

    /**
     * Retorna um iterador para todas as chaves na Skip List
     * Navega apenas pelo nível base (que contém todos os elementos)
     *
     * @return Iterator das chaves em ordem
     */
    public Iterator<Integer> keys() {
        List<Integer> listaChaves = new ArrayList<>();

        // Vai para o nível base (mais baixo)
        QuadNode atual = this.topHead;
        while (atual.down != null) {
            atual = atual.down;
        }

        // Pula o sentinela HEAD e coleta todas as chaves até o TAIL
        atual = atual.next;
        while (atual != null && (Integer) atual.entry.key() != Integer.MAX_VALUE) {
            listaChaves.add((Integer) atual.entry.key());
            atual = atual.next;
        }

        return listaChaves.iterator();
    }

    /**
     * Retorna um iterador para todos os elementos na Skip List
     * Navega apenas pelo nível base (que contém todos os elementos)
     *
     * @return Iterator dos elementos em ordem
     */
    public Iterator<Item> elements() {
        List<Item> listaItens = new ArrayList<>();

        // Vai para o nível base (mais baixo)
        QuadNode atual = this.topHead;
        while (atual.down != null) {
            atual = atual.down;
        }

        // Pula o sentinela HEAD e coleta todos os itens até o TAIL
        atual = atual.next;
        while (atual != null && (Integer) atual.entry.key() != Integer.MAX_VALUE) {
            listaItens.add(atual.entry);
            atual = atual.next;
        }

        return listaItens.iterator();
    }

    /**
     * Retorna informações estatísticas sobre a Skip List
     * Útil para análise de performance e distribuição dos níveis
     */
    public void printStatistics() {
        System.out.println("=== ESTATÍSTICAS DA SKIP LIST ===");
        System.out.println("Elementos: " + size);
        System.out.println("Níveis: " + levels);
        System.out.println("Altura teórica ideal: " + Math.ceil(Math.log(size) / Math.log(2)));

        // Conta elementos por nível
        QuadNode head = topHead;
        int level = levels;
        while (head != null) {
            int count = 0;
            QuadNode cur = head.next; // Pula HEAD
            while (cur != null && (Integer) cur.entry.key() != Integer.MAX_VALUE) {
                count++;
                cur = cur.next;
            }
            System.out.println("Nível " + level + ": " + count + " elementos");
            head = head.down;
            level--;
        }
        System.out.println("==================================");
    }
}
