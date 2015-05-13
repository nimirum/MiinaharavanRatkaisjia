package nimirum.miinaharavanratkaisija;

import java.util.ArrayDeque;
import nimirum.miinaharava.logiikka.Pelilauta;
import nimirum.miinaharava.logiikka.Ruutu;
import nimirum.miinaharavanratkaisija.dataStructures.ArrayList;

/**
 * MiinaharavanRatkaisija ratkaisee pelilaudan siitä löytyvien tietojen
 * perusteella mikäli mahdollista ilman arvaamista.
 * @author nimirum
 */
public class MiinaharavanRatkaisija {

    private final Pelilauta lauta;
    private boolean ruudut[][]; //tieto avatuista(true) ja suljetuista(false) ruuduista
    private boolean miinat[][]; //tekoälyn laskemat paikat miinoille (true)=miina
    private int viereistenMiinojenMaara[][]; //avattujen ruutujen viereisten miinojen todellinen määrä ruuduissa
    private int eiLoydettyjenViereistenMiinojenMaara[][]; //kun määrä on 0 niin kaikki ruudun ympärillä olevat ruudut on turvallsta avata
    private ArrayDeque<Ruutu> jonoSiirrettavista;
    private int miinojenMaara; //voisi lisätä ratkaisumahdollisuuksia kun muutama ruutu enää avamaatta?
    public int laskuri;

    /**
     * 10x10 pelikenttä
     */
    public MiinaharavanRatkaisija() {
        this(30, 30);
        laskuri = 0;
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
        eiLoydettyjenViereistenMiinojenMaara = new int[x][y];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                eiLoydettyjenViereistenMiinojenMaara[i][j] = -1;
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
     * ja käy pelilaudan kaikki ruudut kerran läpi
     */
    public void ratkaisePelia() {
        paivitaTiedotPelikentasta();
        etsiVarmatMiinat();
        avaaNollat();
    }

    /**
     * Tulostaa ratkaisijan sen hetkiset tiedot pelikentästä
     */
    public void tulostaTiedot() {
        for (int j = 0; j < lauta.getY(); j++) {
            for (int i = 0; i < lauta.getX(); i++) {
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

    /**
     * Tulostaa ratkaisijan tiedot joiden perusteella se ratkaisee pelilautaa
     */
    public void tulostaRatkaisuTiedot() {
        System.out.println("----");
        for (int j = 0; j < lauta.getY(); j++) {
            for (int i = 0; i < lauta.getX(); i++) {
                if (miinat[i][j] == true) {
                    System.out.print(" x");
                } else if (ruudut[i][j] == false) {
                    System.out.print(" A");
                } else {
                    System.out.print(" " + eiLoydettyjenViereistenMiinojenMaara[i][j]);
                }
            }
            System.out.print("\n");
        }
    }

    /**
     * Testejä varten
     *
     * @return pelilaudan ratkaisutiedot taulukossa
     */
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
     * @return pelilaudan ensimmäinen siirto
     */
    public Ruutu ensimmainenSiirto() {
        lauta.miinoita(lauta.getX() / 2, lauta.getY() / 2);
        ruudut[lauta.getX() / 2][lauta.getY() / 2] = true;
        viereistenMiinojenMaara[lauta.getX() / 2][lauta.getY() / 2] = lauta.getRuutu(lauta.getX() / 2, lauta.getY() / 2).getViereistenMiinojenMaara();
        eiLoydettyjenViereistenMiinojenMaara[lauta.getX() / 2][lauta.getY() / 2] = 0;
        return lauta.getRuutu(lauta.getX() / 2, lauta.getY() / 2);
    }

    public void nollaaJonoSiirroista() {
        jonoSiirrettavista = new ArrayDeque();
    }

    /**
     * Palauttaa jonosta yhden ratkaistun siirron
     *
     * @return yhden ratkaistavan siirron pelilaudalla
     */
    public Ruutu getYksiRatkaistuSiirto() {
        //jos siirto mahdollinen, palauttaa Ruutu johon siirto tapahtuu
        return jonoSiirrettavista.pollLast();
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
                        eiLoydettyjenViereistenMiinojenMaara[i][j] = laskeLoytymattomienViereistenMiinojenMaara(i, j);
                    }
                }
            }
        }
    }

