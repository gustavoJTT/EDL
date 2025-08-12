import java.util.Iterator;

/**
 * Programa de demonstração da Skip List - VERSÃO SIMPLIFICADA
 *
 * A Skip List é uma estrutura de dados probabilística que mantém elementos
 * ordenados
 * usando múltiplos níveis de listas encadeadas para permitir busca eficiente
 * O(log n).
 *
 * CONCEITOS DEMONSTRADOS:
 * 📊 Estrutura Multi-nível: Como os elementos são organizados em diferentes
 * níveis
 * 🎲 Natureza Probabilística: Como decisões aleatórias determinam a altura dos
 * nós
 * 🔍 Busca Eficiente: Como a busca "pula" elementos usando níveis superiores
 * ⚖️ Balanceamento Automático: Como a probabilidade mantém a estrutura
 * balanceada
 */
public class ProgramSkipList {

    public static void main(String[] args) {
        System.out.println("🏗️ === DEMONSTRAÇÃO DA SKIP LIST ===\n");

        // Demonstração básica
        System.out.println("1️⃣ OPERAÇÕES BÁSICAS:");
        demonstrarOperacoesBasicas();

        System.out.println("\n" + "=".repeat(60) + "\n");

        // Visualização da estrutura probabilística
        System.out.println("2️⃣ ESTRUTURA PROBABILÍSTICA:");
        demonstrarEstruturaProbabilistica();

        System.out.println("\n" + "=".repeat(60) + "\n");

        // Eficiência da busca
        System.out.println("3️⃣ EFICIÊNCIA DA BUSCA:");
        demonstrarEficienciaBusca();
    }

    /**
     * Demonstra as operações fundamentais da Skip List
     * Mostra inserção, busca, remoção e iteração ordenada
     */
    public static void demonstrarOperacoesBasicas() {
        skipList lista = new skipList();

        System.out.println("📝 Inserindo elementos: 10, 5, 15, 3, 7, 12, 18");

        // Inserção: elementos são automaticamente mantidos em ordem
        lista.insertItem(10, "Dez");
        lista.insertItem(5, "Cinco");
        lista.insertItem(15, "Quinze");
        lista.insertItem(3, "Três");
        lista.insertItem(7, "Sete");
        lista.insertItem(12, "Doze");
        lista.insertItem(18, "Dezoito");

        System.out.println("✅ Tamanho após inserções: " + lista.size());
        System.out.println("\n🏗️ Estrutura multi-nível criada:");
        lista.printSkipList();

        // Demonstra busca eficiente
        System.out.println("\n🔍 Testando busca:");
        buscarElemento(lista, 7, "elemento existente");
        buscarElemento(lista, 99, "elemento inexistente");

        // Demonstra remoção
        System.out.println("\n🗑️ Removendo elemento 15:");
        try {
            Item removido = lista.removeElement(15);
            System.out.println("Removido: " + removido.key() + " → " + removido.value());
            System.out.println("Novo tamanho: " + lista.size());
        } catch (Exception e) {
            System.out.println("❌ Erro: " + e.getMessage());
        }

        // Mostra que elementos permanecem ordenados
        System.out.println("\n📋 Elementos em ordem após remoção:");
        listarElementosOrdenados(lista);
    }

    /**
     * Demonstra como a probabilidade cria uma estrutura balanceada
     * Cada elemento tem 50% de chance de aparecer no próximo nível
     */
    public static void demonstrarEstruturaProbabilistica() {
        skipList lista = new skipList();

        System.out.println("🎲 A Skip List usa probabilidade para determinar níveis:");
        System.out.println("   • Cada elemento tem 50% de chance de estar no próximo nível");
        System.out.println("   • Isso cria uma estrutura naturalmente balanceada");
        System.out.println("   • Não precisa de rotações como árvores balanceadas\n");

        // Insere elementos sequenciais para mostrar distribuição probabilística
        System.out.println("Inserindo elementos 1-8 e observando distribuição nos níveis:");

        for (int i = 1; i <= 8; i++) {
            lista.insertItem(i, "Valor_" + i);
            System.out.println("\n▶️ Após inserir " + i + ":");
            lista.printSkipList();
        }

        System.out.println("\n📊 Observe como alguns elementos aparecem em múltiplos níveis!");
        System.out.println("💡 Elementos em níveis altos funcionam como 'expressos' para busca rápida");
    }

    /**
     * Demonstra por que a busca é O(log n) em média
     * Mostra como os níveis superiores aceleram a navegação
     */
    public static void demonstrarEficienciaBusca() {
        skipList lista = new skipList();

        // Cria uma lista maior para demonstrar eficiência
        System.out.println("📈 Criando Skip List com 15 elementos para demonstrar eficiência:");

        int[] elementos = { 50, 25, 75, 12, 37, 62, 87, 6, 18, 31, 43, 56, 68, 81, 93 };

        for (int elemento : elementos) {
            lista.insertItem(elemento, "Valor_" + elemento);
        }

        System.out.println("✅ Lista criada. Estrutura final:");
        lista.printSkipList();

        System.out.println("\n🔍 Como a busca funciona na Skip List:");
        System.out.println("   1. Começa no nível mais alto, à esquerda");
        System.out.println("   2. Move para direita enquanto chave for menor que target");
        System.out.println("   3. Quando não pode mais ir à direita, desce um nível");
        System.out.println("   4. Repete até encontrar ou chegar ao nível 0");

        // Demonstra algumas buscas
        System.out.println("\n🎯 Testando buscas estratégicas:");
        buscarElemento(lista, 25, "início da lista");
        buscarElemento(lista, 75, "meio da lista");
        buscarElemento(lista, 93, "final da lista");
        buscarElemento(lista, 100, "elemento inexistente");

        System.out.println("\n⚡ VANTAGEM: Em uma lista comum, buscar o elemento 93");
        System.out.println("   exigiria percorrer todos os elementos anteriores!");
        System.out.println("   Na Skip List, os níveis superiores aceleram a busca!");

        // Mostra todos os elementos em ordem
        System.out.println("\n📋 Todos os elementos permanecem sempre ordenados:");
        listarElementosOrdenados(lista);
    }

    /**
     * Utilitário para buscar e reportar resultado
     */
    private static void buscarElemento(skipList lista, int chave, String descricao) {
        System.out.println("  🔎 Buscando " + chave + " (" + descricao + "):");
        Item item = lista.findElement(chave);
        if (item != null) {
            System.out.println("    ✅ Encontrado: " + item.key() + " → " + item.value());
        } else {
            System.out.println("    ❌ Não encontrado");
        }
    }

    /**
     * Utilitário para listar elementos em ordem
     */
    private static void listarElementosOrdenados(skipList lista) {
        Iterator<Item> elementos = lista.elements();
        System.out.print("    ");
        while (elementos.hasNext()) {
            Item item = elementos.next();
            System.out.print(item.key() + " ");
        }
        System.out.println("(sempre em ordem crescente!)");
    }
}
