import Excesao.*;

public class Program {
    public static void main(String[] args) {
        // Teste da Fila de Prioridade com HeapArray
        System.out.println("=== Teste da Fila de Prioridade (HeapArray) ===");
        testaHeapArray();

        // Teste da Fila de Prioridade com HeapBT
        System.out.println("\n=== Teste da Fila de Prioridade (HeapBT) ===");
        testaHeapBT();

        // Teste do HeapSort
        System.out.println("\n=== Teste do HeapSort ===");
        testaHeapSort();
    }

    private static void testaHeapArray() {
        HeapArray heap = new HeapArray(10);

        // Teste com fila vazia
        System.out.println("Heap vazio: " + heap.isEmpty());
        System.out.println("Tamanho: " + heap.size());

        // Inserindo elementos
        heap.insert(5, "cinco");
        heap.insert(2, "dois");
        heap.insert(8, "oito");
        heap.insert(1, "um");
        heap.insert(9, "nove");
        heap.insert(3, "três");

        System.out.println("\nApós inserções:");
        System.out.println("Tamanho: " + heap.size());
        System.out.println("Heap vazio: " + heap.isEmpty());

        try {
            System.out.println("Mínimo: " + heap.min().key() + " -> " + heap.min().value());

            // Removendo elementos em ordem de prioridade
            System.out.println("\nRemoção em ordem de prioridade:");
            while (!heap.isEmpty()) {
                Item min = heap.removeMin();
                System.out.println("Removido: " + min.key() + " -> " + min.value());
            }

        } catch (EFilaPrioridade e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void testaHeapBT() {
        HeapBT heap = new HeapBT();

        // Teste com fila vazia
        System.out.println("Heap vazio: " + heap.isEmpty());
        System.out.println("Tamanho: " + heap.size());

        // Inserindo elementos
        heap.insert(5, "cinco");
        heap.insert(2, "dois");
        heap.insert(8, "oito");
        heap.insert(1, "um");
        heap.insert(9, "nove");
        heap.insert(3, "três");

        System.out.println("\nApós inserções:");
        System.out.println("Tamanho: " + heap.size());
        System.out.println("Heap vazio: " + heap.isEmpty());
        System.out.println("Heap é válido: " + heap.isValidHeap());

        System.out.println("\nEstrutura da árvore:");
        heap.printTree();

        try {
            System.out.println("\nMínimo: " + heap.min().key() + " -> " + heap.min().value());

            // Removendo elementos em ordem de prioridade
            System.out.println("\nRemoção em ordem de prioridade:");
            while (!heap.isEmpty()) {
                Item min = heap.removeMin();
                System.out.println("Removido: " + min.key() + " -> " + min.value());
                if (!heap.isEmpty()) {
                    System.out.println("Heap ainda válido: " + heap.isValidHeap());
                }
            }

        } catch (EFilaPrioridade e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void testaHeapSort() {
        int[] array = { 64, 34, 25, 12, 22, 11, 90, 5 };

        System.out.print("Array original: ");
        printArray(array);

        HeapSort.sort(array);

        System.out.print("Array ordenado: ");
        printArray(array);
    }

    private static void printArray(int[] array) {
        for (int value : array) {
            System.out.print(value + " ");
        }
        System.out.println();
    }
}
