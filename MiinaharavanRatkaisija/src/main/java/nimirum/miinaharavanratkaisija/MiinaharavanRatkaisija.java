package nimirum.miinaharavanratkaisija;

import java.util.ArrayList;
import nimirum.miinaharava.Pelilauta;
import nimirum.miinaharava.Ruutu;

/**
 *
 * @author nimirum
 */
public class MiinaharavanRatkaisija {

    private final Pelilauta lauta;
    private boolean ruudut[][]; //tieto avatuista(true) ja suljetuista(false) ruuduista
    private boolean miinat[][]; //tekoälyn laskemat paikat miinoille (true)=miina
    private int viereistenMiinojenMaara[][]; //avattujen ruutujen viereisten miinojen määrä ruuduissa

    /**
     *
     */
    public MiinaharavanRatkaisija() {
        this(10, 10);
    }

    /**
     *
     * @param x
     * @param y
     */
    public MiinaharavanRatkaisija(int x, int y) {
        lauta = new Pelilauta(x, y);

        ruudut = new boolean[x][y];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                ruudut[i][j] = false;
            } //kaikki ruudut aluksi suljettu
        }

        lauta.miinoita(x / 2, y / 2); // tekoölyn aloitusklikkaus aina keskelle, sen jälkeen tapahtuu miinoitus

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
    }

    /**
     *
     * @return
     */
    public Pelilauta getLauta() {
        return lauta;
    }

    private void ratkaisePelia() {
        while (true) {
            etsiTaysinVarmatMiinat();
            avaaNollat();

        }
    }

    private void paivitaTiedotPelikentasta() {
        for (int i = 0; i < lauta.getX(); i++) {
            for (int j = 0; j < lauta.getY(); j++) {
                if (!lauta.getRuutu(i, j).getOnkoRuutuAvattu()) {
                    ruudut[i][j] = true;
                }
            }

        }

    }

    private void etsiTaysinVarmatMiinat() {
        paivitaTiedotPelikentasta();
        for (int i = 0; i < lauta.getX(); i++) {
            for (int j = 0; j < lauta.getY(); j++) {
                if (viereistenMiinojenMaara[i][j] == 1) {
                    int vieressäAvaamattomienRuutujenMaara = laskeVieressaAvaamattomienRuutujenMaara(i, j);
                    if ((vieressäAvaamattomienRuutujenMaara == 1 || vieressäAvaamattomienRuutujenMaara == 2 || vieressäAvaamattomienRuutujenMaara == 3) && ruudut[i][j] == true) {
                        //merkkaa avaamattomat ruutu miinaksi
                        ArrayList<Ruutu> list = palautaAvaamattomatViereisetRuudut(i, j);
                        for (Ruutu ruutu : list) {
                            miinat[ruutu.getX()][ruutu.getY()] = true;
                            ruutu.setOnkoRuutuLiputettu(true); 
                            //Liputa ruutu
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
                if (viereistenMiinojenMaara[i][j] == 0 && miinat[i][j]==false) {
                    for (Ruutu ruutu : lauta.getRuutu(i, j).getViereisetRuudut()) {
                        ruudut[ruutu.getX()][ruutu.getY()] = true;
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
        }
    }
}