    private void etsiVarmatMiinat() {
        for (int i = 0; i < lauta.getX(); i++) {
            for (int j = 0; j < lauta.getY(); j++) {
                //laskuri++;
                if (eiLoydettyjenViereistenMiinojenMaara[i][j] > 0) {
                    int avaamattomienRuutujenMaara = laskeVieressaAvaamattomienRuutujenMaara(i, j);
                    int viereistenMiinojenMaaraRuudussa = viereistenMiinojenMaara[i][j];
                    if (ruudut[i][j] == true && viereistenMiinojenMaaraRuudussa == avaamattomienRuutujenMaara) {
                        laskuri++;
                        //merkkaa varmat avaamattomat ruutu miinaksi, koska ne ei voi olla mitään muutakaan jos viereisiä miinoja on yhtäpaljon löytymättä
                        ArrayList<Ruutu> avaamattomatRuudut = getAvaamattomatViereisetRuudut(i, j);
                        for (int k = 0; k < avaamattomatRuudut.size(); k++) {
                            Ruutu ruutu = avaamattomatRuudut.get(k);
                            if (miinat[ruutu.getX()][ruutu.getY()] == false) {
                                miinat[ruutu.getX()][ruutu.getY()] = true;
                                ruutu.setOnkoRuutuLiputettu(true);
                                korjaaViereistenRuutujenLoydettyjenMiinojenMaara(ruutu.getX(), ruutu.getY());
                            }
                        }
                    }
                }
            }
        }
    }

    private void avaaNollat() {
        for (int i = 0; i < lauta.getX(); i++) {
            for (int j = 0; j < lauta.getY(); j++) {
                if (eiLoydettyjenViereistenMiinojenMaara[i][j] == 0) {
                    ArrayList<Ruutu> viereisetRuudut = lauta.getRuutu(i, j).getViereisetRuudut();
                    for (int k = 0; k < viereisetRuudut.size(); k++) {
                        Ruutu ruutu = viereisetRuudut.get(k);
                        if (lauta.getRuutu(ruutu.getX(), ruutu.getY()).getOnkoRuutuAvattu() == false && miinat[ruutu.getX()][ruutu.getY()] == false && ruudut[ruutu.getX()][ruutu.getY()] == false) {
                            ruudut[ruutu.getX()][ruutu.getY()] = true;
                            jonoSiirrettavista.push(ruutu);
                            viereistenMiinojenMaara[ruutu.getX()][ruutu.getY()] = lauta.getRuutu(ruutu.getX(), ruutu.getY()).getViereistenMiinojenMaara();
                            eiLoydettyjenViereistenMiinojenMaara[ruutu.getX()][ruutu.getY()] = laskeLoytymattomienViereistenMiinojenMaara(ruutu.getX(), ruutu.getY());
                        }
                    }
                }
            }
        }
    }

    private int laskeVieressaAvaamattomienRuutujenMaara(int x, int y) {
        int avattujenRuutujenMaara = 0;
        ArrayList<Ruutu> viereisetRuudut = lauta.getRuutu(x, y).getViereisetRuudut();
        for (int i = 0; i < viereisetRuudut.size(); i++) {
            Ruutu ruutu = viereisetRuudut.get(i);

            if (ruutu.getOnkoRuutuAvattu() == true) {
                avattujenRuutujenMaara++;
            }
        }
        return lauta.getRuutu(x, y).getViereisetRuudut().size() - avattujenRuutujenMaara;
    }

    private ArrayList<Ruutu> getAvaamattomatViereisetRuudut(int x, int y) {
        ArrayList<Ruutu> list = new ArrayList<>();
        ArrayList<Ruutu> viereisetRuudut = lauta.getRuutu(x, y).getViereisetRuudut();
        for (int i = 0; i < viereisetRuudut.size(); i++) {
            Ruutu ruutu = viereisetRuudut.get(i);
            if (ruutu.getOnkoRuutuAvattu() != true) {
                list.add(ruutu);
            }
        }
        return list;
    }

    private void korjaaViereistenRuutujenLoydettyjenMiinojenMaara(int x, int y) {
        ArrayList<Ruutu> list = lauta.getRuutu(x, y).getViereisetRuudut();
        for (int i = 0; i < list.size(); i++) {
            Ruutu ruutu = list.get(i);
            eiLoydettyjenViereistenMiinojenMaara[ruutu.getX()][ruutu.getY()] = laskeLoytymattomienViereistenMiinojenMaara(ruutu.getX(), ruutu.getY());
        }
    }

    private int laskeLoytymattomienViereistenMiinojenMaara(int x, int y) {
        int loydettyjenMiinojenMaara = 0;
        ArrayList<Ruutu> viereisetRuudut = lauta.getRuutu(x, y).getViereisetRuudut();
        for (int i = 0; i < viereisetRuudut.size(); i++) {
            Ruutu ruutu = viereisetRuudut.get(i);
            if (miinat[ruutu.getX()][ruutu.getY()] == true) {
                loydettyjenMiinojenMaara++;
            }
        }
        if (ruudut[x][y]) {
            return viereistenMiinojenMaara[x][y] - loydettyjenMiinojenMaara;
        } else {
            return viereistenMiinojenMaara[x][y];
        }
    }

