# M��rittelydokumentti

MiinaharavanRatkaisija ratkaisee ohjelmoinnin harjoitusty�n� tehdyn Miinaharavan pelin generoimia pelilautoja mik�li mahdollista. Tarkoituksena saada algoritmit l�yt�m��n ainakin kaikki varmat siirrot ja mik�li mahdollista niin arvaamaan todenn�k�isesti paras siirto.

##Toiminta
Ohjelma generoi itsens� sis�ll� aina uuden ratkaistavan pelilaudan k�ynnistett�ess�. Pelilautojen luominen on jo valmiiksi aikaisemmasta projektista. Algoritmin pit�isi pysty� ratkaisemaan miinaharava aina kun se on mahdollista, joskus pelilauta generoi kentti�, joissa on mahdottomia kohtia ratkaista muuten kuin arvaamalla 50% todenn�k�isyydell� kummassa ruudussa miina on. Miinaharavan ratkaisuun ei pit�isi tarvita kovin montaa tietorakennetta. Ainakin alustavasti ArrayListia ja enimm�kseen taulukoita.

##Aikavaativuus
Taulukoita ja ArrayListej� l�pi k�ydess� aikavaativuudet ovat 1-3 silmukan kokoisia eli O(n)-O(n^3). Silmukoita ajetaan jokaisen ruudun l�pi joten aikavaativuus riippuu pelilaudan koosta. Pelilauta on kaksiulotteinen, joten lopullinen aikavaativuus on v�hint��n O(n^2).

##L�hteet

http://stackoverflow.com/questions/1738128/minesweeper-solving-algorithm
https://robertmassaioli.wordpress.com/2013/01/12/solving-minesweeper-with-matricies/
https://luckytoilet.wordpress.com/2012/12/23/2125/