package nimirum.miinaharava.gui;

import java.util.ArrayList;
import nimirum.miinaharava.logiikka.Pelilauta;

/**
 * Luo Tapahtumalueet ja niiden koordinaatit
 *
 * @author nimirum
 */
public final class Sijainnit {

    private final Kayttoliittyma kayttoliittyma;
    private final Pelilauta miinaharava;
    private final int ruudunLeveys;
    private final int ruudunKorkeus;
    private ArrayList<TapahtumaAlue> list;

    /**
     * Konstruktori
     *
     * @param miinaharava
     * @param kayttoliittyma
     */
    public Sijainnit(Pelilauta miinaharava, Kayttoliittyma kayttoliittyma) {
        this.miinaharava = miinaharava;
        this.kayttoliittyma = kayttoliittyma;
        ruudunLeveys = miinaharava.getRuutu(0, 0).getRuudunLeveys();
        ruudunKorkeus = miinaharava.getRuutu(0, 0).getRuudunLeveys();
        luoTapahtumaAlueet();
    }

    /**
     *
     * @return Lista tapahtuma-alueista
     */
    public ArrayList<TapahtumaAlue> tapahtumaAlueet() {
        return this.list;
    }

    private void luoTapahtumaAlueet() {
        list = new ArrayList();
        for (int i = 0; i < miinaharava.getX() * ruudunLeveys; i = i + ruudunLeveys) {
            for (int j = 0; j < miinaharava.getY() * ruudunKorkeus; j = j + ruudunKorkeus) {
                TapahtumaAlue alue = new TapahtumaAlue(i, j, miinaharava.getRuutu(i / ruudunLeveys, j / ruudunKorkeus), miinaharava);
                list.add(alue);
            }
        }
    }
}
