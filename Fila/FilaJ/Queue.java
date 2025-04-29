package Fila.FilaJ;

public interface Queue
{    
    public abstract int size();
    public abstract boolean isEmpty();
    public abstract void enqueue(Object o);
    public abstract Object dequeue() throws EEmptyQueue;
    public Object first() throws EEmptyQueue;
}