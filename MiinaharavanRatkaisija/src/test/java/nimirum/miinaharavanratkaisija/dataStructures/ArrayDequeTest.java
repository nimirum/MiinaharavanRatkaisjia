package nimirum.miinaharavanratkaisija.dataStructures;

import nimirum.miinaharava.logiikka.Ruutu;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author nimirum
 */
public class ArrayDequeTest {

    public ArrayDequeTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void lisaysToimii() {
        ArrayDeque jono = new ArrayDeque(2);
        assertEquals(0, jono.size());
        jono.push(new Ruutu(1, 1));
        assertEquals(1, jono.size());
        jono.poll();
        assertEquals(0, jono.size());
    }

    @Test
    public void jononKasvatusToimii() {
        ArrayDeque jono = new ArrayDeque(2);
        assertEquals(0, jono.size());
        jono.push(new Ruutu(1, 1));
        jono.push(new Ruutu(2, 1));
        assertEquals(2, jono.size());
        assertEquals(4, jono.toArray().length);
        jono.poll();
        assertEquals(1, jono.size());
    }

    @Test
    public void jonoTayteenJaTyhjaksi() {
        ArrayDeque jono = new ArrayDeque(2);
        assertEquals(0, jono.size());
        jono.push(new Ruutu(1, 1));
        jono.push(new Ruutu(2, 1));
        assertEquals(2, jono.size());
        assertEquals(4, jono.toArray().length);
        jono.poll();
        jono.poll();
        assertEquals(0, jono.size());
        jono.push(new Ruutu(1, 1));
        jono.push(new Ruutu(2, 1));
        assertEquals(2, jono.size());
    }
    
    @Test
    public void vakioKonstrtuktoriToimii(){
          ArrayDeque jono = new ArrayDeque();
          assertEquals(0, jono.size());
          assertEquals(16, jono.toArray().length);
    }
}
//
//
//        ArrayDeque jono = new ArrayDeque(2);
//
//        Ruutu[] lista = jono.toArray();
//
//        jono.push(new Ruutu(1, 1));
//        jono.push(new Ruutu(2, 1));
//        jono.push(new Ruutu(3, 1));
//        jono.push(new Ruutu(4, 1));
//        jono.push(new Ruutu(5, 1));
//        lista = jono.toArray();
//        for (int i = 0; i < lista.length; i++) {
//            if (lista[i] != null) {
//                System.out.println("paikka: " + i + " " + lista[i].getX() + ", " + lista[i].getY());
//            } else {
//                System.out.println("paikka: " + i + " null");
//            }
//        }
//        System.out.println("---------------");
//        jono.poll();
//
//        for (int i = 0; i < lista.length; i++) {
//            if (lista[i] != null) {
//                System.out.println("paikka: " + i + " " + lista[i].getX() + ", " + lista[i].getY());
//            } else {
//                System.out.println("paikka: " + i + " null");
//            }
//        }
//
//        System.out.println("---------------");
//        jono.poll();
//
//        for (int i = 0; i < lista.length; i++) {
//            if (lista[i] != null) {
//                System.out.println("paikka: " + i + " " + lista[i].getX() + ", " + lista[i].getY());
//            } else {
//                System.out.println("paikka: " + i + " null");
//            }
//        }
//
//        System.out.println("---------------");
//        jono.poll();
//
//        for (int i = 0; i < lista.length; i++) {
//            if (lista[i] != null) {
//                System.out.println("paikka: " + i + " " + lista[i].getX() + ", " + lista[i].getY());
//            } else {
//                System.out.println("paikka: " + i + " null");
//            }
//        }
//        jono.push(new Ruutu(1, 1));
//        jono.poll();
//        jono.poll();
//        for (int i = 0; i < lista.length; i++) {
//            if (lista[i] != null) {
//                System.out.println("paikka: " + i + " " + lista[i].getX() + ", " + lista[i].getY());
//            } else {
//                System.out.println("paikka: " + i + " null");
//            }
//        }
//
