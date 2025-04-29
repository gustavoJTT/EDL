package Pilha.PilhaJ.PilhaRubroNegra;

import Pilha.PilhaJ.EEmptyStack;

public interface StackRedBlack
{
    public int sizeStack();

    public int redSize();
    public boolean redIsEmpty();
    public void redPushObject(Object o);
    public Object redPopObject() throws EEmptyStack;
    public Object redTop() throws EEmptyStack;

    public int blackSize();
    public boolean blackIsEmpty();
    public void blackPushObject(Object o);
    public Object blackPopObject() throws EEmptyStack;
    public Object blackTop() throws EEmptyStack;
}