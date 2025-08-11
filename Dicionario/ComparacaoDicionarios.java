import java.util.Iterator;
import java.util.Random;

/**
 * Programa de comparação entre Hash Table e Skip List
 * Demonstra as diferenças de performance e características de cada estrutura
 */
public class ComparacaoDicionarios {

    public static void main(String[] args) {
        System.out.println("=== COMPARAÇÃO: HASH TABLE vs SKIP LIST ===\n");

        // Comparação básica
        System.out.println("1. COMPARAÇÃO BÁSICA DE OPERAÇÕES:");
        compararOperacoesBasicas();

        System.out.println("\n" + "=".repeat(70) + "\n");

        // Comparação de performance
        System.out.println("2. COMPARAÇÃO DE PERFORMANCE:");
        compararPerformance();

        System.out.println("\n" + "=".repeat(70) + "\n");

        // Comparação de características
        System.out.println("3. ANÁLISE DE CARACTERÍSTICAS:");
        analisarCaracteristicas();
    }

    /**
     * Compara operações básicas entre as duas estruturas
     */
    public static void compararOperacoesBasicas() {
        System.out.println("Criando ambas as estruturas e inserindo os mesmos elementos...");

        // Cria ambas as estruturas
        hash hashTable = new hash(7, false); // Sondagem linear
        skipList skipList = new skipList();

        // Elementos de teste
        int[] elementos = { 10, 5, 15, 3, 7, 12, 18, 1, 9, 14 };

        // Inserção em ambas
        for (int elemento : elementos) {
            hashTable.insertItem(elemento, "Valor " + elemento);
            skipList.insertItem(elemento, "Valor " + elemento);
        }

        System.out.println("\n--- HASH TABLE ---");
        System.out.println("Tamanho: " + hashTable.size());
        hashTable.printTable();

        System.out.println("\n--- SKIP LIST ---");
        System.out.println("Tamanho: " + skipList.size());
        skipList.printLevels();

        // Teste de busca
        System.out.println("\nTestando busca do elemento 7:");

        Item hashResult = hashTable.findElement(7);
        Item skipResult = skipList.findElement(7);

        System.out.println("Hash Table encontrou: " +
                (hashResult != null ? hashResult.value() : "null"));
        System.out.println("Skip List encontrou: " +
                (skipResult != null ? skipResult.value() : "null"));

        // Teste de ordem (Skip List mantém ordem, Hash Table não)
        System.out.println("\nOrdem dos elementos:");

        System.out.print("Hash Table: ");
        Iterator<Integer> hashKeys = hashTable.keys();
        while (hashKeys.hasNext()) {
            System.out.print(hashKeys.next() + " ");
        }
        System.out.println();

        System.out.print("Skip List:  ");
        Iterator<Integer> skipKeys = skipList.keys();
        while (skipKeys.hasNext()) {
            System.out.print(skipKeys.next() + " ");
        }
        System.out.println(" (sempre ordenada!)");
    }

