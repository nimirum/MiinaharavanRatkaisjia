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
    public void luoArrayList() {
        ArrayList<Ruutu> ruudut = new ArrayList<>();
        assertEquals(ruudut.size(), 0);
        assertEquals(ruudut.isEmpty(), true);
    }
    
    @Test
    public void lisaysToimii(){
        ArrayList<Ruutu> ruudut = new ArrayList<>();
        ruudut.add(new Ruutu(1,1));
        assertEquals(ruudut.get(0).getX(), 1);
        assertEquals(ruudut.get(0).getY(), 1);
    }
    
}
