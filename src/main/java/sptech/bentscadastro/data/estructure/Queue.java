package sptech.bentscadastro.data.estructure;

public class Queue<T> {

    // Atributos
    private int size;
    private T[]  queue;

    // Construtor
    public Queue(int capacity) {
        this.size = 0;
        this.queue = (T[]) new Object[capacity];
    }

    public int queueSize() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == queue.length;
    }

    public T peek() {
        if (isEmpty()) {
            return null;
        }
        return queue[0];
    }

    public void insert(T info) {
        if (isFull()) {
            System.out.println("Queue is full!");
        }
        else {
            queue[size++] = info;
        }
    }

    public T poll() {
        T first = peek();

        if (!isEmpty()) {
            for (int i = 0; i < size-1; i++) {
                queue[i] = queue[i+1];
            }
            queue[size-1] = null;
            size--;
        }
        return first;
    }

    public void clearQueue() {
        size = 0;
    }
}