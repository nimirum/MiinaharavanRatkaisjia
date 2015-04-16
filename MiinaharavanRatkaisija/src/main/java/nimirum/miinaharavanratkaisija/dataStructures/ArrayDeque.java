package nimirum.miinaharavanratkaisija.dataStructures;

import nimirum.miinaharava.logiikka.Ruutu;

/**
 * Itse toteutettu jono
 * @author nimirum
 */

public class ArrayDeque{

    private Ruutu[] elements;
    private int size;
    private int head;
    private int tail;

    /**
     * Konstruktori, joka luo syötteen mukaisen kokoisen jonon
     * @param capacity
     */
    public ArrayDeque(int capacity) {
        this.elements = new Ruutu[capacity];
        size = 0;
        head = 0;
        tail = 0;
    }

    /**
     * Jono, jonka koko on 16
     */
    public ArrayDeque() {
        this(16);
    }

    /**
     * Lisää jonon perälle uuden jäsenen
     * @param e
     */
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

    /**
     * Palauttaa jonossa pisimpään olleen jäsenen
     * @return
     */
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

    /**
     * Palauttaa jonon sisällä olevien jäsenien määrän
     * @return
     */
    public int size() {
        return size;
    }
    
    /**
     * Palauttaa jonon
     * @return
     */
    public Ruutu[] toArray(){
        return elements;
    }

}