import Excesao.*;

public class HeapArray implements FPInterface {
    private Item heap[];
    private int capacidade;
    private int ultimo;

    public HeapArray(int capacidade) {
        this.capacidade = capacidade;
        this.heap = new Item[this.capacidade];
        this.ultimo = 0;
    }

    public int size() {
        return this.ultimo;
    }

    public boolean isEmpty() {
        return this.ultimo == 0;
    }

    public void insert(Object key, Object val) {
        if (this.ultimo + 1 >= this.capacidade) {
            this.capacidade *= 2;

            Item temp[] = new Item[capacidade];

            for (int i = 0; i <= this.ultimo; i++) {
                temp[i] = this.heap[i];
            }
            this.heap = temp;
        }
        Item entry = new Item(key, val);

        this.ultimo++;
        this.heap[ultimo] = entry;

        this.upHeap(ultimo);
    }

    public Item min() throws EFilaPrioridade {
        if (this.isEmpty()) {
            throw new EFilaPrioridade("A Fila de Prioridade está vazia");
        }

        return this.heap[1];
    }

    public Item removeMin() throws EFilaPrioridade {
        if (this.isEmpty()) {
            throw new EFilaPrioridade("A Fila de Prioridade está vazia");
        }

        Item min = this.min();

        this.swap(ultimo, 1);

        this.heap[this.ultimo] = null;
        this.ultimo--;

        this.downHeap(1);

        return min;
    }

    private void downHeap(int raiz) {
        int esquerda = 2 * raiz;
        int direita = 2 * raiz + 1;

        int menor;

        if (esquerda > this.ultimo && direita > this.ultimo) {
            return;
        }

        if (direita > this.ultimo) {
            if (this.compare(esquerda, raiz)) {
                this.swap(raiz, esquerda);

                this.downHeap(esquerda);
            }
            return;
        }

        if (this.compare(direita, esquerda)) {
            menor = direita;
        } else {
            menor = esquerda;
        }

        if (this.compare(menor, raiz)) {
            this.swap(menor, raiz);

            this.downHeap(menor);
        }
    }

    private void upHeap(int n) {
        int pai = n / 2;

        if (pai == 0 || !this.compare(n, pai)) {
            return;
        }

        this.swap(n, pai);

        this.upHeap(pai);
    }

    private boolean compare(int filho, int pai) {
        Item itemFilho = (Item) this.heap[filho];
        Item itemPai = (Item) this.heap[pai];

        int keyFilho = (Integer) itemFilho.key();
        int keyPai = (Integer) itemPai.key();

        return (keyFilho < keyPai);
    }

    private void swap(int a, int b) {
        Item temp = this.heap[a];
        this.heap[a] = this.heap[b];
        this.heap[b] = temp;
    }
}
