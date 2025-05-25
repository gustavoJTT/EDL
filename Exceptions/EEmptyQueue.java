package Exceptions;

public class EEmptyQueue extends RuntimeException
{
    public EEmptyQueue(String err)
    {
        super(err);
    }
}