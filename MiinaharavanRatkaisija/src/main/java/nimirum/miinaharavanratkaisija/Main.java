package nimirum.miinaharavanratkaisija;

import javax.swing.SwingUtilities;
import nimirum.miinaharava.gui.Kayttoliittyma;

/**
 *
 * @author nimirum
 */
public class Main {
    
      public static void main(String[] args) {
          System.out.println("Toimii");
          SwingUtilities.invokeLater((Runnable) new Kayttoliittyma());
      }
    
}
