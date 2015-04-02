package nimirum.miinaharavanratkaisija;

import java.util.ArrayDeque;
import java.util.ArrayList;
import nimirum.miinaharava.Pelilauta;
import nimirum.miinaharava.Ruutu;

/**
 * MiinaharavanRatkaisija
 *
 * @author nimirum
 */
public class MiinaharavanRatkaisija {

    private final Pelilauta lauta;
    private boolean ruudut[][]; //tieto avatuista(true) ja suljetuista(false) ruuduista
    private boolean miinat[][]; //tekoälyn laskemat paikat miinoille (true)=miina
    private int viereistenMiinojenMaara[][]; //avattujen ruutujen viereisten miinojen määrä ruuduissa
    private ArrayDeque<Ruutu> jonoSiirrettavista;

    /**
     * 10x10 pelikenttä
     */
    public MiinaharavanRatkaisija() {
        this(10, 10);
    }

    /**
     * Parametrien kokoinen pelikenttä, arvot väliltä 8-40 Alustaa miinaharavan
     * ratkaisua varten kolme taulukkoa joiden tietoja tarvitsee ratkaisuun.
     * Ruudut[][], missä on tieto onko ruutuja avattu (true = avattu ruutu)
     * Miinat[][], missä on tieto miinojen paikoista (true = ruudussa on
     * algoritmin mukaan miina) ViereistenMiinojenaara[][], missä on ruudun
     * tieto viereisistä löytämättömistä miinoista, luku muuttuu peliä
     * ratkaistessa sitä mukaan kun miinoja merkataan ja löytyy
     *
     * @param x Pelilaudan leveys
     * @param y Pelilaudan korkeus
     */
    public MiinaharavanRatkaisija(int x, int y) {
        lauta = new Pelilauta(x, y);

        ruudut = new boolean[x][y];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                ruudut[i][j] = false;
            } //kaikki ruudut aluksi suljettu
        }

        miinat = new boolean[x][y];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                miinat[i][j] = false;
                //Miinojen sijainneista ei aluksi tiedetä mitään
            }
        }
        viereistenMiinojenMaara = new int[x][y];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                viereistenMiinojenMaara[i][j] = -1;
                //Viereisten miinojen määriä ei vielä tiedetä, joten -1
            }
        }
        jonoSiirrettavista = new ArrayDeque();

    }

    /**
     * Miinaharvan pelilauta
     *
     * @return Pelilauta
     */
    public Pelilauta getLauta() {
        return lauta;
    }

    /**
     * Metodi joka käynnistää pelilaudan ratkaisemisen niillä tiedoilla mitkä on
     * ja ratkaisee niin pitkälle kuin pystyy
     */
    public void ratkaisePelia() {
        etsiTaysinVarmatMiinat();
    }

    public Ruutu ensimmainenSiirto() {
        lauta.miinoita(lauta.getX() / 2, lauta.getY() / 2); // tekoölyn aloitusklikkaus aina keskelle, sen jälkeen tapahtuu miinoitus
        paivitaTiedotPelikentasta();
        return lauta.getRuutu(lauta.getX() / 2, lauta.getY() / 2);
    }

    public Ruutu ratkaiseYksiSiirto() {
        //if siirto mahdollinen, palauta Ruutu johon siirto tapahtuu
        return jonoSiirrettavista.poll();
        //jos ei mahdollinen, return null;
    }

    private void paivitaTiedotPelikentasta() {
        //Käytetään vain ekalla kerralla ekan klikkauksen jälkeen
        for (int i = 0; i < lauta.getX(); i++) {
            for (int j = 0; j < lauta.getY(); j++) {
                if (!lauta.getRuutu(i, j).getOnkoRuutuAvattu()) {
                    ruudut[i][j] = true;
                    viereistenMiinojenMaara[i][j] = lauta.getRuutu(i, j).getViereistenMiinojenMaara();
                }
            }
        }
    }

    private void etsiTaysinVarmatMiinat() {
        for (int i = 0; i < lauta.getX(); i++) {
            for (int j = 0; j < lauta.getY(); j++) {
                int avaamattomatRuudut = laskeVieressaAvaamattomienRuutujenMaara(i, j);
                if (ruudut[i][j] == true && ((viereistenMiinojenMaara[i][j] == 1 && avaamattomatRuudut == 1) || (viereistenMiinojenMaara[i][j] == 2 && avaamattomatRuudut == 2) || (viereistenMiinojenMaara[i][j] == 3 && avaamattomatRuudut == 3))) {
                    //merkkaa varmat avaamattomat ruutu miinaksi, koska ne ei voi olla mitään muutakaan
                    ArrayList<Ruutu> list = palautaAvaamattomatViereisetRuudut(i, j);
                    for (Ruutu ruutu : list) {
                        jonoSiirrettavista.add(ruutu);
                        miinat[ruutu.getX()][ruutu.getY()] = true;
                        ruutu.setOnkoRuutuLiputettu(true);
                        muutaviereisetRuutujenArvoYhdenPienemmäksi(ruutu.getX(), ruutu.getY());
                        //Ja myös avaa ne ruudut jos arvo 0
                    }
                    //eli varma miina jos muita miinoja ei merkattu
                    //Muuta ruudun tieto viereistenmiinojen määrä yhden pienemmäksi
                }
            }
        }
    }

    private void avaaNollat() {
        //Ei tarvita välttämättä !!!
        for (int i = 0; i < lauta.getX(); i++) {
            for (int j = 0; j < lauta.getY(); j++) {
                if (viereistenMiinojenMaara[i][j] == 0 && miinat[i][j] == false) {
                    for (Ruutu ruutu : lauta.getRuutu(i, j).getViereisetRuudut()) {
                        ruudut[ruutu.getX()][ruutu.getY()] = true;
                        viereistenMiinojenMaara[i][j] = lauta.getRuutu(i, j).getViereistenMiinojenMaara();
                        //ruutu.setOnkoRuutuAvattu(true); RUUTUUN KLIKKAUS
                    }
                }
            }
        }
    }

    private int laskeVieressaAvaamattomienRuutujenMaara(int x, int y) {
        //vertaa tiedettyjen ruutujen miinojen määrään
        int avattujenRuutujenMaara = 0;
        for (Ruutu ruutu : lauta.getRuutu(x, y).getViereisetRuudut()) {
            if (ruutu.getOnkoRuutuAvattu() == true) {
                avattujenRuutujenMaara++;
            }
        }
        return lauta.getRuutu(x, y).getViereisetRuudut().size() - avattujenRuutujenMaara;
    }

    private ArrayList<Ruutu> palautaAvaamattomatViereisetRuudut(int x, int y) {
        //vertaa tiedettyjen ruutujen miinojen määrään
        ArrayList<Ruutu> list = new ArrayList<>();
        for (Ruutu ruutu : lauta.getRuutu(x, y).getViereisetRuudut()) {
            if (ruutu.getOnkoRuutuAvattu() == true) {
                list.add(ruutu);
            }
        }
        return list;
    }

    private void muutaviereisetRuutujenArvoYhdenPienemmäksi(int x, int y) {
        ArrayList<Ruutu> list = lauta.getRuutu(x, y).getViereisetRuudut();
        for (Ruutu ruutu : list) {
            viereistenMiinojenMaara[ruutu.getX()][ruutu.getY()] = viereistenMiinojenMaara[ruutu.getX()][ruutu.getY()] - 1;
            if (viereistenMiinojenMaara[ruutu.getX()][ruutu.getY()] - 1 == 0) {
                jonoSiirrettavista.push(ruutu);
                ruudut[ruutu.getX()][ruutu.getY()] = true;
            }
        }
    }
}
