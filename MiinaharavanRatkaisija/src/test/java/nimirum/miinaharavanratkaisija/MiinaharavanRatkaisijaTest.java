package nimirum.miinaharavanratkaisija;

import nimirum.miinaharava.logiikka.Pelilauta;
import nimirum.miinaharava.logiikka.Ruutu;
import nimirum.miinaharavanratkaisija.dataStructures.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class MiinaharavanRatkaisijaTest {

    private MiinaharavanRatkaisija ratkaisija;

    public MiinaharavanRatkaisijaTest() {
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
    public void pelilaudanMiinoittaminen() {
        ratkaisija = new MiinaharavanRatkaisija(8, 8);
        assertEquals(false, ratkaisija.getLauta().isMiinoitettu());
        Ruutu ruutu = ratkaisija.ensimmainenSiirto();
        assertEquals((8 / 2), ruutu.getX());
        assertEquals((8 / 2), ruutu.getY());
    }

    @Test
    public void ensimmainenSiirto() {
        ratkaisija = new MiinaharavanRatkaisija(8, 8);
        ratkaisija.ensimmainenSiirto();
        String[][] tiedot = ratkaisija.ratkaisuTiedotTaulukossa();
        assertEquals("0", tiedot[8 / 2][8 / 2]);
    }

    @Test
    public void toimiikoPelinRatkaiseminenEkaSiirto() {
        ratkaisija = new MiinaharavanRatkaisija(8, 8);
        Ruutu ekaruutu = ratkaisija.ensimmainenSiirto();
        Pelilauta lauta = ratkaisija.getLauta();
        lauta.klikkausRuutuun(ekaruutu.getX(), ekaruutu.getY());
        // ratkaisija.ratkaisePelia();
        ratkaisija.paivitaTiedotPelikentasta();
        String[][] tiedot = ratkaisija.ratkaisuTiedotTaulukossa();
        //  ratkaisija.tulostaTiedot();
        for (int i = 0; i < lauta.getX(); i++) {
            for (int j = 0; j < lauta.getY(); j++) {
                Ruutu ruutu = lauta.getRuutu(i, j);
                if (ruutu.isOnkoRuutuLiputettu()) {
                    assertEquals("x", tiedot[i][j]);
                } else if (!ruutu.getOnkoRuutuAvattu()) {
                    assertEquals("A", tiedot[i][j]);
                } else if (!ruutu.getOnkoRuudussaMiina() && ruutu.getOnkoRuutuAvattu()) {
                    assertEquals("" + ruutu.getViereistenMiinojenMaara(), tiedot[i][j]);
                }
            }
        }
    }

    @Test
    public void pelinRatkaisu() {
        ratkaisija = new MiinaharavanRatkaisija(10, 10);
        // Ruutu ekaruutu = ratkaisija.ensimmainenSiirto();
        ArrayList<Ruutu> miinat = new ArrayList(16);
        miinat.add(new Ruutu(9, 3));
        miinat.add(new Ruutu(9, 6));
        miinat.add(new Ruutu(0, 4));
        miinat.add(new Ruutu(7, 0));
        miinat.add(new Ruutu(1, 4));
        miinat.add(new Ruutu(2, 8));
        miinat.add(new Ruutu(6, 0));
        miinat.add(new Ruutu(2, 0));
        miinat.add(new Ruutu(7, 4));
        miinat.add(new Ruutu(3, 0));
        miinat.add(new Ruutu(4, 0));
        miinat.add(new Ruutu(3, 1));
        miinat.add(new Ruutu(1, 6));
        miinat.add(new Ruutu(7, 3));
        miinat.add(new Ruutu(0, 9));
        Pelilauta lauta = ratkaisija.getLauta();
        lauta.miinoita(miinat);
        lauta.klikkausRuutuun(5, 5);

        while (true) {
            ratkaisija.ratkaisePelia();
            Ruutu ruutu = ratkaisija.getYksiRatkaistuSiirto();
            if (ruutu != null) {
                lauta.klikkausRuutuun(ruutu.getX(), ruutu.getY());
            } else {
                break;
            }
        }
        ratkaisija.paivitaTiedotPelikentasta();
        String[][] tiedot = ratkaisija.ratkaisuTiedotTaulukossa();
        lauta.avaaKaikkiRuudut();
        for (int i = 0; i < lauta.getX(); i++) {
            for (int j = 0; j < lauta.getY(); j++) {
                Ruutu ruutu = lauta.getRuutu(i, j);
                if (ruutu.getOnkoRuudussaMiina() && ruutu.isOnkoRuutuLiputettu()) {
                    assertEquals("x", tiedot[i][j]);
                } else if (!ruutu.getOnkoRuutuAvattu()) {
                    assertEquals("A", tiedot[i][j]);
                } else if (!ruutu.getOnkoRuudussaMiina() && ruutu.getOnkoRuutuAvattu()) {
                    assertEquals("" + ruutu.getViereistenMiinojenMaara(), tiedot[i][j]);
                }
            }

        }
    }

    @Test
    public void ratkaisijanToimivuus121tilanneAlaspain() {
        ratkaisija = new MiinaharavanRatkaisija(3, 3);
        ArrayList<Ruutu> miinat = new ArrayList(2);
        miinat.add(new Ruutu(2, 0));
        miinat.add(new Ruutu(2, 2));
        Pelilauta lauta = ratkaisija.getLauta();
        lauta.miinoita(miinat);
        lauta.klikkausRuutuun(0, 0);
        ratkaisija.ratkaisePelia();

        ratkaisija.etsiLisaaRatkaisuja();
        Ruutu ruutu = ratkaisija.getYksiRatkaistuSiirto();
        if (ruutu != null) {
            lauta.klikkausRuutuun(ruutu.getX(), ruutu.getY());
        }
        assertEquals(lauta.getRuutu(2, 1).getOnkoRuutuAvattu(), true);
    }

    @Test
    public void ratkaisijanToimivuus121tilanneYlospain() {
        ratkaisija = new MiinaharavanRatkaisija(3, 3);
        ArrayList<Ruutu> miinat = new ArrayList(2);
        miinat.add(new Ruutu(0, 0));
        miinat.add(new Ruutu(0, 2));
        Pelilauta lauta = ratkaisija.getLauta();
        lauta.miinoita(miinat);
        lauta.klikkausRuutuun(2, 0);
        ratkaisija.ratkaisePelia();

        ratkaisija.etsiLisaaRatkaisuja();
        Ruutu ruutu = ratkaisija.getYksiRatkaistuSiirto();
        if (ruutu != null) {
            lauta.klikkausRuutuun(ruutu.getX(), ruutu.getY());
        }
        assertEquals(lauta.getRuutu(0, 1).getOnkoRuutuAvattu(), true);
    }

    @Test
    public void ratkaisijanToimivuus121tilanneOikealle() {
        ratkaisija = new MiinaharavanRatkaisija(3, 3);
        ArrayList<Ruutu> miinat = new ArrayList(2);
        miinat.add(new Ruutu(0, 2));
        miinat.add(new Ruutu(2, 2));
        Pelilauta lauta = ratkaisija.getLauta();
        lauta.miinoita(miinat);
        lauta.klikkausRuutuun(0, 0);
        ratkaisija.ratkaisePelia();

        ratkaisija.etsiLisaaRatkaisuja();
        Ruutu ruutu = ratkaisija.getYksiRatkaistuSiirto();
        if (ruutu != null) {
            lauta.klikkausRuutuun(ruutu.getX(), ruutu.getY());
        }
        assertEquals(lauta.getRuutu(1, 2).getOnkoRuutuAvattu(), true);
    }

    @Test
    public void ratkaisijanToimivuus121tilanneVasemmalle() {
        ratkaisija = new MiinaharavanRatkaisija(3, 3);
        ArrayList<Ruutu> miinat = new ArrayList(2);
        miinat.add(new Ruutu(0, 0));
        miinat.add(new Ruutu(2, 0));
        Pelilauta lauta = ratkaisija.getLauta();
        lauta.miinoita(miinat);
        lauta.klikkausRuutuun(2, 0);
        ratkaisija.ratkaisePelia();

        ratkaisija.etsiLisaaRatkaisuja();
        Ruutu ruutu = ratkaisija.getYksiRatkaistuSiirto();
        if (ruutu != null) {
            lauta.klikkausRuutuun(ruutu.getX(), ruutu.getY());
        }
        assertEquals(lauta.getRuutu(1, 0).getOnkoRuutuAvattu(), true);
    }

    @Test
    public void ratkaisijanToimivuus1221tilanneOikealle() {
        ratkaisija = new MiinaharavanRatkaisija(3, 4);
        ArrayList<Ruutu> miinat = new ArrayList(2);
        miinat.add(new Ruutu(2, 1));
        miinat.add(new Ruutu(2, 2));
        Pelilauta lauta = ratkaisija.getLauta();
        lauta.miinoita(miinat);
        lauta.klikkausRuutuun(0, 0);
        ratkaisija.ratkaisePelia();

        ratkaisija.etsiLisaaRatkaisuja();
        Ruutu ruutu = ratkaisija.getYksiRatkaistuSiirto();
        if (ruutu != null) {
            lauta.klikkausRuutuun(ruutu.getX(), ruutu.getY());
        }
        Ruutu ruutu2 = ratkaisija.getYksiRatkaistuSiirto();
        if (ruutu != null) {
            lauta.klikkausRuutuun(ruutu2.getX(), ruutu2.getY());
        }

        assertEquals(lauta.getRuutu(2, 0).getOnkoRuutuAvattu(), true);
        assertEquals(lauta.getRuutu(2, 3).getOnkoRuutuAvattu(), true);
    }

    @Test
    public void ratkaisijanToimivuus1221tilanneVasemmalle() {
        ratkaisija = new MiinaharavanRatkaisija(3, 4);
        ArrayList<Ruutu> miinat = new ArrayList(2);
        miinat.add(new Ruutu(0, 1));
        miinat.add(new Ruutu(0, 2));
        Pelilauta lauta = ratkaisija.getLauta();
        lauta.miinoita(miinat);
        lauta.klikkausRuutuun(2, 0);
        ratkaisija.ratkaisePelia();

        ratkaisija.etsiLisaaRatkaisuja();
        Ruutu ruutu = ratkaisija.getYksiRatkaistuSiirto();
        if (ruutu != null) {
            lauta.klikkausRuutuun(ruutu.getX(), ruutu.getY());
        }
        Ruutu ruutu2 = ratkaisija.getYksiRatkaistuSiirto();
        if (ruutu != null) {
            lauta.klikkausRuutuun(ruutu2.getX(), ruutu2.getY());
        }

        assertEquals(lauta.getRuutu(0, 0).getOnkoRuutuAvattu(), true);
        assertEquals(lauta.getRuutu(0, 3).getOnkoRuutuAvattu(), true);
    }

    @Test
    public void ratkaisijanToimivuus1221tilanneYlospain() {
        ratkaisija = new MiinaharavanRatkaisija(4, 3);
        ArrayList<Ruutu> miinat = new ArrayList(2);
        miinat.add(new Ruutu(1, 0));
        miinat.add(new Ruutu(2, 0));
        Pelilauta lauta = ratkaisija.getLauta();
        lauta.miinoita(miinat);
        lauta.klikkausRuutuun(0, 2);
        ratkaisija.ratkaisePelia();

        ratkaisija.etsiLisaaRatkaisuja();
        Ruutu ruutu = ratkaisija.getYksiRatkaistuSiirto();
        if (ruutu != null) {
            lauta.klikkausRuutuun(ruutu.getX(), ruutu.getY());
        }
        Ruutu ruutu2 = ratkaisija.getYksiRatkaistuSiirto();
        if (ruutu != null) {
            lauta.klikkausRuutuun(ruutu2.getX(), ruutu2.getY());
        }

        assertEquals(lauta.getRuutu(0, 0).getOnkoRuutuAvattu(), true);
        assertEquals(lauta.getRuutu(3, 0).getOnkoRuutuAvattu(), true);
    }

    @Test
    public void ratkaisijanToimivuus1221tilanneAlaspain() {
        ratkaisija = new MiinaharavanRatkaisija(4, 3);
        ArrayList<Ruutu> miinat = new ArrayList(2);
        miinat.add(new Ruutu(1, 2));
        miinat.add(new Ruutu(2, 2));
        Pelilauta lauta = ratkaisija.getLauta();
        lauta.miinoita(miinat);
        lauta.klikkausRuutuun(0, 0);
        ratkaisija.ratkaisePelia();

        ratkaisija.etsiLisaaRatkaisuja();
        Ruutu ruutu = ratkaisija.getYksiRatkaistuSiirto();
        if (ruutu != null) {
            lauta.klikkausRuutuun(ruutu.getX(), ruutu.getY());
        }
        Ruutu ruutu2 = ratkaisija.getYksiRatkaistuSiirto();
        if (ruutu != null) {
            lauta.klikkausRuutuun(ruutu2.getX(), ruutu2.getY());
        }

        assertEquals(lauta.getRuutu(0, 2).getOnkoRuutuAvattu(), true);
        assertEquals(lauta.getRuutu(3, 2).getOnkoRuutuAvattu(), true);
    }

    @Test
    public void ratkaisijanToimivuus11TilanneAlaspain() {
        ratkaisija = new MiinaharavanRatkaisija(5, 3);
        ArrayList<Ruutu> miinat = new ArrayList(2);
        miinat.add(new Ruutu(0, 2));
        miinat.add(new Ruutu(3, 2));
        Pelilauta lauta = ratkaisija.getLauta();
        lauta.miinoita(miinat);
        lauta.klikkausRuutuun(0, 0);
        ratkaisija.ratkaisePelia();
        ratkaisija.etsiLisaaRatkaisuja();
        Ruutu ruutu = ratkaisija.getYksiRatkaistuSiirto();
        if (ruutu != null) {
            lauta.klikkausRuutuun(ruutu.getX(), ruutu.getY());
        }
        assertEquals(lauta.getRuutu(2, 2).getOnkoRuutuAvattu(), true);
    }

    @Test
    public void ratkaisijanToimivuus11TilanneYlospain() {
        ratkaisija = new MiinaharavanRatkaisija(5, 3);
        ArrayList<Ruutu> miinat = new ArrayList(2);
        miinat.add(new Ruutu(0, 0));
        miinat.add(new Ruutu(3, 0));
        Pelilauta lauta = ratkaisija.getLauta();
        lauta.miinoita(miinat);
        lauta.klikkausRuutuun(0, 2);
        ratkaisija.ratkaisePelia();

        ratkaisija.etsiLisaaRatkaisuja();
        Ruutu ruutu = ratkaisija.getYksiRatkaistuSiirto();
        if (ruutu != null) {
            lauta.klikkausRuutuun(ruutu.getX(), ruutu.getY());
            System.out.println(ruutu);
        }
        assertEquals(lauta.getRuutu(2, 0).getOnkoRuutuAvattu(), true);
    }

}
