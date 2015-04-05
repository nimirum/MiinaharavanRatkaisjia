package nimirum.miinaharavanratkaisija.dataStructures;

/**
 *
 * @author nimirum
 * @param <Object>
 */
public class ArrayList<Object> {

    private int size;

    public ArrayList(int capacity) {
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }
    
    public void add(){
        size++;
    }
}
