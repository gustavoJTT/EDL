public class PilhaArray implements Pilha{
    private int posicao;
    private int capacidade;
    private int fatorCrescimento;
    private Object pilha[];
    
    public PilhaArray(int capacidade, int fatorCrescimento)
    {
        this.capacidade = capacidade;
        this.fatorCrescimento = fatorCrescimento;
        posicao = -1; //ela inicia vazia

        if(fatorCrescimento <= 0)
        {
            fatorCrescimento = 0;
        }
        pilha = new Object[capacidade];
    }

    public void push(Object itemNovo)
    {
        if(posicao >= capacidade-1) //se a posição n tiver no final da pilha ele
        {
            if(fatorCrescimento == 0) //
            {
                capacidade *= 2;
            }
            else
            {
                capacidade += fatorCrescimento;
            }
            Object nova[] = new Object[capacidade];
            for(int i = 0; i < pilha.length; i++)
            {
                nova[i] = pilha[i];
            }
            pilha = nova;
        }
        pilha[++posicao] = itemNovo;
    }

    public Object pop() throws PilhaVaziaExcecao
    {
        if(isEmpty())
        {
            throw new PilhaVaziaExcecao("A pilha está vazia");
        }

        Object r = pilha[posicao--];
        return r;
    }

    public Object top() throws PilhaVaziaExcecao
    {
        if(isEmpty())
        {
            throw new PilhaVaziaExcecao("A pilha está vazia");
        }

        return pilha[posicao];
    }

    public boolean isEmpty()
    {
        return posicao == -1;
    }

    public int size()
    {
        return posicao++;
    }
}
