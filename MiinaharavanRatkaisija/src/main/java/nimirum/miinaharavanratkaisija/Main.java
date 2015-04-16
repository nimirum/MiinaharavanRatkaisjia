package nimirum.miinaharavanratkaisija;

import javax.swing.SwingUtilities;
import nimirum.miinaharava.gui.Kayttoliittyma;

public class Main {

    public static void main(String[] args) {
         SwingUtilities.invokeLater((Runnable) new Kayttoliittyma());
//        SuorituskykyTestaaja katis = new SuorituskykyTestaaja();
//        katis.ratkaiseKunnesRatkaistu();
    }

}
