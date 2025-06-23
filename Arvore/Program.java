import java.util.Iterator;
import java.util.Scanner;
import Node.Node;
import Excesao.*;

public class Program {
    private static GenericT tree;

    public static void main(String[] args) {
        boolean menuState = true;
        while (menuState) {
            System.out.println("");
            System.out.println("Selecione a questão");
            System.out.println("1 -> Questão 1 - Operações básicas em árvore");
            System.out.println("2 -> Questão 2 - Percurso e navegação na árvore");
            System.out.println("4 -> Questão 4 - Operações avançadas em árvore");
            System.out.println("0 -> sair");

            Scanner questScanner = new Scanner(System.in);
            System.out.print("Opção: ");
            int quest = questScanner.nextInt();

            switch (quest) {
                case 1:
                    new Program().quest1();
                    break;

                case 2:
                    new Program().quest2();
                    break;

                case 4:
                    new Program().quest4();
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

    public void quest1() {
        System.out.println("\n--- Questão 1: Operações básicas em árvore ---");
        try {
            // Criando uma árvore com raiz
            tree = new GenericT(10);
            System.out.println("Árvore criada com raiz: 10");
            System.out.println("Tamanho da árvore: " + tree.size());

            // Obtendo a raiz
            Node root = tree.root();
            System.out.println("Elemento da raiz: " + root.getElement());

            // Adicionando filhos à raiz
            tree.addChild(root, 20);
            tree.addChild(root, 30);
            tree.addChild(root, 40);
            System.out.println("Adicionados 3 filhos à raiz: 20, 30, 40");
            System.out.println("Tamanho atualizado da árvore: " + tree.size());

            // Verificando se a raiz é interna
            System.out.println("A raiz é um nó interno? " + tree.isInternal(root));

            // Listando os filhos da raiz
            System.out.println("Filhos da raiz:");
            Iterator<Node> children = tree.children(root);
            while (children.hasNext()) {
                Node child = children.next();
                System.out.println("- " + child.getElement());
            }

            // Adicionando um neto
            Node firstChild = root.getChild().get(0);
            tree.addChild(firstChild, 25);
            System.out.println("Adicionado filho 25 ao nó " + firstChild.getElement());

            // Substituindo um elemento
            Object oldValue = tree.replace(firstChild, 21);
            System.out.println("Substituído o valor " + oldValue + " por 21");

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public void quest2() {
        System.out.println("\n--- Questão 2: Percurso e navegação na árvore ---");
        try {
            // Criando uma árvore hierárquica para testes
            tree = new GenericT("A");
            Node root = tree.root();

            tree.addChild(root, "B");
            tree.addChild(root, "C");
            tree.addChild(root, "D");

            Node nodeB = root.getChild().get(0);
            Node nodeC = root.getChild().get(1);
            Node nodeD = root.getChild().get(2);

            tree.addChild(nodeB, "E");
            tree.addChild(nodeB, "F");
            tree.addChild(nodeC, "G");
            tree.addChild(nodeD, "H");
            tree.addChild(nodeD, "I");

            Node nodeE = nodeB.getChild().get(0);
            tree.addChild(nodeE, "J");

            System.out.println("Árvore criada com estrutura hierárquica.");

            // Percorrendo todos os nós da árvore
            System.out.println("\nTodos os nós da árvore:");
            Iterator<Node> nodes = tree.nodes();
            while (nodes.hasNext()) {
                System.out.print(nodes.next().getElement() + " ");
            }
            System.out.println();

            // Percorrendo todos os elementos da árvore
            System.out.println("\nTodos os elementos da árvore:");
            Iterator<Object> elements = tree.elements();
            while (elements.hasNext()) {
                System.out.print(elements.next() + " ");
            }
            System.out.println();

            // Calculando altura e profundidade
            System.out.println("\nAltura da árvore: " + tree.height(root));
            System.out.println("Profundidade do nó J: " + tree.depth(nodeE.getChild().get(0)));

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public void quest4() {
        System.out.println("\n--- Questão 4: Operações avançadas em árvore ---");
        try {
            // Criando árvore para testes
            tree = new GenericT(100);
            Node root = tree.root();

            tree.addChild(root, 200);
            tree.addChild(root, 300);

            Node node200 = root.getChild().get(0);
            Node node300 = root.getChild().get(1);

            tree.addChild(node200, 201);
            tree.addChild(node200, 202);
            tree.addChild(node300, 301);

            System.out.println("Árvore criada para testes avançados");

            // Testando a troca de elementos
            System.out.println("\nAntes da troca:");
            System.out.println("Nó 1: " + node200.getElement());
            System.out.println("Nó 2: " + node300.getElement());

            tree.swapElements(node200, node300);

            System.out.println("\nDepois da troca:");
            System.out.println("Nó 1: " + node200.getElement());
            System.out.println("Nó 2: " + node300.getElement());

            // Removendo um nó folha
            Node leafNode = node200.getChild().get(0);
            System.out.println("\nRemovendo nó folha com valor: " + leafNode.getElement());
            tree.remove(leafNode);
            System.out.println("Tamanho da árvore após remoção: " + tree.size());

            // Tentando remover um nó interno (deve lançar exceção)
            System.out.println("\nTentando remover nó interno " + node200.getElement() + ":");
            try {
                tree.remove(node200);
            } catch (EInvalidPosition e) {
                System.out.println("Exceção capturada como esperado: " + e.getMessage());
            }

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
