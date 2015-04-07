package nimirum.miinaharavanratkaisija;

import java.util.ArrayDeque;
import java.util.ArrayList;
import nimirum.miinaharava.Pelilauta;
import nimirum.miinaharava.Ruutu;

/**
 * MiinaharavanRatkaisija ratkaisee pelilaudan siitä löytyvien tietojen
 * perusteella mikäli mahdollista ilman arvaamista.
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
    private int miinojenMaara;

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
     * tieto vieressä olevien miinojen määrästä
     * loydettyjenViereistenMiinojenMaara[][] ylläpitää tietoa ruudun vierestä
     * löydettyjen miinojen määrästä, kun määrä on 0 niin kaikki ruudun
     * ympärillä olevat ruudut on turvallista avata
     *
     * @param x Pelilaudan leveys
     * @param y Pelilaudan korkeus
     */
    public MiinaharavanRatkaisija(int x, int y) {
        lauta = new Pelilauta(x, y);
        miinojenMaara = lauta.getMiinojenMaara();

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
     * Miinaharavan pelilauta
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
        etsiTaysinVarmatMiinat();
        avaaNollat();
    }

    /**
     * Tulostaa ratkaisijan sen hetkiset tiedot pelikentästä
     */
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
        tulostaRatkaisuTiedot();
    }

    /**
     * Tulostaa ratkaisijan tiedot joiden perusteella se ratkaisee pelilautaa
     */
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

    public String[][] ratkaisuTiedotTaulukossa() {
        String[][] tiedot = new String[lauta.getX()][lauta.getY()];
        for (int i = 0; i < lauta.getX(); i++) {
            for (int j = 0; j < lauta.getY(); j++) {
                if (miinat[i][j] == true) {
                    tiedot[i][j] = "x";
                } else if (ruudut[i][j] == false) {
                    tiedot[i][j] = "A";
                } else {
                    tiedot[i][j] = "" + viereistenMiinojenMaara[i][j];
                }
            }
        }
        return tiedot;
    }

    /**
     * Tekee pelilaudan ensimmäisen siiron, mikä on aina keskelle (Random
     * aloituskohta voisi olla myös mahdollinen) Pelilauta miinoitetaan
     * ensimmäisellä siirrolla
     *
     * @return
     */
    public Ruutu ensimmainenSiirto() {
        lauta.miinoita(lauta.getX() / 2, lauta.getY() / 2);
        ruudut[lauta.getX() / 2][lauta.getY() / 2] = true;
       viereistenMiinojenMaara[lauta.getX() / 2][lauta.getY() / 2] = lauta.getRuutu(lauta.getX()/2, lauta.getY()/2).getViereistenMiinojenMaara();
        return lauta.getRuutu(lauta.getX() / 2, lauta.getY() / 2);
    }

    /**
     * Palauttaa jonosta yhden ratkaistun siirron
     *
     * @return
     */
    public Ruutu getYksiRatkaistuSiirto() {
        //if siirto mahdollinen, palauta Ruutu johon siirto tapahtuu
        return jonoSiirrettavista.poll();
        //jos ei mahdollinen, return null;
    }

    /**
     * Lukee pelikentän läpi ja päivittää sieltä löytyvät tiedot
     */
    public void paivitaTiedotPelikentasta() {
        for (int i = 0; i < lauta.getX(); i++) {
            for (int j = 0; j < lauta.getY(); j++) {
                if (lauta.getRuutu(i, j).getOnkoRuutuAvattu() && ruudut[i][j] == false) {
                    ruudut[i][j] = true;
                    if (viereistenMiinojenMaara[i][j] == -1) {
                        viereistenMiinojenMaara[i][j] = lauta.getRuutu(i, j).getViereistenMiinojenMaara();
                        loydettyjenViereistenMiinojenMaara[i][j] = laskeLoydettyjenViereistenMiinojenMaara(i, j);
                    }
                }
            }
        }
    }

    void etsiTaysinVarmatMiinat() {
        for (int i = 0; i < lauta.getX(); i++) {
            for (int j = 0; j < lauta.getY(); j++) {

                int avaamattomatRuudut = laskeVieressaAvaamattomienRuutujenMaara(i, j);
                int viereistenMiinojenMaaraRuudussa = viereistenMiinojenMaara[i][j];
                if (ruudut[i][j] == true && viereistenMiinojenMaaraRuudussa == avaamattomatRuudut) {
                    //merkkaa varmat avaamattomat ruutu miinaksi, koska ne ei voi olla mitään muutakaan
                    ArrayList<Ruutu> list = palautaAvaamattomatViereisetRuudut(i, j);
                    for (Ruutu ruutu : list) {
                        // System.out.println("Miina: " + ruutu.getX() + ", " + ruutu.getY());
                        if (miinat[ruutu.getX()][ruutu.getY()] == false) {
                            miinat[ruutu.getX()][ruutu.getY()] = true;
                            ruutu.setOnkoRuutuLiputettu(true);
                            korjaaViereistenRuutujenLoydettyjenMiinojenMaara(ruutu.getX(), ruutu.getY());
                            //Ja myös avaa ne ruudut jos arvo 0
                        }
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
                        if (lauta.getRuutu(ruutu.getX(), ruutu.getY()).getOnkoRuutuAvattu() == false && miinat[ruutu.getX()][ruutu.getY()] == false && ruudut[ruutu.getX()][ruutu.getY()] == false) {
                            ruudut[ruutu.getX()][ruutu.getY()] = true;
                            //System.out.println("Avattu ruutu:" + ruutu.getX() + ", " + ruutu.getY());
                            jonoSiirrettavista.add(ruutu);
                            viereistenMiinojenMaara[ruutu.getX()][ruutu.getY()] = lauta.getRuutu(ruutu.getX(), ruutu.getY()).getViereistenMiinojenMaara();
                            loydettyjenViereistenMiinojenMaara[ruutu.getX()][ruutu.getY()] = laskeLoydettyjenViereistenMiinojenMaara(ruutu.getX(), ruutu.getY());
                        }
                    }
                }
            }
        }
    }

    private int laskeVieressaAvaamattomienRuutujenMaara(int x, int y) {
        int avattujenRuutujenMaara = 0;
        for (Ruutu ruutu : lauta.getRuutu(x, y).getViereisetRuudut()) {
            if (ruutu.getOnkoRuutuAvattu() == true) {
                avattujenRuutujenMaara++;
            }
        }
        return lauta.getRuutu(x, y).getViereisetRuudut().size() - avattujenRuutujenMaara;
    }

    private ArrayList<Ruutu> palautaAvaamattomatViereisetRuudut(int x, int y) {
        ArrayList<Ruutu> list = new ArrayList<>();
        for (Ruutu ruutu : lauta.getRuutu(x, y).getViereisetRuudut()) {
            if (ruutu.getOnkoRuutuAvattu() != true) {
                list.add(ruutu);
            }
        }
        return list;
    }

    private void korjaaViereistenRuutujenLoydettyjenMiinojenMaara(int x, int y) {
        ArrayList<Ruutu> list = lauta.getRuutu(x, y).getViereisetRuudut();
        for (Ruutu ruutu : list) {
            loydettyjenViereistenMiinojenMaara[x][y] = laskeLoydettyjenViereistenMiinojenMaara(x, y);
            loydettyjenViereistenMiinojenMaara[ruutu.getX()][ruutu.getY()] = laskeLoydettyjenViereistenMiinojenMaara(ruutu.getX(), ruutu.getY());
            if (loydettyjenViereistenMiinojenMaara[ruutu.getX()][ruutu.getY()] == 0 && miinat[ruutu.getX()][ruutu.getY()] == false && ruudut[ruutu.getX()][ruutu.getY()] == false) {
                jonoSiirrettavista.push(ruutu);
                ruudut[ruutu.getX()][ruutu.getY()] = true;
                viereistenMiinojenMaara[ruutu.getX()][ruutu.getY()] = lauta.getRuutu(ruutu.getX(), ruutu.getY()).getViereistenMiinojenMaara();
            }
        }
    }

    private int laskeLoydettyjenViereistenMiinojenMaara(int x, int y) {
        int loydettyjenMiinojenMaara = 0;
        for (Ruutu ruutu : lauta.getRuutu(x, y).getViereisetRuudut()) {
            if (miinat[ruutu.getX()][ruutu.getY()] == true) {
                loydettyjenMiinojenMaara++;
            }
        }
        return viereistenMiinojenMaara[x][y] - loydettyjenMiinojenMaara;
    }
}
