<?php
require("baza.class.php");
error_reporting(E_ALL & ~E_NOTICE);

$db = new Baza;
$db->spojiDB();

$podaciVelikiJedan = array ();
$podaciVelikiDeset = array ();
            
$zadnjihDeset="SELECT vrijeme, temperatura FROM temperatura ORDER BY vrijeme DESC LIMIT 10;";
$rezultatDeset = $db->selectDB($zadnjihDeset);

$zadnjihJedan="SELECT vrijeme, temperatura FROM temperatura ORDER BY vrijeme DESC LIMIT 1;";
$rezultatJedan = $db->selectDB($zadnjihJedan);

$db->zatvoriDB();

while (list($vrijeme, $temperatura) = $rezultatJedan->fetch_array()) 
{
    $podaciJedan = array();
    array_push($podaciJedan, $vrijeme);
    array_push($podaciJedan, $temperatura);
    array_push($podaciVelikiJedan, $podaciJedan);
}

while (list($vrijeme, $temperatura) = $rezultatDeset->fetch_array()) 
{
    $podaciDeset = array();
    array_push($podaciDeset, $vrijeme);
    array_push($podaciDeset, $temperatura);
    array_push($podaciVelikiDeset, $podaciDeset);
}

$rez;
foreach ($podaciVelikiJedan as &$podatak) {
    $podaciDatum = date_parse($podatak[0]);
    $podatakMjesec = $podaciDatum[month];
    $podatakDan = $podaciDatum[day];     //swipl -q -f provjera_godisnjeg_doba.pl -t "provjera_godisnjeg_doba(1,1)"
    $podatakSat = $podaciDatum[hour];
    $podatakTemperatura = $podatak[1];
    $cmd="swipl -q -f provjera_godisnjeg_doba.pl -t "
            . "\"provjera_godisnjeg_doba($podatakMjesec,$podatakDan)\"";
    $cmd2="swipl -q -f provjera_prosjecne_temperature.pl -t "
            . "\"provjera_prosjecne_temperature($podatakMjesec,$podatakSat,$podatakTemperatura)\"";
    $rez .= shell_exec($cmd)." ".shell_exec($cmd2)."<br>";
}
?>
<!DOCTYPE html>
<html>
    <head>
        <title>mRanger</title>
        <meta charset="UTF-8">
        <meta name="author" content="Jakov Kristović">
        <meta name="description" content="28.10.2018. 15:01">
        <link href="css/jkristovi.css" rel="stylesheet" type="text/css">
    </head>
    <body>
        <h1 class="h1Pocetna">mRanger temperatura</h1>
        
        <p style="text-align: center; font-size: 24px; color:white;">
        <?php 
            echo $rez;
        ?>
        </p>
        
        <section id="podnozje">
            <footer>
                <p class="footerIkone">
                    <a href="http://croatianmakers.hr/hr/stem-revolucija-u-zajednici/">
                        <img src="slike/STEM-logo.jpg" alt="STEM logo" class="STEMlogo"></a>
                </p>
                <h3 class="copyrightSTEM"><small>U suradnji sa: STEM revolucija u zajednici</small></h3>
                <h3 class="copyright"><small>&copy; 2018 mRanger Team</small></h3>
            </footer>
        </section>
    </body>
</html>
