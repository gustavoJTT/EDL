package Pilha.PilhaEncadeada;
import Pilha.EEmptyStack;

public interface LinkedStackInterface
{
    public int size();
    public boolean isEmpty();
    public Object top() throws EEmptyStack;  // ou "peek"
    public void push(Object element);
    public Object pop() throws EEmptyStack;
}