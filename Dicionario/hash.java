import java.util.Iterator;
import java.util.ArrayList;

/**
 * Implementação de uma Hash Table (Tabela de Dispersão) usando endereçamento
 * aberto
 *
 * CONCEITOS FUNDAMENTAIS:
 * - Função de hash: converte chave em índice do array
 * - Tratamento de colisões: quando duas chaves mapeiam para o mesmo índice
 * - Endereçamento aberto: resolve colisões procurando próxima posição vazia
 * - Fator de carga: razão elementos/capacidade (máximo 50% para eficiência)
 *
 * MÉTODOS DE SONDAGEM SUPORTADOS:
 * - Linear Probing: incrementa +1 a cada tentativa
 * - Double Hashing: usa segunda função hash para calcular passo
 */
public class hash implements DictInterface {

    private Item[] array; // Array principal da hash table
    private int capacidade; // Tamanho atual do array (sempre primo)
    private int tamanho; // Número de elementos válidos armazenados
    private boolean is_doubleHash; // Flag: usar double hashing ou linear probing

    /**
     * Construtor da hash table
     *
     * @param capacidade    tamanho inicial desejado
     * @param is_doubleHash true = double hashing, false = linear probing
     *
     *                      IMPORTANTE: Capacidade é ajustada para o próximo número
     *                      primo
     *                      Números primos reduzem clustering e melhoram
     *                      distribuição
     */
    public hash(int capacidade, boolean is_doubleHash) {
        if (this.isPrimo(capacidade)) {
            this.capacidade = capacidade;
        } else {
            this.capacidade = this.proximoPrimo(capacidade);
        }

        this.array = new Item[this.capacidade];
        this.tamanho = 0;
        this.is_doubleHash = is_doubleHash;
    }

    /**
     * Busca um elemento na hash table pela chave
     *
     * @param k chave a ser buscada
     * @return Item encontrado ou null se não existir
     *
     *         ALGORITMO:
     *         1. Calcula posição inicial usando função hash
     *         2. Se posição ocupada por outra chave, aplica sondagem
     *         3. Continua até encontrar a chave ou posição vazia
     *         4. Ignora posições marcadas como "Available" (removidas)
     */
    public Item findElement(int k) {
        int index = this.dispersao(k);

        while (this.array[index] != null && !this.array[index].value().equals("Available")) {
            if ((Integer) this.array[index].key() == k) {
                return this.array[index]; // Encontrou!
            }
            // Aplica sondagem (linear ou double hashing)
            index = (index + pulo(k, this.is_doubleHash)) % this.capacidade;
        }

        return null; // Não encontrou
    }

    /**
     * Insere um novo item na hash table
     *
     * @param k chave do item
     * @param o valor a ser armazenado
     * @throws RuntimeException se chave já existir
     *
     *                          PROCESSO:
     *                          1. Verifica fator de carga (se > 50%, redimensiona)
     *                          2. Verifica se chave já existe (erro se sim)
     *                          3. Encontra posição livre usando sondagem
     *                          4. Insere item e incrementa contador
     */
    public void insertItem(int k, Object o) {
        // Mantém fator de carga ≤ 50% para eficiência
        if (this.tamanho >= this.capacidade / 2) {
            this.reHash();
        }

        Item novo = new Item(k, o);
        Item posicao = this.findElement(k);

        if (posicao != null) {
            throw new RuntimeException("Chave já ocupada");
        }

        int index = this.findElementIndex(k);
        this.array[index] = novo;
        this.tamanho++;
    }

    /**
     * Remove um elemento da hash table
     *
     * @param k chave do elemento a remover
     * @return Item removido
     * @throws RuntimeException se elemento não existir
     *
     *                          REMOÇÃO LAZY: Marca posição como "Available" em vez
     *                          de null
     *                          Isso evita quebrar cadeias de sondagem de outros
     *                          elementos
     */
    public Item removeElement(int k) {
        int index = this.findElementIndex(k);

        if (this.array[index] == null || this.array[index].value().equals("Available")) {
            throw new RuntimeException("Sem elemento para ser removido");
        }

        Item elemento = this.array[index];
        this.array[index] = new Item(k, "Available"); // Remoção lazy

        this.tamanho--;
        return elemento;
    }

    /**
     * Retorna o número de elementos válidos na hash table
     *
     * @return quantidade de elementos (excluindo "Available")
     */
    public int size() {
        return this.tamanho;
    }

    /**
     * Verifica se a hash table está vazia
     *
     * @return true se não há elementos válidos
     */
    public boolean isEmpty() {
        return this.tamanho == 0;
    }

    /**
     * Retorna um iterador para todas as chaves válidas na hash table
     *
     * @return Iterator<Integer> das chaves (ignora "Available")
     */
    public Iterator<Integer> keys() {
        ArrayList<Integer> chaves = new ArrayList<>();
        for (int i = 0; i < this.capacidade; i++) {
            if (this.array[i] != null && !this.array[i].value().equals("Available")) {
                chaves.add((Integer) this.array[i].key());
            }
        }
        return chaves.iterator();
    }

