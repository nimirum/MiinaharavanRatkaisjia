package nimirum.miinaharavanratkaisija;

import javax.swing.SwingUtilities;
import nimirum.miinaharava.gui.Kayttoliittyma;

public class Main {

    public static void main(String[] args) {
         SwingUtilities.invokeLater((Runnable) new Kayttoliittyma(20,17));
//        SuorituskykyTestaaja katis = new SuorituskykyTestaaja(3, 4);
//        katis.ratkaise100kertaa();


    }
}
