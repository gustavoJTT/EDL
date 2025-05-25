package Vector;

import Exceptions.EEmptyVector;
import Exceptions.ERankOOL;

public interface VectorInterface
{
    public int size();
    public boolean isEmpty();
    public Object elemAtRank(int rank) throws EEmptyVector, ERankOOL;
    public Object replaceAtRank(int rank, Object newObject) throws EEmptyVector, ERankOOL;
    public void insertAtRank(int rank, Object newObject) throws ERankOOL;
    public Object removeAtRank(int rank) throws EEmptyVector, ERankOOL;
}