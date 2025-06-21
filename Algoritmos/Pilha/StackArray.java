package Pilha;

import Excesao.EEmptyStack;

public class StackArray implements StackInterface {
    private int capacity, growth, currentPosition;
    private Object stack[];

    public StackArray(int capacity, int growth) {
        this.currentPosition = -1;
        this.capacity = capacity;
        this.growth = growth;
        if (this.growth <= 0) {
            this.growth = 0;
        }
        this.stack = new Object[this.capacity];
    }

    // isEmpty & this.capacity
    public boolean isEmpty() {
        return this.currentPosition == -1;
    }

    public int size() {
        return this.currentPosition + 1;
    }

    // top
    public Object top() throws EEmptyStack {
        if (isEmpty()) {
            throw new EEmptyStack("Pilha vazia");
        }

        return this.stack[this.currentPosition];
    }

    // push & pop
    public void push(Object o) {
        if (this.currentPosition >= this.capacity - 1) // ou if(this.currentPosition + 1 == this.capacity)
        {
            if (this.growth == 0) {
                this.capacity *= 2;
            } else {
                this.capacity += this.growth;
            }

            Object newStack[] = new Object[this.capacity];
            for (int newPosition = 0; newPosition < size(); newPosition++) {
                newStack[newPosition] = this.stack[newPosition];
            }
            this.stack = newStack;
        }
        this.stack[++this.currentPosition] = o;
    }

    public Object pop() throws EEmptyStack {
        if (isEmpty()) {
            throw new EEmptyStack("Pilha vazia");
        }
        Object removed = this.stack[this.currentPosition--];
        return removed;
    }

    // print & empty
    public void printStack() throws EEmptyStack {
        if (isEmpty()) {
            throw new EEmptyStack("Pilha vazia");
        }
        for (int i = 0; i <= this.currentPosition; i++) {
            System.out.println(String.format("Posição %d -> %s | Capacidade: %d", i, this.stack[i], this.capacity));
        }
    }

    public void empty() {
        this.currentPosition = -1;
    }
}