    /**
     * Etsii 1-1, 1-2-1 ja 1-2-2-1 ratkaisuja pelilaudasta, koska muita siirtoja
     * ei löydy.
     */
    public void etsiLisaaRatkaisuja() {
        for (int i = 0; i < lauta.getX(); i++) {
            for (int j = 0; j < lauta.getY(); j++) {
                if (eiLoydettyjenViereistenMiinojenMaara[i][j] == 1) {
                    tarkistaOnkoVieressa121(i, j);
                    tarkistaOnkoVieressa1221(i, j);
                    tarkistaOnkoReunassaRatkaistava11(i, j);
                }
            }
        }
    }

    private void teeMuutokset121tapatauksessa(int i, int j, int iKasvu, int jKasvu) {
        miinat[i + 0 * iKasvu][j + 0 * jKasvu] = true;        // 1 2 1
        ruudut[i + 1 * iKasvu][j + 1 * jKasvu] = true;        // x o x 
        miinat[i + 2 * iKasvu][j + 2 * jKasvu] = true;
        lauta.getRuutu(i + 0 * iKasvu, j + 0 * jKasvu).setOnkoRuutuLiputettu(true);
        lauta.getRuutu(i + 2 * iKasvu, j + 2 * jKasvu).setOnkoRuutuLiputettu(true);
        viereistenMiinojenMaara[i + 1 * iKasvu][j + 1 * jKasvu] = lauta.getRuutu(i + 1 * iKasvu, j + 1 * jKasvu).getViereistenMiinojenMaara();
        korjaaViereistenRuutujenLoydettyjenMiinojenMaara(i + 0 * iKasvu, j + 0 * jKasvu);
        korjaaViereistenRuutujenLoydettyjenMiinojenMaara(i + 2 * iKasvu, j + 2 * jKasvu);
        Ruutu ruutu = lauta.getRuutu(i + 1 * iKasvu, j + 1 * jKasvu);
        jonoSiirrettavista.push(ruutu);
        System.out.println("121 ruutu:" + ruutu.getX() + ", " + ruutu.getY());
    }

    private void tarkistaOnkoVieressa121(int i, int j) {
        //parametrina tulee aina ruutu jonka vieressä on yksi löytymätön miina

        //etsii ruudusta oikealle
        if (i + 2 < lauta.getX() && eiLoydettyjenViereistenMiinojenMaara[i + 1][j] == 2 && eiLoydettyjenViereistenMiinojenMaara[i + 2][j] == 1) {
            //alaspäin
            if (j < lauta.getY() - 1 && ruudut[i][j + 1] == false && ruudut[i + 1][j + 1] == false && ruudut[i + 2][j + 1] == false) {
                teeMuutokset121tapatauksessa(i, j + 1, +1, 0);
            }
            //ylöspäin
            if (j > 0 && ruudut[i][j - 1] == false && ruudut[i + 1][j - 1] == false && ruudut[i + 2][j - 1] == false) {
                teeMuutokset121tapatauksessa(i, j - 1, +1, 0);
            }
        }
        //etsii ruudusta alaspäin
        if (j + 2 < lauta.getY() && eiLoydettyjenViereistenMiinojenMaara[i][j + 1] == 2 && eiLoydettyjenViereistenMiinojenMaara[i][j + 2] == 1) {
            //vasemmalle
            if (i > 0 && ruudut[i - 1][j] == false && ruudut[i - 1][j + 1] == false && ruudut[i - 1][j + 2] == false) {
                teeMuutokset121tapatauksessa(i - 1, j, 0, +1);
            }
            //oikealle
            if (i < lauta.getX() - 1 && ruudut[i + 1][j] == false && ruudut[i + 1][j + 1] == false && ruudut[i + 1][j + 2] == false) {
                teeMuutokset121tapatauksessa(i + 1, j, 0, +1);
            }
        }
    }

