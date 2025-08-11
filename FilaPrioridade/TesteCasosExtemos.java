import Excesao.*;

public class TesteCasosExtemos {
    public static void main(String[] args) {
        System.out.println("=== Testes de Casos Extremos ===");

        // Teste 1: Operações em heap vazio
        testeHeapVazio();

        // Teste 2: Inserção de muitos elementos (teste de redimensionamento)
        testeRedimensionamento();

        // Teste 3: Elementos com chaves iguais
        testeChavesIguais();

        // Teste 4: HeapSort com arrays especiais
        testeHeapSortEspeciais();
    }

    private static void testeHeapVazio() {
        System.out.println("\n--- Teste: Heap Vazio ---");
        HeapArray heap = new HeapArray(5);

        try {
            heap.min();
            System.out.println("ERRO: Deveria lançar exceção ao buscar min em heap vazio");
        } catch (EFilaPrioridade e) {
            System.out.println("OK: Exceção correta ao buscar min em heap vazio: " + e.getMessage());
        }

        try {
            heap.removeMin();
            System.out.println("ERRO: Deveria lançar exceção ao remover de heap vazio");
        } catch (EFilaPrioridade e) {
            System.out.println("OK: Exceção correta ao remover de heap vazio: " + e.getMessage());
        }
    }

    private static void testeRedimensionamento() {
        System.out.println("\n--- Teste: Redimensionamento ---");
        HeapArray heap = new HeapArray(2); // Capacidade pequena para forçar redimensionamento

        // Inserir mais elementos que a capacidade inicial
        for (int i = 10; i >= 1; i--) {
            heap.insert(i, "valor" + i);
        }

        System.out.println("Inseridos 10 elementos em heap com capacidade inicial 2");
        System.out.println("Tamanho final: " + heap.size());

        // Verificar se a ordem está correta
        try {
            System.out.println("Mínimo: " + heap.min().key());
            for (int i = 1; i <= 5; i++) {
                Item min = heap.removeMin();
                System.out.println("Removido: " + min.key());
            }
        } catch (EFilaPrioridade e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void testeChavesIguais() {
        System.out.println("\n--- Teste: Chaves Iguais ---");
        HeapArray heap = new HeapArray(5);

        heap.insert(5, "primeiro");
        heap.insert(5, "segundo");
        heap.insert(5, "terceiro");
        heap.insert(3, "menor");
        heap.insert(7, "maior");

        try {
            System.out.println("Tamanho: " + heap.size());
            while (!heap.isEmpty()) {
                Item min = heap.removeMin();
                System.out.println("Removido: " + min.key() + " -> " + min.value());
            }
        } catch (EFilaPrioridade e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void testeHeapSortEspeciais() {
        System.out.println("\n--- Teste: HeapSort Casos Especiais ---");

        // Array vazio
        int[] vazio = {};
        HeapSort.sort(vazio);
        System.out.println("Array vazio ordenado: " + java.util.Arrays.toString(vazio));

        // Array com um elemento
        int[] umElemento = { 42 };
        HeapSort.sort(umElemento);
        System.out.println("Um elemento ordenado: " + java.util.Arrays.toString(umElemento));

        // Array já ordenado
        int[] jaOrdenado = { 1, 2, 3, 4, 5 };
        HeapSort.sort(jaOrdenado);
        System.out.println("Já ordenado: " + java.util.Arrays.toString(jaOrdenado));

        // Array inversamente ordenado
        int[] inverso = { 5, 4, 3, 2, 1 };
        HeapSort.sort(inverso);
        System.out.println("Inverso ordenado: " + java.util.Arrays.toString(inverso));

        // Array com elementos repetidos
        int[] repetidos = { 3, 1, 4, 1, 5, 9, 2, 6, 5, 3 };
        HeapSort.sort(repetidos);
        System.out.println("Com repetidos: " + java.util.Arrays.toString(repetidos));
    }
}
