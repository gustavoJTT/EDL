public class Item implements Entry {
    private Object key;
    private Object value;

    public Item(Object key, Object val) {
        this.key = key;
        this.value = val;
    }

    public Object key() {
        return this.key;
    }

    public Object value() {
        return this.value;
    }
}
