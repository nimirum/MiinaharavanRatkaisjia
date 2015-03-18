package nimirum.miinaharavanratkaisija;

import nimirum.miinaharava.Pelilauta;

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
        lauta.miinoita(x / 2, y / 2); // tekoölyn aloitusklikkaus aina keskelle
        ruudut = new boolean[x][y];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                ruudut[i][j] = false;
            }
        }
        miinat = new boolean[x][y];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                miinat[i][j] = false;
            }
        }
    }

}
