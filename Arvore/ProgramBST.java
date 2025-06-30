import Node.Node;

public class ProgramBST {
    public static void main(String[] args) {
        try {
            // Criando uma árvore binária de pesquisa
            BinaryST bst = new BinaryST(50);

            System.out.println("=== Teste da Árvore Binária de Pesquisa ===");
            System.out.println("Árvore criada com raiz: 50");

            // Inserindo elementos
            System.out.println("\nInserindo elementos: 30, 70, 20, 40, 60, 80");
            bst.insert(30);
            bst.insert(70);
            bst.insert(20);
            bst.insert(40);
            bst.insert(60);
            bst.insert(80);

            System.out.println("Tamanho da árvore: " + bst.size());

            // Imprimindo elementos em ordem
            bst.printInOrder();

            // Testando busca
            System.out.println("\n=== Teste de Busca ===");
            Node found = bst.search(40);
            System.out.println(
                    "Busca por 40: " + (found != null ? "Encontrado - " + found.getElement() : "Não encontrado"));

            found = bst.search(25);
            System.out.println("Busca por 25: " + (found.getElement().equals(25) ? "Encontrado"
                    : "Não encontrado (retornou: " + found.getElement() + ")"));

            // Encontrando min e max
            System.out.println("\n=== Min e Max ===");
            System.out.println("Menor elemento: " + bst.findMin().getElement());
            System.out.println("Maior elemento: " + bst.findMax().getElement());

            // Testando remoção
            System.out.println("\n=== Teste de Remoção ===");
            System.out.println("Removendo elemento 20 (folha)");
            Node nodeToRemove = bst.search(20);
            bst.removeBST(nodeToRemove);
            bst.printInOrder();

            System.out.println("Removendo elemento 30 (com um filho)");
            nodeToRemove = bst.search(30);
            bst.removeBST(nodeToRemove);
            bst.printInOrder();

            System.out.println("Removendo elemento 50 (raiz com dois filhos)");
            nodeToRemove = bst.search(50);
            bst.removeBST(nodeToRemove);
            bst.printInOrder();

            System.out.println("Tamanho final da árvore: " + bst.size());

        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
