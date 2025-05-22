package Deque;

public interface DequeInterface {
    public int size();
    public boolean isEmpty();
    public Object first() throws EEmptyDeque;
    public Object last() throws EEmptyDeque;

    public void enqueueBeginning(Object element);
    public Object dequeueBeginnig() throws EEmptyDeque;

    public void enqueueEnd(Object element);
    public Object dequeueEnd() throws EEmptyDeque;
}