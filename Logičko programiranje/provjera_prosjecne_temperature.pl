% Author: Jakov
% Date: 11.12.2018.

% Sljedeci dio koji mormao napraviti je za unesene podatke
% o mjesecu i vremenu u kojem je ocitana temperatura,
% odrediti da li je ta temperatura ispod ili iznad prosjeka
% za to doba dana.

provjera_prosjecne_temperature( UlazniMjesec, UlazniSat, UlaznaTemperatura ) :-
                      provjera_doba_dana( UlazniSat, IzlaznoDoba),
                      provjera_temperature( UlazniMjesec, IzlaznoDoba, UlaznaTemperatura, Ispis ),
                      write(Ispis).

% U ovom slucaju odredili smo da jutro traje od 6 do 12 h,
% dan od 12 do 20 h, te noc od 20h do 6h ujutro.
% Sukladno tome, napravljene su sljedece provjere koje
% ce vracati doba dana.

provjera_doba_dana( Sati, Doba ) :-
                    Sati >= 20,
                    Doba = noc.

provjera_doba_dana( Sati, Doba ) :-
                    Sati < 6,
                    Doba = noc.

provjera_doba_dana( Sati, Doba ) :-
                    Sati >= 6,
                    Sati < 12,
                    Doba = jutro.

provjera_doba_dana( Sati, Doba ) :-
                    Sati >= 12,
                    Sati < 20,
                    Doba = dan.
                    
% Mi smo izracunali neke cinjenice, u kojima samo za
% odredeni mjesec i njegovo doba dana odredili
% prosjecne temperature. Te cinjenice su naša baza znanja,
% te cemo ulaznu temperaturu usporedivati s njima.

% Cinjenice jos nazivamo bazom znanja,
% te su zapisane u sljedecem formatu:
% cinjenica(Mjesec, Doba, Temperatura).

cinjenica( 1, jutro, -4.8 ).
cinjenica( 1, dan, -1.1 ).
cinjenica( 1, noc, -8.5 ).

cinjenica( 2, jutro, 4 ).
cinjenica( 2, dan, 8.4 ).
cinjenica( 2, noc, -0.4 ).

cinjenica( 3, jutro, 9.3 ).
cinjenica( 3, dan, 15.9 ).
cinjenica( 3, noc, 2.6 ).

cinjenica( 4, jutro, 10.4 ).
cinjenica( 4, dan, 16.2 ).
cinjenica( 4, noc, 4.6 ).

cinjenica( 5, jutro, 15.8 ).
cinjenica( 5, dan, 22 ).
cinjenica( 5, noc, 9.5 ).

cinjenica( 6, jutro, 20.7 ).
cinjenica( 6, dan, 26.9 ).
cinjenica( 6, noc, 14.4 ).

cinjenica( 7, jutro, 21.8 ).
cinjenica( 7, dan, 28.4 ).
cinjenica( 7, noc, 15.1 ).

cinjenica( 8, jutro, 21.4 ).
cinjenica( 8, dan, 28 ).
cinjenica( 8, noc, 14.7 ).

cinjenica( 9, jutro, 14.2 ).
cinjenica( 9, dan, 18.5 ).
cinjenica( 9, noc, 9.8 ).

cinjenica( 10, jutro, 11.7 ).
cinjenica( 10, dan, 18.5 ).
cinjenica( 10, noc, 4.8 ).

cinjenica( 11, jutro, 5.6 ).
cinjenica( 11, dan, 10 ).
cinjenica( 11, noc, 1.2 ).

cinjenica( 12, jutro, 2.4 ).
cinjenica( 12, dan, 7.3 ).
cinjenica( 12, noc, -2.6 ).


% Sljedeca provjera provjerava podatke u našim cinjenicama poviše,
% te na temelju tih podatka odreduje da li je za ulazne podatke
% o mjesecu, dobu dana i zabilježenoj temperaturi, ta temperatura veca,
% manja ili jednaka onoj iz cinjenica za taj mjesec i to doba dana.

provjera_temperature( Mjesec, Doba, Temperatura, Ispis ) :-
                      cinjenica( Mjesec, Doba, ProsjecnaTemperatura ),
                      (
                                 Temperatura > ProsjecnaTemperatura ->
                                 Ispis = "Temperatura je veca od prosjeka za ovo doba dana u mjesecu."
                                 ;
                                 Temperatura < ProsjecnaTemperatura ->
                                 Ispis = "Temperatura je manja od prosjeka za ovo doba dana u mjesecu."
                                 ;
                                 Ispis = "Temperatura je prosjecna za ovo doba dana u mjesecu."
                      ).
                      
