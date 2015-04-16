package nimirum.miinaharava.logiikka;

import nimirum.miinaharavanratkaisija.dataStructures.ArrayList;

/**
 * Pelilaudan ruutu, mikä voi olla miina, tyhjä tai numero, joka kertoo vieressä
 * olevien miinojen määrän
 *
 * @author nimirum
 */
public class Ruutu {
    
    private int viereistenMiinojenMaara = 0; //8 lähintä ruutua
    private final int x;
    private final int y;
    private final int ruudunLeveys = 24;
    private final int ruudunKorkeus = 24;
    private ArrayList viereisetRuudut;
    private boolean onkoMiina = false;
    private boolean onkoRuutuAvattu = false;
    private boolean klikattuMiina = false;
    private boolean onkoLiputettu = false;

    /**
     *
     * Parametrit x ja y ovat ruudun koordinaatit
     *
     * @param x Leveys koordinaatti
     * @param y Korkeus koordinaatti
     */
    public Ruutu(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public void setViereisetRuudut(ArrayList<Ruutu> list) {
        this.viereisetRuudut = list;
    }
    
    public ArrayList<Ruutu> getViereisetRuudut() {
        return viereisetRuudut;
    }
    
    public void setOnkoRuudussaMiina(boolean onkoMiina) {
        this.onkoMiina = onkoMiina;
    }
    
    public boolean getOnkoRuutuAvattu() {
        return onkoRuutuAvattu;
    }

    /**
     * Asettaa ruudnu näkyvyysarvon Piirtäjää varten False arvolla piirtää
     * päällimmäisen ruudun True arvolla piirtää mitä ruutu on oikeasti
     *
     * @param onkoRuutuKlikattava
     */
    public void setOnkoRuutuAvattu(boolean onkoRuutuKlikattava) {
        this.onkoRuutuAvattu = onkoRuutuKlikattava;
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public boolean getOnkoRuudussaMiina() {
        return onkoMiina;
    }

    /**
     * Käy ruudun viereiset ruudut läpi ja lisää viereisten miinojen määrää, jos
     * itse ruudussa on miina
     */
    public void laskeNumerot() {
        ArrayList<Ruutu> ruudut = getViereisetRuudut();        
        for (int i = 0; i < ruudut.size(); i++) {
            Ruutu viereinen = ruudut.get(i);
            if (viereinen.onkoMiina) {
                addViereistenMiinojenMaaraa();
            }
        }
    }

    /**
     * Ruudulle asetetaan viereisten miinojen määrä, arvo voi olla väliltä 0-8
     *
     * @param viereistenMiinojenMaara
     */
    public void setViereistenMiinojenMaara(int viereistenMiinojenMaara) {
        if (0 <= viereistenMiinojenMaara && viereistenMiinojenMaara <= 8) {
            this.viereistenMiinojenMaara = viereistenMiinojenMaara;
        }
    }

    /**
     * Lisää viereisten miinojen määrää yhdellä
     */
    public void addViereistenMiinojenMaaraa() {
        viereistenMiinojenMaara++;
    }
    
    public int getViereistenMiinojenMaara() {
        return viereistenMiinojenMaara;
    }
    
    public boolean isKlikattuMiina() {
        return klikattuMiina;
    }

    /**
     * Piirtäjää varten mahdollista asettaa mikä on klikattu miina
     *
     * @param klikattuMiina
     */
    public void setKlikattuMiina(boolean klikattuMiina) {
        if (getOnkoRuudussaMiina()) {
            this.klikattuMiina = klikattuMiina;
        }
    }
    
    public int getRuudunKorkeus() {
        return ruudunKorkeus;
    }
    
    public int getRuudunLeveys() {
        return ruudunLeveys;
    }
    
    public boolean isOnkoRuutuLiputettu() {
        return onkoLiputettu;
    }

    /**
     * Ruudun voi liputtaa vain jos sitä ei ole klikattu vielä
     *
     * @param onkoLiputettu
     */
    public void setOnkoRuutuLiputettu(boolean onkoLiputettu) {
        if (!getOnkoRuutuAvattu()) {
            this.onkoLiputettu = onkoLiputettu;
        }
    }

    /**
     * Avaa kaikki tyhjän ruudun vieressä olevat tyhjät ruudut ja viereiset
     * numeroruudut
     */
    public void avaaViereisetRuudut() {
        ArrayList<Ruutu> ruudut = getViereisetRuudut(); 
        for (int i = 0; i < ruudut.size(); i++) {
           Ruutu viereinen = ruudut.get(i);
            if (viereinen.onkoRuutuAvattu == false) {
                if (viereinen.getViereistenMiinojenMaara() == 0) {
                    viereinen.setOnkoRuutuAvattu(true);
                    viereinen.avaaViereisetRuudut();
                } else {
                    viereinen.setOnkoRuutuAvattu(true);
                }
            }
        }
    }

    @Override
    public String toString() {
        return this.x + ", " + this.y;
    }
    
    
}
