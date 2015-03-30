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

    public MiinaharavanRatkaisija() {
        this(10, 10);
    }

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

    private void ratkaisePelia() {
        while (true) {
            etsiYkkosenKulmat();

        }
    }

    private void paivitaTiedotPelikentasta() {
        for (int i = 0; i < lauta.getX(); i++) {
            for (int j = 0; j < lauta.getY(); j++) {
                if (!lauta.getRuutu(i, j).getOnkoRuutuAvattu()) {
                    ruudut[i][j] = true;
                    viereistenMiinojenMaara[i][j] = lauta.getRuutu(i, j).getViereistenMiinojenMaara();
                }
            }

        }

    }

    private void etsiYkkosenKulmat() {
        for (int i = 0; i < lauta.getX(); i++) {
            for (int j = 0; j < lauta.getY(); j++) {
                if (viereistenMiinojenMaara[i][j] == 1) {
                    laskeVieressaAvaamattomienRuutujenMaara(i, j);
                }
            }
        }
    }

    private void laskeVieressaAvaamattomienRuutujenMaara(int x, int y) {
 //vertaa tiedettyjen ruutujen miinojen määrään
    }

//    private void viereisetRuudut() {
//        ArrayList<Ruutu> viereiset = new ArrayList<>();
//        for (int i = 0; i < this.x; i++) {
//            for (int j = 0; j < this.y; j++) {
//                Ruutu ruutu = getRuutu(i, j);
//                if (getRuutu(i + 1, j) != null) {
//                    viereiset.add(getRuutu(i + 1, j));
//                }
//                if (getRuutu(i + 1, j + 1) != null) {
//                    viereiset.add(getRuutu(i + 1, j + 1));
//                }
//                if (getRuutu(i, j + 1) != null) {
//                    viereiset.add(getRuutu(i, j + 1));
//                }
//                if (getRuutu(i, j - 1) != null) {
//                    viereiset.add(getRuutu(i, j - 1));
//                }
//                if (getRuutu(i + 1, j - 1) != null) {
//                    viereiset.add(getRuutu(i + 1, j - 1));
//                }
//                if (getRuutu(i - 1, j) != null) {
//                    viereiset.add(getRuutu(i - 1, j));
//                }
//                if (getRuutu(i - 1, j + 1) != null) {
//                    viereiset.add(getRuutu(i - 1, j + 1));
//                }
//                if (getRuutu(i - 1, j - 1) != null) {
//                    viereiset.add(getRuutu(i - 1, j - 1));
//                }
//                ruutu.setViereisetRuudut(viereiset);
//                viereiset = new ArrayList<>();
//            }
//        }
//    }
}
