package sptech.bentscadastro.data.estructure;

public class Stack<T> {

    private int topo;
    private T[] pilha;

    public Stack(int capacidade) {
        pilha = (T[]) new Object[capacidade];
        topo = -1;
    }

    public boolean isEmpty() {
        return topo == -1;
    }

    public void push(T info) {
        pilha[++topo] = info;
    }

    public T pop() {
        if (isEmpty()) {
            System.out.println("Histórico vázio");
        }
        return pilha[topo--];
    }

    public void exibe() {
        if (isEmpty()) {
            System.out.println("Histórico vázio");
        } else {
            for (int i = topo; i >= 0; i--) {
                System.out.println(pilha[i]);
            }
        }
    }

    public void limpar() {
        while (!isEmpty()) {
            System.out.println("Desempilhou: " + pop());
        }
    }

    public T peek() {
        if (isEmpty()) {
            System.out.println();;
        }
        return pilha[topo];
    }

    public T[] getPilha() {
        return this.pilha;
    }

    public int getSize() {
        return topo;
    }

}
