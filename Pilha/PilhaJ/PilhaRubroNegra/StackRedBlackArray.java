package Pilha.PilhaJ.PilhaRubroNegra;

import Pilha.PilhaJ.EEmptyStack;

public class StackRedBlackArray implements StackRedBlack
{
    private int capacity;
    private int redPosition;
    private int blackPosition;
    private Object stack[];

    public StackRedBlackArray(int capacity)
    {
        this.capacity = capacity;
        redPosition = -1;
        blackPosition = capacity;
        stack = new Object[capacity];
    }
    
    //size
    public int redSize()
    {
        return redPosition + 1;
    }

    public int blackSize()
    {
        return capacity - blackPosition;
    }

    public int sizeStack()
    {
        return redSize() + blackSize();
    }

    //isEmpty
    public boolean redIsEmpty()
    {
        return redPosition == -1;
    }

    public boolean blackIsEmpty()
    {
        return blackPosition == capacity;
    }

    //top
    public Object redTop() throws EEmptyStack
    {
        if(redIsEmpty())
        {
            throw new EEmptyStack("Pilha vermelha vazia");
        }
        return stack[redPosition];
    }

    public Object blackTop() throws EEmptyStack
    {
        if(redIsEmpty())
        {
            throw new EEmptyStack("Pilha vermelha vazia");
        }
        return stack[blackPosition];
    }

    //pop
    public Object redPopObject() throws EEmptyStack
    {
        /*
         * remove o elemento vermelho da pilha
         * 
         * se a pilha estiver 1/3 cheia, a capacidade é metade
         * 
         * se a pilha estiver vazia, lanca uma excecao
         */
        if (redIsEmpty()) {
            throw new EEmptyStack("A Pilha vermelha está vazia");
        }
        Object elemento = stack[redPosition--];
        if (sizeStack() * 3 <= capacity && capacity > 3) {
            capacity /= 2;
            realocarElementosDecrescentes();
        }
        return elemento;
    }

    public Object blackPopObject() throws EEmptyStack
    {
        /*
         * remove o elemento preto da pilha
         * 
         * se a pilha estiver 1/3 cheia, a capacidade é metade
         * 
         * se a pilha estiver vazia, lanca uma excecao
         */
        if (blackIsEmpty()) {
            throw new EEmptyStack("A Pilha preta está vazia");
        }
        Object elemento = stack[blackPosition++];
        if (sizeStack() * 3 <= capacity && capacity > 3) {
            capacity /= 2;
            realocarElementosDecrescentes();
        }
        return elemento;
    }

    //push
    public void realocarElementosCrescentes()
    {
        /* ajustar elementos vermelhos e pretos para suas respectivas posicoes apos a mudança do tamanho da pilha (pilha ficando maior) */
        Object newStack[] = new Object[capacity];
        for (int i = 0; i < stack.length; i++) {
            if (i <= redPosition) {
                newStack[i] = stack[i];
            } else {
                newStack[capacity / 2 + i] = stack[i];
            }
        }
        stack = newStack;
        blackPosition += capacity / 2;
    }

    public void realocarElementosDecrescentes()
    {
        /* ajustar elementos vermelhos e pretos para suas respectivas posicoes apos a mudança do tamanho da pilha (pilha ficando menor) */
        blackPosition = capacity - (capacity * 2 - blackPosition);
        Object newStack[] = new Object[capacity];
        for (int i = 0; i < sizeStack(); i++) {
            if (i <= redPosition) {
                newStack[i] = stack[i];
            } else {
                newStack[sizeStack() / 2 + i] = stack[capacity * 2 - sizeStack() + i];
            }
        }    
        stack = newStack;
    }
    
    public void redPushObject(Object o)
    {
        /* adiciona a pilha um novo elemento vermelho
         * 
         * se a pilha estiver cheia, a capacity é dobrada
         */
        if (sizeStack() >= capacity) {
            capacity *= 2;
            realocarElementosCrescentes();
        }
        stack[++redPosition] = o;
    }
    
    public void blackPushObject(Object o)
    {
        /*
         * adiciona a pilha um novo elemento preto
         * 
         * se a pilha estiver cheia, a capacidade é dobrada
         */
        if (sizeStack() >= capacity) {
            capacity *= 2;
            realocarElementosCrescentes();
        }
        stack[--blackPosition] = o;
    }

    //printStack
    public void printStack() throws EEmptyStack
    {
        if(redIsEmpty() && blackIsEmpty() == true)
        {
            throw new EEmptyStack("Pilha vazia");
        }
        for(int i = 0; i <= sizeStack() - 1; i++)
        {
            System.out.println(stack[i]);
        }
    }
}