package nimirum.miinaharavanratkaisija.dataStructures;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 *
 * @author nimirum
 */
public class ArrayList<Ruutu> implements Collection<Ruutu>, List<Ruutu> {

    private Object[] list;
    private int size;

    public ArrayList() {
        this(10);
    }

    public ArrayList(int size) {
        this.list = new Object[size];
        this.size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Object[] toArray() {
        return list;
    }

    @Override
    public boolean add(Ruutu e) {
        if (size >= list.length) {
            list = Arrays.copyOf(list, size * 2);
        }
        list[size] = e;
        size++;

        return true;
    }

    @Override
    public void clear() {
        size = 0;
    }

    @Override
    public Ruutu get(int index) {
        if (index < list.length && index >= 0) {
            return (Ruutu) list[index];
        }
        return null;
    }

    @Override
    public boolean contains(Object o) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Ruutu set(int index, Ruutu element) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Iterator<Ruutu> iterator() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean addAll(Collection<? extends Ruutu> c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean addAll(int index, Collection<? extends Ruutu> c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void add(int index, Ruutu element) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Ruutu remove(int index) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int indexOf(Object o) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ListIterator<Ruutu> listIterator() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ListIterator<Ruutu> listIterator(int index) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Ruutu> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
