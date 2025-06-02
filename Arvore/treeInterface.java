import Node.Node;

public interface treeInterface {
    //genericos
    public boolean isEmpty();
    public int size();
    public int height();
    public int heightSlow();
    public int elements();
    public int nodes();

    //acesso
    public Node root();
    public Node parent();
    public int children();

    //consulta
    public boolean isInternal();
    public boolean isExternal();
    public boolean isRoot();
    public boolean depth();

    //atualizacao
    public Object replce();
}