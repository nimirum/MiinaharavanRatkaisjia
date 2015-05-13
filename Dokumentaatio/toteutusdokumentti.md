#Toteutusdokumentti

##Yleisrakenne 
MiinaharavanRatkaisija laskee ja päivittää tietoja neljään erilaiseen taulukkoon. Ruudut taulukossa on tieto avatuista ruuduista, miinat taulukossa löydetyistä varmojen miinojen sijainneista, viereistenMiinojenMäärä kertoo avattujen ruutujen viereisen miinojen määrän ja loydettyjenViereistenMiinojenMaara sisältää lasketun tiedon kuinka moni ruudun ympärillä olevista miinoista on löydetty.
Ratkaisija tekee aina aloitussiirron keskelle pelilautaa, koska silloin on todennäköisintä saada mahdollisimman iso tyhjä alue avattua. 

##Algoritmin toiminta
Ratkaistessaan peliä päivittää tiedot pelilaudasta, eli hakee avonaisten ruutujen tiedot ja tallentaa ne ratkaisijan käyttöön.
Seuraavaksi ratkaisija etsii täysin varmoja miinoja, jolloin miina ei voi olla muualla kuin ainoassa tai ainoaissa avaamattomissa ruuduissa. Esimerkiksi ruudussa, jonka arvo on 1 ja sen vieressä on vain yksi avaamaton ruutu, ratkaisija tulkitsee avaamattoman ruudun tällöin miinaksi ja liputtaa sen. Miinojen löytyessä päivitetään eiLöydettyjenMiinojenmäärä taulukkoon laskemalla tieto löydetyistä miinoista. Kun tieto on nolla, se tarkoittaa sitä että avataanNollat metodi avaa kaikki nolla ruutujen vieressä olevat ruudut, koska ne ovat ilmiselvästi turvallisia avata. 

Kun algoritmi jää jumiin eikä löydä varmoja miinoja enää, niin sitten algoritmin viimeinen keino on vielä etsiä löytyisikö 11-, 121- ja 1221-tilanteita pelilaudalta, joiden ratkaisemisen jälkeen normaali tapa ratkaista peliä toimisi taas. Jos tämäkään keino ei toimi, algoritmini ei osaa ratkaista peliä ilman arvaamista.

##Aikavaativuudet
Ratkaisijan aikavaativuus on O(pelilaudanKorkeus x pelilaudanLeveys x ruudunViereisetRuudut), missä viereistenRuutujen määrä on maksimissaan 8kpl.

##Parannettavaa
MiinaharavanRatkaisijan suurin ongelma että ratkaisija metodit käyvät jokaisen ruudun läpi, vaikka se ei olisi tarpeellista. Jos olisi tiedossa esimerkiksi ArrayListissä kaikki ruudut mitä tarvitsee käydä läpi, raskaimmat metodit eli etsiVarmatMiinat() ja avaaNollat() muuttuisivat kevyemmiksi.
EtsiVarmatMiinat metodia kutsutaan 70 x 70 kokoisella pelilaudalla noin 15 000 000 kertaa, mutta tarvittavia muutoksia tehdään vain n. 620kpl. Metodia olisi siis mahdollista optimoida jopa 99% nopeammaksi, jos läpikäydyt ruudut olisi tiedossa eikä kaikkia ruutuja tulisi käytylä jok'ikisellä ratkaisukierroksella läpi. 
EtsiVarmatMiinatmetodi vie noin kolmanneksen ratkaisijan toiminta-ajasta. Aikaa voisi nipistää vähän myös lisäämällä taulukkoon tiedon avaamattomien ruutujen määrän, jotta sitä ei tarvitsisi laskea aina uudestaan tarvittaessa.
Miinojen määrää voisi myös hyödyntää viimeisien siirtojen yhteydessä.
Algoritmi ei osaa ratkaista kaikkia mahdollisia ratkaistavia tilanteita pelilaudalta, koska en itsekään osaa löytää niitä tai edes ymmärtää miten sellaisia tilanteita tunnistavan metodin tekisin. Algoritmin kehittämistä rajoittaa siis oma miinaharavan pelaamistaitoni. Toisaalta näiden tilanteiden määrä on minimaalinen ja algoritmini kattaa ylivoimaisesti yleisimmät tapaukset ja pystyykin parhaimmillaan ratkaisemaan 60% peleistä ilman arvaamista. Erityisesti nurkkiin muodostuu helposti 50/50 tilanteita, joissa informaatioita ei ole riittävästi miinojen sijaintien laskemiseen.