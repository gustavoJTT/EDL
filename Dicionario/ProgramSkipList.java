import java.util.Iterator;

/**
 * Programa de demonstração da Skip List
 * Mostra como usar as operações básicas e visualiza a estrutura multi-nível
 */
public class ProgramSkipList {

    public static void main(String[] args) {
        System.out.println("=== DEMONSTRAÇÃO DA SKIP LIST ===\n");

        // Teste básico de operações
        System.out.println("1. TESTANDO OPERAÇÕES BÁSICAS:");
        testarOperacoesBasicas();

        System.out.println("\n" + "=".repeat(60) + "\n");

        // Teste de estrutura e níveis
        System.out.println("2. VISUALIZANDO ESTRUTURA MULTI-NÍVEL:");
        testarEstrutura();

        System.out.println("\n" + "=".repeat(60) + "\n");

        // Teste de performance
        System.out.println("3. TESTE DE PERFORMANCE COM MUITOS ELEMENTOS:");
        testarPerformance();
    }

    /**
     * Testa as operações básicas da Skip List
     */
    public static void testarOperacoesBasicas() {
        skipList lista = new skipList();

        System.out.println("Inserindo elementos: 10, 5, 15, 3, 7, 12, 18");

        // Inserção de elementos
        lista.insertItem(10, "Dez");
        lista.insertItem(5, "Cinco");
        lista.insertItem(15, "Quinze");
        lista.insertItem(3, "Três");
        lista.insertItem(7, "Sete");
        lista.insertItem(12, "Doze");
        lista.insertItem(18, "Dezoito");

        System.out.println("Tamanho após inserções: " + lista.size());
        lista.printLevels();

        // Busca de elementos
        System.out.println("\nTestando busca:");
        Item item = lista.findElement(7);
        if (item != null) {
            System.out.println("Encontrado: " + item.key() + " -> " + item.value());
        }

        item = lista.findElement(99);
        if (item == null) {
            System.out.println("Chave 99 não encontrada (como esperado)");
        }

        // Verificação de existência
        System.out.println("\nVerificando existência:");
        System.out.println("Contém 12? " + lista.contains(12));
        System.out.println("Contém 20? " + lista.contains(20));

        // Iteração sobre chaves (deve estar em ordem)
        System.out.println("\nChaves em ordem:");
        Iterator<Integer> chaves = lista.keys();
        while (chaves.hasNext()) {
            System.out.print(chaves.next() + " ");
        }
        System.out.println();

        // Remoção de elemento
        System.out.println("\nRemovendo elemento com chave 15:");
        Item removido = lista.removeElement(15);
        System.out.println("Removido: " + removido.key() + " -> " + removido.value());
        System.out.println("Tamanho após remoção: " + lista.size());

        lista.printLevels();

        // Teste de chave já ocupada
        System.out.println("\nTentando inserir chave duplicada (deve gerar erro):");
        try {
            lista.insertItem(10, "Dez Duplicado");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro capturado: " + e.getMessage());
        }
    }

    /**
     * Testa e visualiza a estrutura multi-nível da Skip List
     */
    public static void testarEstrutura() {
        skipList lista = new skipList();

        System.out.println("Inserindo elementos sequenciais para observar a estrutura probabilística:");

        // Insere elementos e mostra como a estrutura evolui
        int[] elementos = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };

        for (int elemento : elementos) {
            lista.insertItem(elemento, "Valor " + elemento);
            System.out.println("\nApós inserir " + elemento + ":");
            lista.printLevels();
        }

        // Mostra estatísticas finais
        System.out.println("\nEstatísticas finais:");
        lista.printStatistics();

        // Demonstra a eficiência da busca
        System.out.println("\nDemonstrando busca eficiente:");
        System.out.println("Buscando elemento 8...");
        Item resultado = lista.findElement(8);
        if (resultado != null) {
            System.out.println("Encontrado: " + resultado.key() + " -> " + resultado.value());
            System.out.println("(A busca 'pulou' vários elementos usando os níveis superiores)");
        }

        // Remove alguns elementos para mostrar como a estrutura se adapta
        System.out.println("\nRemovendo elementos 3, 5, 7:");
        lista.removeElement(3);
        lista.removeElement(5);
        lista.removeElement(7);

        System.out.println("Estrutura após remoções:");
        lista.printLevels();
        lista.printStatistics();
    }

    /**
     * Testa performance com muitos elementos
     */
    public static void testarPerformance() {
        skipList lista = new skipList();

        int numElementos = 20;
        System.out.println("Inserindo " + numElementos + " elementos aleatórios...");

        long inicio = System.nanoTime();

        // Insere elementos em ordem não sequencial para simular uso real
        int[] elementos = { 50, 25, 75, 12, 37, 62, 87, 6, 18, 31,
                43, 56, 68, 81, 93, 3, 9, 15, 21, 27 };

        for (int i = 0; i < Math.min(numElementos, elementos.length); i++) {
            lista.insertItem(elementos[i], "Elemento " + elementos[i]);
        }

        long tempoInsercao = System.nanoTime() - inicio;

        System.out.println("Tempo de inserção: " + tempoInsercao / 1000000.0 + " ms");
        System.out.println("Estrutura final:");
        lista.printLevels();

        // Teste de busca
        inicio = System.nanoTime();

        System.out.println("\nTestando buscas:");
        int[] chavesBusca = { 3, 25, 50, 75, 93, 100 }; // Inclui uma chave inexistente

        for (int chave : chavesBusca) {
            Item resultado = lista.findElement(chave);
            if (resultado != null) {
                System.out.println("Encontrado: " + chave);
            } else {
                System.out.println("Não encontrado: " + chave);
            }
        }

        long tempoBusca = System.nanoTime() - inicio;
        System.out.println("Tempo total de " + chavesBusca.length + " buscas: " +
                tempoBusca / 1000000.0 + " ms");

        // Estatísticas finais
        System.out.println("\nEstatísticas finais:");
        lista.printStatistics();

        // Demonstra que os elementos estão em ordem
        System.out.println("\nTodos os elementos em ordem:");
        Iterator<Integer> chaves = lista.keys();
        while (chaves.hasNext()) {
            System.out.print(chaves.next() + " ");
        }
        System.out.println();
    }
}
