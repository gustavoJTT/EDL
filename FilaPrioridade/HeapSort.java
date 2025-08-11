/**
 * Implementação do algoritmo HeapSort
 * Usa o HeapArray para ordenar um array de inteiros
 *
 * COMPLEXIDADE: O(n log n)
 * - Inserção: n elementos * O(log n) = O(n log n)
 * - Remoção: n elementos * O(log n) = O(n log n)
 */
public class HeapSort {

    /**
     * Ordena um array usando HeapSort
     * 
     * @param array array a ser ordenado (modificado in-place)
     *
     *              ALGORITMO:
     *              1. Insere todos elementos no heap (min-heap)
     *              2. Remove elementos um por um (sempre o menor)
     *              3. Resultado: array ordenado crescente
     *
     *              PONTO IMPORTANTE: Min-heap produz ordem crescente
     *              Para ordem decrescente, usaria max-heap
     */
    public static void sort(int[] array) {
        // Verifica casos especiais
        if (array == null || array.length <= 1) {
            return;
        }

        // Cria heap com capacidade para todos elementos
        HeapArray heap = new HeapArray(array.length);

        // FASE 1: Insere todos os elementos no heap
        // Cada inserção mantém a propriedade do heap automaticamente
        for (int elem : array) {
            heap.insert(elem, elem); // chave = valor = elemento
        }

        // FASE 2: Remove elementos em ordem (menor primeiro)
        // Como é min-heap, removeMin() sempre retorna o menor elemento restante
        try {
            for (int i = 0; i < array.length; i++) {
                array[i] = (Integer) heap.removeMin().key();
            }
        } catch (Exception e) {
            System.err.println("Erro durante ordenação: " + e.getMessage());
        }
    }
}
