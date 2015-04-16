package nimirum.miinaharavanratkaisija.dataStructures;

import nimirum.miinaharava.logiikka.Ruutu;

/**
 *
 * @author nimirum
 */

public class ArrayDeque{

    //private int numElements;
    private Ruutu[] elements;
    private int size;
    private int head;
    private int tail;

    public ArrayDeque(int capacity) {
        this.elements = new Ruutu[capacity];
        size = 0;
        head = 0;
        tail = 0;
    }

    public ArrayDeque() {
        this(16);
    }

    public void push(Ruutu e) {
        if (e == null) {
            throw new NullPointerException();
        }
        if (size == elements.length-1) {
            doubleCapacity();
        }
        elements[tail] = e;
        tail++;
        size++;
    }

    public Ruutu poll() {
        Ruutu result = elements[head];
        if (result == null) {
            return null;
        }

        elements[head] = null;
        head = (head + 1);// & (elements.length - 1);
        tail = (tail - 1);// & (elements.length - 1);
        size--;
        return result;
    }

    private void doubleCapacity() {
        Ruutu[] copy = new Ruutu[elements.length * 2];

        for (int i = 0; i < elements.length; i++) {
            copy[i] = elements[i];
        }

        elements = copy;

        head = 0;
        //tail = size - 1;
    }

    public int size() {
        return size;
    }
    
    public Ruutu[] toArray(){
        return elements;
    }

}