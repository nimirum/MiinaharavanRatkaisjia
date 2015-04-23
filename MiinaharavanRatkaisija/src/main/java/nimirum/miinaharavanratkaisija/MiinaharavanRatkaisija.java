package nimirum.miinaharavanratkaisija;

import java.util.ArrayDeque;
import nimirum.miinaharava.logiikka.Pelilauta;
import nimirum.miinaharava.logiikka.Ruutu;
import nimirum.miinaharavanratkaisija.dataStructures.ArrayList;

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
    private int eiLoydettyjenViereistenMiinojenMaara[][]; //kun määrä on 0 niin kaikki ruudun ympärillä olevat ruudut on turvallsta avata
    private ArrayDeque<Ruutu> jonoSiirrettavista;
    private int miinojenMaara; //voisi lisätä ratkaisumahdollisuuksia kun muutama ruutu enää avamaatta?

    /**
     * 10x10 pelikenttä
     */
    public MiinaharavanRatkaisija() {
        this(60, 40);
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
        //  tulostaRatkaisuTiedot();
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
        return lauta.getRuutu(lauta.getX() / 2, lauta.getY() / 2);
    }

    /**
     * Palauttaa jonosta yhden ratkaistun siirron
     *
     * @return yhden ratkaistavan siirron pelilaudalla
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
                        eiLoydettyjenViereistenMiinojenMaara[i][j] = laskeLoytymattomienViereistenMiinojenMaara(i, j);
                    }
                }
            }
        }
    }

    private void etsiTaysinVarmatMiinat() {
        for (int i = 0; i < lauta.getX(); i++) {
            for (int j = 0; j < lauta.getY(); j++) {
                int avaamattomienRuutujenMaara = laskeVieressaAvaamattomienRuutujenMaara(i, j);
                int viereistenMiinojenMaaraRuudussa = viereistenMiinojenMaara[i][j];
                if (ruudut[i][j] == true && viereistenMiinojenMaaraRuudussa == avaamattomienRuutujenMaara) {
                    //merkkaa varmat avaamattomat ruutu miinaksi, koska ne ei voi olla mitään muutakaan jos viereisiä miinoja on yhtäpaljon löytymättä
                    ArrayList<Ruutu> avaamattomatRuudut = palautaAvaamattomatViereisetRuudut(i, j);
                    for (int k = 0; k < avaamattomatRuudut.size(); k++) {
                        Ruutu ruutu = avaamattomatRuudut.get(k);
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

    private ArrayList<Ruutu> palautaAvaamattomatViereisetRuudut(int x, int y) {
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
            //Kun vieressä ei ole enää löydettäviä miinoja, ruutu on turvallista avaa
            if (eiLoydettyjenViereistenMiinojenMaara[ruutu.getX()][ruutu.getY()] == 0 && miinat[ruutu.getX()][ruutu.getY()] == false && ruudut[ruutu.getX()][ruutu.getY()] == false) {
                jonoSiirrettavista.push(ruutu);
                ruudut[ruutu.getX()][ruutu.getY()] = true;
                viereistenMiinojenMaara[ruutu.getX()][ruutu.getY()] = lauta.getRuutu(ruutu.getX(), ruutu.getY()).getViereistenMiinojenMaara();
            }
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
        return viereistenMiinojenMaara[x][y] - loydettyjenMiinojenMaara;
    }

    /**
     * Etsii 1-2-1 ja 1-2-2-1 ratkaisuja pelilaudasta, koska muita siirtoja ei
     * löydy.
     */
    public void etsiLisaaRatkaisuja() {
        for (int i = 0; i < lauta.getX(); i++) {
            for (int j = 0; j < lauta.getY(); j++) {
                if (eiLoydettyjenViereistenMiinojenMaara[i][j] == 1) {
                    tarkistaOnkoVieressa121tai1221(i, j);
                }
            }
        }
    }

    private void tarkistaOnkoVieressa121tai1221(int i, int j) {
        //parametrina tulee aina ruutu jonka vieressä on yksi löytymätön miina

        //etsii ruudusta vasemmalle
        if (i - 2 >= 0 && eiLoydettyjenViereistenMiinojenMaara[i - 1][j] == 2 && eiLoydettyjenViereistenMiinojenMaara[i - 2][j] == 1) {
            //alaspäin, onko avaamattomia ruutuja?
            if (j < lauta.getY() - 1 && ruudut[i][j + 1] == false && ruudut[i - 1][j + 1] == false && ruudut[i - 2][j + 1] == false) {
                miinat[i][j + 1] = true;        // 1 2 1
                ruudut[i - 1][j + 1] = true;    // x o x 
                miinat[i - 2][j + 1] = true;
                lauta.getRuutu(i, j + 1).setOnkoRuutuLiputettu(true);
                lauta.getRuutu(i - 2, j + 1).setOnkoRuutuLiputettu(true);
                Ruutu ruutu = lauta.getRuutu(i - 1, j + 1);
                jonoSiirrettavista.push(ruutu);

            }
            //ylöspäin
            if (j > 0 && ruudut[i][j - 1] == false && ruudut[i - 1][j - 1] == false && ruudut[i - 2][j - 1] == false) {
                miinat[i][j - 1] = true;        // x o x
                ruudut[i - 1][j - 1] = true;    // 1 2 1 
                miinat[i - 2][j - 1] = true;
                lauta.getRuutu(i, j - 1).setOnkoRuutuLiputettu(true);
                lauta.getRuutu(i - 2, j - 1).setOnkoRuutuLiputettu(true);
                Ruutu ruutu = lauta.getRuutu(i - 1, j - 1);
                jonoSiirrettavista.push(ruutu);

            }
        }
        //etsii ruudusta oikealle
        if (i + 2 < lauta.getX() && eiLoydettyjenViereistenMiinojenMaara[i + 1][j] == 2 && eiLoydettyjenViereistenMiinojenMaara[i + 2][j] == 1) {
            //alaspäin
            if (j < lauta.getY() - 1 && ruudut[i][j + 1] == false && ruudut[i + 1][j + 1] == false && ruudut[i + 2][j + 1] == false) {
                miinat[i][j + 1] = true;        // 1 2 1
                ruudut[i + 1][j + 1] = true;    // x o x 
                miinat[i + 2][j + 1] = true;
                lauta.getRuutu(i, j + 1).setOnkoRuutuLiputettu(true);
                lauta.getRuutu(i + 2, j + 1).setOnkoRuutuLiputettu(true);
                Ruutu ruutu = lauta.getRuutu(i + 1, j + 1);
                jonoSiirrettavista.push(ruutu);

            }
            //ylöspäin
            if (j > 0 && ruudut[i][j - 1] == false && ruudut[i + 1][j - 1] == false && ruudut[i + 2][j - 1] == false) {
                miinat[i][j - 1] = true;        // x o x
                ruudut[i + 1][j - 1] = true;    // 1 2 1 
                miinat[i + 2][j - 1] = true;
                lauta.getRuutu(i, j - 1).setOnkoRuutuLiputettu(true);
                lauta.getRuutu(i + 2, j - 1).setOnkoRuutuLiputettu(true);
                Ruutu ruutu = lauta.getRuutu(i + 1, j - 1);
                jonoSiirrettavista.push(ruutu);

            }

        }
        //etsii ruudusta ylöspäin
        if (j - 2 >= 0 && eiLoydettyjenViereistenMiinojenMaara[i][j - 1] == 2 && eiLoydettyjenViereistenMiinojenMaara[i][j - 2] == 1) {
            //vasemmalle, onko avaamattomia ruutuja?
            if (i > 0 && ruudut[i - 1][j] == false && ruudut[i - 1][j - 1] == false && ruudut[i - 1][j - 2] == false) {
                miinat[i - 1][j] = true;        // x 1
                ruudut[i - 1][j - 1] = true;    // o 2 
                miinat[i - 1][j - 2] = true;    // x 1
                lauta.getRuutu(i - 1, j).setOnkoRuutuLiputettu(true);
                lauta.getRuutu(i - 1, j - 2).setOnkoRuutuLiputettu(true);
                Ruutu ruutu = lauta.getRuutu(i - 1, j - 1);
                jonoSiirrettavista.push(ruutu);

            }
            //oikealle
            if (i < lauta.getX() - 1 && ruudut[i + 1][j] == false && ruudut[i + 1][j - 1] == false && ruudut[i + 1][j - 2] == false) {
                miinat[i + 1][j] = true;        // 1 x
                ruudut[i + 1][j - 1] = true;    // 2 o 
                miinat[i + 1][j - 2] = true;    // 1 x
                lauta.getRuutu(i + 1, j).setOnkoRuutuLiputettu(true);
                lauta.getRuutu(i + 1, j - 2).setOnkoRuutuLiputettu(true);
                Ruutu ruutu = lauta.getRuutu(i + 1, j - 1);
                jonoSiirrettavista.push(ruutu);

            }
        }
        //etsii ruudusta alaspäin
        if (j + 2 < lauta.getY() && eiLoydettyjenViereistenMiinojenMaara[i][j + 1] == 2 && eiLoydettyjenViereistenMiinojenMaara[i][j + 2] == 1) {
            //vasemmalle
            if (i > 0 && ruudut[i - 1][j] == false && ruudut[i - 1][j + 1] == false && ruudut[i - 1][j + 2] == false) {
                miinat[i - 1][j] = true;        // x 1
                ruudut[i - 1][j + 1] = true;    // o 2 
                miinat[i - 1][j + 2] = true;    // x 1
                lauta.getRuutu(i - 1, j).setOnkoRuutuLiputettu(true);
                lauta.getRuutu(i - 1, j + 2).setOnkoRuutuLiputettu(true);
                Ruutu ruutu = lauta.getRuutu(i - 1, j - 1);
                jonoSiirrettavista.push(ruutu);

            }
            //oikealle
            if (i < lauta.getX() - 1 && ruudut[i + 1][j] == false && ruudut[i + 1][j + 1] == false && ruudut[i + 1][j + 2] == false) {
                miinat[i + 1][j] = true;        // 1 x
                ruudut[i + 1][j + 1] = true;    // 2 o 
                miinat[i + 1][j + 2] = true;    // 1 x
                lauta.getRuutu(i + 1, j).setOnkoRuutuLiputettu(true);
                lauta.getRuutu(i + 1, j + 2).setOnkoRuutuLiputettu(true);
                Ruutu ruutu = lauta.getRuutu(i + 1, j + 1);
                jonoSiirrettavista.push(ruutu);

            }
        }
    }

}
