package nimirum.miinaharavanratkaisija;

import javax.swing.SwingUtilities;
import nimirum.miinaharava.gui.Kayttoliittyma;
import nimirum.miinaharava.logiikka.Ruutu;
import nimirum.miinaharavanratkaisija.dataStructures.ArrayDeque;

public class Main {

    public static void main(String[] args) {
         SwingUtilities.invokeLater((Runnable) new Kayttoliittyma());

    }

}
