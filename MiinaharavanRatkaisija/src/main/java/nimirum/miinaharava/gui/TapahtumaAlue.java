package nimirum.miinaharava.gui;

import java.awt.Rectangle;
import nimirum.miinaharava.Pelilauta;
import nimirum.miinaharava.Ruutu;

/**
 * Toiminnallisia ruudun muotoisia tapahtumaalueita jotka yhdistetään
 * klikkaustenkuuntelijaan
 *
 * @author nimirum
 */
public class TapahtumaAlue extends Rectangle {

    private final Kayttoliittyma kayttoliittyma;
    private final Pelilauta miinaharavainen;
    private final Ruutu ruutu;
    private final int x;
    private final int y;

    /**
     * Luo neliön muotoisen tapahtumaalueen, kooltaan 24x24
     *
     * @param x Leveys koordinaatti
     * @param y Korkeus koordinaatti
     * @param ruutu Ruutu
     * @param miinaharavainen Miinaharavainen
     * @param kayttoliittyma Käyttöliittymä
     */
    public TapahtumaAlue(int x, int y, Ruutu ruutu, Pelilauta miinaharavainen, Kayttoliittyma kayttoliittyma) {
        super(x, y, ruutu.getRuudunLeveys(), ruutu.getRuudunKorkeus());
        this.miinaharavainen = miinaharavainen;
        this.kayttoliittyma = kayttoliittyma;
        this.ruutu = ruutu;
        this.x = x;
        this.y = y;
    }

    /**
     * Tarkistaa onko alueeseen klikattu ja toimii sen mukaan Jos pelilautaa ei
     * ole miinoitettu, se miinoitetaan ensimmäisen klikkauksen mukaan Kun ruutu
     * ei ole näkyvä, se muuttuu näkyväksi klikkauksesta Ruutu joka ei ole
     * näkyvä voi liputtaa
     *
     * @param klikkausX Leveys koordinaatti
     * @param klikkausY Korkeus koordinaatti
     */
    public void alueeseenKlikattu(int klikkausX, int klikkausY) {

        if (onkoKlikkausAlueella(klikkausX, klikkausY)) {
            if (ruutu == null) {
                return;
            }
          //  System.out.println("Klikkaus:" + klikkausX + ", " + klikkausY + "ruutuun: " + ruutu.getX() + " ," + ruutu.getY());
            miinaharavainen.paivitaKlikatutRuudut();
            if (!miinaharavainen.isMiinoitettu()) {
                miinaharavainen.miinoita(ruutu.getX(), ruutu.getY());
            }
            if (!ruutu.getOnkoRuutuAvattu() && !ruutu.isOnkoRuutuLiputettu()) {

                ruutu.setOnkoRuutuAvattu(true);
                miinaharavainen.paivitaKlikatutRuudut();

                if (ruutu.getViereistenMiinojenMaara() == 0) {
                    ruutu.avaaViereisetRuudut();
                }
                if (miinaharavainen.onkoPeliPaattynyt()) {
                    //  miinaharavainen.gameOver("Voitto");
                    // kayttoliittyma.ennatyksenTallentaminen();
                }
                if (ruutu.getOnkoRuudussaMiina()) {
                    ruutu.setKlikattuMiina(true);
                    //miinaharavainen.gameOver("Havio");
                }
            }

        }
    }

    /**
     * Avaa parametrina annetun ruudun
     * @param ruutu
     */
    public void alueeseenKlikattu(Ruutu ruutu) {
        System.out.println("Klikkaus ruutuun: " + ruutu.getX() + " ," + ruutu.getY());
        if (ruutu.isOnkoRuutuLiputettu() == false) {
            ruutu.setOnkoRuutuAvattu(true);
            miinaharavainen.paivitaKlikatutRuudut();
            
            if (!miinaharavainen.isMiinoitettu()) {
                miinaharavainen.miinoita(ruutu.getX(), ruutu.getY());
            }
            ruutu.setOnkoRuutuAvattu(true);
            miinaharavainen.paivitaKlikatutRuudut();

            if (ruutu.getViereistenMiinojenMaara() == 0) {
                ruutu.avaaViereisetRuudut();
            }
            if (ruutu.getOnkoRuudussaMiina()) {
                ruutu.setKlikattuMiina(true);
                //miinaharavainen.gameOver("Havio");
            }
        }
    }

    private boolean onkoKlikkausAlueella(int x, int y) {
        return (this.x + ruutu.getRuudunLeveys() > x && this.x < x && this.y + ruutu.getRuudunLeveys() > y && this.y < y);
    }

    /**
     * Liputtaa halutun ruudun(jos sallittua), joka on parametreina saaduissa
     * koordinaateissa
     *
     * @param x Leveys koordinaatti
     * @param y Korkeus koordinaatti
     */
    public void alueenLiputus(int x, int y) {
        
        if (onkoKlikkausAlueella(x, y)) {
            if (ruutu == null) {
                return;
            }
            if (!ruutu.getOnkoRuutuAvattu()) {
                if (!ruutu.isOnkoRuutuLiputettu()) {
                    ruutu.setOnkoRuutuLiputettu(true);
                } else {
                    ruutu.setOnkoRuutuLiputettu(false);
                }
            }
        }
    }

    /**
     * Liputtaa parametrina annetun ruudun
     * @param ruutu
     */
    public void alueenLiputus(Ruutu ruutu) {    
        System.out.println("Liputus ruutuun: " + ruutu.getX() + " ," + ruutu.getY());
        if (!ruutu.getOnkoRuutuAvattu()) {
            if (ruutu.isOnkoRuutuLiputettu()) {
                ruutu.setOnkoRuutuLiputettu(true);
            } else {
                ruutu.setOnkoRuutuLiputettu(false);
            }
        }
    }
}
