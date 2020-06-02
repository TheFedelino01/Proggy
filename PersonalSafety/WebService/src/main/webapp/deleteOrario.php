<?php
 if (session_status() == PHP_SESSION_NONE) {
	session_start();
}
if (!isset($_SESSION["username"])) {
	header("Location: index.php?err=Devi effettuare il login!");
	die();
}
include 'connection.php';


$id=$_GET['idOrario'];
$sql="DELETE FROM orari WHERE ID=$id;";
$result = $conn->query($sql);
header("location: visualizzaOrari.php");

?>