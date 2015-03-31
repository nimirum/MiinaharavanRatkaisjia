package nimirum.miinaharava.gui;

import java.util.ArrayList;
import nimirum.miinaharava.Pelilauta;

/**
 * Luo Tapahtumalueet ja niiden koordinaatit
 *
 * @author nimirum
 */
public class Sijainnit {

    private final Kayttoliittyma kayttoliittyma;
    private Pelilauta miinaharava;
    private final int ruudunLeveys;
    private final int ruudunKorkeus;

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
    }

    /**
     *
     * @return Lista tapahtuma-alueista
     */
    public ArrayList tapahtumaAlueet() {
        ArrayList<TapahtumaAlue> list = new ArrayList();
        for (int i = 0; i < miinaharava.getX() * ruudunLeveys; i = i + ruudunLeveys) {
            for (int j = 0; j < miinaharava.getY() * ruudunKorkeus; j = j + ruudunKorkeus) {
                TapahtumaAlue alue = new TapahtumaAlue(i, j, miinaharava.getRuutu(i / ruudunLeveys, j / ruudunKorkeus), miinaharava, kayttoliittyma);
                list.add(alue);
            }
        }
        return list;
    }

}
