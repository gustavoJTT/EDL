package Vector;

import Excesao.EEmptyVector;
import Excesao.ERankOOL;

public class VectorArray implements VectorInterface {
    private int size, growth, lastElement;
    private Object array[];

    public VectorArray(int size, int growth) {
        this.lastElement = 0;
        this.size = size;
        this.growth = growth;
        if (this.growth <= 0) {
            this.growth = 0;
        }
        this.array = new Object[this.size];
    }

    // isEmpty & this.size
    public boolean isEmpty() {
        return this.lastElement == 0;
    }

    public int size() {
        return this.lastElement;
    }

    // element
    public Object elemAtRank(int rank) throws EEmptyVector, ERankOOL {
        if (isEmpty()) {
            throw new EEmptyVector("Array vazio");
        }

        if (rank < 0 || rank >= size()) {
            throw new ERankOOL("Rank fora dos limites");
        }
        return this.array[rank];
    }

    // replace
    public Object replaceAtRank(int rank, Object newObject) throws EEmptyVector, ERankOOL {
        if (isEmpty()) {
            throw new EEmptyVector("Array vazio");
        }

        if (rank < 0 || rank >= size()) {
            throw new ERankOOL("Rank fora dos limites");
        }
        Object old = this.array[rank];
        this.array[rank] = newObject;
        return old;
    }

    // insert
    public void insertAtRank(int rank, Object newObject) throws ERankOOL {
        if (rank < 0 || rank > size()) {
            throw new ERankOOL("Rank fora dos limites");
        }

        if (size() == this.size) {
            int newSize;
            if (this.growth == 0) {
                newSize = this.size * 2;
            } else {
                newSize = this.size + this.growth;
            }
            Object newArray[] = new Object[newSize];

            for (int i = 0; i < size(); i++) {
                if (i < rank) {
                    newArray[i] = this.array[i];
                } else {
                    newArray[i + 1] = this.array[i];
                }
            }
            this.size = newSize;
            this.array = newArray;
        }
        for (int i = size(); i > rank; i--) {
            this.array[i] = this.array[i - 1];
        }
        this.array[rank] = newObject;
        this.lastElement++;
    }

    // remove
    public Object removeAtRank(int rank) throws EEmptyVector, ERankOOL {
        if (isEmpty()) {
            throw new EEmptyVector("Array vazio");
        }
        if (rank < 0 || rank >= size()) {
            throw new ERankOOL("Rank fora dos limites");
        }

        Object removed = this.array[rank];

        // Move todos os elementos após o rank uma posição para trás
        for (int i = rank; i < size() - 1; i++) {
            this.array[i] = this.array[i + 1];
        }

        this.array[size() - 1] = null; // Remove a referência do último elemento
        this.lastElement--;

        return removed;
    }
}
