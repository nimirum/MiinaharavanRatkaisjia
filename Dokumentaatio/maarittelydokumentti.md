# Määrittelydokumentti

MiinaharavanRatkaisija ratkaisee ohjelmoinnin harjoitustyönä tehdyn Miinaharavan pelin generoimia pelilautoja mikäli mahdollista. Tarkoituksena saada algoritmit löytämään ainakin kaikki varmat siirrot ja mikäli mahdollista niin arvaamaan todennäköisesti paras siirto.

##Toiminta
Ohjelma generoi itsensä sisällä aina uuden ratkaistavan pelilaudan käynnistettäessä. Pelilautojen luominen on jo valmiiksi aikaisemmasta projektista. Algoritmin pitäisi pystyä ratkaisemaan miinaharava aina kun se on mahdollista, joskus pelilauta generoi kenttiä, joissa on mahdottomia kohtia ratkaista muuten kuin arvaamalla 50% todennäköisyydellä kummassa ruudussa miina on. Miinaharavan ratkaisuun ei pitäisi tarvita kovin montaa tietorakennetta. Ainakin alustavasti ArrayListia ja enimmäkseen taulukoita.

##Aikavaativuus
Taulukoita ja ArrayListejä läpi käydessä aikavaativuudet ovat 1-3 silmukan kokoisia eli O(n)-O(n^3). Silmukoita ajetaan jokaisen ruudun läpi joten aikavaativuus riippuu pelilaudan koosta. Pelilauta on kaksiulotteinen, joten lopullinen aikavaativuus on vähintään O(n^2).

##Lähteet

http://stackoverflow.com/questions/1738128/minesweeper-solving-algorithm
https://robertmassaioli.wordpress.com/2013/01/12/solving-minesweeper-with-matricies/
https://luckytoilet.wordpress.com/2012/12/23/2125/