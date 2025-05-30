package Pilha;

import Excesao.EEmptyStack;

public class StackArray implements StackInterface {
    private int capacity, growth, currentPosition;
    private Object stack[];

    public StackArray(int capacity, int growth) {
        this.currentPosition = -1;
        this.capacity = capacity;
        this.growth = growth;
        if (growth <= 0) {
            this.growth = 0;
        }
        stack = new Object[capacity];
    }

    // isEmpty & capacity
    public boolean isEmpty() {
        return currentPosition == -1;
    }

    public int size() {
        return currentPosition + 1;
    }

    // top
    public Object top() throws EEmptyStack {
        if (isEmpty()) {
            throw new EEmptyStack("Pilha vazia");
        }

        return stack[currentPosition];
    }

    // push & pop
    public void push(Object o) {
        if (currentPosition >= capacity - 1) // ou if(currentPosition + 1 == capacity)
        {
            if (growth == 0) {
                capacity *= 2;
            } else {
                capacity += growth;
            }

            Object newStack[] = new Object[capacity];
            for (int newPosition = 0; newPosition < size(); newPosition++) {
                newStack[newPosition] = stack[newPosition];
            }
            stack = newStack;
        }
        stack[++currentPosition] = o;
    }

    public Object pop() throws EEmptyStack {
        if (isEmpty()) {
            throw new EEmptyStack("Pilha vazia");
        }
        Object removed = stack[currentPosition--];
        return removed;
    }

    // print & empty
    public void printStack() throws EEmptyStack {
        if (isEmpty()) {
            throw new EEmptyStack("Pilha vazia");
        }
        for (int i = 0; i <= currentPosition; i++) {
            System.out.println(String.format("Posição %d -> %s | Capacidade: %d", i, stack[i], capacity));
        }
    }

    public void empty() {
        currentPosition = -1;
    }
}