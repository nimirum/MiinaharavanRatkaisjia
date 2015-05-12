#Toteutusdokumentti

##Yleisrakenne 
MiinaharavanRatkaisija laskee ja päivittää tietoja neljään erilaiseen taulukkoon. Ruudut taulukossa on tieto avatuista ruuduista, miinat taulukossa löydetyistä varmojen miinojen sijainneista, viereistenMiinojenMäärä kertoo avattujen ruutujen viereisen miinojen määrän ja loydettyjenViereistenMiinojenMaara sisältää lasketun tiedon kuinka moni ruudun ympärillä olevista miinoista on löydetty.
Ratkaisija tekee aina aloitus siirron keskelle pelilautaa, päivittää tiedot pelilaudasta, etsii täysin varmat miinat, jolloin miina ei voi olla muualla kuin ainoassa tai ainoaissa avaamattomissa ruuduissa. Tämän jälkeen algortimi päivittää jälleen tietonsa ja niiden perusteella avaa kaikki turvalliset ruudut. Ratkaisija ei vielä yritä avata ruutuja todennäköisyyksien perusteella, jos varmoja ruutuja ei enää löydy. Miinojen määrää voisi myös hyödyntää viimeisien siirtojen yhteydessä.

##Aikavaativuudet
Ratkaisijan aikavaativuus on O(pelilaudanKorkeus x pelilaudanLeveys x ruudunViereisetRuudut), missä viereistenRuutujen määrä on maksimissaan 8kpl.

##Parannettavaa
MiinaharavanRatkaisijan suurin ongelma että ratkaisija metodit käyvät jokaisen ruudun läpi, vaikka se ei olisi tarpeellista. Jos olisi tiedossa esimerkiksi ArrayListissä kaikki ruudut mitä tarvitsee käydä läpi, raskaimmat metodit eli etsiVarmatMiinat() ja avaaNollat() muuttuisivat kevyemmiksi.
EtsiVarmatMiinat metodia kutsutaan 70 x 70 kokoisella pelilaudalla noin 15 000 000 kertaa, mutta tarvittavia muutoksia tehdään vain n. 620kpl. Metodia olisi siis mahdollista optimoida jopa 99% nopeammaksi, jos läpikäydyt ruudut olisi tiedossa eikä kaikkia ruutuja tulisi käytylä jok'ikisellä ratkaisukierroksella läpi. 
EtsiVarmatMiinatmetodi vie noin kolmanneksen ratkaisijan toiminta-ajasta. Aikaa voisi nipistää vähän myös lisäämällä taulukkoon tiedon avaamattomien ruutujen määrän, jotta sitä ei tarvitsisi laskea aina uudestaan tarvittaessa.
