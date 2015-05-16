package nimirum.miinaharava.gui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.Timer;
import nimirum.miinaharava.logiikka.Pelilauta;
import nimirum.miinaharava.logiikka.Ruutu;
import nimirum.miinaharava.gui.kuuntelijat.KlikkaustenKuuntelija;
import nimirum.miinaharava.gui.kuuntelijat.NappuloidenKuuntelija;
import nimirum.miinaharavanratkaisija.MiinaharavanRatkaisija;

/**
 * Luokka hallitsee käyttöliittymän ikkunoita ja käynnistää pelin
 *
 * @author nimirum
 */
public class Kayttoliittyma implements Runnable {

    private JFrame frame;
    private Pelilauta miinaharava;
    private final int ruudunLeveys;
    private final int ruudunKorkeus;
    private Piirtaja piirtoalusta;
    private Sijainnit sijainnit;
    private RatkaisijanKomentaja komentaja;
    private Timer timer;
    private int x;
    private int y;

    /**
     * Kayttoliittyma luo 10x10 kokoisen Miinaharavan pelin
     * MiinaharvanRatkaisijalla
     *
     */
    public Kayttoliittyma() {
        MiinaharavanRatkaisija ratkaisija = new MiinaharavanRatkaisija(20, 15);
        komentaja = new RatkaisijanKomentaja(this, ratkaisija);

        this.miinaharava = ratkaisija.getLauta();
        ruudunLeveys = miinaharava.getRuutu(0, 0).getRuudunLeveys();
        ruudunKorkeus = miinaharava.getRuutu(0, 0).getRuudunKorkeus();
        sijainnit = new Sijainnit(miinaharava, this);
    }

    public Kayttoliittyma(int x, int y) {
        MiinaharavanRatkaisija ratkaisija = new MiinaharavanRatkaisija(x, y);
        this.x = x;
        this.y = y;
        komentaja = new RatkaisijanKomentaja(this, ratkaisija);

        this.miinaharava = ratkaisija.getLauta();
        ruudunLeveys = miinaharava.getRuutu(0, 0).getRuudunLeveys();
        ruudunKorkeus = miinaharava.getRuutu(0, 0).getRuudunKorkeus();
        sijainnit = new Sijainnit(miinaharava, this);
    }

    @Override
    public void run() {
        frame = new JFrame("Miinaharavainen");

        int leveys = ((miinaharava.getX()) * ruudunLeveys);
        int korkeus = ((miinaharava.getY()) * ruudunKorkeus + 60);
        //60 pikseliä tilaa kellolle ja pelin päättymis tiedoille

        frame.setResizable(false);
        frame.setVisible(true);
        centreWindow();

        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        });
        frame.setSize(new Dimension(leveys + frame.getInsets().left + frame.getInsets().right, korkeus + frame.getInsets().top + frame.getInsets().bottom));
        setIconImage();
        luoValikko();
        luoKomponentit(frame.getContentPane());
    }

    /**
     * Käynnistää miinaharavan ratkaisemisen
     */
    public void ratkaise() {
        int viive = 1; //millisekunteja

        ActionListener taskPerformer = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                komentaja.ratkaise();
            }
        };
        timer = new Timer(viive, taskPerformer);
        timer.start();

    }

    /**
     * Pysäyttää miinahararvan ratkaisemeisen.
     */
    public void stop() {
        timer.stop();
    }

    /**
     * Luo käyttöliittymään kuuluvat elementit, eli piirtoalustan
     *
     * @param container
     */
    public void luoKomponentit(Container container) {
        piirtoalusta = new Piirtaja(miinaharava);
        lisaaKuuntelija(piirtoalusta);
        container.add(piirtoalusta);
    }

    /**
     * Klikkaa parametrina annettua ruutua
     *
     * @param ruutu
     */
    public void klikkaaRuutua(Ruutu ruutu) {
        ArrayList<TapahtumaAlue> list = sijainnit.tapahtumaAlueet();
        for (TapahtumaAlue tapahtumaAlue : list) {
            tapahtumaAlue.alueeseenKlikattu(ruutu);
        }
        piirtoalusta.repaint();
    }

    private void lisaaKuuntelija(Piirtaja piirtaja) {
        KlikkaustenKuuntelija kuuntelija = new KlikkaustenKuuntelija(piirtaja, sijainnit.tapahtumaAlueet());
        piirtaja.addMouseListener(kuuntelija);
    }

    private void centreWindow() {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 3);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 3);
        frame.setLocation(x, y);
    }

    private void luoValikko() {
        JMenuBar valikko = new JMenuBar();
        frame.setJMenuBar(valikko);

        JMenuItem uusiPeli = new JMenuItem("Uusi peli");
        JMenuItem yksiSiirto = new JMenuItem("Ratkaise siirto");
        JMenuItem ratkaise = new JMenuItem("Ratkaise peli");
        valikko.add(uusiPeli);
        valikko.add(yksiSiirto);
        valikko.add(ratkaise);

        NappuloidenKuuntelija kuuntelija = new NappuloidenKuuntelija(this, miinaharava);
        uusiPeli.addActionListener(kuuntelija);
        yksiSiirto.addActionListener(kuuntelija);
        ratkaise.addActionListener(kuuntelija);
    }

    /**
     *
     * @return JFrame
     */
    public JFrame getFrame() {
        return frame;
    }

    private void setIconImage() {
        BufferedImage miinaRuutu = null;
        try {
            InputStream kuvaIcon = this.getClass().getClassLoader().getResourceAsStream("graphics/icon24x24.png");
            miinaRuutu = ImageIO.read(kuvaIcon);
        } catch (IOException ex) {
            System.out.println("Kuvien lataus epäonnistui");
        }
        frame.setIconImage(miinaRuutu);
    }

    /**
     * Luo uuden tyhjän Miinaharava pelin
     *
     * @param x
     * @param y
     */
    public void uusiPeli(int x, int y) {
        MiinaharavanRatkaisija ratkaisija = new MiinaharavanRatkaisija(this.x, this.y);
        komentaja = new RatkaisijanKomentaja(this, ratkaisija);
        this.miinaharava = ratkaisija.getLauta();
        sijainnit = new Sijainnit(miinaharava, this);

//        nimirum.miinaharavanratkaisija.dataStructures.ArrayList<Ruutu> miinat = new nimirum.miinaharavanratkaisija.dataStructures.ArrayList(3);
//        miinat.add(new Ruutu(0, 0));
//        miinat.add(new Ruutu(3, 0));
//        miinaharava.miinoita(miinat);
        Container c = frame.getContentPane();
        c.removeAll();
        luoKomponentit(c);
        frame.setVisible(true);
    }

    /**
     * Sulkee käyttöliittymän ikkunan
     */
    public void sulje() {
        frame.setVisible(false);
        frame.dispose();
    }

    public void ratkaiseYksiSiirto() {
        komentaja.ratkaiseYksiSiirto();
    }

}
