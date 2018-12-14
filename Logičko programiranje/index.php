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
    $podaciDeset = array("datum" => $vrijeme, "temperatura" => $temperatura);
    array_push($podaciVelikiDeset, $podaciDeset);
}

$rez;
foreach ($podaciVelikiJedan as &$podatak) {
    $podaciDatum = date_parse($podatak[0]);
    $podatakMjesec = $podaciDatum[month];
    $podatakDan = $podaciDatum[day];
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
        
        <script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.3.1.min.js"></script>
        <script type="text/javascript" src="//translate.google.com/translate_a/element.js?cb=googleTranslateElementInit"></script>
        <script src="https://canvasjs.com/assets/script/canvasjs.min.js"> </script>
    </head>
    <body onload="crtaj()">
        <h1 class="h1Pocetna">mRanger temperatura</h1>
        
        <br><br>
        <p style="text-align: center; font-size: 24px; color:white;">
        <?php
            echo "Posljedna očitana temperatura na robotiću je bila ".$podaciJedan[0]." i iznosi ".$podaciJedan[1]." °C.<br><br>";
            echo $rez;
        ?>
        </p>

        <br><br>
        <div id="chartContainer" style="height: 370px; width: 100%;"></div>
        <br><br>

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
    <script type="text/javascript">
        function crtaj(){
            var rezultat = <?php echo json_encode($podaciVelikiDeset, JSON_HEX_TAG); ?>;
            
            var mojiPodaci = [];
            for (var i = 0; i < rezultat.length;i++){
                    mojiPodaci.push({"label":rezultat[i].datum, "y":parseFloat(rezultat[i].temperatura)});
                }
        
            var chart = new CanvasJS.Chart("chartContainer", {
                animationEnabled:true,
                zoomEnabled:true,
                panEnabled:true,
                
                theme: "light1",
                title:{
                    text: "Statistika zadnjih deset temperatura"
                },
                axisY:{
                    title: "Temperatura"
                },
                axisX:{
                    title: "Vrijeme"
                },
                data:[{
                        type: "column",
                        dataPoints: mojiPodaci
                }]
            });
            chart.render();
        }
    </script>
</html>
