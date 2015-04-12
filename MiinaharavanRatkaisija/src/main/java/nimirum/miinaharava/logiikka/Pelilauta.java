package nimirum.miinaharava.logiikka;


import java.util.Random;
import nimirum.miinaharavanratkaisija.dataStructures.ArrayList;

/**
 * Muodostaa Pelilaudan, joka muodostuu ruuduista(x,y), miinoittaa ja laskee
 * numero arvot.
 *
 * @author nimirum
 */
public class Pelilauta {

    private final int x;
    private final int y;
    private int miinojenMaara;
    private final Ruutu pelilauta[][];
    private int klikatutRuudut = 0;
    private boolean miinoitettu = false;

    /**
     * Muodostaa pelilaudan johon kuuluu x*y määrä ruutuja
     *
     * @param x Pelilaudan leveys
     * @param y Pelilaudan korkeus
     */
    public Pelilauta(int x, int y) {
        this.x = x;
        this.y = y;
        pelilauta = new Ruutu[this.x][this.y];

        for (int i = 0; i < this.x; i++) {
            for (int j = 0; j < this.y; j++) {
                pelilauta[i][j] = new Ruutu(i, j);
            }
        }
        viereisetRuudut();
    }

    /**
     *
     * @param x Leveys koordinaatti
     * @param y Korkeus koordinaatti
     * @return Ruutu
     */
    public Ruutu getRuutu(int x, int y) {
        if (0 <= x && x < this.x && 0 <= y && y < this.y) {
            return pelilauta[x][y];
        }
        return null;
    }

