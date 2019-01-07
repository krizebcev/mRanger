
provjera_godisnjeg_doba( UlazniMjesec, UlazniDan ) :-
                                godisnje_doba( UlazniMjesec, UlazniDan, Ispis ),
                                write( Ispis ).

%Slozeniji ali kraci nacin pronalazenja godisnjeg doba.
%Kod nizanja uvjeta u slozenom predikatu ',' (zarez) predstavlja
%logicko 'i', dok ';' (tocka zarez) predstavlja logicko 'ili'.
		   
godisnje_doba( Mjesec, Dan, Ispis):-

	% U prvom mjesecu je sigurno godisnje doba zima, tako da dan ne moramo provjeravat.
    Mjesec=1 -> Ispis="Godisnje doba je zima!";
	% U drugom mjesecu je sigurno godisnje doba zima, tako da dan ne moramo provjeravat.
    Mjesec=2 -> Ispis="Godisnje doba je zima!";
	% U trecem mjesecu ukoliko je dan veci ili jednak 21, pocinje proljece, tako da dan moramo provjeravat.
    Mjesec=3, Dan =< 20 -> Ispis="Godisnje doba je zima!";
	
	% U trecem mjesecu ukoliko je dan veci ili jednak 21, pocinje proljece, tako da dan moramo provjeravat.
    Mjesec=3, Dan >= 21 -> Ispis = "Godisnje doba je proljece!";    
    Mjesec=4 -> Ispis="Godisnje doba je proljece!";    % Ista logika slijedi u nastavku za sve ostale mjesece.
	Mjesec=5 -> Ispis="Godisnje doba je proljece!";
    Mjesec=6, Dan =< 20 -> Ispis = "Godisnje doba je proljece!";
	
    Mjesec=6, Dan >= 21 -> Ispis = "Godisnje doba je ljeto!";
    Mjesec=7 -> Ispis="Godisnje doba je ljeto!";
    Mjesec=8 -> Ispis="Godisnje doba je ljeto!";
    Mjesec=9, Dan =< 22 -> Ispis = "Godisnje doba je ljeto!";
	
    Mjesec=9, Dan >= 23 -> Ispis = "Godisnje doba je jesen!";
    Mjesec=10 -> Ispis="Godisnje doba je jesen!";
    Mjesec=11 -> Ispis="Godisnje doba je jesen!";
	Mjesec=12, Dan =< 20 -> Ispis = "Godisnje doba je jesen!";
	
    Mjesec=12, Dan >= 21 -> Ispis = "Godisnje doba je zima!".


/*
%Jednostavniji ali znatno duzi nacin pronalazenja godišnjeg doba
godisnje_doba( Mjesec, _, Ispis ) :-                    % U prvom mjesecu je sigurno godišnje doba zima,
               Mjesec = 1,                              % tako da dan ne moramo provjeravat.
               Ispis = "Godisnje doba je zima!".
               
godisnje_doba( Mjesec, _, Ispis ) :-                    % U drugom mjesecu je sigurno godišnje doba zima,
               Mjesec = 2,                              % tako da dan ne moramo provjeravat.
               Ispis = "Godisnje doba je zima!".
               
godisnje_doba( Mjesec, Dan, Ispis ) :-                    % U trecem mjesecu ukoliko je dan veci ili jednak 21,
               Mjesec = 3,                                % pocinje proljece, tako da dan moramo provjeravat.
               Dan =< 20,
               Ispis = "Godisnje doba je zima!".
               
godisnje_doba( Mjesec, Dan, Ispis ) :-                           % U trecem mjesecu ukoliko je dan veci ili jednak 21,
               Mjesec = 3,                                % pocinje proljece, tako da dan moramo provjeravat.
               Dan >= 21,
               Ispis = "Godisnje doba je proljece!".

godisnje_doba( Mjesec, _, Ispis) :-                             % Ista logika slijedi u nastavku.
               Mjesec = 4,
               Ispis = "Godisnje doba je proljece!".

godisnje_doba( Mjesec, _, Ispis ) :-
               Mjesec = 5,
               Ispis = "Godisnje doba je proljece!".

godisnje_doba( Mjesec, Dan, Ispis ) :-
               Mjesec = 6,
               Dan =< 20,
               Ispis = "Godisnje doba je proljece!".
               
godisnje_doba( Mjesec, Dan, Ispis ) :-
               Mjesec = 6,
               Dan >= 21,
               Ispis = "Godisnje doba je ljeto!".

godisnje_doba( Mjesec, _, Ispis ) :-
               Mjesec = 7,
               Ispis = "Godisnje doba je ljeto!".

godisnje_doba( Mjesec, _, Ispis ) :-
               Mjesec = 8,
               Ispis = "Godisnje doba je ljeto!".

godisnje_doba( Mjesec, Dan, Ispis ) :-
               Mjesec = 9,
               Dan =< 22,
               Ispis = "Godisnje doba je ljeto!".

godisnje_doba( Mjesec, Dan, Ispis ) :-
               Mjesec = 9,
               Dan >= 23,
               Ispis = "Godisnje doba je jesen!".

godisnje_doba( Mjesec, _, Ispis ) :-
               Mjesec = 10,
               Ispis = "Godisnje doba je jesen!".

godisnje_doba( Mjesec, _, Ispis ) :-
               Mjesec = 11,
               Ispis = "Godisnje doba je jesen!".

godisnje_doba( Mjesec, Dan, Ispis ) :-
               Mjesec = 12,
               Dan =< 20,
               Ispis = "Godisnje doba je jesen!".

godisnje_doba( Mjesec, Dan, Ispis ) :-
               Mjesec = 12,
               Dan >= 21,
               Ispis = "Godisnje doba je zima!".
*/