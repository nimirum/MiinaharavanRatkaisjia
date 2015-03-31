package nimirum.miinaharava.gui.kuuntelijat;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputAdapter;
import nimirum.miinaharava.gui.Piirtaja;
import nimirum.miinaharava.gui.TapahtumaAlue;

/**
 * Kuuntelee käyttäjän hiirenklikkauksia tapahtumaAlueiden päällä
 *
 * @author nimirum
 */
public class KlikkaustenKuuntelija extends MouseInputAdapter {

    private final Piirtaja piirtaja;
    private final ArrayList<TapahtumaAlue> tapahtumaalueet;

    /**
     * Konstruktori
     *
     * @param piirtaja
     * @param tapahtumaAlueet
     */
    public KlikkaustenKuuntelija(Piirtaja piirtaja, ArrayList tapahtumaAlueet) {
        this.piirtaja = piirtaja;
        tapahtumaalueet = tapahtumaAlueet;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            for (TapahtumaAlue alue : tapahtumaalueet) {
                alue.alueeseenKlikattu(e.getX(), e.getY());
            }
        }
        if (SwingUtilities.isRightMouseButton(e)) {
            for (TapahtumaAlue alue : tapahtumaalueet) {
                alue.alueenLiputus(e.getX(), e.getY());
            }
        }
        piirtaja.repaint();
    }

}


