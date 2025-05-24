package Fila;
import java.util.Scanner;

public class Program
{
    private static QueueArray queue;

    public static void main(String[] args)
    {
        boolean menuState = true;
        while (menuState)
        {
            System.out.println("");
            System.out.println("Selecione a questão");
            System.out.println("1 -> Questão 1");
            System.out.println("2 -> Questão 2");
            System.out.println("4 -> Questão 4");
            System.out.println("0 -> sair");

            Scanner questScanner = new Scanner(System.in);
            System.out.print("Opção: ");
            int quest = questScanner.nextInt();

            switch (quest)
            {
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

    public void Quest1()
    {
        queue = new QueueArray(10, 10);

        queue.enqueue(5);
        queue.enqueue(3);
        queue.dequeue();
        queue.enqueue(2);
        queue.enqueue(8);
        queue.dequeue();
        queue.dequeue();
        queue.enqueue(9);
        queue.enqueue(1);
        queue.dequeue();
        queue.enqueue(7);
        queue.enqueue(6);
        // queue.dequeue();
        // queue.dequeue();
        queue.enqueue(4);
        queue.enqueue(7);
        // queue.dequeue();
        System.out.println("-----------------------------------");
        queue.print();
    }

    public void Quest2()
    {
        // Teste com incremento fixo
        QueueArray queueIncrement = new QueueArray(5, 5);
        queueIncrement.enqueue(1);
        queueIncrement.enqueue(2);
        queueIncrement.enqueue(3);
        System.out.println("Primeiro elemento (incremento): " + queueIncrement.first()); // 1
        System.out.println("Tamanho (incremento): " + queueIncrement.size()); // 3

        // Teste com duplicação
        QueueArray queueDoubling = new QueueArray(5, 0);
        queueDoubling.enqueue(10);
        queueDoubling.enqueue(20);
        queueDoubling.enqueue(30);
        System.out.println("Primeiro elemento (duplicação): " + queueDoubling.first()); // 10
        System.out.println("Tamanho (duplicação): " + queueDoubling.size()); // 3
    }

    public void Quest4()
    {
        queue = new QueueArray(10, 10);

        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        queue.enqueue(4);
        queue.enqueue(5);
        queue.enqueue(6);
        queue.enqueue(7);
        queue.enqueue(8);
        queue.enqueue(9);

        System.out.println("-----------------------------");

        queue.print();

        System.out.println("Invertida abaixo:");
        queue.reverse();
        queue.print();
    }
}