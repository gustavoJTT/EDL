public class HeapSort {
    public static void sort(int[] array) {
        HeapArray heap = new HeapArray(array.length);

        for (int elem : array) {
            heap.insert(elem, elem);
        }

        int n = heap.size();
        for (int i = 0; i < n; i++) {
            array[i] = (int) heap.removeMin().key();
        }
    }
}
