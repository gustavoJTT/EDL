package Pilha.PilhaJ;

public interface Stack
{
    public int size();
    public boolean isEmpty();
    public void pushObject(Object o);
    public Object popObject() throws EEmptyStack;
    public Object top() throws EEmptyStack;
}