import java.util.Iterator;
import java.util.ArrayList;

/**
 * Implementação de uma Hash Table (Tabela Hash) usando endereçamento aberto
 * com tratamento de colisões por sondagem linear ou double hashing.
 *
 * Uma hash table é uma estrutura de dados que mapeia chaves para valores,
 * permitindo operações de inserção, busca e remoção em tempo médio O(1).
 */
public class hash implements DictInterface {

    // Array que armazena os itens da hash table
    private Item[] array;

    // Capacidade total da hash table (sempre um número primo para melhor
    // distribuição)
    private int capacidade;

    // Número atual de elementos armazenados
    private int tamanho;

    // Flag que indica se deve usar double hashing (true) ou sondagem linear (false)
    private boolean is_doubleHash;

    /**
     * Construtor da hash table
     *
     * @param capacidade    Capacidade inicial desejada
     * @param is_doubleHash Se true, usa double hashing; se false, usa sondagem
     *                      linear
     */
    public hash(int capacidade, boolean is_doubleHash) {
        // Garante que a capacidade seja um número primo para melhor distribuição dos
        // hash codes
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
     * Verifica se um número é primo
     * Números primos são importantes para hash tables pois reduzem colisões
     *
     * @param num Número a ser verificado
     * @return true se o número for primo, false caso contrário
     */
    private boolean isPrimo(int num) {
        if (num <= 1) {
            return false;
        }

        if (num == 2) {
            return true;
        }

        // Números pares (exceto 2) não são primos
        if (num % 2 == 0) {
            return false;
        }

        // Verifica divisibilidade apenas até a raiz quadrada
        // Se houver um divisor maior que sqrt(num), já teria sido encontrado um menor
        for (int i = 3; i <= Math.sqrt(num); i += 2) {
            if (num % i == 0) {
                return false;
            }
        }

        return true;
    }

    /**
     * Encontra o próximo número primo maior que o número dado
     *
     * @param num Número de referência
     * @return O próximo número primo
     */
    private int proximoPrimo(int num) {
        int candidato = num + 1;

        while (!isPrimo(candidato)) {
            candidato++;
        }

        return candidato;
    }

    /**
     * Busca um elemento na hash table pela chave
     * Usa sondagem linear ou double hashing para resolver colisões
     *
     * @param k Chave do elemento a ser buscado
     * @return O item encontrado ou null se não existir
     */
    public Item findElement(int k) {
        int index = this.dispersao(k);

        // Continua procurando até encontrar o elemento, uma posição vazia, ou dar a
        // volta completa
        while (this.array[index] != null && !this.array[index].value().equals("Available")) {
            if ((Integer) this.array[index].key() == k) {
                break;
            }
            // Calcula o próximo índice usando a estratégia de resolução de colisões
            // escolhida
            index = (index + pulo(k, this.is_doubleHash)) % this.capacidade;
        }

        return this.array[index];
    }

    /**
     * Encontra o índice onde um elemento está ou deveria estar
     *
     * @param k Chave do elemento
     * @return Índice do elemento na tabela
     */
    public int findElementIndex(int k) {
        int index = this.dispersao(k);

        while (this.array[index] != null && !this.array[index].value().equals("Available")) {
            if ((Integer) this.array[index].key() == k) {
                break;
            }
            index = (index + pulo(k, this.is_doubleHash)) % this.capacidade;
        }

        return index;
    }

    /**
     * Insere um novo item na hash table
     * Redimensiona a tabela se o fator de carga exceder 50%
     *
     * @param k Chave do item
     * @param o Valor do item
     */
    public void insertItem(int k, Object o) {
        // Redimensiona a tabela se estiver mais de 50% cheia
        // Isso mantém o desempenho das operações próximo de O(1)
        if (this.tamanho >= this.capacidade / 2) {
            this.reHash();
        }

        Item novo = new Item(k, o);
        Item posicao = this.findElement(k);
        int index = this.findElementIndex(k);

        // Verifica se a chave já existe (não permite duplicatas)
        if (posicao != null && !posicao.value().equals("Available")) {
            throw new RuntimeException("Chave já ocupada");
        }

        this.array[index] = novo;
        this.tamanho++;
    }

    /**
     * Redimensiona a hash table quando o fator de carga fica muito alto
     * Dobra a capacidade e recalcula as posições de todos os elementos
     */
    private void reHash() {
        // Salva o array antigo
        Item[] arrayAntigo = this.array;
        int capacidadeAntiga = this.capacidade;

        // Calcula nova capacidade (primo próximo ao dobro da capacidade atual)
        int nova_capacidade = this.capacidade * 2;
        if (this.isPrimo(nova_capacidade)) {
            this.capacidade = nova_capacidade;
        } else {
            this.capacidade = this.proximoPrimo(nova_capacidade);
        }

        // Cria novo array com a nova capacidade
        this.array = new Item[this.capacidade];
        this.tamanho = 0;

        // Reinsere todos os elementos válidos do array antigo no novo array
        for (int i = 0; i < capacidadeAntiga; i++) {
            if (arrayAntigo[i] != null && !arrayAntigo[i].value().equals("Available")) {
                // Usa sondagem linear para evitar problemas durante o rehashing
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
     * Função de dispersão que converte uma chave em um índice da tabela
     * Combina código hash com mapa de compressão
     *
     * @param k Chave a ser dispersada
     * @return Índice na tabela hash
     */
    private int dispersao(int k) {
        int hash = this.codigoHash(k);
        int compressao = this.mapaCompressao(hash);
        return compressao;
    }

    /**
     * Calcula o tamanho do "pulo" para resolução de colisões
     *
     * @param k             Chave
     * @param is_doubleHash Se deve usar double hashing ou sondagem linear
     * @return Tamanho do pulo
     */
    private int pulo(int k, boolean is_doubleHash) {
        int compressao;
        if (is_doubleHash) {
            // Double hashing: usa uma segunda função hash
            int hash = this.segundoCodigoHash(k);
            compressao = this.segundoMapaCompressao(hash);
            // Garante que o pulo nunca seja 0
            if (compressao == 0) {
                compressao = 1;
            }
        } else {
            // Sondagem linear: sempre pula 1 posição
            compressao = 1;
        }
        return compressao;
    }

    /**
     * Primeira função de código hash
     *
     * @param k Chave
     * @return Código hash
     */
    private int codigoHash(int k) {
        return Math.abs(k); // Garante valor positivo
    }

    /**
     * Segunda função de código hash (para double hashing)
     *
     * @param k Chave
     * @return Segundo código hash
     */
    private int segundoCodigoHash(int k) {
        return Math.abs(k);
    }

    /**
     * Primeiro mapa de compressão - converte hash code em índice válido
     *
     * @param hash Código hash
     * @return Índice na tabela
     */
    private int mapaCompressao(int hash) {
        return hash % this.capacidade;
    }

    /**
     * Segundo mapa de compressão (para double hashing)
     * Usa um primo menor que a capacidade para garantir boa distribuição
     *
     * @param hash Código hash
     * @return Valor para o pulo no double hashing
     */
    private int segundoMapaCompressao(int hash) {
        int primeMenor = this.proximoPrimo(this.capacidade / 2);
        return hash % primeMenor;
    }

    /**
     * Remove um elemento da hash table
     * Marca a posição como "Available" para manter a integridade da sondagem
     *
     * @param k Chave do elemento a ser removido
     * @return Item removido
     */
    public Item removeElement(int k) {
        int index = this.findElementIndex(k);

        if (this.array[index] == null || this.array[index].value().equals("Available")) {
            throw new RuntimeException("Sem elemento para ser removido");
        }

        Item elemento = this.array[index];
        // Marca como disponível em vez de null para não quebrar a sequência de sondagem
        this.array[index] = new Item(k, "Available");

        this.tamanho--;
        return elemento;
    }

    /**
     * Retorna o número de elementos na hash table
     *
     * @return Tamanho atual
     */
    public int size() {
        return this.tamanho;
    }

    /**
     * Verifica se a hash table está vazia
     *
     * @return true se vazia, false caso contrário
     */
    public boolean isEmpty() {
        return this.tamanho == 0;
    }

    /**
     * Retorna um iterador para todas as chaves válidas na hash table
     *
     * @return Iterator das chaves
     */
    public Iterator<Integer> keys() {
        ArrayList<Integer> chaves = new ArrayList<>();
        if (this.tamanho != 0) {
            for (int i = 0; i < this.capacidade; i++) {
                if (this.array[i] != null && !this.array[i].value().equals("Available")) {
                    chaves.add((Integer) this.array[i].key());
                }
            }
        }
        return chaves.iterator();
    }

    /**
     * Retorna um iterador para todos os elementos válidos na hash table
     *
     * @return Iterator dos elementos
     */
    public Iterator<Item> elements() {
        ArrayList<Item> elementos = new ArrayList<>();
        if (this.tamanho != 0) {
            for (int i = 0; i < this.capacidade; i++) {
                if (this.array[i] != null && !this.array[i].value().equals("Available")) {
                    elementos.add(this.array[i]);
                }
            }
        }
        return elementos.iterator();
    }

    /**
     * Método auxiliar para visualizar o estado atual da hash table
     * Útil para debugging e compreensão do funcionamento
     */
    public void printTable() {
        System.out.println("=== Estado da Hash Table ===");
        System.out.println("Capacidade: " + this.capacidade);
        System.out.println("Tamanho: " + this.tamanho);
        System.out.println("Fator de carga: " + (double) this.tamanho / this.capacidade);
        System.out.println("Tipo de resolução: " + (this.is_doubleHash ? "Double Hashing" : "Sondagem Linear"));

        for (int i = 0; i < this.capacidade; i++) {
            if (this.array[i] != null) {
                if (this.array[i].value().equals("Available")) {
                    System.out.println("Posição " + i + ": [DISPONÍVEL]");
                } else {
                    System.out.println(
                            "Posição " + i + ": [" + this.array[i].key() + " -> " + this.array[i].value() + "]");
                }
            } else {
                System.out.println("Posição " + i + ": [VAZIO]");
            }
        }
        System.out.println("============================");
    }
}
