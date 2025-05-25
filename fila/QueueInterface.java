package Fila;

import Exceptions.EEmptyQueue;

public interface QueueInterface
{
    public int size();
    public boolean isEmpty();
    public Object first() throws EEmptyQueue;
    public void enqueue(Object newObject);
    public Object dequeue() throws EEmptyQueue;
}