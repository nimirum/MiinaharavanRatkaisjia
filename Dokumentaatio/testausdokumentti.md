#Testausdokumentti

##Ohjelman testaus
Ohjelma on testattu JUnit testeillä ja ne kaikki löytyvät projektin test kansiosta. Testauksen kattavuutta olen ajanut pit mutaatio testauksen, joka kertoo rivikattavuuden tällä hetkellä olevan 72% ja mutaatio kattavuus 55%.

##Algoritmin suorituskyky
Suorituskykyä varten on erikseen luokka nimeltä SuorituskykyTestaaja, sillä graafinen käyttöliittymä hidastaisi huomattavasti algoritmin suoritusaikaa. SuorituskykyTestaaja luo uusia pelilautoja niin kauan kunnes se osaa ratkaista pelilaudan ilman arvaamista ja ilmoittaa ratkaisuun menneen ajan sekunteina.

Ohessa tuloksia algoritmin nopeudesta pelilaudan kasvaessa. Algoritmin O(n^3) aikavaativuuden vuoksi ei ole yllättävää että suoritusaika alkaa kasvamaan exponentiaalisesti hyvin nopeasti. Jo 100x100 pelilaudalla laskuajat ovat n. 10 sekunnin paikkeilla.

| Pelilaudan koko | Aika(s) |
|:----------------|:--------|
| 10 x 10         | 0.01710 |
| 15 x 15         | 0.03396 |
| 20 x 20         | 0.02312 |
| 40 x 40         | 0.14799 |
| 50 x 50         | 0.43099 |
| 70 x 70         | 1.82081 |

##Havaitut bugit
Jos tokavika siirto osuu miinaan, peli luulee että peli voitettiin.
Joskus ei avaa kaikkia 1 ruudun vieressä olevia miinoja joita ei avattu
Jättää vikan 121 siirron tekemättä eikä sen jälkeen avaa sen vieressä olevia tyhjiä ruutuja? Varmaan löydettyjen miinojen tiedot ei päivity?

##Käyttöliittymä
Graafista käyttöliittymää on hyvin yksinkertaista testata. Siinä on kaksi nappulaa: "Uusi peli" ja "Ratkaise peliä". Uusi peli luo aina uuden pelin käyttöliittymään. Ratkaise peliä käynnistää MiinaharavanRatkaisijan missä tahansa vaiheessa peliä ja ratkaisijan jäädessään jumiin pelaamista voi jatkaa itse ja käynnistää ratkaisijan jälleen uudestaan. RatkaisijanKomentaja välittää ratkaisijan jonoon laittamat siirrot käyttöliittymälle. Liputtaminen on vain extra lisäinformaatiota ja havainnollistaa selkeämmin missä miinat ovat, mutta se ei ole välttämätöntä.
Graafisen käyttöliittymän rajoitteena on, että sen piirtämien ruutujen koko on 24x24 näytöllä, joten korkein mahdollinen pelilauta, joka mahtuu tietokoneen näytölle on 40 ruutua.