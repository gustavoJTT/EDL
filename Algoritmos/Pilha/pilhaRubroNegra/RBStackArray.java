package Pilha.PilhaRubroNegra;

import Excesao.EEmptyStack;

public class RBStackArray implements RBStackInterface {
    private int size, growth, redPosition, blkPosition;
    private Object rbStack[];

    public RBStackArray(int size, int growth) {
        this.redPosition = -1;
        this.blkPosition = size;
        this.size = size;
        this.growth = growth;
        if (this.growth <= 0) {
            this.growth = 0;
        }
        this.rbStack = new Object[this.size];
    }

    // isEmpty & this.size
    public boolean redIsEmpty() {
        return this.redPosition == -1;
    }

    public boolean blkIsEmpty() {
        return this.blkPosition == this.size;
    }

    public int redSize() {
        return this.redPosition + 1;
    }

    public int blkSize() {
        return this.size - this.blkPosition;
    }

    public int sizeStack() {
        return redSize() + blkSize();
    }

    // top
    public Object redTop() throws EEmptyStack {
        if (redIsEmpty()) {
            throw new EEmptyStack("Pilha vermelha vazia");
        }
        return this.rbStack[this.redPosition];
    }

    public Object blkTop() throws EEmptyStack {
        if (blkIsEmpty()) {
            throw new EEmptyStack("Pilha preta vazia");
        }
        return this.rbStack[this.blkPosition];
    }

    // replace elements
    public void verifySpace() {
        if (this.redPosition + 1 == this.blkPosition) {
            int newSize;
            if (this.growth == 0) {
                newSize = this.size * 2;
            } else {
                newSize = this.size + this.growth;
            }
            resizeElements(newSize);
        } else if (sizeStack() <= this.size / 3 && this.size > 1) {
            int newSize = this.size / 2;
            resizeElements(newSize);
        }
    }

    public void resizeElements(int newSize) {
        Object newRbStack[] = new Object[newSize];

        for (int i = 0; i <= this.redPosition; i++) {
            newRbStack[i] = this.rbStack[i];
        }

        int newBlkPosition = newSize - blkSize();
        for (int i = 0; i < blkSize(); i++) {
            newRbStack[newBlkPosition + i] = this.rbStack[this.blkPosition + i];
        }

        this.size = newSize;
        this.blkPosition = newBlkPosition;
        this.rbStack = newRbStack;
    }

    // push & pop
    public void redPushObject(Object newObject) {
        verifySpace();
        this.rbStack[++this.redPosition] = newObject;
    }

    public void blkPushObject(Object newObject) {
        verifySpace();
        this.rbStack[--this.blkPosition] = newObject;
    }

    public Object redPopObject() throws EEmptyStack {
        if (redIsEmpty()) {
            throw new EEmptyStack("Pilha vermelha vazia");
        }
        Object removed = this.rbStack[this.redPosition--];
        verifySpace();
        return removed;
    }

    public Object blkPopObject() throws EEmptyStack {
        if (blkIsEmpty()) {
            throw new EEmptyStack("Pilha preta vazia");
        }
        Object removed = this.rbStack[this.blkPosition++];
        verifySpace();
        return removed;
    }

    // print
    public void printRbStack() {
        System.out.println("-----------------------------------");
        System.out.println("Pilha Array");
        System.out.println(String.format(
                "Tamanho do Array: %d\nTamanho Pilha Vermelha: %d - P: %d\nTamanho Pilha Preta: %d - P: %d", this.size,
                redSize(), this.redPosition, blkSize(), this.blkPosition));

        for (int i = 0; i < this.size; i++) {
            if (i <= this.redPosition) {
                System.out.print(String.format(" %s V |", this.rbStack[i]));
            } else if (i >= this.blkPosition) {
                System.out.print(String.format(" %s P |", this.rbStack[i]));
            } else {
                System.out.print("     |");
            }
        }
        System.out.println("\n-----------------------------------");
    }
}
