package nimirum.miinaharavanratkaisija.dataStructures;

import nimirum.miinaharava.logiikka.Ruutu;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class ArrayListTest {

    public ArrayListTest() {
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
    public void luoArrayListJaSeOnTyhja() {
        ArrayList<Ruutu> ruudut = new ArrayList<>();
        assertEquals(ruudut.size(), 0);
        assertEquals(ruudut.isEmpty(), true);
    }

    @Test
    public void lisaysToimii() {
        ArrayList<Ruutu> ruudut = new ArrayList<>();
        ruudut.add(new Ruutu(1, 1));
        assertEquals(ruudut.get(0).getX(), 1);
        assertEquals(ruudut.get(0).getY(), 1);
    }

    @Test
    public void vaarallaIndeksillaGetReturnNull() {
        //taulukko oletuksena 8 kokoinenn, kutsutaan indexiä 9
        ArrayList<Ruutu> ruudut = new ArrayList<>();
        ruudut.add(new Ruutu(1, 1));
        assertEquals(ruudut.get(9), null);
         assertEquals(ruudut.get(-1), null);
    }

}
