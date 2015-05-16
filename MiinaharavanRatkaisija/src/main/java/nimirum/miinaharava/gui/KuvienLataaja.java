package nimirum.miinaharava.gui;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

/**
 * Lataa pelin kuvat, jotka ovat kokoa 24x24
 *
 * @author nimirum
 */
public class KuvienLataaja {

    private BufferedImage icon;
    private BufferedImage ulkoRuutu;
    private BufferedImage liputettuRuutu;
    private BufferedImage miinaRuutu;
    private BufferedImage miinaRuutuRikki;
    private BufferedImage numeroRuutuYksi;
    private BufferedImage numeroRuutuKaksi;
    private BufferedImage numeroRuutuKolme;
    private BufferedImage numeroRuutuNelja;
    private BufferedImage numeroRuutuViisi;
    private BufferedImage numeroRuutuKuusi;
    private BufferedImage numeroRuutuSeitseman;
    private BufferedImage numeroRuutuKahdeksan;
    private BufferedImage tyhjaRuutu;

    /**
     * Lataa kaikki ennalta mÃ¤Ã¤ritellyt kuvat kÃ¤ytettÃ¤vÃ¤ksi
     */
    public KuvienLataaja() {
        try {
            InputStream kuvaIcon = this.getClass().getClassLoader().getResourceAsStream("graphics/icon24x24.png");
            icon =  ImageIO.read(kuvaIcon);
            InputStream kuvaRuutu = this.getClass().getClassLoader().getResourceAsStream("graphics/tile24x24.png");
            ulkoRuutu =  ImageIO.read(kuvaRuutu);
            InputStream kuvaMiina = this.getClass().getClassLoader().getResourceAsStream("graphics/mine24x24.png");
            miinaRuutu = ImageIO.read(kuvaMiina);
            InputStream kuvaMiinaRikki = this.getClass().getClassLoader().getResourceAsStream("graphics/brokenMine24x24.png");
            miinaRuutuRikki = ImageIO.read(kuvaMiinaRikki);
            InputStream kuvaTyhja = this.getClass().getClassLoader().getResourceAsStream("graphics/empty24x24.png");
            tyhjaRuutu = ImageIO.read(kuvaTyhja);
            InputStream kuvaLippu = this.getClass().getClassLoader().getResourceAsStream("graphics/flagtile24x24.png");
            liputettuRuutu = ImageIO.read(kuvaLippu);
            InputStream kuva1 = this.getClass().getClassLoader().getResourceAsStream("graphics/one24x24.png");
            numeroRuutuYksi = ImageIO.read(kuva1);
            InputStream kuva2 = this.getClass().getClassLoader().getResourceAsStream("graphics/two24x24.png");
            numeroRuutuKaksi = ImageIO.read(kuva2);
            InputStream kuva3 = this.getClass().getClassLoader().getResourceAsStream("graphics/three24x24.png");
            numeroRuutuKolme = ImageIO.read(kuva3);
            InputStream kuva4 = this.getClass().getClassLoader().getResourceAsStream("graphics/four24x24.png");
            numeroRuutuNelja = ImageIO.read(kuva4);
            InputStream kuva5 = this.getClass().getClassLoader().getResourceAsStream("graphics/five24x24.png");
            numeroRuutuViisi = ImageIO.read(kuva5);
            InputStream kuva6 = this.getClass().getClassLoader().getResourceAsStream("graphics/six24x24.png");
            numeroRuutuKuusi = ImageIO.read(kuva6);
            InputStream kuva7 = this.getClass().getClassLoader().getResourceAsStream("graphics/seven24x24.png");
            numeroRuutuSeitseman = ImageIO.read(kuva7);
            InputStream kuva8 = this.getClass().getClassLoader().getResourceAsStream("graphics/eight24x24.png");
            numeroRuutuKahdeksan = ImageIO.read(kuva8);
        } catch (IOException ex) {
           System.out.println("Kuvien lataus epäonnistui");
        }
    }

    /**
     * Kuvien kutsumismetodi, palauttaa halutun kuvan
     *
     * @param name Halutunlaisen ruudun nimi
     * @return Pyydetty kuva
     */
    public BufferedImage getImage(String name) {
        switch (name) {
            case "Icon":
                return icon;
            case "Tile":
                return ulkoRuutu;
            case "Mine":
                return miinaRuutu;
            case "BrokenMine":
                return miinaRuutuRikki;
            case "Flag":
                return liputettuRuutu;
            case "Empty":
                return tyhjaRuutu;
            case "1":
                return numeroRuutuYksi;
            case "2":
                return numeroRuutuKaksi;
            case "3":
                return numeroRuutuKolme;
            case "4":
                return numeroRuutuNelja;
            case "5":
                return numeroRuutuViisi;
            case "6":
                return numeroRuutuKuusi;
            case "7":
                return numeroRuutuSeitseman;
            case "8":
                return numeroRuutuKahdeksan;
            default:
                return tyhjaRuutu;
        }

    }
}
