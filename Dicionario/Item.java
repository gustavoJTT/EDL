public class Item implements Entry {
    private Object key;
    private Object value;

    public Item(Object key, Object val) {
        this.key = key;
        this.value = val;
    }

    public void setKey(int k) {
        this.key = k;
    }

    public Object key() {
        return this.key;
    }

    public void setValue(Object v) {
        this.value = v;
    }

    public Object value() {
        return this.value;
    }
}
