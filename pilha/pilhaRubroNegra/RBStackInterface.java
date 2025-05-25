package Pilha.pilhaRubroNegra;
import Exceptions.EEmptyStack;

public interface RBStackInterface
{
    public int sizeStack();
    public void verifySpace();
    public void resizeElements(int newCapacity);

    public int redSize();
    public boolean redIsEmpty();
    public void redPushObject(Object newObject);
    public Object redPopObject() throws EEmptyStack;
    public Object redTop() throws EEmptyStack;

    public int blkSize();
    public boolean blkIsEmpty();
    public void blkPushObject(Object newObject);
    public Object blkPopObject() throws EEmptyStack;
    public Object blkTop() throws EEmptyStack;
}