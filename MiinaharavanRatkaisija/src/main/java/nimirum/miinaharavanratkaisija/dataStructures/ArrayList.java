package nimirum.miinaharavanratkaisija.dataStructures;

/**
 * Hyvin yksinkertainen oma toteutus ArrayListista, missä on vain kaikki tarvittavat metodit MiinaharavanRatkaisijaan
 * @author nimirum
 * @param <Ruutu>
 */
public class ArrayList<Ruutu>{

    private Object[] list;
    private int size;

    /**
     * Luo oletuksena 8 kokoisen taulukon, koska viereisten ruutujen määrä on maksimissaan 8kpl
     */
    public ArrayList() {
        this(8); 
    }

    /**
     * ArrayList konstruktori, jossa voi valita listan koon 
     * @param capacity
     */
    public ArrayList(int capacity) {
        this.list = new Object[capacity];
        this.size = 0;
    }

    /**
     * Palauttaa listan jäsenien määrän
     * @return size
     */
    public int size() {
        return size;
    }

    /**
     * Kertoo onko lista tyhjä
     * @return boolean
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Palauttaa listan taulukkona
     * @return Object[]
     */
    public Object[] toArray() {
        return list;
    }

    /**
     * Lisää uuden jäsenen listaan
     * @param e
     */
    public void add(Ruutu e) {
        list[size] = e;
        size++;

    }

    /**
     * Palauttaa listan jäsenen parametrina tulleen indexin kohdalta mikäli mahdollista
     * @param index
     * @return Ruutu
     */
    public Ruutu get(int index) {
        if (index < list.length && index >= 0) {
            return (Ruutu) list[index];
        }
        return null;
    }
}