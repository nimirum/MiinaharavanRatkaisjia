#Viikkoraporti 4

##ArrayList
Oma ArrayList tietorakenne on valmis, johon tarvitsi toteuttaa loppujen lopuksi vain muutaman metodin. Konstruktorin oletus taulukon koko on 8, mik� on maksimi m��r� ruutujen naapureita. Impelentoin kaikki Listin metodit ArrayListiin, mutta suurin osa ei niist� ei ole k�yt�ss�. Tarvittat metodit tehhty itse. ArrayListin luomisen, objektien lis�ys ja hakeminen. Objektien tyypiksi piti laittaa <Ruutu> jotta tietorakenne suostui toimimimaan. K�yt�nn�ss� oma ArrayListini on helpompi k�ytt�inen taulukko size muuttujan ansiosta, jolloin taulukon l�pik�ynti automaattisesti helpompaa eik� voi vahingossa lukea tyhji� arvoja.

##ArrayDeque
ArrayDequen toteutus valmiiksi. Jonon toteutus on selv�sti haastavampi kuin ArrayList, mutta loppujen lopuksi taulukkoon pohjautuva tietorakenne. Jono n�ytt�isi toimivan oikein, mutta en viel� k�yt� sit� ite ratkaisijassa ennen kuin toimivuus 100%.

##Algoritmin nopeuden testaus
Testaamista varten pit� tehd� oma luokka, sill� siirrot k�ytt�liittym�n kautta hidastaisivat aikaa. Tarkoituksena tutkia my�s miten kent�n koon kasvattaminen vaikuttaa ratkaisemisen nopeuteen.