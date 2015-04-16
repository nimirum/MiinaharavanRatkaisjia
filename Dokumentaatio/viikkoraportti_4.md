#Viikkoraporti 4

##ArrayList
Oma ArrayList tietorakenne on valmis, johon tarvitsi toteuttaa loppujen lopuksi vain muutaman metodin. Konstruktorin oletus taulukon koko on 8, mikä on maksimi määrä ruutujen naapureita. Impelentoin kaikki Listin metodit ArrayListiin, mutta suurin osa ei niistä ei ole käytössä. Tarvittat metodit tehhty itse. ArrayListin luomisen, objektien lisäys ja hakeminen. Objektien tyypiksi piti laittaa <Ruutu> jotta tietorakenne suostui toimimimaan. Käytännössä oma ArrayListini on helpompi käyttöinen taulukko size muuttujan ansiosta, jolloin taulukon läpikäynti automaattisesti helpompaa eikä voi vahingossa lukea tyhjiä arvoja.

##ArrayDeque
ArrayDequen toteutus valmiiksi. Jonon toteutus on selvästi haastavampi kuin ArrayList, mutta loppujen lopuksi taulukkoon pohjautuva tietorakenne. Jono näyttäisi toimivan oikein, mutta en vielä käytä sitä ite ratkaisijassa ennen kuin toimivuus 100%.

##Algoritmin nopeuden testaus
Testaamista varten pitä tehdä oma luokka, sillä siirrot käyttöliittymän kautta hidastaisivat aikaa. Tarkoituksena tutkia myös miten kentän koon kasvattaminen vaikuttaa ratkaisemisen nopeuteen.