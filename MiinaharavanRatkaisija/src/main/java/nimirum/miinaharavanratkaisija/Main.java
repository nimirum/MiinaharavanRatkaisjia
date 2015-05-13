package nimirum.miinaharavanratkaisija;

import javax.swing.SwingUtilities;
import nimirum.miinaharava.gui.Kayttoliittyma;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater((Runnable) new Kayttoliittyma(40,30));
//        SuorituskykyTestaaja katis = new SuorituskykyTestaaja(80,80);
//        katis.ratkaiseKunnesRatkaistu();
//        
    }
}