    /**
     * Retorna um iterador para todos os elementos válidos na hash table
     *
     * @return Iterator<Item> dos elementos (ignora "Available")
     */
    public Iterator<Item> elements() {
        ArrayList<Item> elementos = new ArrayList<>();
        for (int i = 0; i < this.capacidade; i++) {
            if (this.array[i] != null && !this.array[i].value().equals("Available")) {
                elementos.add(this.array[i]);
            }
        }
        return elementos.iterator();
    }

    // ========== MÉTODOS AUXILIARES NECESSÁRIOS ==========

    /**
     * Encontra o índice onde um elemento deveria estar ou onde pode ser inserido
     *
     * @param k chave a procurar
     * @return índice da posição (ocupada pela chave ou primeira vazia/Available)
     */
    private int findElementIndex(int k) {
        int index = this.dispersao(k);

        while (this.array[index] != null && !this.array[index].value().equals("Available")) {
            if ((Integer) this.array[index].key() == k) {
                break; // Encontrou a chave
            }
            index = (index + pulo(k, this.is_doubleHash)) % this.capacidade;
        }

        return index;
    }

    /**
     * Redimensiona a hash table quando fator de carga excede 50%
     *
     * PROCESSO:
     * 1. Salva array atual
     * 2. Cria novo array com dobro da capacidade (próximo primo)
     * 3. Re-insere todos elementos válidos (recalcula posições)
     * 4. Elimina fragmentação de "Available"
     */
    private void reHash() {
        Item[] arrayAntigo = this.array;
        int capacidadeAntiga = this.capacidade;

        // Dobra capacidade e ajusta para próximo primo
        int nova_capacidade = this.capacidade * 2;
        if (this.isPrimo(nova_capacidade)) {
            this.capacidade = nova_capacidade;
        } else {
            this.capacidade = this.proximoPrimo(nova_capacidade);
        }

        this.array = new Item[this.capacidade];
        this.tamanho = 0;

        // Re-insere todos elementos válidos
        for (int i = 0; i < capacidadeAntiga; i++) {
            if (arrayAntigo[i] != null && !arrayAntigo[i].value().equals("Available")) {
                int index = this.dispersao((Integer) arrayAntigo[i].key());

                while (this.array[index] != null) {
                    index = (index + 1) % this.capacidade;
                }

                this.array[index] = arrayAntigo[i];
                this.tamanho++;
            }
        }
    }

    /**
     * Função de dispersão principal
     *
     * @param k chave
     * @return posição inicial no array (0 a capacidade-1)
     */
    private int dispersao(int k) {
        return this.codigoHash(k) % this.capacidade;
    }

    /**
     * Calcula tamanho do passo para sondagem
     *
     * @param k             chave
     * @param is_doubleHash usar double hashing?
     * @return tamanho do pulo (1 para linear, calculado para double)
     */
    private int pulo(int k, boolean is_doubleHash) {
        if (is_doubleHash) {
            int compressao = this.codigoHash(k) % this.proximoPrimo(this.capacidade / 2);
            return compressao == 0 ? 1 : compressao; // Evita pulo 0
        } else {
            return 1; // Linear probing
        }
    }

    /**
     * Função hash primária - converte chave em código
     *
     * @param k chave
     * @return código hash (sempre positivo)
     */
    private int codigoHash(int k) {
        return Math.abs(k);
    }

    /**
     * Verifica se um número é primo
     *
     * @param num número a verificar
     * @return true se primo, false caso contrário
     */
    private boolean isPrimo(int num) {
        if (num <= 1)
            return false;
        if (num == 2)
            return true;
        if (num % 2 == 0)
            return false;

        for (int i = 3; i <= Math.sqrt(num); i += 2) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Encontra o próximo número primo maior que o dado
     *
     * @param num número base
     * @return próximo primo
     */
    private int proximoPrimo(int num) {
        int candidato = num + 1;
        while (!isPrimo(candidato)) {
            candidato++;
        }
        return candidato;
    }

    // ========== MÉTODO PRINT ==========

    /**
     * Imprime o estado atual da hash table
     * Mostra todos os slots, elementos válidos e estatísticas
     */
    public void printHashTable() {
        System.out.println("\n=== HASH TABLE ===");
        System.out.println("Capacidade: " + capacidade);
        System.out.println("Elementos: " + tamanho);
        System.out.println("Fator de carga: " + String.format("%.2f", (double) tamanho / capacidade));
        System.out.println("Método: " + (is_doubleHash ? "Double Hashing" : "Linear Probing"));
        System.out.println("\nEstado do array:");

        for (int i = 0; i < capacidade; i++) {
            String status;
            String conteudo = "";

            if (array[i] == null) {
                status = "VAZIO";
            } else if (array[i].value().equals("Available")) {
                status = "REMOVIDO";
                conteudo = " (era chave " + array[i].key() + ")";
            } else {
                status = "OCUPADO";
                conteudo = " | Chave: " + array[i].key() + " | Valor: " + array[i].value();
            }

            System.out.printf("[%2d] %-8s %s\n", i, status, conteudo);
        }

        System.out.println("\nElementos válidos:");
        Iterator<Item> elementos = elements();
        while (elementos.hasNext()) {
            Item item = elementos.next();
            System.out.println("- " + item.key() + " → " + item.value());
        }
        System.out.println("==================\n");
    }
}
