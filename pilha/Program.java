package pilha;
import java.util.Scanner;

public class Program
{
    private static StackArray stack;

    public static void main(String[] args) {
        boolean menuState = true;
        while (menuState) {
            System.out.println("");
            System.out.println("Selecione a questão");
            System.out.println("1 -> Questão 1");
            System.out.println("2 -> Questão 2");
            System.out.println("4 -> Questão 4");
            System.out.println("0 -> sair");

            Scanner questScanner = new Scanner(System.in);
            System.out.print("Opção: ");
            int quest = questScanner.nextInt();

            switch (quest) {
                case 1:
                    new Program().Quest1();
                    break;

                case 2:
                    new Program().Quest2();
                    break;

                case 4:
                    new Program().Quest4();
                    break;

                case 0:
                    questScanner.close();
                    menuState = false;
                    break;

                default:
                    System.out.println("Questão inválida");
                    continue;
            }
        }
    }

    public void Quest1() {
        stack = new StackArray(10, 0);

        stack.push(5);
        stack.push(3);
        stack.pop();
        stack.push(8);
        stack.pop();
        stack.pop();
        stack.push(9);
        stack.push(1);
        stack.pop();
        stack.push(7);
        stack.push(6);
        // stack.pop();
        // stack.pop();
        stack.push(4);
        // stack.pop();
        // stack.pop();

        System.out.println("-------------------------------------");
        stack.printStack();
    }

    public void Quest2() {
        int[] numberElements = { 10, 100, 1000, 0, 10000, 100000, 1000000 };

        while (true) {
            System.out.println("");
            System.out.println("Selecione uma opção;");
            System.out.println("1 -> 10 elementos");
            System.out.println("2 -> 100 elementos");
            System.out.println("3 -> 1000 elementos");
            System.out.println("4 -> 10000 elementos");
            System.out.println("5 -> 100000 elementos");
            System.out.println("6 -> 1000000 elementos");
            System.out.println("0 -> sair");

            try (Scanner selectionScanner = new Scanner(System.in))
            {
                System.out.print("Opção: ");
                int operation = selectionScanner.nextInt();

                if (operation == 0) {
                    break;
                }

                if (operation < 1 || operation > 6) {
                    System.out.println("Opção inválida!");
                    continue;
                }

                int value = numberElements[operation - 1];
                System.out.println("\nTestando com " + value + " elementos:");
                testTime(value);
            }
        }
    }

    public void testTime(int numberElements) {
        System.out.println("");
        // 10 growth
        long startTime = System.nanoTime();
        for (int i = 0; i < numberElements; i++) {
            stack = new StackArray(numberElements, 10);
            stack.push(i);
        }
        long finalTime = System.nanoTime() - startTime;
        System.out.println(String.format("elements and growth 10, time = %d", finalTime));

        // 100 growth
        long startTime100 = System.nanoTime();
        for (int i = 0; i < numberElements; i++) {
            stack = new StackArray(numberElements, 100);
            stack.push(i);
        }
        long finalTime100 = System.nanoTime() - startTime100;
        System.out.println(String.format("elements 10 growth 100, time = %d", finalTime100));

        // 1000 growth
        long startTime1000 = System.nanoTime();
        for (int i = 0; i < numberElements; i++) {
            stack = new StackArray(numberElements, 1000);
            stack.push(i);
        }
        long finalTime1000 = System.nanoTime() - startTime1000;
        System.out.println(String.format("elements 10 growth 1000, time = %d", finalTime1000));

        // double growth
        long startTimeDouble = System.nanoTime();
        for (int i = 0; i < numberElements; i++) {
            stack = new StackArray(numberElements, 0);
            stack.push(i);
        }
        long finalTimeDouble = System.nanoTime() - startTimeDouble;
        System.out.println(String.format("elements 10 growth double, time = %d", finalTimeDouble));

    }

    public void Quest4() {
        stack = new StackArray(10, 0);

        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        System.out.println("-------------------------------------");
        stack.printStack();
        System.out.println("-------------------------------------");
        stack.empty();
    }

    public void Quest5() {

    }
}