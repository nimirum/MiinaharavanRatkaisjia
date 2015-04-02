package nimirum.miinaharava.gui;

import nimirum.miinaharava.Ruutu;
import nimirum.miinaharavanratkaisija.MiinaharavanRatkaisija;

/**
 *
 * @author nimirum
 */
public class RatkaisijanKomentaja {

    private final Kayttoliittyma kayttoliittyma;
    private final MiinaharavanRatkaisija miinaharavanRatkaisija;
    private boolean ekaSiirtoTehty = false;

    public RatkaisijanKomentaja(Kayttoliittyma kayttoliittyma, MiinaharavanRatkaisija miinaharavanRatkaisija) {
        this.kayttoliittyma = kayttoliittyma;
        this.miinaharavanRatkaisija = miinaharavanRatkaisija;
    }

    public void ratkaise() {
        if (ekaSiirtoTehty == false) {
            ekaSiirto();
            ekaSiirtoTehty = true;
        } else {
            miinaharavanRatkaisija.ratkaisePelia();
            teeYksiSiirto();
        }
    }

    public void ekaSiirto() {
        Ruutu ruutu = miinaharavanRatkaisija.ensimmainenSiirto();
        if (ruutu != null) {
            kayttoliittyma.klikkaaRuutua(ruutu);
        }
    }

    private void teeYksiSiirto() {
        Ruutu ruutu = miinaharavanRatkaisija.ratkaiseYksiSiirto();
        if (ruutu != null) {
            kayttoliittyma.klikkaaRuutua(ruutu);
        } else {
            System.out.println("Ei pysty tekemään siirtoja");
        }
    }
}
