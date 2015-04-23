#Viikkoraporti 4

##ArrayList
Oma ArrayList tietorakenne on valmis, johon tarvitsi toteuttaa loppujen lopuksi vain muutaman metodin. Konstruktorin oletus taulukon koko on 8, mikä on maksimimäärä ruutujen naapureita. Tarpeelliset metodit olivat vain lopulta ArrayListin luominen, objektien lisäys ja hakeminen. Objektien tyypiksi piti laittaa <Ruutu> jotta tietorakenne suostui toimimimaan. Käytännössä oma ArrayListini on helpompi käyttöinen taulukko size muuttujan ansiosta, jolloin taulukon läpikäynti automaattisesti helpompaa eikä voi vahingossa lukea tyhjiä arvoja tai saada nullPointerExceptioneita. ArrayListille tehtyjen testien kattavuus on myös hyvä.

##ArrayDeque
ArrayDequen toteutus valmiiksi. Jonon toteutus oli selvästi haastavampi kuin ArrayList, mutta loppujen lopuksi taulukkoon pohjautuva yksinkertainen tietorakenne. Jono näyttäisi toimivan oikein, mutta en vielä käytä sitä ite ratkaisijassa ennen kuin sen toimivuus 100%. Testejä jonkun verran, mutta niitä tulee vielä lisää.

##Algoritmin nopeuden testaus
Testaamista varten piti tehdä oma luokka, SuorituskyvynTestaaja, sillä siirrot käyttöliittymän kautta hidastaisivat huomattavasti suoritus aikaa. Tarkoituksena on tutkia myös miten kentän koon kasvattaminen vaikuttaa ratkaisemisen nopeuteen.