package Deque;

import Excesao.EEmptyDeque;

public class Program {
    private static DequeArray array;

    public static void main(String[] args) {
        array = new DequeArray(5, 0);

        array.enqueueEnd(1);
        array.enqueueEnd(2);
        array.enqueueEnd(3);
        array.enqueueBeginning(9);

        array.printDeque();

        // Test the corrected method name
        try {
            System.out.println("Removing from beginning: " + array.dequeueBeginnig());
            array.printDeque();
        } catch (EEmptyDeque e) {
            System.out.println(e.getMessage());
        }
    }
}