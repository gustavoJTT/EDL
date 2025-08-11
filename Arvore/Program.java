import java.util.Iterator;
import java.util.Scanner;
import Node.Node;
import Excesao.*;

public class Program {
    private static GenericT tree;

    public static void main(String[] args) {
        boolean menuState = true;
        while (menuState) {
            System.out.println("\n===================================");
            System.out.println("           MENU PRINCIPAL          ");
            System.out.println("===================================");
            System.out.println("1 -> Questão 1 - Operações básicas em árvore");
            System.out.println("2 -> Questão 2 - Percurso e navegação na árvore");
            System.out.println("4 -> Questão 3 - Operações avançadas em árvore");
            System.out.println("0 -> sair");
            System.out.println("===================================");

            Scanner questScanner = new Scanner(System.in);
            System.out.print("Opção: ");
            int quest = questScanner.nextInt();

            switch (quest) {
                case 1:
                    new ProgramHash().quest1();
                    break;

                case 2:
                    new ProgramHash().quest2();
                    break;

                case 3:
                    new ProgramHash().quest3();
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
        System.out.println("\n===================================");
        System.out.println("   Questão 1: Operações básicas    ");
        System.out.println("===================================");

        try {
            // Criando uma árvore com raiz
            tree = new GenericT(10);
            System.out.println("Árvore criada com raiz: 10");
            System.out.println("Tamanho da árvore: " + tree.size()); // Espera-se: 1
            tree.printTree(); // Visualização da árvore
            System.out.println("-----------------------------------");

            // Obtendo a raiz
            Node root = tree.root();
            System.out.println("Elemento da raiz: " + root.getElement()); // Espera-se: 10
            System.out.println("-----------------------------------");

            // Adicionando filhos à raiz
            tree.addChild(root, 20);
            tree.addChild(root, 30);
            tree.addChild(root, 40);
            System.out.println("Adicionados 3 filhos à raiz: 20, 30, 40");
            System.out.println("Tamanho atualizado da árvore: " + tree.size()); // Espera-se: 4
            tree.printTree(); // Visualização da árvore após adicionar filhos
            System.out.println("-----------------------------------");

            // Verificando se a raiz é interna
            System.out.println("A raiz é um nó interno? " + tree.isInternal(root)); // Espera-se: true
            System.out.println("-----------------------------------");

            // Listando os filhos da raiz
            System.out.println("Filhos da raiz:");
            tree.printTree();
            System.out.println("-----------------------------------");

            // Adicionando um neto
            Node firstChild = root.getChild().get(0);
            tree.addChild(firstChild, 25);
            System.out.println("Adicionado filho 25 ao nó " + firstChild.getElement()); // Espera-se: "Adicionado filho 25 ao nó 20"
            tree.printTree(); // Visualização da árvore após adicionar neto
            System.out.println("-----------------------------------");

            // Substituindo um elemento
            Object oldValue = tree.replace(firstChild, 21);
            System.out.println("Substituído o valor " + oldValue + " por 21"); // Espera-se: "Substituído o valor 20 por 21"
            tree.printTree(); // Visualização da árvore após substituir elemento

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
        System.out.println("===================================");
    }

    public void quest2() {
        System.out.println("\n===================================");
        System.out.println("   Questão 2: Percurso e navegação  ");
        System.out.println("===================================");

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
            tree.printTree(); // Visualização da árvore hierárquica
            System.out.println("-----------------------------------");

            // Percorrendo todos os nós da árvore
            System.out.println("Todos os nós da árvore:");
            Iterator<Node> nodes = tree.nodes();
            while (nodes.hasNext()) {
                System.out.print(nodes.next().getElement() + " "); // Espera-se: Todos os nós de A a J em alguma ordem de percurso
            }
            System.out.println();
            System.out.println("-----------------------------------");

            // Percorrendo todos os elementos da árvore
            System.out.println("Todos os elementos da árvore:");
            Iterator<Object> elements = tree.elements();
            while (elements.hasNext()) {
                System.out.print(elements.next() + " "); // Espera-se: A B C D E F G H I J em alguma ordem de percurso
            }
            System.out.println();
            System.out.println("-----------------------------------");

            // Calculando altura e profundidade
            System.out.println("Altura da árvore: " + tree.height(root)); // Espera-se: 3 (A->B->E->J)
            System.out.println("Profundidade do nó J: " + tree.depth(nodeE.getChild().get(0))); // Espera-se: 3 (J está a 3 níveis da raiz)

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
        System.out.println("===================================");
    }

    public void quest3() {
        System.out.println("\n===================================");
        System.out.println("   Questão 4: Operações avançadas   ");
        System.out.println("===================================");

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
            tree.printTree(); // Visualização da árvore inicial
            System.out.println("-----------------------------------");

            // Testando a troca de elementos
            System.out.println("Antes da troca:");
            System.out.println("Nó 1: " + node200.getElement()); // Espera-se: 200
            System.out.println("Nó 2: " + node300.getElement()); // Espera-se: 300

            tree.swapElements(node200, node300);

            System.out.println("\nDepois da troca:");
            System.out.println("Nó 1: " + node200.getElement()); // Espera-se: 300
            System.out.println("Nó 2: " + node300.getElement()); // Espera-se: 200
            tree.printTree(); // Visualização da árvore após trocar elementos
            System.out.println("-----------------------------------");

            // Removendo um nó folha
            Node leafNode = node200.getChild().get(0);
            System.out.println("Removendo nó folha com valor: " + leafNode.getElement()); // Espera-se: 201
            tree.remove(leafNode);
            System.out.println("Tamanho da árvore após remoção: " + tree.size()); // Espera-se: 5 (era 6 - 1)
            tree.printTree(); // Visualização da árvore após remover nó folha
            System.out.println("-----------------------------------");

            // Tentando remover um nó interno (deve lançar exceção)
            System.out.println("Tentando remover nó interno " + node200.getElement() + ":"); // Espera-se: 300 (após o swap)
            try {
                tree.remove(node200); // Espera-se: lançar exceção EInvalidPosition
            } catch (EInvalidPosition e) {
                System.out.println("Exceção capturada como esperado: " + e.getMessage()); // Espera-se: Mensagem de erro
                tree.printTree(); // Mostrar que a árvore permanece inalterada após a exceção
            }

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
        System.out.println("===================================");
    }
}
