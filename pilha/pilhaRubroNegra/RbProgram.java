package Pilha.pilhaRubroNegra;
import Pilha.EEmptyStack;

public class RbProgram
{
    public static void main(String[] args) {
        try {
            // 1. Criar uma pilha rubro-negra com capacidade inicial 4
            System.out.println("1. Criando pilha com capacidade 4...");
            RBStackArray pilha = new RBStackArray(4, 2);

            // 2. Verificar se as pilhas estão vazias inicialmente
            System.out.println("\n2. Verificando pilhas vazias:");
            System.out.println("Vermelha vazia? " + pilha.redIsEmpty());
            System.out.println("Preta vazia? " + pilha.blkIsEmpty());

            // 3. Adicionar elementos na pilha vermelha
            System.out.println("\n3. Adicionando 2 elementos na pilha vermelha...");
            pilha.redPushObject("Vermelho1");
            pilha.redPushObject("Vermelho2");
            System.out.println("Topo vermelho: " + pilha.redTop());
            System.out.println("Tamanho vermelho: " + pilha.redSize());

            // 4. Adicionar elementos na pilha preta
            System.out.println("\n4. Adicionando 2 elementos na pilha preta...");
            pilha.blkPushObject("Preto1");
            pilha.blkPushObject("Preto2");
            System.out.println("Topo preto: " + pilha.blkTop());
            System.out.println("Tamanho preto: " + pilha.blkSize());

            // 5. Verificar redimensionamento
            System.out.println("\n5. Testando redimensionamento...");
            System.out.println("Adicionando mais 1 elemento em cada pilha (deve redimensionar)");
            pilha.redPushObject("Vermelho3");
            pilha.blkPushObject("Preto3");
            System.out.println("Novo tamanho vermelho: " + pilha.redSize());
            System.out.println("Novo tamanho preto: " + pilha.blkSize());

            // 6. Remover elementos
            System.out.println("\n6. Removendo elementos...");
            System.out.println("Removendo do topo vermelho: " + pilha.redPopObject());
            System.out.println("Removendo do topo preto: " + pilha.blkPopObject());

            // 7. Imprimir pilha completa
            System.out.println("\n7. Conteúdo atual da pilha:");
            pilha.printRbStack();

            // 8. Testar pilhas vazias - CORREÇÃO: Remover TODOS os elementos
            System.out.println("\n8. Esvaziando pilhas...");
            System.out.println("Removido vermelho: " + pilha.redPopObject());
            System.out.println("Removido vermelho: " + pilha.redPopObject());

            // Agora removendo TODOS os elementos pretos
            System.out.println("Removido preto: " + pilha.blkPopObject());
            System.out.println("Removido preto: " + pilha.blkPopObject());

            System.out.println("Vermelha vazia? " + pilha.redIsEmpty());
            System.out.println("Preta vazia? " + pilha.blkIsEmpty());

            // 9. Testar exceções
            System.out.println("\n9. Testando exceções...");
            try {
                pilha.redPopObject();
            } catch (EEmptyStack e) {
                System.out.println("Exceção vermelha capturada: " + e.getMessage());
            }

            try {
                pilha.blkPopObject();
            } catch (EEmptyStack e) {
                System.out.println("Exceção preta capturada: " + e.getMessage());
            }

            // 10. Teste adicional - verificar capacidade após esvaziar
            System.out.println("\n10. Estado final:");
            System.out.println("Tamanho vermelho: " + pilha.redSize());
            System.out.println("Tamanho preto: " + pilha.blkSize());
            System.out.println("Tamanho total: " + pilha.sizeStack());

        } catch (Exception e) {
            System.out.println("Erro durante a execução: " + e.getMessage());
            e.printStackTrace();
        }
    }
}