    /**
     *
     * @return Pelilauta kaksiulotteisena taulukkona
     */
    public Ruutu[][] getPelilauta() {
        return pelilauta;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     * Asettaa pelilaudan ruudulle(x,y) miinan
     *
     * @param x leveys koordinaatti
     * @param y korkeus koordinaatti
     */
    public void setMiina(int x, int y) {
        Ruutu ruutu = getRuutu(x, y);
        ruutu.setOnkoRuudussaMiina(true);
    }

    /**
     * Lisää randomisti miinat ja lopuksi laskeNumerot()
     *
     * @param x
     * @param y
     */
    public void miinoita(int x, int y) {
        if (miinoitettu == false) {
            miinojenMaara = miinojenMaaraLaskuri();
            Ruutu ekaKlikkaus = getRuutu(x, y);

            Random random = new Random();
            int min = 0;
            int maxX = this.x - 1;
            int maxY = this.y - 1;

            int laskuri = 0;

            while (laskuri < miinojenMaara) {
                int randomNumX = random.nextInt((maxX - min) + 1) + min;
                int randomNumY = random.nextInt((maxY - min) + 1) + min;
                Ruutu ruutu = getRuutu(randomNumX, randomNumY);
                if (!ruutu.getOnkoRuudussaMiina() && ruutu.getX() != ekaKlikkaus.getX() && ruutu.getY() != ekaKlikkaus.getY() && viereisetRuudutEiMiinoitetaTarkitus(ruutu.getX(), ruutu.getY(), ekaKlikkaus)) {
                   // System.out.println("Miina: " + randomNumX + ", " + randomNumY);
                    ruutu.setOnkoRuudussaMiina(true);
                    laskuri++;
                }
            }
            laskeNumerot();
            miinoitettu = true;
        }
    }

    public void miinoita(ArrayList<Ruutu> miinat) {
        if (miinoitettu = false) {
            for (Ruutu ruutu : miinat) {
                ruutu.setOnkoRuudussaMiina(true);
            }
            miinoitettu = true;
        }
    }

    public boolean isMiinoitettu() {
        return miinoitettu;
    }

    private boolean viereisetRuudutEiMiinoitetaTarkitus(int x, int y, Ruutu ruutu) {
        ArrayList<Ruutu> ruudut = ruutu.getViereisetRuudut();
        for (int i = 0; i < ruudut.size(); i++) {
            Ruutu ruutuinen = ruudut.get(i);
            if (ruutuinen.getX() == x && ruutuinen.getY() == y) {
                return false;
            }
        }
        return true;
    }

    /**
     * Laskee numero arvot miinojen viereen, eli kuinka moneen miinaan kyseinen
     * ruutu koskee
     */
    public void laskeNumerot() {
        for (int i = 0; i < this.x; i++) {
            for (int j = 0; j < this.y; j++) {
                Ruutu ruutu = getRuutu(i, j);
                ruutu.laskeNumerot();
            }
        }
    }

    private int miinojenMaaraLaskuri() {
        return (int) (0.15 * this.x * this.y);
    }

    public int getMiinojenMaara() {
        return miinojenMaara;
    }

    private void viereisetRuudut() {
        ArrayList<Ruutu> viereiset = new ArrayList<>();
        for (int i = 0; i < this.x; i++) {
            for (int j = 0; j < this.y; j++) {
                Ruutu ruutu = getRuutu(i, j);
                if (getRuutu(i + 1, j) != null) {
                    viereiset.add(getRuutu(i + 1, j));
                }
                if (getRuutu(i + 1, j + 1) != null) {
                    viereiset.add(getRuutu(i + 1, j + 1));
                }
                if (getRuutu(i, j + 1) != null) {
                    viereiset.add(getRuutu(i, j + 1));
                }
                if (getRuutu(i, j - 1) != null) {
                    viereiset.add(getRuutu(i, j - 1));
                }
                if (getRuutu(i + 1, j - 1) != null) {
                    viereiset.add(getRuutu(i + 1, j - 1));
                }
                if (getRuutu(i - 1, j) != null) {
                    viereiset.add(getRuutu(i - 1, j));
                }
                if (getRuutu(i - 1, j + 1) != null) {
                    viereiset.add(getRuutu(i - 1, j + 1));
                }
                if (getRuutu(i - 1, j - 1) != null) {
                    viereiset.add(getRuutu(i - 1, j - 1));
                }
                ruutu.setViereisetRuudut(viereiset);
                viereiset = new ArrayList<>();
            }
        }
    }

    /**
     * Avaa kaikki ruudut, eli vaihtaa ne näkyviksi
     */
    public void avaaKaikkiRuudut() {
        for (int i = 0; i < this.x; i++) {
            for (int j = 0; j < this.y; j++) {
                Ruutu ruutu = getRuutu(i, j);
                ruutu.setOnkoRuutuAvattu(true);
            }
        }
    }

    public int getKlikatutRuudut() {
        return klikatutRuudut;
    }

    /**
     * Kertoo piirtäjälle mikä on klikattujen eli avattujen ruutujen määrä
     */
    public void paivitaKlikatutRuudut() {
        this.klikatutRuudut = 0;
        for (int i = 0; i < this.x; i++) {
            for (int j = 0; j < this.y; j++) {
                Ruutu ruutu = getRuutu(i, j);
                if (ruutu.getOnkoRuutuAvattu()) {
                    klikatutRuudut++;
                }
            }
        }
    }

    /**
     *
     * @param x klikatun ruudun x koordinaatti
     * @param y klikatun ruudun y koordinaatti
     */
    public void klikkausRuutuun(int x, int y) {
        Ruutu ruutu = getRuutu(x, y);
        if (!ruutu.getOnkoRuutuAvattu() && !ruutu.isOnkoRuutuLiputettu()) {

            ruutu.setOnkoRuutuAvattu(true);
            paivitaKlikatutRuudut();

            if (ruutu.getViereistenMiinojenMaara() == 0) {
                ruutu.avaaViereisetRuudut();
            }
        }
    }

    /**
     * Laskee klikatuista ruuduista onko peli voitettu
     *
     * @return Onko klikattuja ruutuja riittävästi pelin voittamiseen
     *
     */
    public boolean onkoPeliPaattynyt() {
        paivitaKlikatutRuudut();
        return (klikatutRuudut == (this.x * this.y - miinojenMaara));
    }

    @Override
    public String toString() {
        return getX() + "x" + getY();
    }
}
