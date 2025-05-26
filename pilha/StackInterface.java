package Pilha;

import Excesao.EEmptyStack;

public interface StackInterface
{
    public int size();
    public boolean isEmpty();
    public Object top() throws EEmptyStack;
    public void push(Object o);
    public Object pop() throws EEmptyStack;
}