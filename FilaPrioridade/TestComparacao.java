import Excesao.*;

public class TestComparacao {
    public static void main(String[] args) {
        System.out.println("=== Comparação entre HeapArray e HeapBT ===");

        // Teste básico com ambas implementações
        testeBasico();

        // Teste de performance
        testePerformance();

        // Teste visual da estrutura (apenas HeapBT)
        testeVisual();

        // Teste de validação do heap
        testeValidacao();
    }

    private static void testeBasico() {
        System.out.println("\n--- Teste Básico ---");

        // Testa HeapArray
        System.out.println("HeapArray:");
        HeapArray heapArray = new HeapArray(10);
        testaImplementacao(heapArray);

        // Testa HeapBT
        System.out.println("\nHeapBT:");
        HeapBT heapBT = new HeapBT();
        testaImplementacao(heapBT);
    }

    private static void testaImplementacao(FPInterface heap) {
        // Inserindo elementos
        int[] valores = { 5, 2, 8, 1, 9, 3, 7, 4, 6 };

        for (int valor : valores) {
            heap.insert(valor, "valor" + valor);
        }

        System.out.println("Tamanho após inserções: " + heap.size());

        try {
            System.out.println("Mínimo: " + heap.min().key());

            // Remove alguns elementos
            System.out.print("Removendo em ordem: ");
            for (int i = 0; i < 5; i++) {
                Item min = heap.removeMin();
                System.out.print(min.key() + " ");
            }
            System.out.println();
            System.out.println("Tamanho após remoções: " + heap.size());

        } catch (EFilaPrioridade e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void testePerformance() {
        System.out.println("\n--- Teste de Performance ---");

        int numElementos = 1000;

        // Teste HeapArray
        long inicio = System.nanoTime();
        HeapArray heapArray = new HeapArray(numElementos);

        for (int i = numElementos; i > 0; i--) {
            heapArray.insert(i, "valor" + i);
        }

        try {
            while (!heapArray.isEmpty()) {
                heapArray.removeMin();
            }
        } catch (EFilaPrioridade e) {
            System.out.println("Erro no HeapArray: " + e.getMessage());
        }

        long fimArray = System.nanoTime();
        double tempoArray = (fimArray - inicio) / 1_000_000.0; // em milissegundos

        // Teste HeapBT
        inicio = System.nanoTime();
        HeapBT heapBT = new HeapBT();

        for (int i = numElementos; i > 0; i--) {
            heapBT.insert(i, "valor" + i);
        }

        try {
            while (!heapBT.isEmpty()) {
                heapBT.removeMin();
            }
        } catch (EFilaPrioridade e) {
            System.out.println("Erro no HeapBT: " + e.getMessage());
        }

        long fimBT = System.nanoTime();
        double tempoBT = (fimBT - inicio) / 1_000_000.0; // em milissegundos

        System.out.printf("HeapArray: %.2f ms\n", tempoArray);
        System.out.printf("HeapBT: %.2f ms\n", tempoBT);
        System.out.printf("Diferença: %.2fx\n", tempoBT / tempoArray);
    }

    private static void testeVisual() {
        System.out.println("\n--- Teste Visual (apenas HeapBT) ---");

        HeapBT heap = new HeapBT();

        // Inserindo elementos em ordem específica para visualizar
        int[] valores = { 4, 2, 6, 1, 3, 5, 7 };

        for (int valor : valores) {
            heap.insert(valor, "val" + valor);
            System.out.println("\nApós inserir " + valor + ":");
            heap.printTree();
        }

        // Remove alguns elementos
        try {
            System.out.println("\nApós remover o mínimo:");
            heap.removeMin();
            heap.printTree();

            System.out.println("\nApós remover outro mínimo:");
            heap.removeMin();
            heap.printTree();
        } catch (EFilaPrioridade e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void testeValidacao() {
        System.out.println("\n--- Teste de Validação ---");

        HeapBT heap = new HeapBT();

        // Testa com vários elementos
        int[] valores = { 10, 5, 15, 2, 7, 12, 20, 1, 3, 6, 8 };

        for (int valor : valores) {
            heap.insert(valor, "valor" + valor);
        }

        System.out.println("Heap é válido após inserções: " + heap.isValidHeap());

        try {
            // Remove alguns elementos e verifica se continua válido
            for (int i = 0; i < 5; i++) {
                heap.removeMin();
                System.out.println("Heap é válido após remoção " + (i + 1) + ": " + heap.isValidHeap());
            }
        } catch (EFilaPrioridade e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