    private void teeMuutokset1221tapatauksessa(int i, int j, int iKasvu, int jKasvu) {
        ruudut[i + 0 * iKasvu][j + 0 * jKasvu] = true;        // 1 2 1
        miinat[i + 1 * iKasvu][j + 1 * jKasvu] = true;        // x o x 
        miinat[i + 2 * iKasvu][j + 2 * jKasvu] = true;
        ruudut[i + 3 * iKasvu][j + 3 * jKasvu] = true;
        lauta.getRuutu(i + 1 * iKasvu, j + 1 * jKasvu).setOnkoRuutuLiputettu(true);
        lauta.getRuutu(i + 2 * iKasvu, j + 2 * jKasvu).setOnkoRuutuLiputettu(true);
        viereistenMiinojenMaara[i + 0 * iKasvu][j + 0 * jKasvu] = lauta.getRuutu(i + 0 * iKasvu, j + 0 * jKasvu).getViereistenMiinojenMaara();
        viereistenMiinojenMaara[i + 3 * iKasvu][j + 3 * jKasvu] = lauta.getRuutu(i + 3 * iKasvu, j + 3 * jKasvu).getViereistenMiinojenMaara();
        korjaaViereistenRuutujenLoydettyjenMiinojenMaara(i + 1 * iKasvu, j + 1 * jKasvu);
        korjaaViereistenRuutujenLoydettyjenMiinojenMaara(i + 2 * iKasvu, j + 2 * jKasvu);
        Ruutu ruutu = lauta.getRuutu(i + 0 * iKasvu, j + 0 * jKasvu);
        Ruutu ruutu2 = lauta.getRuutu(i + 3 * iKasvu, j + 3 * jKasvu);
        jonoSiirrettavista.push(ruutu);
        jonoSiirrettavista.push(ruutu2);
        System.out.println("1221 ruudut:" + ruutu.toString() + " ja " + ruutu2.toString());
    }

    private void tarkistaOnkoVieressa1221(int i, int j) {
        //parametrina tulee aina ruutu jonka vieressä on yksi löytymätön miina
        //etsii ruudusta oikealle ----> x-akseli
        if (i + 3 < lauta.getX() && eiLoydettyjenViereistenMiinojenMaara[i + 1][j] == 2 && eiLoydettyjenViereistenMiinojenMaara[i + 2][j] == 2 && eiLoydettyjenViereistenMiinojenMaara[i + 3][j] == 1) {
            //alaspäin
            if (j < lauta.getY() - 1 && ruudut[i][j + 1] == false && ruudut[i + 1][j + 1] == false && ruudut[i + 2][j + 1] == false && ruudut[i + 3][j + 1] == false) {
                teeMuutokset1221tapatauksessa(i, j + 1, +1, 0);
            }
            //ylöspäin
            if (j > 0 && ruudut[i][j - 1] == false && ruudut[i + 1][j - 1] == false && ruudut[i + 2][j - 1] == false && ruudut[i + 3][j - 1] == false) {
                teeMuutokset1221tapatauksessa(i, j - 1, +1, 0);
            }
        }
        //etsii ruudusta ylöspäin y-akseli
        if (j - 3 >= 0 && eiLoydettyjenViereistenMiinojenMaara[i][j - 1] == 2 && eiLoydettyjenViereistenMiinojenMaara[i][j - 2] == 2 && eiLoydettyjenViereistenMiinojenMaara[i][j - 3] == 1) {
            //vasemmalle, onko avaamattomia ruutuja?
            if (i > 0 && ruudut[i - 1][j] == false && ruudut[i - 1][j - 1] == false && ruudut[i - 1][j - 2] == false) {
                teeMuutokset1221tapatauksessa(i - 1, j, 0, -1);
            }
            //oikealle
            if (i < lauta.getX() - 1 && ruudut[i + 1][j] == false && ruudut[i + 1][j - 1] == false && ruudut[i + 1][j - 2] == false) {
                teeMuutokset1221tapatauksessa(i + 1, j, 0, -1);
            }
        }
    }

    private void tarkistaOnkoReunassaRatkaistava11(int i, int j) {
        //Etsii  o o 1 tilanteita
        //       1 1 ? 

        //oikea reuna lauta
        if (i == lauta.getX() && eiLoydettyjenViereistenMiinojenMaara[i - 1][j] == 1) {
            // ylöspäin
            //alaspäin
        }
        //vasen reuna
        if (i == 0 && eiLoydettyjenViereistenMiinojenMaara[i + 1][j] == 1) {
            // ylöspäin
            //alaspäin
        }
        //ylhäältä päin alas
        if (j == 0 && eiLoydettyjenViereistenMiinojenMaara[i][j + 1] == 1) {
            //oikealle
            //vasemmalle
        }
        //alhaaltapäin ylös
        if (j == lauta.getY() && eiLoydettyjenViereistenMiinojenMaara[i][j - 1] == 1) {
            //oikealle
            //vasemmalle
        }
    }
}
