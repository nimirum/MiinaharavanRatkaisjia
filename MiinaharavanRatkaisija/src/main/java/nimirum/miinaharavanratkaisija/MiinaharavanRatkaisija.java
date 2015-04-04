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
    private int viereistenMiinojenMaara[][]; //avattujen ruutujen viereisten miinojen todellinen määrä ruuduissa
    private int loydettyjenViereistenMiinojenMaara[][]; //kun määrä on 0 niin kaikki ruudun ympärillä olevat ruudut on turvallsta avata
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
        loydettyjenViereistenMiinojenMaara = new int[x][y];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                loydettyjenViereistenMiinojenMaara[i][j] = -1;
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
        paivitaTiedotPelikentasta();
        avaaNollat();
        etsiTaysinVarmatMiinat();
        
    }

    public void tulostaTiedot() {
        for (int j = 0; j < lauta.getX(); j++) {
            for (int i = 0; i < lauta.getY(); i++) {
                if (miinat[i][j] == true) {
                    System.out.print(" x");
                } else if (ruudut[i][j] == false) {
                    System.out.print(" A");
                } else {
                    System.out.print(" " + viereistenMiinojenMaara[i][j]);
                }
            }
            System.out.print("\n");
        }
       // tulostaRatkaisuTiedot();
    }

    public void tulostaRatkaisuTiedot() {
        System.out.println("----");
        for (int j = 0; j < lauta.getX(); j++) {
            for (int i = 0; i < lauta.getY(); i++) {
                if (miinat[i][j] == true) {
                    System.out.print(" x");
                } else if (ruudut[i][j] == false) {
                    System.out.print(" A");
                } else {
                    System.out.print(" " + loydettyjenViereistenMiinojenMaara[i][j]);
                }
            }
            System.out.print("\n");
        }
    }

    public Ruutu ensimmainenSiirto() {
        lauta.miinoita(lauta.getX() / 2, lauta.getY() / 2); // tekoölyn aloitusklikkaus aina keskelle, sen jälkeen tapahtuu miinoitus
//        ruudut[lauta.getX() / 2][lauta.getY() / 2] = true;
//        paivitaTiedotPelikentasta();
        return lauta.getRuutu(lauta.getX() / 2, lauta.getY() / 2);
    }

    public Ruutu ratkaiseYksiSiirto() {
        //if siirto mahdollinen, palauta Ruutu johon siirto tapahtuu
        return jonoSiirrettavista.poll();
        //jos ei mahdollinen, return null;
    }

    public void paivitaTiedotPelikentasta() {
        //Käytetään vain ekalla kerralla ekan klikkauksen jälkeen
        for (int i = 0; i < lauta.getX(); i++) {
            for (int j = 0; j < lauta.getY(); j++) {
                if (lauta.getRuutu(i, j).getOnkoRuutuAvattu()) {
                    ruudut[i][j] = true;
                    if (viereistenMiinojenMaara[i][j] == -1) {
                        viereistenMiinojenMaara[i][j] = lauta.getRuutu(i, j).getViereistenMiinojenMaara();
                        loydettyjenViereistenMiinojenMaara[i][j] = viereistenMiinojenMaara[i][j];
                    }
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
                        // System.out.println("Miina: " + ruutu.getX() + ", " + ruutu.getY());
                        if (miinat[ruutu.getX()][ruutu.getY()] == false) {
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
    }

    private void avaaNollat() {
        for (int i = 0; i < lauta.getX(); i++) {
            for (int j = 0; j < lauta.getY(); j++) {
                if (loydettyjenViereistenMiinojenMaara[i][j] == 0) {
                    
                    for (Ruutu ruutu : lauta.getRuutu(i, j).getViereisetRuudut()) {
                        if (lauta.getRuutu(ruutu.getX(), ruutu.getY()).getOnkoRuutuAvattu() == false && miinat[ruutu.getX()][ruutu.getY()] == false) {
                            ruudut[ruutu.getX()][ruutu.getY()] = true;
                            System.out.println("Avattu ruutu:" + ruutu.getX() + ", " + ruutu.getY());
                            jonoSiirrettavista.add(ruutu);
                            viereistenMiinojenMaara[ruutu.getX()][ruutu.getY()] = lauta.getRuutu(ruutu.getX(), ruutu.getY()).getViereistenMiinojenMaara();
                        }
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
            if (ruutu.getOnkoRuutuAvattu() != true) {
                list.add(ruutu);
            }
        }
        return list;
    }

    private void muutaviereisetRuutujenArvoYhdenPienemmäksi(int x, int y) {
        ArrayList<Ruutu> list = lauta.getRuutu(x, y).getViereisetRuudut();
        for (Ruutu ruutu : list) {
            loydettyjenViereistenMiinojenMaara[ruutu.getX()][ruutu.getY()] = loydettyjenViereistenMiinojenMaara[ruutu.getX()][ruutu.getY()] - 1;
            if (loydettyjenViereistenMiinojenMaara[ruutu.getX()][ruutu.getY()] == 0 && miinat[ruutu.getX()][ruutu.getY()] == false && ruudut[ruutu.getX()][ruutu.getY()] == false) {
                jonoSiirrettavista.push(ruutu);
                ruudut[ruutu.getX()][ruutu.getY()] = true;
            }
        }
    }
}
