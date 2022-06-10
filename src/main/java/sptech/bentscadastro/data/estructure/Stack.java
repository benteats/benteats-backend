package sptech.bentscadastro.data.estructure;

public class Stack {

    private int topo;
    private int[] pilha;

    public Stack(int capacidade) {
        pilha = new int[capacidade];
        topo = -1;
    }

    public boolean isEmpty() {
        return topo == -1;
    }

    public void push(int info) {
        pilha[++topo] = info;
    }

    public int pop() {
        if (isEmpty()) {
            System.out.println("Histórico vázio");
        }
        return pilha[topo--];
    }

    public void exibe() {
        if (isEmpty()) {
            System.out.println("Histórico vázio");
        }
        else {
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

}
