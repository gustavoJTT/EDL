import Excesao.*;

public interface FPInterface {
    int size();
    boolean isEmpty();

    Item min() throws EFilaPrioridade;
    Item removeMin() throws EFilaPrioridade;
    void insert(Object k, Object o);
}