    /**
     * Compara performance entre as duas estruturas
     */
    public static void compararPerformance() {
        int numElementos = 1000;
        Random rand = new Random(42); // Seed fixo para resultados reproduzíveis

        System.out.println("Testando performance com " + numElementos + " elementos...");

        // Gera elementos aleatórios
        int[] elementos = new int[numElementos];
        for (int i = 0; i < numElementos; i++) {
            elementos[i] = rand.nextInt(10000);
        }

        // Teste Hash Table
        hash hashTable = new hash(101, true); // Double hashing
        long inicio = System.nanoTime();

        for (int elemento : elementos) {
            try {
                hashTable.insertItem(elemento, "Valor " + elemento);
            } catch (RuntimeException e) {
                // Ignora duplicatas
            }
        }

        long tempoHashInsercao = System.nanoTime() - inicio;

        // Teste Skip List
        skipList skipList = new skipList();
        inicio = System.nanoTime();

        for (int elemento : elementos) {
            try {
                skipList.insertItem(elemento, "Valor " + elemento);
            } catch (IllegalArgumentException e) {
                // Ignora duplicatas
            }
        }

        long tempoSkipInsercao = System.nanoTime() - inicio;

        System.out.println("\n--- RESULTADOS DE INSERÇÃO ---");
        System.out.println("Hash Table: " + tempoHashInsercao / 1000000.0 + " ms");
        System.out.println("Skip List:  " + tempoSkipInsercao / 1000000.0 + " ms");

        // Teste de busca
        int[] chavesBusca = new int[100];
        for (int i = 0; i < 100; i++) {
            chavesBusca[i] = rand.nextInt(10000);
        }

        // Busca Hash Table
        inicio = System.nanoTime();
        int encontradosHash = 0;
        for (int chave : chavesBusca) {
            if (hashTable.findElement(chave) != null) {
                encontradosHash++;
            }
        }
        long tempoHashBusca = System.nanoTime() - inicio;

        // Busca Skip List
        inicio = System.nanoTime();
        int encontradosSkip = 0;
        for (int chave : chavesBusca) {
            if (skipList.findElement(chave) != null) {
                encontradosSkip++;
            }
        }
        long tempoSkipBusca = System.nanoTime() - inicio;

        System.out.println("\n--- RESULTADOS DE BUSCA (100 buscas) ---");
        System.out.println("Hash Table: " + tempoHashBusca / 1000000.0 + " ms (" +
                encontradosHash + " encontrados)");
        System.out.println("Skip List:  " + tempoSkipBusca / 1000000.0 + " ms (" +
                encontradosSkip + " encontrados)");

        System.out.println("\n--- INFORMAÇÕES DAS ESTRUTURAS ---");
        System.out.println("Hash Table - Elementos: " + hashTable.size());
        System.out.println("Skip List  - Elementos: " + skipList.size() +
                " em " + skipList.getLevels() + " níveis");
    }

    /**
     * Analisa as características únicas de cada estrutura
     */
    public static void analisarCaracteristicas() {
        System.out.println("=== CARACTERÍSTICAS DAS ESTRUTURAS ===\n");

        System.out.println("HASH TABLE:");
        System.out.println("✓ Acesso O(1) médio, O(n) pior caso");
        System.out.println("✓ Uso eficiente de memória");
        System.out.println("✓ Excelente para buscas rápidas");
        System.out.println("✗ Não mantém ordem dos elementos");
        System.out.println("✗ Performance depende da função hash");
        System.out.println("✗ Pode haver clustering com má função hash");

        System.out.println("\nSKIP LIST:");
        System.out.println("✓ Acesso O(log n) probabilístico");
        System.out.println("✓ Mantém elementos sempre ordenados");
        System.out.println("✓ Estrutura auto-balanceante");
        System.out.println("✓ Implementação mais simples que árvores balanceadas");
        System.out.println("✗ Maior uso de memória (múltiplos ponteiros)");
        System.out.println("✗ Performance dependente de aleatoriedade");

        System.out.println("\n=== QUANDO USAR CADA UMA ===\n");

        System.out.println("Use HASH TABLE quando:");
        System.out.println("• Precisar de acesso muito rápido");
        System.out.println("• Não precisar de elementos ordenados");
        System.out.println("• Memória for limitada");
        System.out.println("• Tiver uma boa função hash");

        System.out.println("\nUse SKIP LIST quando:");
        System.out.println("• Precisar de elementos ordenados");
        System.out.println("• Quiser simplicidade vs árvores balanceadas");
        System.out.println("• Precisar de range queries");
        System.out.println("• Performance O(log n) for aceitável");

        // Demonstração prática da diferença de ordem
        System.out.println("\n=== DEMONSTRAÇÃO PRÁTICA ===");

        hash hash = new hash(7, false);
        skipList skip = new skipList();

        int[] dados = { 50, 30, 70, 20, 40, 60, 80 };

        for (int dado : dados) {
            hash.insertItem(dado, "V" + dado);
            skip.insertItem(dado, "V" + dado);
        }

        System.out.println("\nElementos inseridos: 50, 30, 70, 20, 40, 60, 80");

        System.out.print("Hash Table (sem ordem): ");
        Iterator<Integer> hashIt = hash.keys();
        while (hashIt.hasNext()) {
            System.out.print(hashIt.next() + " ");
        }
        System.out.println();

        System.out.print("Skip List (ordenada):   ");
        Iterator<Integer> skipIt = skip.keys();
        while (skipIt.hasNext()) {
            System.out.print(skipIt.next() + " ");
        }
        System.out.println();
    }
}
