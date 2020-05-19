<?php
 if (session_status() == PHP_SESSION_NONE) {
	session_start();
}
if (!isset($_SESSION["username"])) {
	header("Location: index.php?err=Devi effettuare il login!");
	die();
}
include 'connection.php';
$ID=$_GET['idOrario'];
if(isset($_GET['idOrario'])&& $_SERVER['REQUEST_METHOD'] == 'GET'){
	$sql="SELECT oraInizio, oraFine, giorno, postiDisponibili, ID FROM orari WHERE id='$ID'";
	$result = $conn->query($sql);
	$row=$result->fetch_assoc();
}else{
	if(isset($_POST['inizio'])&&isset($_POST['fine'])&&isset($_POST['posti'])){
		$inizio=$_POST['inizio'];
		$fine=$_POST['fine'];
		$posti=$_POST['posti'];
		$sql="UPDATE orari SET oraInizio = '$inizio',oraFine = '$fine', postiDisponibili = '$posti' WHERE ID='$ID';";
		$result = $conn->query($sql);
		header("location: visualizzaOrari.php");
	}
}
?>

<html>
<body>
	<div align="center">
		<h1> Modifica orario </h1>
		</div>
	<form action="" method="post">
<label for="inizio">Orario di inizio</label>
<input type="time" name="inizio" value="<?php echo($row['oraInizio']); ?>">
<label for="fine">Orario di fine</label>
<input type="time" name="fine" value="<?php echo($row['oraFine']); ?>">
<label for="posti">Posti disponibili</label>
<input type="number" name="posti" value="<?php echo($row['postiDisponibili']); ?>">
<br><br>
<input type="submit" value="Invia">
<input type="reset" value="Cancella">
</form>
</body>
		
</html>
