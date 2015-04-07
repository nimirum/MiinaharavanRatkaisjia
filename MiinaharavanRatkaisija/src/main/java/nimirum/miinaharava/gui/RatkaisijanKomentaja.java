package nimirum.miinaharava.gui;

import nimirum.miinaharava.Ruutu;
import nimirum.miinaharavanratkaisija.MiinaharavanRatkaisija;

/**
 * RatkaisijanKomentaja käskee ratkaisijaa tekemään siirtoja ja välittää
 * ratkaisijan tekemät siirrot käyttöliittymälle.
 *
 * @author nimirum
 */
public class RatkaisijanKomentaja {

    private final Kayttoliittyma kayttoliittyma;
    private final MiinaharavanRatkaisija miinaharavanRatkaisija;

    /**
     *
     * @param kayttoliittyma
     * @param miinaharavanRatkaisija
     */
    public RatkaisijanKomentaja(Kayttoliittyma kayttoliittyma, MiinaharavanRatkaisija miinaharavanRatkaisija) {
        this.kayttoliittyma = kayttoliittyma;
        this.miinaharavanRatkaisija = miinaharavanRatkaisija;
    }

    /**
     * Ohjaa MiinaharvanRatkaisijaa tekemään siirtoja riippuen onko ensimmäinen
     * siirto jo tehty vai ei
     */
    public void ratkaise() {
        if (miinaharavanRatkaisija.getLauta().isMiinoitettu() == false) {
            ekaSiirto();
        } else {
            miinaharavanRatkaisija.ratkaisePelia();
            teeYksiSiirto();
        }

    }

    /**
     * Hakee ratkaisilta ensimmäisen siiroon ja välittää sen käyttöliittymälle
     */
    public void ekaSiirto() {
        Ruutu ruutu = miinaharavanRatkaisija.ensimmainenSiirto();
        if (ruutu != null) {
            kayttoliittyma.klikkaaRuutua(ruutu);
        }
        miinaharavanRatkaisija.ratkaisePelia();
    }

    private void teeYksiSiirto() {
        Ruutu ruutu = miinaharavanRatkaisija.getYksiRatkaistuSiirto();
        if (ruutu != null) {
            kayttoliittyma.klikkaaRuutua(ruutu);
        } else {
            if (miinaharavanRatkaisija.getLauta().onkoPeliPaattynyt()) {
                System.out.println("Peli ratkaistu");
            } else {
                System.out.println("Ei pysty tekemään siirtoja");
            }
            kayttoliittyma.stop();
            // miinaharavanRatkaisija.tulostaTiedot();
        }
    }
}
