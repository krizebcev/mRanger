<?php

function provjera_godisnjeg_doba($UlazniMjesec,$UlazniDan){

$Mjesec=$UlazniMjesec;
$Dan=$UlazniDan;

$Ispis;

if ($Mjesec==1)
{
	$Ispis="Godisnje doba je zima!"
}
else if ($Mjesec==2)
{
	$Ispis="Godisnje doba je zima!"
}
else if ($Mjesec==3 && $Dan<=20)
{
	$Ispis="Godisnje doba je zima!"
}
else if ($Mjesec==3 && $Dan>=21)
{
	$Ispis="Godisnje doba je proljece!"
}
else if ($Mjesec==4)
{
	$Ispis="Godisnje doba je proljece!"
}
else if ($Mjesec==5)
{
	$Ispis="Godisnje doba je proljece!"
}
else if ($Mjesec==6 && $Dan<=20)
{
	$Ispis="Godisnje doba je proljece!"
}
else if ($Mjesec==6 && $Dan>=21)
{
	$Ispis="Godisnje doba je ljeto!"
}
else if ($Mjesec==7)
{
	$Ispis="Godisnje doba je ljeto!"
}
else if ($Mjesec==8)
{
	$Ispis="Godisnje doba je ljeto!"
}
else if ($Mjesec==9 && $Dan<=22)
{
	$Ispis="Godisnje doba je jesen!"
}
else if ($Mjesec==9 && $Dan>=23)
{
	$Ispis="Godisnje doba je jesen!"
}
else if ($Mjesec==10)
{
	$Ispis="Godisnje doba je jesen!"
}
else if ($Mjesec==11)
{
	$Ispis="Godisnje doba je jesen!"
}
else if ($Mjesec==12 && $Dan<=20)
{
	$Ispis="Godisnje doba je jesen!"
}
else if ($Mjesec==12 && $Dan>=21)
{
	$Ispis="Godisnje doba je zima!"
}
return $Ispis;
}
?>