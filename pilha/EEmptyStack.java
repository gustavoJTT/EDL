package pilha;

public class EEmptyStack extends RuntimeException
{
    public EEmptyStack(String err)
    {
        super(err);
    }
}