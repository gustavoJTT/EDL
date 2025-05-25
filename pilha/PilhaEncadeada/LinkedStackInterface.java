package Pilha.PilhaEncadeada;
import Exceptions.EEmptyStack;

public interface LinkedStackInterface
{
    public boolean isEmpty();
    public int size();
    public Object top() throws EEmptyStack;  // ou "peek"
    public void push(Object newElement);
    public Object pop() throws EEmptyStack;
}