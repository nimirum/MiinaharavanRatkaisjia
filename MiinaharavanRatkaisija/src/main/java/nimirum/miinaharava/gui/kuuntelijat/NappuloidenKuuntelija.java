package nimirum.miinaharava.gui.kuuntelijat;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import nimirum.miinaharava.Pelilauta;
import nimirum.miinaharava.gui.Kayttoliittyma;

/**
 * Kuuntelee käyttöliittymän menu nappuloiden (Uusipeli, Asetukset, Ennätykset)
 * klikkauksia ja toimii sen mukaan
 *
 * @author nimirum
 */
public class NappuloidenKuuntelija implements ActionListener {

    private final Kayttoliittyma kayttoliittyma;
    private final Pelilauta miinaharava;

    /**
     * Konstruktori
     *
     * @param kayttoliittyma
     * @param miinaharava
     */
    public NappuloidenKuuntelija(Kayttoliittyma kayttoliittyma, Pelilauta miinaharava) {
        this.kayttoliittyma = kayttoliittyma;
        this.miinaharava = miinaharava;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Uusi peli")) {
            kayttoliittyma.uusiPeli(this.miinaharava.getX(), this.miinaharava.getY());
        }
        if (e.getActionCommand().equals("Ratkaise peli")) {
           kayttoliittyma.ratkaise();
        }
        if (e.getActionCommand().equals("Ennätykset")) {
         //   kayttoliittyma.avaaEnnatykset();
        }
    }

}
