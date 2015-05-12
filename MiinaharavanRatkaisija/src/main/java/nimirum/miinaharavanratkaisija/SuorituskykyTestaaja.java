package nimirum.miinaharavanratkaisija;

import nimirum.miinaharava.logiikka.Pelilauta;
import nimirum.miinaharava.logiikka.Ruutu;

/**
 * MiinaharavanRatkaisijan nopeuden testausta varten
 *
 * @author nimirum
 */
public class SuorituskykyTestaaja {

    private MiinaharavanRatkaisija ratkaisija;
    private boolean ratkaise = true;
    private boolean ratkaistu = false;
    long stop;
    long start;
    private int x;
    private int y;

    /**
     * Konstruktori
     */
    public SuorituskykyTestaaja(int x, int y) {
        this.ratkaisija = new MiinaharavanRatkaisija(x, y);
        this.x = x;
        this.y = y;
    }

    /**
     * Ratkaisee yhden pelilaudan niin pitkälle kuin pystyy
     */
    public void ratkaise() {
        ratkaise = true;
        start = System.nanoTime();
        while (ratkaise) {
            if (ratkaisija.getLauta().isMiinoitettu() == false) {
                ekaSiirto();
            } else {
                ratkaisija.ratkaisePelia();
                teeYksiSiirto();
            }
        }
        long elapsedTime = stop - start;
        System.out.println(elapsedTime / 1000000000.0);
        System.out.println(ratkaisija.laskuri);
    }

    /**
     * Ratkaisee pelilautoja, kunnes ratkaisija saa ratkaistua yhden pelilaudan kokonaan.
     */
    public void ratkaiseKunnesRatkaistu() {
        while (!ratkaistu) {
            ratkaisija = new MiinaharavanRatkaisija(x, y);
            ratkaise();
            System.out.println("");
            System.out.println("Uusi peli");
        }
    }

    private void ekaSiirto() {
        Ruutu ruutu = ratkaisija.ensimmainenSiirto();
        if (ruutu != null) {
            klikkausRuutuun(ruutu);
        }
        ratkaisija.ratkaisePelia();
    }

    private void teeYksiSiirto() {
        Ruutu ruutu = ratkaisija.getYksiRatkaistuSiirto();
        if (ruutu != null) {
            klikkausRuutuun(ruutu);
        } else {
            ratkaise = false;
            stop = System.nanoTime();
           // ratkaisija.tulostaTiedot();
            if (ratkaisija.getLauta().onkoPeliPaattynyt()) {
                System.out.println("Peli ratkaistu");
                ratkaistu = true;
            } else {
                System.out.println("Ei pysty tekemään siirtoja");
            }

        }
//        Ruutu ruutu = miinaharavanRatkaisija.getYksiRatkaistuSiirto();
//        boolean pelinRatkaisuJumissa = false;
//        if (ruutu != null) {
//            kayttoliittyma.klikkaaRuutua(ruutu);
//            pelinRatkaisuJumissa = false;
//        }
//        if (ruutu == null) {
//            System.out.println("Etsitään 11, 121 ja 1221 ratkaisuja");
//            miinaharavanRatkaisija.etsiLisaaRatkaisuja();
//            Ruutu ruutuExtra = miinaharavanRatkaisija.getYksiRatkaistuSiirto();
//            if (ruutuExtra != null) {
//                kayttoliittyma.klikkaaRuutua(ruutuExtra);
//                pelinRatkaisuJumissa = false;
//            } else {
//                if (miinaharavanRatkaisija.getLauta().onkoPeliPaattynyt()) {
//                    System.out.println("Peli ratkaistu");
//                    pelinRatkaisuJumissa = false;
//                } else {
//                    System.out.println("Ei pysty tekemään siirtoja");
//                    pelinRatkaisuJumissa = true;
//                }
//                kayttoliittyma.stop();
//                miinaharavanRatkaisija.tulostaTiedot();
//            }
//        }
    }

    private void klikkausRuutuun(Ruutu ruutu) {
        ratkaisija.getLauta().paivitaKlikatutRuudut();
        if (!ruutu.getOnkoRuutuAvattu() && !ruutu.isOnkoRuutuLiputettu()) {

            ruutu.setOnkoRuutuAvattu(true);
            ratkaisija.getLauta().paivitaKlikatutRuudut();

            if (ruutu.getViereistenMiinojenMaara() == 0) {
                ruutu.avaaViereisetRuudut();
            }
            if (ratkaisija.getLauta().onkoPeliPaattynyt()) {
                ratkaistu = true;
                //System.out.println("Voitto");
            }
            if (ruutu.getOnkoRuudussaMiina()) {
                ruutu.setKlikattuMiina(true);
                //   System.out.println("Havio");
            }
        }
    }

}
