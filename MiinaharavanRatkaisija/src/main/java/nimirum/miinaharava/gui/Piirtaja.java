package nimirum.miinaharava.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.Timer;
import nimirum.miinaharava.logiikka.Pelilauta;
import nimirum.miinaharava.logiikka.Ruutu;

/**
 * Luokka piirtää pelikentän ja sen komponentit eli ruudut
 *
 * @author nimirun
 */
public class Piirtaja extends JPanel {

    private final KuvienLataaja kuvat;
    private final Pelilauta miinaharava;
    private boolean havitty;

    Piirtaja(Pelilauta miinaharava) {
        super.setBackground(Color.white);
        this.kuvat = new KuvienLataaja();
        havitty = false;
        this.miinaharava = miinaharava;
        int viive = 1; //millisekunteja
        ActionListener taskPerformer = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
            }
        };
        new Timer(viive, taskPerformer).start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        piirraRuudut(g);
    }

    /**
     * Piirtää kaikki pelilaudan ruudut .png kuvina
     *
     * @param g Graphics
     */
    public void piirraRuudut(Graphics g) {
        if (miinaharava.onkoPeliPaattynyt()) {
            piirraGameOver(g, "Voitto");
        }
        int ruudunKoko = miinaharava.getRuutu(0, 0).getRuudunKorkeus();

        for (int i = 0; i < miinaharava.getX() * ruudunKoko; i = i + ruudunKoko) {
            for (int j = 0; j < miinaharava.getY() * ruudunKoko; j = j + ruudunKoko) {
                Ruutu ruutu = miinaharava.getRuutu(i / ruudunKoko, j / ruudunKoko);
                Image kuva = kuvat.getImage("Tile");
                if (ruutu.getOnkoRuutuAvattu()) { //ruutu true
                    if (ruutu.getOnkoRuudussaMiina() && ruutu.isKlikattuMiina()) {
                        kuva = kuvat.getImage("BrokenMine");
                        miinaharava.avaaKaikkiRuudut();
                        piirraGameOver(g, "Havio");
                        havitty = true;
                    }
                    if (ruutu.getOnkoRuudussaMiina() && !ruutu.isKlikattuMiina()) {
                        kuva = kuvat.getImage("Mine");
                    }
                    if (!ruutu.getOnkoRuudussaMiina() && ruutu.getViereistenMiinojenMaara() > 0) {
                        kuva = kuvat.getImage(String.valueOf(ruutu.getViereistenMiinojenMaara()));
                    }
                    if (!ruutu.getOnkoRuudussaMiina() && ruutu.getViereistenMiinojenMaara() == 0) {
                        kuva = kuvat.getImage("Empty");
                    }
                } else { //ruutu true
                    if (!ruutu.isOnkoRuutuLiputettu()) {

                    }
                    if (!ruutu.isOnkoRuutuLiputettu()) {
                        kuva = kuvat.getImage("Tile");
                    } else {
                        kuva = kuvat.getImage("Flag");
                    }
                }
                g.drawImage(kuva, i, j, null);
            }
        }
    }

    private void piirraGameOver(Graphics g, String tilanne) {
        int leveys = (int) (0.3 * miinaharava.getX() * miinaharava.getRuutu(0, 0).getRuudunKorkeus());
        int korkeus = miinaharava.getY() * miinaharava.getRuutu(0, 0).getRuudunKorkeus();

        if (tilanne.equals("Voitto") && !havitty) {
            g.drawString("Voitit pelin!", leveys, korkeus + 24);
        }
        if (tilanne.equals("Havio")) {
            g.drawString("Hävisit pelin.", leveys, korkeus + 24);
        }
    }
}
