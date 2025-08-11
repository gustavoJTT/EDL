import java.util.Iterator;

public interface DictInterface {
    public Item findElement(int k);
    public void insertItem(int k, Object o);
    public Item removeElement(int k);

    public int size();
    public boolean isEmpty();
    public Iterator<Integer> keys();
    public Iterator<Item> elements();
}
