#Viikkoraporti 4

##ArrayList
Oma ArrayList tietorakenne on valmis, johon tarvitsi toteuttaa loppujen lopuksi vain muutaman metodin. Konstruktorin oletus taulukon koko on 8, mik� on maksimim��r� ruutujen naapureita. Tarpeelliset metodit olivat vain lopulta ArrayListin luominen, objektien lis�ys ja hakeminen. Objektien tyypiksi piti laittaa <Ruutu> jotta tietorakenne suostui toimimimaan. K�yt�nn�ss� oma ArrayListini on helpompi k�ytt�inen taulukko size muuttujan ansiosta, jolloin taulukon l�pik�ynti automaattisesti helpompaa eik� voi vahingossa lukea tyhji� arvoja tai saada nullPointerExceptioneita. ArrayListille tehtyjen testien kattavuus on my�s hyv�.

##ArrayDeque
ArrayDequen toteutus valmiiksi. Jonon toteutus oli selv�sti haastavampi kuin ArrayList, mutta loppujen lopuksi taulukkoon pohjautuva yksinkertainen tietorakenne. Jono n�ytt�isi toimivan oikein, mutta en viel� k�yt� sit� ite ratkaisijassa ennen kuin sen toimivuus 100%. Testej� jonkun verran, mutta niit� tulee viel� lis��.

##Algoritmin nopeuden testaus
Testaamista varten piti tehd� oma luokka, SuorituskyvynTestaaja, sill� siirrot k�ytt�liittym�n kautta hidastaisivat huomattavasti suoritus aikaa. Tarkoituksena on tutkia my�s miten kent�n koon kasvattaminen vaikuttaa ratkaisemisen nopeuteen.