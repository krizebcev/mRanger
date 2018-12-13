<?php
include("baza.class.php");

$bp = new Baza();
$bp->spojiDB();

if(isset($_GET["temp"])){
	
	$temp = $_GET["temp"];
	$floatTemp = (float) $temp;
	
	$sql = "INSERT INTO temperatura(temperatura) VALUES($floatTemp)";
	$bp->selectDB($sql);
}
 
$bp->zatvoriDB();

?>