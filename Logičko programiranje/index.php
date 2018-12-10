<?php
require("baza.class.php");
error_reporting(E_ALL & ~E_NOTICE);

/*$db = new Baza; dohvacanje iz baze
$db->spojiDB();

$podaci = array();
            
$zadnjihDeset="SELECT vrijeme, temperatura FROM lp_test ORDER BY vrijeme DESC LIMIT 10;";
$rezultat = $db->selectDB($zadnjihDeset);

while (list($vrijeme, $temperatura) = $rezultat->fetch_array()) {
    $element = $vrijeme." - ".$temperatura;
    array_push($podaci, $element);
}            
$db->zatvoriDB();*/

$podaci = array
  (
  array("2018-12-12 18:55:13", 22),
  array("2018-10-12 18:55:16", 23),
  array("2018-12-21 18:55:19", 24),
  array("2018-7-12 18:55:21", 25)
  );

$rez;
foreach ($podaci as &$podatak) {
    $podaciDatum = date_parse($podatak[0]);
    $podatakMjesec = $podaciDatum[month];
    $podatakDan = $podaciDatum[day];
    $podatakSat = $podaciDatum[hour];
    $podatakTemperatura = $podatak[1];
    $cmd="C:\Users\krist\Downloads\swipl\bin\swipl.exe -f provjera_godisnjeg_doba.pl -g provjera_godisnjeg_doba($podatakMjesec,$podatakDan)";
    $rez .= shell_exec($cmd)."<br>";
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
