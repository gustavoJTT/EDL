package Deque;

public class Program {
    private static DequeArray array;

    public static void main(String[] args) {
        array = new DequeArray(5, 0);

        array.enqueueEnd(1);
        array.enqueueEnd(2);
        array.enqueueEnd(3);
        array.enqueueBeginning(9);

        array.printDeque(); // Corrigido para chamar o método correto
    }
}