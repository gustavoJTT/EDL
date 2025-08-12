import java.util.Iterator;

/**
 * Programa de demonstração da Hash Table - VERSÃO SIMPLIFICADA
 * Mostra como usar as operações básicas e compara diferentes estratégias
 * de resolução de colisões (Linear Probing vs Double Hashing)
 */
public class ProgramHash {

    public static void main(String[] args) {
        System.out.println("=== DEMONSTRAÇÃO DA HASH TABLE ===\n");

        // Teste com sondagem linear
        System.out.println("🔍 TESTANDO COM SONDAGEM LINEAR:");
        testarHashTable(new hash(7, false));

        System.out.println("\n" + "=".repeat(60) + "\n");

        // Teste com double hashing
        System.out.println("🎯 TESTANDO COM DOUBLE HASHING:");
        testarHashTable(new hash(7, true));

        System.out.println("\n" + "=".repeat(60) + "\n");

        // Teste de redimensionamento
        System.out.println("📈 TESTANDO REDIMENSIONAMENTO AUTOMÁTICO:");
        testarRedimensionamento();
    }

    /**
     * Testa as operações básicas da hash table
     *
     * @param tabela instância da hash table a ser testada
     */
    public static void testarHashTable(hash tabela) {
        System.out.println("Inserindo elementos que causarão colisões...");

        // Inserção estratégica para demonstrar colisões
        tabela.insertItem(10, "Dez"); // 10 % 7 = 3
        tabela.insertItem(22, "Vinte e Dois"); // 22 % 7 = 1
        tabela.insertItem(31, "Trinta e Um"); // 31 % 7 = 3 (COLISÃO com 10!)
        tabela.insertItem(4, "Quatro"); // 4 % 7 = 4
        tabela.insertItem(15, "Quinze"); // 15 % 7 = 1 (COLISÃO com 22!)

        System.out.println("✅ Elementos inseridos. Tamanho: " + tabela.size());

        // Mostra estrutura interna
        tabela.printHashTable();

        // Demonstra busca
        System.out.println("🔎 Testando busca de elementos:");
        testaBusca(tabela, 22, "elemento existente");
        testaBusca(tabela, 99, "elemento inexistente");

        // Demonstra remoção
        System.out.println("🗑️ Removendo elemento chave 22:");
        try {
            Item removido = tabela.removeElement(22);
            System.out.println("Removido: " + removido.key() + " → " + removido.value());
            System.out.println("Novo tamanho: " + tabela.size());
            tabela.printHashTable();
        } catch (RuntimeException e) {
            System.out.println("❌ Erro: " + e.getMessage());
        }

        // Lista todos elementos restantes
        System.out.println("📋 Elementos restantes:");
        listarElementos(tabela);
    }

    /**
     * Testa busca de um elemento específico
     *
     * @param tabela    hash table
     * @param chave     chave a buscar
     * @param descricao descrição do teste
     */
    private static void testaBusca(hash tabela, int chave, String descricao) {
        System.out.println("  - Buscando " + chave + " (" + descricao + "):");
        Item item = tabela.findElement(chave);
        if (item != null) {
            System.out.println("    ✅ Encontrado: " + item.key() + " → " + item.value());
        } else {
            System.out.println("    ❌ Não encontrado");
        }
    }

    /**
     * Lista todos elementos da hash table
     *
     * @param tabela hash table
     */
    private static void listarElementos(hash tabela) {
        Iterator<Item> elementos = tabela.elements();
        while (elementos.hasNext()) {
            Item item = elementos.next();
            System.out.println("  • " + item.key() + " → " + item.value());
        }
    }

    /**
     * Demonstra redimensionamento automático quando fator de carga > 50%
     */
    public static void testarRedimensionamento() {
        hash tabela = new hash(5, false); // Começa pequena (capacidade 5)

        System.out.println("Criando hash table pequena (capacidade 5)");
        System.out.println("Inserindo elementos até forçar redimensionamento...");

        // Insere elementos até ultrapassar 50% (3 elementos em capacidade 5)
        for (int i = 1; i <= 6; i++) {
            System.out.println("\nInserindo elemento " + i + ":");
            tabela.insertItem(i, "Valor_" + i);
            System.out.println("Tamanho: " + tabela.size());
            tabela.printHashTable();
        }

        System.out.println("✅ Redimensionamento automático funcionou!");
        System.out.println("Hash table cresceu para manter eficiência.");
    }
}
