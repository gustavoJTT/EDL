import java.util.Iterator;
import java.util.Scanner;
import Node.Node;
import Excesao.*;

public class ProgramBT {
    private static BinaryT tree;

    public static void main(String[] args) {
        boolean menuState = true;
        while (menuState) {
            System.out.println("\n===================================");
            System.out.println("           MENU PRINCIPAL          ");
            System.out.println("===================================");
            System.out.println("1 -> Questão 1 - Operações básicas em árvore binária");
            System.out.println("2 -> Questão 2 - Percurso e navegação na árvore binária");
            System.out.println("3 -> Questão 3 - Operações avançadas em árvore binária");
            System.out.println("0 -> sair");
            System.out.println("===================================");

            Scanner questScanner = new Scanner(System.in);
            System.out.print("Opção: ");
            int quest = questScanner.nextInt();

            switch (quest) {
                case 1:
                    new ProgramBT().quest1();
                    break;

                case 2:
                    new ProgramBT().quest2();
                    break;

                case 3:
                    new ProgramBT().quest3();
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
            tree = new BinaryT(10);
            System.out.println("Árvore binária criada com raiz: 10");
            System.out.println("Tamanho da árvore: " + tree.size()); // Espera-se: 1
            tree.printTree(); // Visualização da árvore
            System.out.println("-----------------------------------");

            // Obtendo a raiz
            Node root = tree.root();
            System.out.println("Elemento da raiz: " + root.getElement()); // Espera-se: 10
            System.out.println("-----------------------------------");

            // Adicionando filhos à raiz
            tree.addLeft(root, 20);
            tree.addRight(root, 30);
            System.out.println("Adicionados 2 filhos à raiz: 20 (esquerda), 30 (direita)");
            System.out.println("Tamanho atualizado da árvore: " + tree.size()); // Espera-se: 3
            tree.printTree(); // Visualização da árvore após adicionar filhos
            System.out.println("-----------------------------------");

            // Verificando se a raiz é interna
            System.out.println("A raiz é um nó interno? " + tree.isInternal(root)); // Espera-se: true
            System.out.println("-----------------------------------");

            // Obtendo filhos esquerdo e direito
            Node leftChild = tree.leftChild(root);
            Node rightChild = tree.rightChild(root);
            System.out.println("Filho esquerdo da raiz: " + leftChild.getElement()); // Espera-se: 20
            System.out.println("Filho direito da raiz: " + rightChild.getElement()); // Espera-se: 30
            System.out.println("-----------------------------------");

            // Adicionando filhos ao nó esquerdo
            tree.addLeft(leftChild, 15);
            tree.addRight(leftChild, 25);
            System.out.println("Adicionados filhos ao nó 20: 15 (esquerda), 25 (direita)");
            tree.printTree(); // Visualização da árvore após adicionar netos
            System.out.println("-----------------------------------");

            // Substituindo um elemento
            Object oldValue = tree.replace(leftChild, 21);
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
            // Criando uma árvore binária para testes de percurso
            tree = new BinaryT("A");
            Node root = tree.root();

            tree.addLeft(root, "B");
            tree.addRight(root, "C");

            Node nodeB = tree.leftChild(root);
            Node nodeC = tree.rightChild(root);

            tree.addLeft(nodeB, "D");
            tree.addRight(nodeB, "E");
            tree.addLeft(nodeC, "F");
            tree.addRight(nodeC, "G");

            Node nodeD = tree.leftChild(nodeB);
            tree.addLeft(nodeD, "H");
            tree.addRight(nodeD, "I");

            System.out.println("Árvore binária criada com estrutura para percurso:");
            tree.printTree(); // Visualização da árvore hierárquica
            System.out.println("-----------------------------------");

            // Percorrendo todos os nós da árvore (in-order)
            System.out.println("Todos os nós da árvore (in-order):");
            Iterator<Node> nodes = tree.nodes();
            while (nodes.hasNext()) {
                System.out.print(nodes.next().getElement() + " "); // Espera-se: nós em ordem infixa (in-order)
            }
            System.out.println();
            System.out.println("-----------------------------------");

            // Percorrendo todos os elementos da árvore (in-order)
            System.out.println("Todos os elementos da árvore (in-order):");
            Iterator<Object> elements = tree.elements();
            while (elements.hasNext()) {
                System.out.print(elements.next() + " "); // Espera-se: elementos em ordem infixa (in-order)
            }
            System.out.println();
            System.out.println("-----------------------------------");

            // Calculando altura e profundidade
            System.out.println("Altura da árvore: " + tree.height(root)); // Espera-se: 3 (A->B->D->H)
            System.out.println("Profundidade do nó H: " + tree.depth(tree.leftChild(nodeD))); // Espera-se: 3 (H está a 3 níveis da raiz)
            System.out.println("-----------------------------------");

            // Verificando se nós têm filhos esquerdo ou direito
            System.out.println("O nó 'A' tem filho esquerdo? " + tree.hasLeft(root)); // Espera-se: true
            System.out.println("O nó 'A' tem filho direito? " + tree.hasRight(root)); // Espera-se: true
            System.out.println("O nó 'D' tem filho esquerdo? " + tree.hasLeft(nodeD)); // Espera-se: true
            System.out.println("O nó 'G' tem filho direito? " + tree.hasRight(tree.rightChild(nodeC))); // Espera-se: false

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
        System.out.println("===================================");
    }

    public void quest3() {
        System.out.println("\n===================================");
        System.out.println("   Questão 3: Operações avançadas   ");
        System.out.println("===================================");

        try {
            // Criando árvore binária para testes avançados
            tree = new BinaryT(100);
            Node root = tree.root();

            tree.addLeft(root, 200);
            tree.addRight(root, 300);

            Node node200 = tree.leftChild(root);
            Node node300 = tree.rightChild(root);

            tree.addLeft(node200, 201);
            tree.addRight(node200, 202);
            tree.addLeft(node300, 301);

            System.out.println("Árvore binária criada para testes avançados:");
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
            Node leafNode = tree.leftChild(node200);
            System.out.println("Removendo nó folha com valor: " + leafNode.getElement()); // Espera-se: 201
            tree.remove(leafNode);
            System.out.println("Tamanho da árvore após remoção: " + tree.size()); // Espera-se: tamanho reduzido em 1
            tree.printTree(); // Visualização da árvore após remover nó folha
            System.out.println("-----------------------------------");

            // Tentando remover um nó interno (deve lançar exceção)
            System.out.println("Tentando remover nó interno " + node200.getElement() + ":"); // Espera-se: 300 (após o swap)
            try {
                tree.remove(node200); // Espera-se: lançar exceção
            } catch (EInvalidPosition e) {
                System.out.println("Exceção capturada como esperado: " + e.getMessage()); // Espera-se: Mensagem de erro
                tree.printTree(); // Mostrar que a árvore permanece inalterada após a exceção
            }

            // Testando a consulta de irmãos
            System.out.println("\nTestando relacionamentos entre nós:");
            Node rightChild202 = tree.rightChild(node200);
            System.out.println("Nó 1 (" + node200.getElement() + ") é pai do nó 2 (" + rightChild202.getElement() + ")? " + (node200 == tree.parent(rightChild202))); // Espera-se: true

            // Verificando o pai do nó folha
            Node parent = tree.parent(rightChild202);
            System.out.println("Pai do nó " + rightChild202.getElement() + ": " + parent.getElement()); // Espera-se: 300 (após o swap)

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
        System.out.println("===================================");
    }
}
