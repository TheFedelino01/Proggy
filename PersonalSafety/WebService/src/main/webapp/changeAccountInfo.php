<?php

if (session_status() == PHP_SESSION_NONE) {
    session_start();
}
if(!isset($_SESSION["username"]) || isset($_SESSION["idAdmin"])){
	header("Location: index.php?err=Devi effettuare il login!");
	die();
}

include 'connection.php';

//Prendo id cartellaClinica del cliente
$sql = "SELECT * FROM cartellaClinica where idCliente=".$_SESSION["idCliente"];
$result = $conn->query($sql);
$row = $result->fetch_assoc();
$idCartella=$row["id"];

//Modifico la cartella dell'utente
$sql ="UPDATE `cartellaclinica` SET `allergie`='".$_POST["allergie"]."',`farmaci`='".$_POST["farmaci"]."',`gruppoSanguigno`='".$_POST["gruppo"]."',`note`='".$_POST["note"]."' WHERE id='$idCartella'";
$result = $conn->query($sql);

if ($result) {
	header("Location: account.php?ris=Informazioni dell'account modificate con successo");
}else{
	header("Location: account.php?ris=Impossibile modificate le impostazioni del tuo account!");
}

$conn->close();
?>
