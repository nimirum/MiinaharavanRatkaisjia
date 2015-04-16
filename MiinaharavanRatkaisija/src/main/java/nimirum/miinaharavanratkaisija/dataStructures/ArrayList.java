package nimirum.miinaharavanratkaisija.dataStructures;

/**
 *
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
     * ArrayList konstruktori, jossa voi valita listan koon (testausta varten..)
     * @param capacity
     */
    public ArrayList(int capacity) {
        this.list = new Object[capacity];
        this.size = 0;
    }

    /**
     *
     * @return
     */
    public int size() {
        return size;
    }

    /**
     *
     * @return
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     *
     * @return
     */
    public Object[] toArray() {
        return list;
    }

    /**
     *
     * @param e
     */
    public void add(Ruutu e) {
        list[size] = e;
        size++;

    }

    /**
     *
     * @param index
     * @return
     */
    public Ruutu get(int index) {
        if (index < list.length && index >= 0) {
            return (Ruutu) list[index];
        }
        return null;
    }
}