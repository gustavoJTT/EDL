import java.util.Iterator;

/**
 * Programa de demonstração da Hash Table
 * Mostra como usar as operações básicas e compara diferentes estratégias de
 * resolução de colisões
 */
public class ProgramHash {

    public static void main(String[] args) {
        System.out.println("=== DEMONSTRAÇÃO DA HASH TABLE ===\n");

        // Teste com sondagem linear
        System.out.println("1. TESTANDO COM SONDAGEM LINEAR:");
        testarHashTable(new hash(7, false));

        System.out.println("\n" + "=".repeat(50) + "\n");

        // Teste com double hashing
        System.out.println("2. TESTANDO COM DOUBLE HASHING:");
        testarHashTable(new hash(7, true));

        System.out.println("\n" + "=".repeat(50) + "\n");

        // Teste de redimensionamento
        System.out.println("3. TESTANDO REDIMENSIONAMENTO AUTOMÁTICO:");
        testarRedimensionamento();
    }

    /**
     * Testa as operações básicas da hash table
     */
    public static void testarHashTable(hash tabela) {
        System.out.println("Inserindo elementos...");

        // Inserção de elementos
        tabela.insertItem(10, "Dez");
        tabela.insertItem(22, "Vinte e Dois");
        tabela.insertItem(31, "Trinta e Um");
        tabela.insertItem(4, "Quatro");
        tabela.insertItem(15, "Quinze");

        System.out.println("Tamanho após inserções: " + tabela.size());
        tabela.printTable();

        // Busca de elementos
        System.out.println("\nTestando busca:");
        Item item = tabela.findElement(22);
        if (item != null) {
            System.out.println("Encontrado: " + item.key() + " -> " + item.value());
        }

        item = tabela.findElement(99);
        if (item == null) {
            System.out.println("Chave 99 não encontrada (como esperado)");
        }

        // Iteração sobre chaves
        System.out.println("\nChaves na tabela:");
        Iterator<Integer> chaves = tabela.keys();
        while (chaves.hasNext()) {
            System.out.print(chaves.next() + " ");
        }
        System.out.println();

        // Remoção de elemento
        System.out.println("\nRemovendo elemento com chave 22:");
        Item removido = tabela.removeElement(22);
        System.out.println("Removido: " + removido.key() + " -> " + removido.value());
        System.out.println("Tamanho após remoção: " + tabela.size());

        tabela.printTable();

        // Teste de chave já ocupada
        System.out.println("\nTentando inserir chave duplicada (deve gerar erro):");
        try {
            tabela.insertItem(10, "Dez Duplicado");
        } catch (RuntimeException e) {
            System.out.println("Erro capturado: " + e.getMessage());
        }
    }

    /**
     * Testa o redimensionamento automático da hash table
     */
    public static void testarRedimensionamento() {
        hash tabela = new hash(3, false); // Começa bem pequena

        System.out.println("Inserindo muitos elementos para forçar redimensionamento...");

        // Insere elementos até forçar redimensionamento
        for (int i = 1; i <= 10; i++) {
            System.out.println("Inserindo " + i + ":");
            tabela.insertItem(i, "Valor " + i);
            tabela.printTable();
            System.out.println();
        }

        System.out.println("Estado final:");
        System.out.println("Tamanho: " + tabela.size());

        // Mostra todos os elementos
        System.out.println("Todos os elementos:");
        Iterator<Item> elementos = tabela.elements();
        while (elementos.hasNext()) {
            Item item = elementos.next();
            System.out.println(item.key() + " -> " + item.value());
        }
    }
}
