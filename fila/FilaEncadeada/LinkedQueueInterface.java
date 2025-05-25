package Fila.FilaEncadeada;
import Exceptions.EEmptyQueue;

public interface LinkedQueueInterface
{
    public int size();
    public boolean isEmpty();
    public Object first() throws EEmptyQueue;
    public void enqueue(Object element);
    public Object dequeue() throws EEmptyQueue;
}