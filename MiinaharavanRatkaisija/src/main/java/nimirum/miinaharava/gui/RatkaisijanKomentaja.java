package nimirum.miinaharava.gui;

import nimirum.miinaharava.logiikka.Ruutu;
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
//            boolean siirtoMahdollinen = teeYksiSiirto();
//            if (siirtoMahdollinen) {
//                System.out.println("Etsitään 11, 121 ja 1221 ratkaisuja");
//                miinaharavanRatkaisija.etsiLisaaRatkaisuja();
//                teeYksiSiirto();
//            }
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

    private boolean teeYksiSiirto() {
       // miinaharavanRatkaisija.tulostaTiedot();
        Ruutu ruutu = miinaharavanRatkaisija.getYksiRatkaistuSiirto();
        boolean pelinRatkaisuJumissa = false;
        if (ruutu != null) {
            kayttoliittyma.klikkaaRuutua(ruutu);
            pelinRatkaisuJumissa = false;
        }
        if (ruutu == null) {
            System.out.println("Etsitään 11, 121 ja 1221 ratkaisuja");
            miinaharavanRatkaisija.etsiLisaaRatkaisuja();
            Ruutu ruutuExtra = miinaharavanRatkaisija.getYksiRatkaistuSiirto();
            if (ruutuExtra != null) {
                kayttoliittyma.klikkaaRuutua(ruutuExtra);
                pelinRatkaisuJumissa = false;
            } else {
                if (miinaharavanRatkaisija.getLauta().onkoPeliPaattynyt()) {
                    System.out.println("Peli ratkaistu");
                    pelinRatkaisuJumissa = false;
                } else {
                    System.out.println("Ei pysty tekemään siirtoja");
                    pelinRatkaisuJumissa = true;
                }
                kayttoliittyma.stop();
                miinaharavanRatkaisija.tulostaTiedot();
            }
        }
        return pelinRatkaisuJumissa;
    }
}

